package club.neru.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.List;

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
     * @param in 要处理的字符串
     * @return 转换后的带有 Bukkit 颜色代码的字符串
     */
    public static List<String> handle(List<String> in) {

        in.replaceAll(StringUtil::handle);

        return in;
    }

    /**
     * 向控制台发送带有颜色的字符串，用于更好的 展示数据 以及 Debug 。
     *
     * @param message 要处理的发向控制台的字符串
     * @since 2023/8/16
     */
    public static void consoleMsg(String message) {
        Bukkit.getConsoleSender().sendMessage(handle("&f[&bNeru&f] &f" + message));
    }

    public static void consoleDebugMsg(String message) {
        Bukkit.getConsoleSender().sendMessage(handle("&f[&bNeru&f] &c[DEBUG] &f" + message));
    }
}
