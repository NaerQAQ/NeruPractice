package club.neru.arena.copy.objects;

import club.neru.arena.copy.interfaces.ArenaInterface;
import club.neru.serialization.interfaces.SerializableInterface;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Location;

/**
 * 基础竞技场实现对象。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/9
 */
@Getter
@Setter
@Accessors(chain = true)
public class ArenaImpl implements ArenaInterface, SerializableInterface {
    /**
     * 名称。
     */
    private String name;

    /**
     * 最低点坐标。
     */
    private Location lowestLocation;

    /**
     * 最高点坐标。
     */
    private Location highestLocation;

    /**
     * 第一出生坐标。
     */
    private Location firstSpawnLocation;

    /**
     * 第二出生坐标。
     */
    private Location secondSpawnLocation;

    /**
     * 观战玩家出生坐标。
     */
    private Location spectatorSpawnLocation;

    /**
     * 转换为 {@link ArenaParent} 对象。
     *
     * @return {@link ArenaParent}
     */
    public ArenaParent toArenaParent() {
        return (ArenaParent) this;
    }

    /**
     * 转换为 {@link ArenaChild} 对象。
     *
     * @return {@link ArenaChild}
     */
    public ArenaChild toArenaChild() {
        return (ArenaChild) this;
    }
}
