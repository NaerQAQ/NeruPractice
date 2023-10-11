package club.neru.io.file.impl;

import club.neru.io.file.interfaces.FileManagerInterface;
import de.leonhard.storage.SimplixBuilder;
import de.leonhard.storage.Yaml;
import de.leonhard.storage.internal.settings.ConfigSettings;
import de.leonhard.storage.internal.settings.DataType;
import de.leonhard.storage.internal.settings.ReloadSettings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;

/**
 * 该类实现 {@link FileManagerInterface}，为 Yaml 管理类，单例模式。
 *
 * <p>
 * 注意: 在获取实例时应使用 {@link YamlManager#getInstance()} 方法，不推荐直接创建对象实例。
 * </p>
 *
 * <p>
 * 使用示例:
 * <pre>
 * YamlManager yamlManager = YamlManager.getInstance();
 * Yaml yaml = yamlManager.get("data", "/path/to/files", false);
 * </pre>
 * </p>
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/7/29
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class YamlManager implements FileManagerInterface<Yaml> {
    /**
     * {@link YamlManager} 实例。
     *
     * <p>
     * 此处使用 {@link Getter} 注解生成对应 getter 方法。
     * </p>
     */
    @Getter
    private static final YamlManager instance = new YamlManager();

    /**
     * {@code Yaml} 文件后缀常量。
     */
    private static final String YAML_FILE_EXTENSION = ".yml";

    /**
     * 通过文件名与路径获取一个 {@link Yaml} 对象。
     *
     * <p>
     * 使用此方法生成的 {@link Yaml} 对象其重载属性为 {@link ReloadSettings#AUTOMATICALLY}，将会在内容更改后自动重载。
     * </p>
     *
     * @param name                    文件名
     * @param path                    文件路径
     * @param inputStreamFromResource 是否从资源内获取输入流
     * @return {@link Yaml}
     */
    @Override
    public Yaml get(String name, String path, boolean inputStreamFromResource) {
        if (name.contains(YAML_FILE_EXTENSION)) {
            name = name.split(YAML_FILE_EXTENSION)[0];
        }

        File file = new File(path, name + YAML_FILE_EXTENSION);

        if (file.exists() && inputStreamFromResource) {
            inputStreamFromResource = false;
        }

        SimplixBuilder simplixBuilder = SimplixBuilder.fromFile(file)
                .setDataType(DataType.SORTED)
                .setConfigSettings(ConfigSettings.PRESERVE_COMMENTS)
                .setReloadSettings(ReloadSettings.AUTOMATICALLY);

        if (inputStreamFromResource) {
            simplixBuilder.addInputStreamFromResource(name + YAML_FILE_EXTENSION);
        }

        return simplixBuilder.createYaml();
    }
}
