package club.neru.thread;

import org.bukkit.Bukkit;

import club.neru.NeruPractice;

public class Scheduler {
    public static void async(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(
                NeruPractice.getInstance(), runnable
        );
    }

    public static void sync(Runnable runnable) {
        Bukkit.getScheduler().runTask(
                NeruPractice.getInstance(), runnable
        );
    }

    public static void timerAsync(Runnable runnable, long delay, long period) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(
                NeruPractice.getInstance(), runnable, delay, period
        );
    }

    public static void timerSync(Runnable runnable, long delay, long period) {
        Bukkit.getScheduler().runTaskTimer(
                NeruPractice.getInstance(), runnable, delay, period
        );
    }

    public static void laterAsync(Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(
                NeruPractice.getInstance(), runnable, delay
        );
    }

    public static void laterSync(Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLater(
                NeruPractice.getInstance(), runnable, delay
        );
    }
}
