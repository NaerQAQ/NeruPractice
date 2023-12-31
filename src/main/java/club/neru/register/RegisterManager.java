package club.neru.register;

import club.neru.Mochi;
import club.neru.annotations.AnnotationProcessor;
import club.neru.commands.annotations.AutoRegisterCommand;
import club.neru.io.config.ConfigManager;
import club.neru.register.annotations.AutoRegisterListener;
import club.neru.script.ScriptHandler;
import club.neru.serialization.interfaces.SerializableInterface;
import club.neru.thread.Scheduler;
import club.neru.thread.annotations.AutoStartTimerTask;
import club.neru.thread.enums.SchedulerExecutionMode;
import club.neru.thread.enums.SchedulerTypeEnum;
import club.neru.utils.common.text.QuickUtils;
import club.neru.utils.common.text.enums.ConsoleMessageTypeEnum;
import com.google.gson.Gson;
import com.jonahseguin.drink.CommandService;
import com.jonahseguin.drink.Drink;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;

/**
 * 注册类。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/7
 */
public class RegisterManager {
    /**
     * 初始化。
     */
    @SuppressWarnings({"ResultOfMethodCallIgnored", "unused"})
    public static void init() {
        ConfigManager.getConfig();
        ConfigManager.getEnLanguage();
        ConfigManager.getZhCnLanguage();
        Gson gson = SerializableInterface.GSON;

        registerListener();
        registerCommand();
        autoStartTimerTask();

        ScriptHandler.registerScript();
    }

    /**
     * 注册所有使用 {@link AutoRegisterListener} 注解的监听器。
     *
     * @see AutoRegisterListener
     */
    private static void registerListener() {
        Mochi mochi = Mochi.getInstance();
        PluginManager pluginManager = Bukkit.getPluginManager();
        Set<Class<?>> classesWithAnnotation = AnnotationProcessor.getClassesWithAnnotation(AutoRegisterListener.class);

        classesWithAnnotation.forEach(aClass -> {
            String className = aClass.getName();

            try {
                Listener listener = (Listener) aClass.getDeclaredConstructor().newInstance();
                pluginManager.registerEvents(listener, mochi);

                QuickUtils.sendMessage(
                        ConsoleMessageTypeEnum.NORMAL,
                        "Listener successfully registered: <class_name>.",
                        "<class_name>", className
                );
            } catch (Exception exception) {
                String message = exception.getMessage();

                QuickUtils.sendMessage(
                        ConsoleMessageTypeEnum.ERROR,
                        "Unable to register listener: <class_name>, message: <message>.",
                        "<class_name>", className,
                        "<message>", message
                );
            }
        });
    }

    /**
     * 注册所有使用 {@link AutoRegisterCommand} 注解的指令。
     *
     * @see AutoRegisterCommand
     */
    private static void registerCommand() {
        CommandService drink = Drink.get(Mochi.getInstance());
        Set<Class<?>> classesWithAnnotation = AnnotationProcessor.getClassesWithAnnotation(AutoRegisterCommand.class);

        classesWithAnnotation.forEach(aClass -> {
            String className = aClass.getName();

            try {
                Object object = aClass.getDeclaredConstructor().newInstance();
                AutoRegisterCommand autoRegisterCommand =
                        aClass.getAnnotation(AutoRegisterCommand.class);

                String command = autoRegisterCommand.command();
                String[] aliases = autoRegisterCommand.aliases();

                if (aliases.length == 0) {
                    drink.register(object, command);
                } else {
                    drink.register(object, command, aliases);
                }

                QuickUtils.sendMessage(
                        ConsoleMessageTypeEnum.NORMAL,
                        "Command successfully registered: <class_name>.",
                        "<class_name>", className
                );
            } catch (Exception exception) {
                String message = exception.getMessage();

                QuickUtils.sendMessage(
                        ConsoleMessageTypeEnum.ERROR,
                        "Unable to register command: <class_name>, message: <message>.",
                        "<class_name>", className,
                        "<message>", message
                );
            }
        });

        drink.registerCommands();
    }

    /**
     * 开启所有 {@link AutoStartTimerTask} 注解的任务。
     *
     * @see AutoStartTimerTask
     */
    private static void autoStartTimerTask() {
        Set<Class<?>> classesWithAnnotation = AnnotationProcessor.getClassesWithAnnotation(AutoStartTimerTask.class);

        classesWithAnnotation.forEach(aClass -> {
            String className = aClass.getName();

            try {
                AutoStartTimerTask annotation = aClass.getAnnotation(AutoStartTimerTask.class);
                BukkitRunnable bukkitRunnable = (BukkitRunnable) aClass.getDeclaredConstructor().newInstance();

                int delay = annotation.delay();
                int period = annotation.period();
                SchedulerExecutionMode schedulerExecutionMode = annotation.schedulerExecutionMode();

                new Scheduler()
                        .setSchedulerTypeEnum(SchedulerTypeEnum.TIMER)
                        .setSchedulerExecutionMode(schedulerExecutionMode)
                        .setDelay(delay)
                        .setPeriod(period)
                        .setBukkitRunnable(bukkitRunnable)
                        .run();

                QuickUtils.sendMessage(
                        ConsoleMessageTypeEnum.NORMAL,
                        "Task successfully started: <class_name>, execution mode: <execution_mode>, delay: <delay>, period: <period>.",
                        "<class_name>", className,
                        "<execution_mode>", schedulerExecutionMode.name(),
                        "<delay>", String.valueOf(delay),
                        "<period>", String.valueOf(period)
                );
            } catch (Exception exception) {
                String message = exception.getMessage();

                QuickUtils.sendMessage(
                        ConsoleMessageTypeEnum.ERROR,
                        "Unable to start task: <class_name>, message: <message>.",
                        "<class_name>", className,
                        "message", message
                );
            }
        });
    }
}
