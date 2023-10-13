package club.neru.player.playerdata.objects.nonpersistent;

import club.neru.match.interfaces.MatchInterface;
import club.neru.player.playerdata.enums.PlayerState;
import club.neru.player.playerdata.objects.persistent.SettingsData;
import fr.mrmicky.fastboard.FastBoard;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.scoreboard.Scoreboard;

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

    /**
     * 玩家比赛对象。
     */
    private MatchInterface matchInterface;

    /**
     * 玩家计分板对象。
     */
    private Scoreboard scoreboard;

    /**
     * {@link FastBoard} 对象。
     */
    private FastBoard fastBoard;

    /**
     * 是否在水中。
     */
    private boolean inWater;
}
