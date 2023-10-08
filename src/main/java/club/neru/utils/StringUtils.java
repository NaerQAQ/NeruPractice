package club.neru.utils;

import org.bukkit.ChatColor;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 效果工具类。
 *
 * @author L1ncey
 * @version 1.0
 * @since 2023/10/8
 */
public class StringUtils {
    /**
     * 对字符串进行处理，支持参数替换和颜色代码转换。
     *
     * @param string 要处理的字符串，可以包含占位符 {@code {0} {1}} 等等
     * @param args 替换占位符的参数
     * @return 处理后的字符串，包括参数替换和颜色代码转换
     */
    public static String handle(String string, String... args) {
        try {
            string = new MessageFormat(string).format(args);
        } catch (Exception ignore) {
            // ignore
        }

        return ChatColor.translateAlternateColorCodes('&', string);
    }

    /**
     * 对字符串列表进行处理，支持参数替换和颜色代码转换。
     *
     * @param listString 要处理的字符串列表
     * @param args 替换占位符的参数
     * @return 处理后的字符串列表，包括参数替换和颜色代码转换
     */
    public static List<String> handle(List<String> listString, String... args) {
        return listString.stream()
                .map(string -> handle(string, args))
                .collect(Collectors.toList());
    }
}
