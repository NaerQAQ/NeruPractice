package club.neru.player.playerdata.objects.persistent;

import club.neru.utils.serialization.SerializableInterface;
import com.google.common.primitives.ImmutableDoubleArray;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * 持久化存储的玩家设置数据对象。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/7
 */
@Getter
@Setter
@Accessors(chain = true)
public class SettingsData implements SerializableInterface {
    /**
     * 玩家主题色。
     */
    private String themeColor = "&d";
}
