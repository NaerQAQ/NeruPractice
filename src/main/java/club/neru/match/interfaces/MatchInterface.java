package club.neru.match.interfaces;

import club.neru.thread.Scheduler;

public interface MatchInterface {
    default void start() {
        Scheduler.timerAsync(() -> {
            init();

            if (checkEndConditions()) {
                end();
            }
        }, 0, 5);
    }

    void init();
    void end();
    boolean checkEndConditions();
}
