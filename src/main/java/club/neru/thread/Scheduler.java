package club.neru.thread;

import club.neru.NeruPractice;
import org.bukkit.Bukkit;

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
     * @param runnable {@link Runnable} 对象
     * @param delay    第一次执行前的延迟
     * @param period   两次执行之间的间隔
     */
    public static void timerAsync(Runnable runnable, long delay, long period) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(
                NeruPractice.getInstance(), runnable, delay, period
        );
    }

    /**
     * 同步重复执行任务。
     *
     * @param runnable {@link Runnable} 对象
     * @param delay    第一次执行前的延迟
     * @param period   两次执行之间的间隔
     */
    public static void timerSync(Runnable runnable, long delay, long period) {
        Bukkit.getScheduler().runTaskTimer(
                NeruPractice.getInstance(), runnable, delay, period
        );
    }

    /**
     * 异步延迟执行任务。
     *
     * @param runnable {@link Runnable} 对象
     * @param delay    执行前的延迟
     */
    public static void laterAsync(Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(
                NeruPractice.getInstance(), runnable, delay
        );
    }

    /**
     * 同步延迟执行任务。
     *
     * @param runnable {@link Runnable} 对象
     * @param delay    执行前的延迟
     */
    public static void laterSync(Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLater(
                NeruPractice.getInstance(), runnable, delay
        );
    }
}
