package club.neru.kit.objects;

import club.neru.arena.interfaces.ArenaParentInterface;
import club.neru.arena.objects.ArenaParent;
import club.neru.basic.impl.ObjectNameImpl;
import club.neru.basic.interfaces.ReflectCommandInterface;
import club.neru.io.file.interfaces.JsonPersistableInterface;
import club.neru.kit.KitHandler;
import club.neru.kit.interfaces.KitInterfaces;
import club.neru.serialization.interfaces.SerializableInterface;
import club.neru.serialization.strategy.annotations.ExclusionField;
import de.leonhard.storage.Json;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Material;

import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

/**
 * 装备包对象。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/10
 */
@Getter
@Setter
@Accessors(chain = true)
public class Kit extends ObjectNameImpl implements KitInterfaces, SerializableInterface, ReflectCommandInterface, JsonPersistableInterface {
    /**
     * 序列化后 Json 字符串所在的键值。
     */
    @Getter
    @ExclusionField
    private final String jsonKey = KitHandler.KIT_JSON_KEY;

    /**
     * 是否允许建筑。
     */
    private boolean canBuild;

    /**
     * 此装备包可用的竞技场名称。
     */
    private ConcurrentLinkedQueue<String> availableArenaNames;

    /**
     * 此装备包玩家可破坏的竞技场方块类型。
     */
    private ConcurrentLinkedQueue<Material> destroyableArenaBlocks;

    /**
     * 对应装备包库存名称，
     */
    private String kitInventoryName;

    /**
     * 添加此装备包可用的竞技场。
     *
     * @param arenaName 竞技场名称
     * @return {@link Kit}
     */
    public Kit addAvailableArenaName(String arenaName) {
        availableArenaNames.add(arenaName);
        return this;
    }

    /**
     * 获取此装备包可用的竞技场。
     *
     * @return 此装备包可用的 {@link ArenaParent} 对象
     */
    public ConcurrentLinkedQueue<ArenaParent> getAvailableArenas() {
        return availableArenaNames.stream()
                .map(ArenaParentInterface::getArenaParent)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(ConcurrentLinkedQueue::new));
    }

    /**
     * 添加此装备包可破坏的竞技场方块类型。
     *
     * @param material 方块类型
     * @return {@link Kit}
     */
    public Kit addDestroyableArenaBlocks(Material material) {
        destroyableArenaBlocks.add(material);
        return this;
    }

    /**
     * 获取该装备包的 {@link Json} 对象。
     *
     * @return {@link Json}
     */
    @Override
    public Json getJson() {
        return KitInterfaces.getKitJson(getName());
    }

    /**
     * 写入装备包。
     *
     * <p>
     * 该操作为覆盖性操作，即无论是否存在，均直接写入。
     * </p>
     */
    @Override
    public void write() {
        write(toJson());
    }
}
