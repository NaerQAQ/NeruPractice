package club.neru.player.accessservice.data;

import club.neru.basic.interfaces.ReflectCommandInterface;
import club.neru.io.file.impl.JsonManager;
import club.neru.io.file.interfaces.JsonPersistableInterface;
import club.neru.kit.interfaces.KitInventoryInterface;
import club.neru.kit.objects.KitInventory;
import club.neru.player.accessservice.AccessServiceHandler;
import club.neru.serialization.interfaces.SerializableInterface;
import club.neru.serialization.strategy.annotations.ExclusionField;
import club.neru.thread.Scheduler;
import club.neru.thread.enums.SchedulerExecutionMode;
import club.neru.thread.enums.SchedulerTypeEnum;
import com.google.gson.annotations.Expose;
import de.leonhard.storage.Json;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * 玩家进入服务数据。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/11
 */
@Getter
@Setter
@Accessors(chain = true)
public class AccessServiceData implements SerializableInterface, JsonPersistableInterface, ReflectCommandInterface {
    /**
     * 玩家进入时传送的坐标。
     */
    private Location location;

    /**
     * 玩家进入时加载的装备包库存名。
     */
    private String kitInventoryName;

    /**
     * 序列化后 Json 字符串所在的键值。
     */
    @ExclusionField
    private final String jsonKey =
            AccessServiceHandler.ACCESS_SERVICE_DATA_JSON_KEY;

    /**
     * 应用到某位玩家。
     *
     * @param player 玩家对象
     */
    public void apply(Player player) {
        KitInventory kitInventory = KitInventoryInterface
                .getKitInventory(kitInventoryName);

        // KitInventory#apply 方法内会进行线程安全处理
        if (kitInventory != null) {
            kitInventory.apply(player);
        }

        // 为了线程安全
        if (location != null) {
            new Scheduler()
                    .setSchedulerTypeEnum(SchedulerTypeEnum.RUN)
                    .setSchedulerExecutionMode(SchedulerExecutionMode.SYNC)
                    .setRunnable(() -> player.teleport(location))
                    .run();
        }
    }

    /**
     * 写入数据。
     *
     * <p>
     * 该操作为覆盖性操作，即无论是否存在，均直接写入。
     * </p>
     */
    @Override
    public void write() {
        write(toJson());
    }

    /**
     * 获取该服务的 {@link Json} 对象。
     *
     * @return {@link Json}
     */
    @Override
    public Json getJson() {
        JsonManager jsonManager = JsonManager.getInstance();

        return jsonManager.get(
                AccessServiceHandler.ACCESS_SERVICE_DATA_NAME,
                AccessServiceHandler.ACCESS_SERVICE_DATA_PATH,
                false
        );
    }
}
