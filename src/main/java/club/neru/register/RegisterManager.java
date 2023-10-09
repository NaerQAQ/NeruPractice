package club.neru.register;

import club.neru.NeruPractice;
import club.neru.annotations.AnnotationProcessor;
import club.neru.annotations.AutoRegisterCommand;
import club.neru.annotations.AutoRegisterListener;
import club.neru.annotations.AutoStartTimerTask;
import club.neru.io.config.ConfigManager;
import club.neru.thread.Scheduler;
import club.neru.thread.enums.SchedulerExecutionMode;
import club.neru.thread.enums.SchedulerTypeEnum;
import club.neru.utils.common.QuickUtils;
import club.neru.utils.common.enums.ConsoleMessageTypeEnum;
import me.despical.commandframework.CommandFramework;
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
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void init() {
        ConfigManager.getConfig();
        ConfigManager.getEnLanguage();
        ConfigManager.getZhCnLanguage();

        registerListener();
        registerCommand();
        autoStartTimerTask();
    }

    /**
     * 注册所有使用 {@link AutoRegisterListener} 注解的监听器。
     *
     * @see AutoRegisterListener
     */
    private static void registerListener() {
        NeruPractice neruPractice = NeruPractice.getInstance();
        PluginManager pluginManager = Bukkit.getPluginManager();
        Set<Class<?>> classesWithAnnotation = AnnotationProcessor.getClassesWithAnnotation(AutoRegisterListener.class);

        for (Class<?> aClass : classesWithAnnotation) {
            String className = aClass.getName();

            try {
                Listener listener = (Listener) aClass.getDeclaredConstructor().newInstance();
                pluginManager.registerEvents(listener, neruPractice);

                QuickUtils.sendMessage(
                        ConsoleMessageTypeEnum.NORMAL,
                        "&7成功注册监听器: {0}.",
                        className
                );
            } catch (Exception exception) {
                String message = exception.getMessage();

                QuickUtils.sendMessage(
                        ConsoleMessageTypeEnum.ERROR,
                        "无法注册监听器: {0}, Message: {1}.",
                        className, message
                );
            }
        }
    }

    /**
     * 注册所有使用 {@link AutoRegisterCommand} 注解的监听器。
     *
     * @see AutoRegisterCommand
     */
    private static void registerCommand() {
        CommandFramework commandFramework = NeruPractice.getCommandFramework();
        Set<Class<?>> classesWithAnnotation = AnnotationProcessor.getClassesWithAnnotation(AutoRegisterCommand.class);

        for (Class<?> aClass : classesWithAnnotation) {
            String className = aClass.getName();

            try {
                Object object = aClass.getDeclaredConstructor().newInstance();
                commandFramework.registerCommands(object);

                QuickUtils.sendMessage(
                        ConsoleMessageTypeEnum.NORMAL,
                        "&7成功注册指令: {0}.",
                        className
                );
            } catch (Exception exception) {
                String message = exception.getMessage();

                QuickUtils.sendMessage(
                        ConsoleMessageTypeEnum.ERROR,
                        "无法注册指令: {0}, Message: {1}.",
                        className, message
                );
            }
        }
    }

    /**
     * 注册所有使用 {@link AutoRegisterCommand} 注解的监听器。
     *
     * @see AutoRegisterCommand
     */
    private static void autoStartTimerTask() {
        Set<Class<?>> classesWithAnnotation = AnnotationProcessor.getClassesWithAnnotation(AutoStartTimerTask.class);

        for (Class<?> aClass : classesWithAnnotation) {
            String className = aClass.getName();

            try {
                AutoStartTimerTask annotation = aClass.getAnnotation(AutoStartTimerTask.class);
                BukkitRunnable bukkitRunnable = (BukkitRunnable) aClass.getDeclaredConstructor().newInstance();

                int delay = annotation.delay();
                int period = annotation.period();
                SchedulerExecutionMode schedulerExecutionMode = annotation.schedulerExecutionMode();

                Scheduler.builder()
                        .setSchedulerTypeEnum(SchedulerTypeEnum.TIMER)
                        .setSchedulerExecutionMode(schedulerExecutionMode)
                        .setDelay(delay)
                        .setPeriod(period)
                        .setBukkitRunnable(bukkitRunnable)
                        .build()
                        .run();

                QuickUtils.sendMessage(
                        ConsoleMessageTypeEnum.NORMAL,
                        "&7成功开启任务: {0}.",
                        className
                );
            } catch (Exception exception) {
                String message = exception.getMessage();

                QuickUtils.sendMessage(
                        ConsoleMessageTypeEnum.ERROR,
                        "无法开启任务: {0}, Message: {1}.",
                        className, message
                );
            }
        }
    }
}
