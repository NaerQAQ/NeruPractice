package club.neru.utils.player;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 玩家工具类。
 *
 * @author L1ncey / NaerQAQ
 * @version 1.0
 * @since 2023/10/9
 */
@UtilityClass
@SuppressWarnings("unused")
public class PlayerUtils {
    /**
     * 无限水检查，这似乎还存在一些问题。
     *
     * <p>
     * 检查对角方块 {@code (1,2,3,4)} ， 如果为水， 对该 {@code 2*2} 区域遍历是否全都是水
     * <pre>
     * [0]         [3]
     *     [block]
     * [2]         [1]
     * </pre>
     * </p>
     *
     * @param block 方块
     * @return 是否形成无限水
     */
    public boolean isInfiniteWaterSource(Block block) {
        Location location = block.getLocation();

        HashMap<Location, Location> locs = new HashMap<>();
        locs.put(location.add(0, 0, 1), location.add(-1, 0, 1));
        locs.put(location.add(1, 0, 0),  location.add(1, 0, -1));
        locs.put(location,  location.add(-1, 0, -1));
        locs.put(location.add(1, 0, 1),  location.add(1, 0, 1));

        Deque<Location> shouldCheckLoc = new LinkedList<>();
        locs.forEach((k, loc) -> {
            if (loc.getBlock().getType() == Material.WATER) shouldCheckLoc.add(k);
        });

        while (!shouldCheckLoc.isEmpty()) {
            Location locInCheck = shouldCheckLoc.poll();
            int confirmCount = 0;

            for (int dx = -1; dx <= 0; dx++) {
                for (int dz = -1; dz <= 0; dz++) {
                    if (locInCheck.add(dx, 0, dz).getBlock().getType() == Material.WATER) {
                        confirmCount++;

                        if (confirmCount == 4) return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * 获取以点位置为中心，给定半径的圆内的实体。
     *
     * @param location 给定的点位置
     * @param radius   半径，单位为 <b>方块</b>
     * @return 获取到的实体集
     */
    public static List<Entity> getEntitiesWithinRadius(Location location, double radius) {
        World world = location.getWorld();
        double radiusSquared = radius * radius;

        double x = location.getX();
        double z = location.getZ();

        List<Entity> entities = new LinkedList<>();

        int minX = (int) Math.floor((x - radius) / 16.0D);
        int maxX = (int) Math.floor((x + radius) / 16.0D);
        int minZ = (int) Math.floor((z - radius) / 16.0D);
        int maxZ = (int) Math.floor((z + radius) / 16.0D);

        for (int locX = minX; locX <= maxX; locX++) {
            for (int locZ = minZ; locZ <= maxZ; locZ++) {
                if (world != null && world.isChunkLoaded(locX, locZ)) {
                    entities.addAll(Arrays.stream(world.getChunkAt(locX, locZ).getEntities())
                            .filter(entity -> entity != null && entity.getLocation().distanceSquared(location) <= radiusSquared)
                            .collect(Collectors.toList()));
                }
            }
        }

        return entities;
    }

    /**
     * 判断玩家下一步动作是否撞墙。
     *
     * <p>
     * 确保该方法运行在玩家运动过程中。
     * </p>
     *
     * @param player 玩家
     * @return 下一步动作是否撞墙
     */
    public static boolean isNextToWall(Player player) {
        Location location = player.getLocation();

        World world = location.getWorld();

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        int blockX = location.getBlockX();
        int blockZ = location.getBlockZ();

        if (!world.isChunkLoaded(blockX >> 4, blockZ >> 4)) {
            return false;
        }

        List<Location> locations = Arrays.asList(
                new Location(world, x + 0.5, y, z),
                new Location(world, x - 0.5, y, z),
                new Location(world, x, y, z + 0.5),
                new Location(world, x, y, z - 0.5)
        );

        return locations.stream().anyMatch(loc -> confirmSafe(loc).isSolid());
    }

    /**
     * 在确保区块可以读取的情况下读取指定坐标的方块类型。
     *
     * @param location 坐标
     * @return 方块类型
     */
    public static Material confirmSafe(Location location) {
        return Optional.of(location)
                .filter(location1 -> location1.getWorld().isChunkLoaded(location1.getBlockX() >> 4, location1.getBlockZ() >> 4))
                .map(Location::getBlock)
                .map(Block::getType)
                .orElse(Material.AIR);
    }
}
