package club.neru.arena.copy.objects;

import club.neru.arena.copy.interfaces.ArenaInterface;
import club.neru.utils.serialization.SerializableInterface;
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
}
