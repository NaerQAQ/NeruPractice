package club.neru.arena.copy.interfaces;

import club.neru.arena.ArenaHandler;
import club.neru.arena.copy.ArenaCopyHandler;
import club.neru.arena.copy.objects.ArenaChild;
import club.neru.arena.copy.objects.ArenaImpl;
import club.neru.arena.copy.utils.WorldEditVectorUtils;
import club.neru.io.file.impl.JsonManager;
import club.neru.io.file.utils.IOUtils;
import club.neru.utils.serialization.SerializableInterface;
import com.sk89q.worldedit.Vector;
import de.leonhard.storage.Json;
import org.bukkit.Location;

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

    /**
     * 复制竞技场。
     *
     * <p>
     * 复制逻辑:
     * <pre>
     *  Z ------------------------------------->
     * X  A (1,1)  A-Copy1 (1,2)  A-Copy2 (1,3)
     * |  B (2,1)  B-Copy1 (2,2)  B-Copy1 (2,3)
     * |  C (3,1)  C-Copy1 (3,2)  C-Copy1 (3,3)
     * |  D (4,1)  D-Copy1 (4,2)  D-Copy1 (4,3)
     * V
     * </pre>
     * </p>
     */
    default void copy(int amount) {
        amount ++;

        String arenaName = getName();

        // 已有子竞技场数量，+2 是因为从 0 计数，且在 for 内跳过第一位，因为第一位为已经复制
        int arenaChildrenCount = getArenaChildren().size() + 2;

        // 复制完后应有的子竞技场数量
        int finalArenaChildrenCount = arenaChildrenCount + amount;

        Location lowestLocation = getLowestLocation();
        Location highestLocation = getHighestLocation();

        String worldName = lowestLocation.getWorld().getName();

        Vector lowestVector = WorldEditVectorUtils.locationToVector(lowestLocation);
        Vector highestVector = WorldEditVectorUtils.locationToVector(highestLocation);

        JsonManager jsonManager = JsonManager.getInstance();

        for (int i = arenaChildrenCount; i < finalArenaChildrenCount; i++) {
            // z 偏移量与这是第几个子竞技场挂钩
            int offsetZ = i * 100;

            Vector offsetVector = new Vector(
                    0, 0, offsetZ
            );

            org.bukkit.util.Vector bukkitOffsetVector = new org.bukkit.util.Vector(
                    0, 0, offsetZ
            );

            new ArenaCopyHandler()
                    .setLowest(lowestVector)
                    .setHighest(highestVector)
                    .setOffset(offsetVector)
                    .setWorldName(worldName)
                    .copy();

            String newName = arenaName + "#" + i;

            // 母竞技场坐标偏移
            Location newLowestLocation = getLowestLocation().clone().add(bukkitOffsetVector);
            Location newHighestLocation = getHighestLocation().clone().add(bukkitOffsetVector);
            Location newFirstSpawnLocation = getFirstSpawnLocation().clone().add(bukkitOffsetVector);
            Location newSecondSpawnLocation = getSecondSpawnLocation().clone().add(bukkitOffsetVector);
            Location newSpectatorSpawnLocation = getSpectatorSpawnLocation().clone().add(bukkitOffsetVector);

            ArenaImpl arenaChild = new ArenaChild()
                    .setName(newName)
                    .setLowestLocation(newLowestLocation)
                    .setHighestLocation(newHighestLocation)
                    .setFirstSpawnLocation(newFirstSpawnLocation)
                    .setSecondSpawnLocation(newSecondSpawnLocation)
                    .setSpectatorSpawnLocation(newSpectatorSpawnLocation);

            // 写入
            Json json = jsonManager.get(
                    newName, getArenaChildPath(), false
            );

            String arenaChildString = arenaChild.toJson();

            json.set(ArenaHandler.ARENA_JSON_KEY, arenaChildString);
        }
    }
}
