package club.neru.utils.common;

import org.bukkit.Bukkit;

/**
 * 快捷工具类。
 *
 * @author L1ncey / NaerQAQ
 * @version 1.0
 * @since 2023/10/8
 */
public class QuickUtils {
    /**
     * 向控制台发送输出消息。
     *
     * @param message 要处理的发向控制台的字符串
     */
    public static void sendMsg(String message, String... args) {
        Bukkit.getConsoleSender().sendMessage(
                StringUtils.handle("&f[&bNeru&f] &f" + message, args)
        );
    }

    /**
     * 向控制台发送 Debug 消息。
     *
     * @param message 要处理的发向控制台的字符串
     */
    public static void sendDebugMsg(String message, String... args) {
        Bukkit.getConsoleSender().sendMessage(
                StringUtils.handle("&f[&bNeru&f] &e[DEBUG] &f" + message, args)
        );
    }

    /**
     * 向控制台发送 Error 消息。
     *
     * @param message 要处理的发向控制台的字符串
     */
    public static void sendErrorMsg(String message, String... args) {
        Bukkit.getConsoleSender().sendMessage(
                StringUtils.handle("&f[&bNeru&f] &4[ERROR] &f" + message, args)
        );
    }
}
