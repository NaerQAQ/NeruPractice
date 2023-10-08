package club.neru.player.playerdata.objects.nonpersistent;

import club.neru.player.playerdata.enums.PlayerState;
import club.neru.player.playerdata.objects.persistent.SettingsData;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 非持久化存储的玩家数据对象。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/7
 */
@Getter
@Setter
@Accessors(chain = true)
public class PlayerData {
    /**
     * 玩家状态。
     */
    private PlayerState playerState = PlayerState.IN_LOBBY;

    /**
     * 玩家设置数据对象。
     */
    private SettingsData settingsData;
}
