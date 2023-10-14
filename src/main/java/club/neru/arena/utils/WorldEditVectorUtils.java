package club.neru.arena.utils;

import com.sk89q.worldedit.Vector;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;

/**
 * 创世神向量工具类。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/9
 */
@UtilityClass
public class WorldEditVectorUtils {
    /**
     * 将 {@link Location} 转换为 {@link Vector} 对象。
     *
     * @param location {@link Location}
     * @return {@link Vector}
     */
    public static Vector locationToVector(Location location) {
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        return new Vector(x, y, z);
    }
}
