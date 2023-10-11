package club.neru.kit.interfaces;

import club.neru.basic.interfaces.ObjectNameInterface;
import club.neru.io.file.impl.JsonManager;
import club.neru.io.file.utils.JsonFileProcessor;
import club.neru.kit.KitHandler;
import club.neru.kit.objects.Kit;
import de.leonhard.storage.Json;
import org.bukkit.Material;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 装备包接口。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/10
 */
public interface KitInterfaces extends ObjectNameInterface {
    /**
     * 获取 {@link Kit} 对象。
     *
     * @param kitName 装备包名
     * @return {@link Kit} 对象，如果没有则返回 {@code null}
     */
    static Kit getKit(String kitName) {
        return getKits().stream()
                .filter(kit -> kit.getName().equals(kitName))
                .findFirst()
                .orElse(null);
    }

    /**
     * 获取装备包 {@link Json} 对象。
     *
     * @param kitName 装备包名
     * @return {@link Json}
     */
    static Json getKitJson(String kitName) {
        JsonManager jsonManager = JsonManager.getInstance();

        return jsonManager.get(
                kitName, KitHandler.KIT_PATH, false
        );
    }

    /**
     * 获取所有 {@link Kit} 对象。
     *
     * <p>
     * 使用 {@link ConcurrentLinkedQueue} 容器保证线程安全。
     * </p>
     *
     * @return {@link Kit}
     */
    static ConcurrentLinkedQueue<Kit> getKits() {
        return new JsonFileProcessor()
                .setPath(KitHandler.KIT_PATH)
                .setJsonKey(KitHandler.KIT_JSON_KEY)
                .setTargetClass(Kit.class)
                .deserializeJsonAndConvertToQueue();
    }

    /**
     * 是否允许建筑。
     *
     * @return 是否允许建筑
     */
    boolean isCanBuild();

    /**
     * 获取此装备包可用的竞技场名称。
     *
     * @return 此装备包可用的竞技场名称
     */
    ConcurrentLinkedQueue<String> getAvailableArenaNames();

    /**
     * 此装备包玩家可破坏的竞技场方块类型。
     *
     * @return 此装备包玩家可破坏的竞技场方块类型
     */
    ConcurrentLinkedQueue<Material> getDestroyableArenaBlocks();
}
