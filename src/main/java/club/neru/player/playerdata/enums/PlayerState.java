package club.neru.player.playerdata.enums;

import org.bukkit.Bukkit;

/**
 * 玩家状态枚举类。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/7
 */
public enum PlayerState {
    /**
     * 位于大厅。
     */
    IN_LOBBY,

    /**
     * 正在匹配。
     */
    IN_QUEUE,

    /**
     * 比赛中。
     */
    IN_MATCH,

    /**
     * 比赛结束后等待 Requeue 请求。
     */
    WAITING_REQUEUE
}
