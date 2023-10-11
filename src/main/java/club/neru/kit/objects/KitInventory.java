package club.neru.kit.objects;

import club.neru.serialization.interfaces.SerializableInterface;
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
 * 装备包对象。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/11
 */
@Getter
@Setter
@Accessors(chain = true)
public class KitInventory implements SerializableInterface {
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
    }
}
