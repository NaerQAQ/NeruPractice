package club.neru.player.playerdata;

import club.neru.Mochi;
import club.neru.io.file.impl.JsonManager;
import club.neru.player.playerdata.objects.persistent.SettingsData;
import club.neru.serialization.interfaces.SerializableInterface;
import de.leonhard.storage.Json;

import java.util.UUID;

/**
 * 玩家设置数据处理器。
 *
 * @author NaerQAQ
 * @version 1.0
 * @see SettingsData
 * @since 2023/10/7
 */
public class SettingsDataHandler {
    /**
     * 序列化后 Json 字符串所在的键值。
     */
    private static final String SETTINGS_JSON_KEY = "settings";

    /**
     * 玩家数据所在的路径。
     */
    private static final String SETTINGS_DATA_PATH =
            Mochi.getDataFolderAbsolutePath() + "/player/data/settings";

    /**
     * 从玩家对应的 Json 文件中获取玩家设置数据。
     *
     * @param uuid 玩家 {@link UUID}
     * @return {@link SettingsData}
     */
    public static SettingsData get(UUID uuid) {
        String uuidString = uuid.toString();

        Json json = JsonManager.getInstance().get(
                uuidString, SETTINGS_DATA_PATH, false
        );

        String settingsDataString = json.getString(SETTINGS_JSON_KEY);

        // 如果没有找到则写入后返回
        if (settingsDataString == null || settingsDataString.isEmpty()) {
            SettingsData settingsData = new SettingsData();
            json.set(SETTINGS_JSON_KEY, settingsData.toJson());
            return settingsData;
        }

        // 反序列化
        return SerializableInterface.fromJson(settingsDataString, SettingsData.class);
    }

    /**
     * 重置玩家设置数据。
     *
     * @param uuid 玩家 {@link UUID}
     */
    public static void reset(UUID uuid) {
        String uuidString = uuid.toString();

        Json json = JsonManager.getInstance().get(
                uuidString, SETTINGS_DATA_PATH, false
        );

        json.set(SETTINGS_JSON_KEY, null);
    }
}
