package club.neru.utils;

import club.neru.thread.Scheduler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.List;

/**
 * 效果工具类。
 *
 * @author L1ncey
 * @version 1.0
 * @since 2023/10/8
 */
public class StringUtil {
    /**
     * 将字符串中的颜色代码转换为 Bukkit 中的颜色代码。
     *
     * <p>
     * 该方法用于将字符串中的颜色代码 <b>&</b> 转换为 Bukkit 中的颜色代码。
     * </p>
     *
     * @param string 要处理的字符串
     * @return 转换后的带有 Bukkit 颜色代码的字符串
     */
    public static String handle(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    /**
     * 将字符串中的颜色代码转换为 Bukkit 中的颜色代码。
     *
     * <p>
     * 该方法用于将字符串中的颜色代码 {@code &} 转换为 Bukkit 中的颜色代码。
     * </p>
     *
     * @param listString 要处理的字符串
     * @return 转换后的带有 Bukkit 颜色代码的字符串
     */
    public static List<String> handle(List<String> listString) {
        listString.replaceAll(StringUtil::handle);
        return listString;
    }

    /**
     * 向控制台发送输出消息。
     *
     * @param message 要处理的发向控制台的字符串
     */
    public static void consoleMsg(String message) {
        Bukkit.getConsoleSender().sendMessage(handle("&f[&bNeru&f] &f" + message));
    }

    /**
     * 向控制台发送 Debug 消息。
     *
     * @param message 要处理的发向控制台的字符串
     */
    public static void consoleDebugMsg(String message) {
        Bukkit.getConsoleSender().sendMessage(handle("&f[&bNeru&f] &c[DEBUG] &f" + message));
    }
}
