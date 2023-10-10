package club.neru.utils.common;

import club.neru.io.config.ConfigManager;
import club.neru.io.config.languages.LanguagesEnum;
import club.neru.player.playerdata.PlayerDataHandler;
import club.neru.player.playerdata.objects.nonpersistent.PlayerData;
import club.neru.player.playerdata.objects.persistent.SettingsData;
import club.neru.utils.common.enums.ConsoleMessageTypeEnum;
import de.leonhard.storage.Yaml;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

/**
 * 快捷工具类。
 *
 * @author L1ncey / NaerQAQ / 2000000
 * @version 1.0
 * @since 2023/10/8
 */
public class QuickUtils {
    /**
     * 向控制台发送输出消息。
     *
     * @param consoleMessageTypeEnum 信息类型
     * @param message                要处理的发向控制台的字符串
     * @param params                 替换的可选参数
     */
    public static void sendMessage(ConsoleMessageTypeEnum consoleMessageTypeEnum, String message, String... params) {
        StringBuilder prefix = new StringBuilder()
                .append("&f[&bNeru&f] ");

        switch (consoleMessageTypeEnum) {
            case DEBUG:
                prefix.append("&e[DEBUG] &f");
                break;

            case ERROR:
                prefix.append("&4[ERROR] &f");
                break;

            case NORMAL:
                prefix.append("&f");
                break;

            default:
            case NO_PREFIX:
                prefix.setLength(0);
                break;
        }

        Bukkit.getConsoleSender().sendMessage(
                StringUtils.handle(prefix + message, params)
        );
    }

    /**
     * 读取语言配置文件内对应键值字符串列表，向控制台发送输出消息。
     *
     * @param consoleMessageTypeEnum 信息类型
     * @param key                    键值
     * @param params                 替换的可选参数
     */
    public static void sendMessageByKey(ConsoleMessageTypeEnum consoleMessageTypeEnum, String key, String... params) {
        Yaml yaml = ConfigManager.getLanguageConfig(
                ConfigManager.getConsoleMessageLanguage()
        );

        List<String> messages = StringUtils.handle(
                yaml.getStringList(key), params
        );

        StringUtils.handle(messages).forEach(string -> sendMessage(consoleMessageTypeEnum, string));
    }

    /**
     * 将字符串处理后发送给玩家。
     *
     * @param player  可选用于变量替换的第一玩家
     * @param message 发送给玩家的信息
     * @param params  替换的可选参数
     */
    public static void sendMessage(Player player, String message, String... params) {
        player.sendMessage(StringUtils.handle(player, message, params));
    }

    /**
     * 读取语言配置文件内对应键值字符串列表，处理后发送给玩家。
     *
     * @param player 可选用于变量替换的第一玩家
     * @param key    键值
     * @param params 替换的可选参数
     * @author 2000000
     */
    public static void sendMessageByKey(Player player, String key, String... params) {
        UUID uuid = player.getUniqueId();

        PlayerData playerData = PlayerDataHandler.get(uuid);
        SettingsData settingsData = playerData.getSettingsData();
        LanguagesEnum languagesEnum = settingsData.getLanguagesEnum();

        Yaml yaml = ConfigManager.getLanguageConfig(languagesEnum);

        List<String> messages = StringUtils.handle(
                yaml.getStringList(key), params
        );

        StringUtils.handle(player, messages).forEach(player::sendMessage);
    }
}
