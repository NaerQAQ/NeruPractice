package club.neru.utils.common;

import club.neru.player.playerdata.PlayerDataHandler;
import club.neru.player.playerdata.objects.nonpersistent.PlayerData;
import club.neru.player.playerdata.objects.persistent.SettingsData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 效果工具类。
 *
 * @author L1ncey / 2000000
 * @version 1.0
 * @since 2023/10/8
 */
public class StringUtils {
    /**
     * 对字符串进行处理，支持参数替换和颜色代码转换。
     *
     * @param player 玩家对象
     * @param string 要处理的字符串，可以包含占位符 {@code {0} {1}} 等等
     * @param params 替换占位符的参数
     * @return 处理后的字符串，包括参数替换和颜色代码转换
     */
    public static String handle(Player player, String string, String... params) {
        UUID uuid = player.getUniqueId();

        PlayerData playerData = PlayerDataHandler.get(uuid);
        SettingsData settingsData = playerData.getSettingsData();

        String themeColor = settingsData.getThemeColor();

        return handle(
                string.replace("<theme_color>", themeColor),
                params
        );
    }

    /**
     * 对字符串列表进行处理，支持参数替换和颜色代码转换。
     *
     * @param player  玩家对象
     * @param strings 要处理的字符串列表，可以包含占位符 {@code {0} {1}} 等等
     * @param params  替换占位符的参数
     * @return 处理后的字符串，包括参数替换和颜色代码转换
     */
    public static List<String> handle(Player player, List<String> strings, String... params) {
        return strings.stream()
                .map(string -> StringUtils.handle(player, string, params))
                .collect(Collectors.toList());
    }

    /**
     * 通过替换指定参数对来生成新的字符串，并进行颜色处理等。
     *
     * @param string 原始字符串
     * @param params 替换参数对，格式为 {@code key1, value1, key2, value2, ...}
     * @return 替换后的新字符串
     * @author 2000000
     */
    public static String handle(String string, String... params) {
        if (params == null) {
            return string;
        }

        for (int i = 0; i < params.length; i += 2) {
            string = string.replaceAll(params[i], params[i + 1]);
        }

        return ChatColor.translateAlternateColorCodes('&', string);
    }

    /**
     * 通过替换指定参数对来生成新的字符串，并进行颜色处理等。
     *
     * @param strings 原始字符串列表
     * @param params  替换参数对，格式为 {@code key1, value1, key2, value2, ...}
     * @return 替换后的新字符串列表
     * @author 2000000
     */
    public static List<String> handle(List<String> strings, String... params) {
        return strings.stream()
                .map(string -> StringUtils.handle(string, params))
                .collect(Collectors.toList());
    }
}
