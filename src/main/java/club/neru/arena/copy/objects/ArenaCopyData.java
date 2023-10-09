package club.neru.arena.copy.objects;

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
 * 竞技场复制数据对象。
 *
 * <p>
 * 使用示例:
 * <pre>
 * new ArenaCopyData()
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
public class ArenaCopyData {
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
     * 计算偏移后正确的放置坐标。
     *
     * @return 正确的放置坐标
     */
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
                    "竞技场复制停止，必要的参数不能为 null!"
            );
            return;
        }

        Scheduler.builder()
                .setSchedulerTypeEnum(SchedulerTypeEnum.RUN)
                .setSchedulerExecutionMode(SchedulerExecutionMode.ASYNC)
                .setRunnable(() -> {
                    long start = System.currentTimeMillis();

                    World world = new BukkitWorld(
                            Bukkit.getWorld(worldName)
                    );

                    EditSessionFactory editSessionFactory = WorldEdit.getInstance().getEditSessionFactory();
                    EditSession editSession = editSessionFactory.getEditSession(world, Integer.MAX_VALUE);

                    Region region = new CuboidRegion(
                            world,
                            lowest,
                            highest
                    );

                    BlockArrayClipboard blockArrayClipboard = editSession.lazyCopy(region);
                    Schematic schematic = new Schematic(blockArrayClipboard);

                    schematic.paste(world, getFinalCopyVector());

                    QuickUtils.sendMessage(
                            ConsoleMessageTypeEnum.DEBUG,
                            "完成复制，用时: {time}",
                            "{time}",
                            String.valueOf(System.currentTimeMillis() - start)
                    );
                })
                .build()
                .run();
    }
}
