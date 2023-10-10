package club.neru.arena.copy.objects;

import club.neru.arena.ArenaHandler;
import club.neru.arena.copy.ArenaCopyHandler;
import club.neru.arena.copy.interfaces.ArenaParentInterface;
import club.neru.arena.copy.utils.WorldEditVectorUtils;
import club.neru.io.file.impl.JsonManager;
import club.neru.serialization.interfaces.SerializableInterface;
import com.sk89q.worldedit.Vector;
import de.leonhard.storage.Json;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Location;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

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
public class ArenaParent extends ArenaImpl implements ArenaParentInterface, SerializableInterface {
    /**
     * 是否允许建筑。
     */
    private boolean canBuild = false;

    /**
     * 最低死亡高度。
     */
    private int minDeathHeight = 0;

    /**
     * 最高建筑高度。
     */
    private int maxBuildHeight = 255;

    /**
     * 指令设置方法。
     *
     * <p>
     * 该方法主要用于指令的处理，传入竞技场名，方法名和值，通过反射直接调用对应方法。
     * </p>
     *
     * @param arenaName  竞技场名
     * @param methodName 方法名 (忽略大小写)
     * @param value      值
     * @return 是否成功调用
     */
    public static boolean commandSet(String arenaName, String methodName, Object value) {
        return Optional.ofNullable(ArenaParentInterface.getArenaParent(arenaName))
                .map(arenaParent -> {
                    try {
                        Class<? extends ArenaParent> arenaParentClass = arenaParent.getClass();
                        Method[] methods = arenaParentClass.getMethods();

                        Method invokeMethod = Arrays.stream(methods)
                                .filter(method -> method.getName().equalsIgnoreCase(methodName))
                                .filter(method -> method.getParameterCount() == 1)
                                .findFirst()
                                .orElse(null);

                        if (invokeMethod == null) {
                            return false;
                        }

                        invokeMethod.invoke(arenaParent, value);
                        arenaParent.write();

                        return true;
                    } catch (Exception ignore) {
                        return false;
                    }
                })
                .orElse(false);
    }

    /**
     * 写入母竞技场。
     *
     * <p>
     * 该操作为覆盖性操作，即无论是否存在，均直接写入。
     * </p>
     */
    public void write() {
        String arenaParentString = toJson();

        JsonManager jsonManager = JsonManager.getInstance();

        Json json = jsonManager.get(
                getName(), ArenaHandler.ARENA_PARENT_PATH, false
        );

        json.set(ArenaHandler.ARENA_JSON_KEY, arenaParentString);
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
        amount++;

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
