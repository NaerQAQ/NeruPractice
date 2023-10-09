package club.neru.arena.copy.objects;

import club.neru.arena.copy.enums.ArenaState;
import club.neru.arena.copy.interfaces.ArenaChildInterface;
import club.neru.arena.copy.utils.WorldEditVectorUtils;
import club.neru.thread.Scheduler;
import club.neru.thread.enums.SchedulerExecutionMode;
import club.neru.thread.enums.SchedulerTypeEnum;
import club.neru.utils.common.QuickUtils;
import club.neru.utils.common.enums.ConsoleMessageTypeEnum;
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
 * 子竞技场对象。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/9
 */
@Getter
@Setter
@Accessors(chain = true)
public class ArenaChild extends ArenaImpl implements ArenaChildInterface {
    /**
     * 竞技场状态。
     */
    private ArenaState arenaState;

    /**
     * 重置该子竞技场。
     */
    public void reset() {
        Scheduler.builder()
                .setSchedulerTypeEnum(SchedulerTypeEnum.RUN)
                .setSchedulerExecutionMode(SchedulerExecutionMode.ASYNC)
                .setRunnable(() -> {
                    long start = System.currentTimeMillis();

                    setArenaState(ArenaState.RESETTING);

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

                    setArenaState(ArenaState.AVAILABLE);

                    QuickUtils.sendMessageByKey(
                            ConsoleMessageTypeEnum.DEBUG,
                            "reset-done",
                            "<name>",
                            getName(),
                            "<time>",
                            String.valueOf(System.currentTimeMillis() - start)
                    );
                })
                .build()
                .run();
    }
}
