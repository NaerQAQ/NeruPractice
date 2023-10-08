package club.neru.player.playerdata;

import club.neru.player.playerdata.objects.nonpersistent.PlayerData;
import club.neru.player.playerdata.objects.persistent.SettingsData;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 玩家数据处理器。
 *
 * @author NaerQAQ
 * @version 1.0
 * @see PlayerData
 * @since 2023/10/7
 */
public class PlayerDataHandler {
    /**
     * 该哈希表将存储玩家 {@link UUID} 与数据对象 {@link PlayerData}.
     *
     * <p>
     * 使用 {@link ConcurrentHashMap} 容器保证线程安全。
     * </p>
     */
    private static final Map<UUID, PlayerData> PROFILES_MAP = new ConcurrentHashMap<>();

    /**
     * 从 {@link #PROFILES_MAP} 获取对应玩家数据。
     *
     * @param uuid 玩家 {@link UUID}
     * @return {@link PlayerData}
     */
    public static PlayerData get(UUID uuid) {
        PlayerData playerData = PROFILES_MAP.get(uuid);

        if (playerData != null) {
            return playerData;
        }

        SettingsData settingsData = SettingsDataHandler.get(uuid);

        playerData = new PlayerData()
                .setSettingsData(settingsData);

        PROFILES_MAP.put(uuid, playerData);

        return playerData;
    }

    /**
     * 删除玩家数据。
     *
     * @param uuid 玩家 {@link UUID}
     */
    public static void remove(UUID uuid) {
        PROFILES_MAP.remove(uuid);
    }
}
