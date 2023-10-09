package club.neru.arena.copy.interfaces;

import club.neru.arena.copy.enums.ArenaState;

/**
 * 子竞技场接口。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/9
 */
public interface ArenaChildInterface extends ArenaInterface {
    /**
     * 获取竞技场状态。
     *
     * @return 竞技场状态
     */
    ArenaState getArenaState();
}
