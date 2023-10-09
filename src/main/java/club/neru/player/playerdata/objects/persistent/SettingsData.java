package club.neru.player.playerdata.objects.persistent;

import club.neru.io.config.languages.LanguagesEnum;
import club.neru.serialization.interfaces.SerializableInterface;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

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
    private String themeColor = "&3";

    /**
     * 多语言。
     */
    private LanguagesEnum languagesEnum = LanguagesEnum.EN;
}
