package club.neru.annotations;

import club.neru.thread.enums.SchedulerExecutionMode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoStartTimerTask {
    int delay() default 0;

    int period() default 10;

    SchedulerExecutionMode schedulerExecutionMode() default SchedulerExecutionMode.ASYNC;
}
