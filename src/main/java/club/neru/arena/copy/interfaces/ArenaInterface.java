package club.neru.arena.copy.interfaces;

import club.neru.basic.interfaces.ObjectNameInterface;
import org.bukkit.Location;

/**
 * 基础竞技场接口。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/9
 */
public interface ArenaInterface extends ObjectNameInterface {
    /**
     * 获取最低点坐标。
     *
     * @return {@link Location}
     */
    Location getLowestLocation();

    /**
     * 获取最高点坐标。
     *
     * @return {@link Location}
     */
    Location getHighestLocation();

    /**
     * 获取第一出生坐标。
     *
     * @return {@link Location}
     */
    Location getFirstSpawnLocation();

    /**
     * 获取第二出生坐标。
     *
     * @return {@link Location}
     */
    Location getSecondSpawnLocation();

    /**
     * 获取观战玩家出生坐标。
     *
     * @return {@link Location}
     */
    Location getSpectatorSpawnLocation();
}
