package club.neru.register;

import club.neru.NeruPractice;
import club.neru.annotations.AnnotationProcessor;
import club.neru.annotations.AutoRegisterCommand;
import club.neru.annotations.AutoRegisterListener;
import club.neru.io.config.ConfigManager;
import club.neru.utils.common.QuickUtils;
import club.neru.utils.common.enums.ConsoleMessageTypeEnum;
import me.despical.commandframework.CommandFramework;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

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
    }

    /**
     * 注册所有使用 {@link AutoRegisterListener} 注解的监听器。
     *
     * @see AutoRegisterListener
     */
    private static void registerListener() {
        NeruPractice neruPractice = NeruPractice.getInstance();
        PluginManager pluginManager = Bukkit.getPluginManager();

        for (Class<?> aClass : AnnotationProcessor.getClassesWithAnnotation(AutoRegisterListener.class)) {
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

        for (Class<?> aClass : AnnotationProcessor.getClassesWithAnnotation(AutoRegisterCommand.class)) {
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
}
