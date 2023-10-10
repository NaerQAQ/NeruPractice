package club.neru.arena.copy;

import club.neru.thread.Scheduler;
import club.neru.thread.enums.SchedulerExecutionMode;
import club.neru.thread.enums.SchedulerTypeEnum;
import club.neru.utils.common.QuickUtils;
import club.neru.utils.common.enums.ConsoleMessageTypeEnum;
import com.boydti.fawe.object.schematic.Schematic;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.EditSessionFactory;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.World;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Bukkit;

/**
 * 竞技场复制处理程序。
 *
 * <p>
 * 使用示例:
 * <pre>
 * new ArenaCopyHandler()
 *         .setWorldName("world")
 *         .setLowest(new Vector(47, 77, 284))
 *         .setHighest(new Vector(36, 88, 298))
 *         .setOffset(new Vector(100, 30, 100))
 *         .copy(); // 复制完后: 147 107 384
 * </pre>
 * </p>
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/8
 */
@Getter
@Setter
@Accessors(chain = true)
public class ArenaCopyHandler {
    /**
     * 偏移量。
     */
    private Vector offset;

    /**
     * 最低点。
     */
    private Vector lowest;

    /**
     * 最高点。
     */
    private Vector highest;

    /**
     * 世界名。
     */
    private String worldName;

    /**
     * 延迟多久进行复制。
     */
    private int delay;

    /**
     * 计算偏移后正确的放置坐标。
     *
     * <p>
     * 这实在是太诡异了，在第一次测试中需要偏移，但生产环境中不需要偏移就可以正常工作。
     * </p>
     *
     * @return 正确的放置坐标
     */
    @Deprecated
    public Vector getFinalCopyVector() {
        int lowestX = lowest.getBlockX();
        int highestX = highest.getBlockX();

        int xDifference = (-Math.abs(lowestX - highestX));

        return lowest.add(
                offset.add(xDifference, 0, 0)
        );
    }

    /**
     * 异步复制竞技场。
     */
    public void copy() {
        if (offset == null || lowest == null || highest == null || worldName == null) {
            QuickUtils.sendMessage(
                    ConsoleMessageTypeEnum.ERROR,
                    "Arena copying stopped, essential parameters cannot be null!"
            );
            return;
        }

        new Scheduler()
                .setSchedulerTypeEnum(SchedulerTypeEnum.LATER)
                .setSchedulerExecutionMode(SchedulerExecutionMode.ASYNC)
                .setDelay(delay)
                .setRunnable(() -> {
                    long start = System.currentTimeMillis();

                    World world = new BukkitWorld(
                            Bukkit.getWorld(worldName)
                    );

                    EditSessionFactory editSessionFactory = WorldEdit.getInstance().getEditSessionFactory();
                    EditSession editSession = editSessionFactory.getEditSession(world, Integer.MAX_VALUE);

                    Region region = new CuboidRegion(
                            lowest,
                            highest
                    );

                    BlockArrayClipboard blockArrayClipboard = editSession.lazyCopy(region);
                    Schematic schematic = new Schematic(blockArrayClipboard);

                    schematic.paste(world, lowest.add(offset));

                    QuickUtils.sendMessage(
                            ConsoleMessageTypeEnum.DEBUG,
                            "Copy completed, time taken: <time>ms.",
                            "<time>", String.valueOf(System.currentTimeMillis() - start)
                    );
                })
                .run();
    }
}
