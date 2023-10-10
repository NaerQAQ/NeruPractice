package club.neru.io.config;

import club.neru.Mochi;
import club.neru.io.config.languages.LanguagesEnum;
import club.neru.io.file.impl.YamlManager;
import de.leonhard.storage.Yaml;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 配置文件管理类。
 *
 * <p>
 * 该类提供配置文件的创建、获取等等。
 * </p>
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/7/29
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfigManager {
    private static final String LANGUAGE_PATH =
            Mochi.getDataFolderAbsolutePath() + "/language";

    /**
     * {@code config.yml} 配置文件实例。
     */
    @Getter
    private static final Yaml config = YamlManager.getInstance().get(
            "config", Mochi.getDataFolderAbsolutePath(), true
    );

    /**
     * 英文语言配置文件实例。
     */
    @Getter
    private static final Yaml enLanguage = YamlManager.getInstance().get(
            "en", LANGUAGE_PATH, true
    );

    /**
     * 简体中文语言配置文件实例。
     */
    @Getter
    private static final Yaml zhCnLanguage = YamlManager.getInstance().get(
            "zh_CN", LANGUAGE_PATH, true
    );

    /**
     * 控制台语言。
     */
    @Getter
    private static final LanguagesEnum consoleMessageLanguage = LanguagesEnum.caseInsensitiveValueOf(
            getConfig().getString("console-message-language")
    );

    /**
     * 获取对应语言配置文件。
     *
     * @param languagesEnum {@link LanguagesEnum}
     * @return 对应语言配置文件
     */
    public static Yaml getLanguageConfig(LanguagesEnum languagesEnum) {
        switch (languagesEnum) {
            case ZH_CN:
                return getZhCnLanguage();

            case EN:
            default:
                return getEnLanguage();
        }
    }
}
