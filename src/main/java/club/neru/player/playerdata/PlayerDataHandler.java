package club.neru.player.playerdata;

import club.neru.player.playerdata.objects.nonpersistent.PlayerData;
import club.neru.player.playerdata.objects.persistent.SettingsData;
import club.neru.player.playerdata.objects.persistent.SettingsDataHandler;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerDataHandler {
    private static final Map<UUID, PlayerData> PROFILES_MAP = new ConcurrentHashMap<>();

    public static PlayerData get(UUID uuid) {
        PlayerData playerData = PROFILES_MAP.getOrDefault(uuid, null);

        if (playerData != null) {
            return playerData;
        }

        SettingsData settingsData = SettingsDataHandler.get(uuid);

        playerData = new PlayerData()
                .setSettingsData(settingsData);

        PROFILES_MAP.put(uuid, playerData);

        return playerData;
    }

    public static void remove(UUID uuid) {
        PROFILES_MAP.remove(uuid);
    }
}
