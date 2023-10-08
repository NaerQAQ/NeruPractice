package club.neru.thread;

import club.neru.NeruPractice;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * 线程调度器。
 *
 * <p>
 * 该类提供了用于在 Bukkit 中快速安排任务的实用方法。
 * </p>
 *
 * @author NaerQAQ
 * @version 1.0
 * @see Bukkit#getScheduler()
 * @since 2023/10/8
 */
public class Scheduler {
    /**
     * 异步执行任务。
     *
     * @param runnable {@link Runnable} 对象
     */
    public static void async(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(
                NeruPractice.getInstance(), runnable
        );
    }

    /**
     * 同步执行任务。
     *
     * @param runnable {@link Runnable} 对象
     */
    public static void sync(Runnable runnable) {
        Bukkit.getScheduler().runTask(
                NeruPractice.getInstance(), runnable
        );
    }

    /**
     * 异步重复执行任务。
     *
     * @param bukkitRunnable {@link BukkitRunnable} 对象
     * @param delay          第一次执行前的延迟
     * @param period         两次执行之间的间隔
     */
    public static void timerAsync(BukkitRunnable bukkitRunnable, long delay, long period) {
        bukkitRunnable.runTaskTimerAsynchronously(
                NeruPractice.getInstance(), delay, period
        );
    }

    /**
     * 同步重复执行任务。
     *
     * @param bukkitRunnable {@link BukkitRunnable} 对象
     * @param delay          第一次执行前的延迟
     * @param period         两次执行之间的间隔
     */
    public static void timerSync(BukkitRunnable bukkitRunnable, long delay, long period) {
        bukkitRunnable.runTaskTimer(
                NeruPractice.getInstance(), delay, period
        );
    }

    /**
     * 异步延迟执行任务。
     *
     * @param bukkitRunnable {@link BukkitRunnable} 对象
     * @param delay          执行前的延迟
     */
    public static void laterAsync(BukkitRunnable bukkitRunnable, long delay) {
        bukkitRunnable.runTaskLaterAsynchronously(
                NeruPractice.getInstance(), delay
        );
    }

    /**
     * 同步延迟执行任务。
     *
     * @param bukkitRunnable {@link BukkitRunnable} 对象
     * @param delay          执行前的延迟
     */
    public static void laterSync(BukkitRunnable bukkitRunnable, long delay) {
        bukkitRunnable.runTaskLater(
                NeruPractice.getInstance(), delay
        );
    }
}
