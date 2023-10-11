package club.neru.io.file.impl;

import club.neru.io.file.interfaces.FileManagerInterface;
import de.leonhard.storage.SimplixBuilder;
import de.leonhard.storage.Toml;
import de.leonhard.storage.internal.settings.ConfigSettings;
import de.leonhard.storage.internal.settings.DataType;
import de.leonhard.storage.internal.settings.ReloadSettings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;

/**
 * 该类实现 {@link FileManagerInterface}，为 Toml 管理类，单例模式。
 *
 * <p>
 * 注意: 在获取实例时应使用 {@link TomlManager#getInstance()} 方法，不推荐直接创建对象实例。
 * </p>
 *
 * <p>
 * 使用示例:
 * <pre>
 * TomlManager tomlManager = TomlManager.getInstance();
 * Toml toml = tomlManager.get("data", "/path/to/files", false);
 * </pre>
 * </p>
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/7/29
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TomlManager implements FileManagerInterface<Toml> {
    /**
     * {@link TomlManager} 实例。
     *
     * <p>
     * 此处使用 {@link Getter} 注解生成对应 getter 方法。
     * </p>
     */
    @Getter
    private static final TomlManager instance = new TomlManager();

    /**
     * {@code Toml} 文件后缀常量。
     */
    private static final String TOML_FILE_EXTENSION = ".toml";

    /**
     * 通过文件名与路径获取一个 {@link Toml} 对象。
     *
     * <p>
     * 使用此方法生成的 {@link Toml} 对象其重载属性为 {@link ReloadSettings#AUTOMATICALLY}，将会在内容更改后自动重载。
     * </p>
     *
     * @param name                    文件名
     * @param path                    文件路径
     * @param inputStreamFromResource 是否从资源内获取输入流
     * @return {@link Toml}
     */
    @Override
    public Toml get(String name, String path, boolean inputStreamFromResource) {
        if (name.contains(TOML_FILE_EXTENSION)) {
            name = name.split(TOML_FILE_EXTENSION)[0];
        }

        File file = new File(path, name + TOML_FILE_EXTENSION);

        if (file.exists() && inputStreamFromResource) {
            inputStreamFromResource = false;
        }

        SimplixBuilder simplixBuilder = SimplixBuilder.fromFile(file)
                .setDataType(DataType.SORTED)
                .setConfigSettings(ConfigSettings.PRESERVE_COMMENTS)
                .setReloadSettings(ReloadSettings.AUTOMATICALLY);

        if (inputStreamFromResource) {
            simplixBuilder.addInputStreamFromResource(name + TOML_FILE_EXTENSION);
        }

        return simplixBuilder.createToml();
    }
}
