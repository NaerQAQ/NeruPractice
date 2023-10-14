package club.neru.arena.interfaces;

import club.neru.arena.enums.ArenaState;
import club.neru.arena.objects.ArenaParent;

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
     * 获取母竞技场。
     *
     * @return 母竞技场对象
     */
    ArenaParent getArenaParent();

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

    /**
     * 获取该子竞技场编号。
     *
     * @return 子竞技场编号
     */
    default int getArenaParentNumber() {
        String[] split = getName().split("#");
        return Integer.parseInt(split[split.length - 1]);
    }
}
