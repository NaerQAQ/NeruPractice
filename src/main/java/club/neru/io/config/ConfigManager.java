package club.neru.io.config;

import club.neru.NeruPractice;
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
    /**
     * {@code config.yml} 配置文件实例。
     */
    @Getter
    private static final Yaml config = YamlManager.getInstance().get(
            "config", NeruPractice.getDataFolderAbsolutePath(), true
    );
}
