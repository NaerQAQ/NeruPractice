package club.neru.thread;

import club.neru.NeruPractice;
import club.neru.thread.enums.SchedulerExecutionMode;
import club.neru.thread.enums.SchedulerTypeEnum;
import lombok.Builder;
import org.bukkit.scheduler.BukkitRunnable;

@Builder(setterPrefix = "set")
public class Scheduler {
    private Runnable runnable;
    private BukkitRunnable bukkitRunnable;

    private SchedulerTypeEnum schedulerTypeEnum;
    private SchedulerExecutionMode schedulerExecutionMode;

    private int delay;
    private int period;

    public void run() {
        BukkitRunnable finalBukkitRunnable;

        NeruPractice neruPractice = NeruPractice.getInstance();

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
