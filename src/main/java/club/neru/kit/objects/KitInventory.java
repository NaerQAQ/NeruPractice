package club.neru.kit.objects;

import club.neru.basic.impl.ObjectNameImpl;
import club.neru.io.file.interfaces.JsonPersistableInterface;
import club.neru.kit.KitHandler;
import club.neru.kit.interfaces.KitInventoryInterface;
import club.neru.serialization.interfaces.SerializableInterface;
import club.neru.serialization.strategy.annotations.ExclusionField;
import club.neru.thread.Scheduler;
import club.neru.thread.enums.SchedulerExecutionMode;
import club.neru.thread.enums.SchedulerTypeEnum;
import de.leonhard.storage.Json;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

import java.util.Arrays;
import java.util.Collection;

/**
 * 装备包库存对象。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/11
 */
@Getter
@Setter
@Accessors(chain = true)
public class KitInventory extends ObjectNameImpl implements SerializableInterface, JsonPersistableInterface {
    /**
     * 装备包库存对象序列化后 Json 字符串所在的键值。
     */
    @Getter
    @ExclusionField
    private final String jsonKey = KitHandler.KIT_INVENTORY_JSON_KEY;

    /**
     * 背包内容。
     */
    private ItemStack[] contents;

    /**
     * 盔甲内容。
     */
    private ItemStack[] armorContents;

    /**
     * 持有物品槽位。
     */
    private int heldItemSlot;

    /**
     * 生命值。
     */
    private double health;

    /**
     * 饱食度。
     */
    private int foodLevel;

    /**
     * 药水效果。
     */
    private PotionEffect[] potionEffects;

    /**
     * 构造器。
     *
     * @param player 玩家对象
     */
    public KitInventory(Player player) {
        PlayerInventory inventory = player.getInventory();

        setContents(inventory.getContents());
        setArmorContents(inventory.getArmorContents());
        setHeldItemSlot(inventory.getHeldItemSlot());

        setHealth(player.getHealth());
        setFoodLevel(player.getFoodLevel());

        setPotionEffects(player.getActivePotionEffects().toArray(new PotionEffect[0]));
    }

    /**
     * 将此装备包应用到指定玩家。
     *
     * @param player 玩家对象
     */
    public void apply(Player player) {
        PlayerInventory inventory = player.getInventory();

        // 为了线程安全
        new Scheduler()
                .setSchedulerTypeEnum(SchedulerTypeEnum.RUN)
                .setSchedulerExecutionMode(SchedulerExecutionMode.SYNC)
                .setRunnable(() -> {
                    // 先清除
                    inventory.clear();

                    inventory.setContents(contents);
                    inventory.setArmorContents(armorContents);
                    inventory.setHeldItemSlot(heldItemSlot);

                    player.setHealth(health);
                    player.setFoodLevel(foodLevel);

                    // 清除药水效果
                    Collection<PotionEffect> activePotionEffects = player.getActivePotionEffects();
                    activePotionEffects.forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));

                    Arrays.stream(potionEffects).forEach(player::addPotionEffect);
                })
                .run();
    }

    /**
     * 获取该装备包库存的 {@link Json} 对象。
     *
     * @return {@link Json}
     */
    @Override
    public Json getJson() {
        return KitInventoryInterface.getKitInventoryJson(getName());
    }

    /**
     * 写入装备包库存。
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
