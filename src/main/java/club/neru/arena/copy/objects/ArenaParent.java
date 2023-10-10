package club.neru.arena.copy.objects;

import club.neru.arena.ArenaHandler;
import club.neru.arena.copy.ArenaCopyHandler;
import club.neru.arena.copy.interfaces.ArenaParentInterface;
import club.neru.arena.copy.utils.WorldEditVectorUtils;
import club.neru.basic.interfaces.ReflectCommandInterface;
import club.neru.io.file.impl.JsonManager;
import club.neru.serialization.interfaces.SerializableInterface;
import com.sk89q.worldedit.Vector;
import de.leonhard.storage.Json;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Location;

/**
 * 母竞技场对象。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/9
 */
@Getter
@Setter
@Accessors(chain = true)
public class ArenaParent extends ArenaImpl implements ArenaParentInterface, SerializableInterface, ReflectCommandInterface {
    /**
     * 最低死亡高度。
     */
    private int minDeathHeight = 0;

    /**
     * 最高建筑高度。
     */
    private int maxBuildHeight = 255;

    /**
     * 写入母竞技场。
     *
     * <p>
     * 该操作为覆盖性操作，即无论是否存在，均直接写入。
     * </p>
     */
    @Override
    public void write() {
        String arenaParentString = toJson();
        getArenaParentJson().set(ArenaHandler.ARENA_JSON_KEY, arenaParentString);
    }

    /**
     * 删除该母竞技场。
     *
     * <p>
     * 该操作将顺带删除所有子竞技场。
     * </p>
     *
     * @param ignore 无意义
     */
    public void delete(boolean ignore) {
        reset();
        deleteCopy(true);

        // noinspection ResultOfMethodCallIgnored
        getArenaParentJson().getFile().delete();
    }

    /**
     * 删除所有子竞技场。
     *
     * @param ignore 无意义
     */
    public void deleteCopy(boolean ignore) {
        JsonManager jsonManager = JsonManager.getInstance();

        for (ArenaChild arenaChild : getArenaChildren()) {
            arenaChild.reset();

            Json json = jsonManager.get(
                    arenaChild.getName(), getArenaChildPath(), false
            );

            // noinspection ResultOfMethodCallIgnored
            json.getFile().delete();
        }
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
    public void copy(int amount) {
        String arenaName = getName();

        // 已有子竞技场数量，+2 是因为从 0 计数，且在 for 内跳过第一位，因为第一位为已经复制
        int arenaChildrenCount = getArenaChildren().size() + 2;

        // 复制完后应有的子竞技场数量
        int finalArenaChildrenCount = arenaChildrenCount + amount - 1;

        Location lowestLocation = getLowestLocation();
        Location highestLocation = getHighestLocation();

        String worldName = lowestLocation.getWorld().getName();

        Vector lowestVector = WorldEditVectorUtils.locationToVector(lowestLocation);
        Vector highestVector = WorldEditVectorUtils.locationToVector(highestLocation);

        JsonManager jsonManager = JsonManager.getInstance();

        for (int i = arenaChildrenCount; i < finalArenaChildrenCount; i++) {
            // z 偏移量与这是第几个子竞技场挂钩
            int offsetZ = i * 100;

            // 10 ticks 复制间隔
            int delay = i * 10;

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
                    .setDelay(delay)
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
