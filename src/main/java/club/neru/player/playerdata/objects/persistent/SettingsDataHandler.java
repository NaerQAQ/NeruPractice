package club.neru.player.playerdata.objects.persistent;

import club.neru.NeruPractice;
import club.neru.io.file.impl.JsonManager;
import club.neru.utils.serialization.SerializableInterface;
import de.leonhard.storage.Json;

import java.util.UUID;

public class SettingsDataHandler {
    private static final String SETTINGS_JSON_KEY = "settings";
    private static final String SETTINGS_DATA_PATH =
            NeruPractice.getDataFolderAbsolutePath() + "/player/data/settings";

    public static SettingsData get(UUID uuid) {
        String uuidString = uuid.toString();

        Json json = JsonManager.getInstance().get(
                uuidString, SETTINGS_DATA_PATH, false
        );

        String settingsDataString = json.getString(SETTINGS_JSON_KEY);

        if (settingsDataString == null || settingsDataString.isEmpty()) {
            SettingsData settingsData = new SettingsData();
            json.set(SETTINGS_JSON_KEY, settingsData.toJson());
            return settingsData;
        }

        return SerializableInterface.fromJson(settingsDataString, SettingsData.class);
    }

    public static void reset(UUID uuid) {
        String uuidString = uuid.toString();

        Json json = JsonManager.getInstance().get(
                uuidString, SETTINGS_DATA_PATH, false
        );

        json.set(SETTINGS_JSON_KEY, null);
    }
}
