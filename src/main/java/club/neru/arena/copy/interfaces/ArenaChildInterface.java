package club.neru.arena.copy.interfaces;

import club.neru.arena.copy.enums.ArenaState;

import java.util.Arrays;

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

    /**
     * 获取该子竞技场对应母竞技场的名称。
     *
     * @return 母竞技场名称
     */
    default String getArenaParentName() {
        String name = getName();
        return Arrays.stream(name.split("#"))
                .reduce((first, second) -> second)
                .map(lastPart -> name.substring(0, name.lastIndexOf(lastPart) - 1))
                .orElse(name);
    }
}
