package club.neru.arena.interfaces;

import club.neru.arena.ArenaHandler;
import club.neru.arena.objects.ArenaChild;
import club.neru.arena.objects.ArenaParent;
import club.neru.io.file.impl.JsonManager;
import club.neru.io.file.utils.JsonFileProcessor;
import de.leonhard.storage.Json;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 母竞技场接口。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/9
 */
public interface ArenaParentInterface extends ArenaInterface {
    /**
     * 获取 {@link ArenaParent} 对象。
     *
     * @param arenaName 母竞技场名
     * @return {@link ArenaParent} 对象，如果没有则返回 {@code null}
     */
    static ArenaParent getArenaParent(String arenaName) {
        return getArenaParents().stream()
                .filter(arenaParent -> arenaParent.getName().equals(arenaName))
                .findFirst()
                .orElse(null);
    }

    /**
     * 获取母竞技场 {@link Json} 对象。
     *
     * @param arenaName 母竞技场名
     * @return {@link Json}
     */
    static Json getArenaParentJson(String arenaName) {
        JsonManager jsonManager = JsonManager.getInstance();

        return jsonManager.get(
                arenaName, ArenaHandler.ARENA_PARENT_PATH, false
        );
    }

    /**
     * 获取所有 {@link ArenaParent} 对象。
     *
     * <p>
     * 使用 {@link ConcurrentLinkedQueue} 容器保证线程安全。
     * </p>
     *
     * @return {@link ArenaParent}
     */
    static ConcurrentLinkedQueue<ArenaParent> getArenaParents() {
        return new JsonFileProcessor()
                .setPath(ArenaHandler.ARENA_PARENT_PATH)
                .setJsonKey(ArenaHandler.ARENA_JSON_KEY)
                .setTargetClass(ArenaParent.class)
                .deserializeJsonAndConvertToQueue();
    }

    /**
     * 获取最低死亡高度。
     *
     * @return 最低死亡高度
     */
    int getMinDeathHeight();

    /**
     * 获取最高建筑高度。
     *
     * @return 最高建筑高度
     */
    int getMaxBuildHeight();

    /**
     * 获取放置子竞技场的文件夹路径。
     *
     * @return 放置子竞技场的文件夹路径
     */
    default String getArenaChildPath() {
        return ArenaHandler.ARENA_PATH + "/" + getName();
    }

    /**
     * 获取该竞技场的所有 {@link ArenaChild} 对象。
     *
     * <p>
     * 使用 {@link ConcurrentLinkedQueue} 容器保证线程安全。
     * </p>
     *
     * @return {@link ArenaChild}
     */
    default ConcurrentLinkedQueue<ArenaChild> getArenaChildren() {
        return new JsonFileProcessor()
                .setPath(getArenaChildPath())
                .setJsonKey(ArenaHandler.ARENA_JSON_KEY)
                .setTargetClass(ArenaChild.class)
                .deserializeJsonAndConvertToQueue();
    }
}
