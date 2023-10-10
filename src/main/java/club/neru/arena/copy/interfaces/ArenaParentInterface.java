package club.neru.arena.copy.interfaces;

import club.neru.arena.ArenaHandler;
import club.neru.arena.copy.objects.ArenaChild;
import club.neru.arena.copy.objects.ArenaParent;
import club.neru.io.file.impl.JsonManager;
import club.neru.io.file.utils.IOUtils;
import club.neru.serialization.interfaces.SerializableInterface;
import de.leonhard.storage.Json;

import java.io.File;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

/**
 * 母竞技场接口。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/9
 */
public interface ArenaParentInterface extends ArenaInterface {
    /**
     * 获取母竞技场对象。
     *
     * @param arenaName 母竞技场名
     * @return 母竞技场对象，如果没有则返回 {@code null}
     */
    static ArenaParent getArenaParent(String arenaName) {
        return getArenaParents().stream()
                .filter(arenaParent -> arenaParent.getName().equals(arenaName))
                .findFirst()
                .orElse(null);
    }

    /**
     * 获取所有母竞技场对象。
     *
     * <p>
     * 使用 {@link ConcurrentLinkedQueue} 容器保证线程安全。
     * </p>
     *
     * @return {@link ArenaParent}
     */
    static ConcurrentLinkedQueue<ArenaParent> getArenaParents() {
        ConcurrentLinkedQueue<File> files =
                IOUtils.getFiles(ArenaHandler.ARENA_PARENT_PATH);

        // 每次获取都直接读取现有文件，绝对同步，这也许是不必要的性能损耗，但实际上可以忽略
        return files.stream()
                .map(file -> {
                    JsonManager jsonManager = JsonManager.getInstance();
                    Json json = jsonManager.get(file);
                    String arenaString = json.getString(ArenaHandler.ARENA_JSON_KEY);
                    return SerializableInterface.fromJson(arenaString, ArenaParent.class);
                })
                .collect(Collectors.toCollection(ConcurrentLinkedQueue::new));
    }

    /**
     * 是否允许建筑。
     *
     * @return 是否允许建筑
     */
    boolean isCanBuild();

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
     * 获取该竞技场的所有子竞技场对象。
     *
     * <p>
     * 使用 {@link ConcurrentLinkedQueue} 容器保证线程安全。
     * </p>
     *
     * @return {@link ArenaChild}
     */
    default ConcurrentLinkedQueue<ArenaChild> getArenaChildren() {
        ConcurrentLinkedQueue<File> files =
                IOUtils.getFiles(getArenaChildPath());

        // 每次获取都直接读取现有文件，绝对同步，这也许是不必要的性能损耗，但实际上可以忽略
        return files.stream()
                .map(file -> {
                    JsonManager jsonManager = JsonManager.getInstance();
                    Json json = jsonManager.get(file);
                    String arenaString = json.getString(ArenaHandler.ARENA_JSON_KEY);
                    return SerializableInterface.fromJson(arenaString, ArenaChild.class);
                })
                .collect(Collectors.toCollection(ConcurrentLinkedQueue::new));
    }
}
