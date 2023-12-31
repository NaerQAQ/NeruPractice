package club.neru.arena.objects;

import club.neru.arena.interfaces.ArenaInterface;
import club.neru.arena.utils.WorldEditVectorUtils;
import club.neru.basic.impl.ObjectNameImpl;
import club.neru.thread.Scheduler;
import club.neru.thread.enums.SchedulerExecutionMode;
import club.neru.thread.enums.SchedulerTypeEnum;
import club.neru.utils.common.text.QuickUtils;
import club.neru.utils.common.text.enums.ConsoleMessageTypeEnum;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.EditSessionFactory;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.World;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Location;
import org.bukkit.Material;

/**
 * 基础竞技场实现对象。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/9
 */
@Getter
@Setter
@Accessors(chain = true)
public class ArenaImpl extends ObjectNameImpl implements ArenaInterface {
    /**
     * 最低点坐标。
     */
    private Location lowestLocation;

    /**
     * 最高点坐标。
     */
    private Location highestLocation;

    /**
     * 第一出生坐标。
     */
    private Location firstSpawnLocation;

    /**
     * 第二出生坐标。
     */
    private Location secondSpawnLocation;

    /**
     * 观战玩家出生坐标。
     */
    private Location spectatorSpawnLocation;

    /**
     * 重置该竞技场。
     */
    public void clean() {
        new Scheduler()
                .setSchedulerTypeEnum(SchedulerTypeEnum.RUN)
                .setSchedulerExecutionMode(SchedulerExecutionMode.ASYNC)
                .setRunnable(() -> {
                    long start = System.currentTimeMillis();

                    Location lowestLocation = getLowestLocation();
                    Location highestLocation = getHighestLocation();

                    World world = new BukkitWorld(
                            lowestLocation.getWorld()
                    );

                    Vector lowest = WorldEditVectorUtils.locationToVector(
                            lowestLocation
                    );

                    Vector highest = WorldEditVectorUtils.locationToVector(
                            highestLocation
                    );

                    Region region = new CuboidRegion(
                            lowest,
                            highest
                    );

                    @SuppressWarnings("deprecation")
                    BaseBlock air = new BaseBlock(Material.AIR.getId());

                    EditSessionFactory editSessionFactory = WorldEdit.getInstance().getEditSessionFactory();
                    EditSession editSession = editSessionFactory.getEditSession(world, Integer.MAX_VALUE);

                    editSession.setBlocks(region, air);

                    QuickUtils.sendMessage(
                            ConsoleMessageTypeEnum.DEBUG,
                            "Reset completed, arena name: <arena_name>, time taken: <time>ms.",
                            "<arena_name>", getName(),
                            "<time>", String.valueOf(System.currentTimeMillis() - start)
                    );
                })
                .run();
    }
}
