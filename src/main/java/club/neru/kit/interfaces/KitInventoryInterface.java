package club.neru.kit.interfaces;

import club.neru.arena.objects.ArenaParent;
import club.neru.basic.interfaces.ObjectNameInterface;
import club.neru.io.file.impl.JsonManager;
import club.neru.io.file.utils.JsonFileProcessor;
import club.neru.kit.KitHandler;
import club.neru.kit.objects.KitInventory;
import de.leonhard.storage.Json;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentLinkedQueue;

public interface KitInventoryInterface extends ObjectNameInterface {
    /**
     * 获取 {@link KitInventory} 对象。
     *
     * @param kitInventoryName 装备包库存名
     * @return {@link KitInventory} 对象，如果没有则返回 {@code null}
     */
    static KitInventory getKitInventory(String kitInventoryName) {
        return getKitInventors().stream()
                .filter(kitInventory -> kitInventory.getName().equals(kitInventoryName))
                .findFirst()
                .orElse(null);
    }

    /**
     * 获取装备包库存 {@link Json} 对象。
     *
     * @param kitInventoryName 装备包库存名
     * @return {@link Json}
     */
    static Json getKitInventoryJson(String kitInventoryName) {
        JsonManager jsonManager = JsonManager.getInstance();

        return jsonManager.get(
                kitInventoryName, KitHandler.KIT_INVENTORY_PATH, false
        );
    }

    /**
     * 获取所有 {@link KitInventory} 对象。
     *
     * <p>
     * 使用 {@link ConcurrentLinkedQueue} 容器保证线程安全。
     * </p>
     *
     * @return {@link ArenaParent}
     */
    static ConcurrentLinkedQueue<KitInventory> getKitInventors() {
        return new JsonFileProcessor()
                .setPath(KitHandler.KIT_INVENTORY_PATH)
                .setJsonKey(KitHandler.KIT_INVENTORY_JSON_KEY)
                .setTargetClass(KitInventory.class)
                .deserializeJsonAndConvertToQueue();
    }

    /**
     * 将此装备包应用到指定玩家。
     *
     * @param player 玩家对象
     */
    void apply(Player player);
}
