package club.neru.thread;

import club.neru.NeruPractice;
import club.neru.thread.enums.SchedulerExecutionMode;
import club.neru.thread.enums.SchedulerTypeEnum;
import lombok.Builder;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * 任务调度器类，用于根据不同的调度类型和执行模式运行任务。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/9
 */
@Builder(setterPrefix = "set")
public class Scheduler {
    /**
     * {@link Runnable} 对象。
     *
     * <p>
     * 若没有使用 {@link BukkitRunnable} 中的一些方法，则推荐直接传入 {@link Runnable} 对象。
     * </p>
     *
     * @see BukkitRunnable
     */
    private Runnable runnable;

    /**
     * {@link BukkitRunnable} 对象。
     */
    private BukkitRunnable bukkitRunnable;

    /**
     * 任务类型。
     */
    private SchedulerTypeEnum schedulerTypeEnum;

    /**
     * 任务执行模式。
     */
    private SchedulerExecutionMode schedulerExecutionMode;

    /**
     * 延迟。
     *
     * <p>
     * 仅在 {@link #schedulerTypeEnum} 为非 {@link SchedulerTypeEnum#RUN} 时有用。
     * </p>
     */
    private int delay;

    /**
     * 重复间隔。
     *
     * <p>
     * 仅在 {@link #schedulerTypeEnum} 为非 {@link SchedulerTypeEnum#RUN} 时有用。
     * </p>
     */
    private int period;

    /**
     * 运行任务调度器，根据设定的参数执行相应的任务。
     */
    public void run() {
        BukkitRunnable finalBukkitRunnable;

        NeruPractice neruPractice = NeruPractice.getInstance();

        // 如果传入的为 runnable 则转换为 bukkit runnable
        if (runnable == null) {
            finalBukkitRunnable = bukkitRunnable;
        } else {
            finalBukkitRunnable = new BukkitRunnable() {
                @Override
                public void run() {
                    runnable.run();
                }
            };
        }

        switch (schedulerTypeEnum) {
            case RUN:
                if (schedulerExecutionMode == SchedulerExecutionMode.SYNC) {
                    finalBukkitRunnable.runTask(neruPractice);
                } else {
                    finalBukkitRunnable.runTaskAsynchronously(neruPractice);
                }
                return;

            case LATER:
                if (schedulerExecutionMode == SchedulerExecutionMode.SYNC) {
                    finalBukkitRunnable.runTaskLater(neruPractice, delay);
                } else {
                    finalBukkitRunnable.runTaskLaterAsynchronously(neruPractice, delay);
                }
                return;

            case TIMER:
                if (schedulerExecutionMode == SchedulerExecutionMode.SYNC) {
                    finalBukkitRunnable.runTaskTimer(neruPractice, delay, period);
                } else {
                    finalBukkitRunnable.runTaskTimerAsynchronously(neruPractice, delay, period);
                }
        }
    }
}
