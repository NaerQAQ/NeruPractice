package club.neru.register;

import club.neru.NeruPractice;
import club.neru.annotations.AnnotationProcessor;
import club.neru.annotations.AutoRegisterListener;
import club.neru.utils.QuickUtils;
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
    public static void init() {
        registerListener();
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
            try {
                Listener listener = (Listener) aClass.getDeclaredConstructor().newInstance();
                pluginManager.registerEvents(listener, neruPractice);
            } catch (Exception exception) {
                String className = aClass.getName();
                String message = exception.getMessage();
                QuickUtils.sendErrorMsg(
                        "无法注册监听器: {0}, Message: {1}.", className, message
                );
            }
        }
    }
}
