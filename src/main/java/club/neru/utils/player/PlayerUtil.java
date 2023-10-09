package club.neru.utils.player;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;

@UtilityClass
public class PlayerUtil {

    public boolean isInfiniteWaterSource(Block block) {
        Location location = block.getLocation();

        /*
                 [0]         [3]
                     [block]
                 [2]         [1]

               检查对角方块 (1,2,3,4) ， 如果为水， 对该 2*2 区域遍历是否全都是水
               如果是，则直接 return true， 否，进入下一对角的检查
         */

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
     * 获取 以点位置为中心，给定半径的圆 内的实体
     *
     * @param location 给定的点位置
     * @param radius 半径，单位为 <b>方块</b>
     * @return 获取到的实体集
     */
    public List<Entity> getEntitiesWithinRadius(Location location, double radius) {
        double x = location.getX();
        double z = location.getZ();

        World world = location.getWorld();
        List<Entity> entities = new LinkedList<>();

        for (int locX = (int) Math.floor((x - radius) / 16.0D);
             locX <= (int) Math.floor((x + radius) / 16.0D); locX++) {
            for (int locZ = (int) Math.floor((z - radius) / 16.0D);
                 locZ <= (int) Math.floor((z + radius) / 16.0D); locZ++) {
                if (!Objects.requireNonNull(world).isChunkLoaded(locX, locZ)) continue;

                for (Entity entity : world.getChunkAt(locX, locZ).getEntities()) {
                    if (entity == null || entity.getLocation().distanceSquared(location) > radius * radius) continue;


                    entities.add(entity);
                }
            }
        }

        return entities;
    }

    /**
     * 判断玩家下一步动作是否撞墙
     * <p>确保该方法运行在玩家运动过程中</p>
     *
     * @param player 玩家
     * @return 下一步动作是否撞墙
     */
    public static boolean isNextToWall(Player player) {
        Location loc = player.getLocation();

//        在异步的线程中会发生玩家所在的区块判断为未加载的情况， 所以我加上该判断
//        [特别注意] 异步线程无法 加载区块 ，会报错!!
        if (!loc.getWorld().isChunkLoaded(loc.getBlockX() >> 4, loc.getBlockZ() >> 4)) {
            return false;
        }

        /*
             zp            ↑ z
        xn [Loc] xp        O → x
             zn
                         (NO Y axis)
        */
        Location xp = new Location(loc.getWorld(), loc.getX() + 0.5, loc.getY(), loc.getZ());
        Location xn = new Location(loc.getWorld(), loc.getX() - 0.5, loc.getY(), loc.getZ());
        Location zp = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() + 0.5);
        Location zn = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() - 0.5);


        return confirmSafe(xp).isSolid() ||
                confirmSafe(xn).isSolid() ||
                confirmSafe(zp).isSolid() ||
                confirmSafe(zn).isSolid();
    }

//    在确保区块可以读取的情况下获取该位置的方块
    public static Material confirmSafe(Location loc) {
        if (loc.getWorld().isChunkLoaded(loc.getBlockX() >> 4, loc.getBlockZ() >> 4)) {
            return loc.getBlock().getType();
        }

//        :p
        return Material.AIR;
    }
}
