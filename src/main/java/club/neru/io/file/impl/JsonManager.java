package club.neru.io.file.impl;

import club.neru.io.file.interfaces.FileManagerInterface;
import de.leonhard.storage.Json;
import de.leonhard.storage.SimplixBuilder;
import de.leonhard.storage.internal.settings.ConfigSettings;
import de.leonhard.storage.internal.settings.DataType;
import de.leonhard.storage.internal.settings.ReloadSettings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;

/**
 * 该类实现 {@link FileManagerInterface}，为 Json 管理类，单例模式。
 *
 * <p>
 * 注意: 在获取实例时应使用 {@link JsonManager#getInstance()} 方法，不推荐直接创建对象实例。
 * </p>
 *
 * <p>
 * 使用示例:
 * <pre>
 * JsonManager jsonManager = JsonManager.getInstance();
 * Json json = jsonManager.get("data", "/path/to/files", false);
 * </pre>
 * </p>
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/7/29
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonManager implements FileManagerInterface<Json> {
    /**
     * {@link JsonManager} 实例。
     *
     * <p>
     * 此处使用 {@link Getter} 注解生成对应 getter 方法。
     * </p>
     */
    @Getter
    private static final JsonManager instance = new JsonManager();

    /**
     * {@code Json} 文件后缀常量。
     */
    private static final String JSON_FILE_EXTENSION = ".json";

    /**
     * 通过文件名与路径获取一个 {@link Json} 对象。
     *
     * <p>
     * 使用此方法生成的 {@link Json} 对象其重载属性为 {@link ReloadSettings#INTELLIGENT}，将会在内容更改后自动重载。
     * </p>
     *
     * @param name                    文件名
     * @param path                    文件路径
     * @param inputStreamFromResource 是否从资源内获取输入流
     * @return {@link Json}
     */
    @Override
    public Json get(String name, String path, boolean inputStreamFromResource) {
        if (name.contains(JSON_FILE_EXTENSION)) {
            name = name.split(JSON_FILE_EXTENSION)[0];
        }

        File file = new File(path, name + JSON_FILE_EXTENSION);

        if (file.exists() && inputStreamFromResource) {
            inputStreamFromResource = false;
        }

        SimplixBuilder simplixBuilder = SimplixBuilder.fromFile(file)
                .setDataType(DataType.SORTED)
                .setConfigSettings(ConfigSettings.PRESERVE_COMMENTS)
                .setReloadSettings(ReloadSettings.INTELLIGENT);

        if (inputStreamFromResource) {
            simplixBuilder.addInputStreamFromResource(name + JSON_FILE_EXTENSION);
        }

        return simplixBuilder.createJson();
    }

    /**
     * 通过 {@link File} 对象获取一个 {@link Json} 对象。
     *
     * <p>
     * 使用此方法生成的 {@link Json} 对象其重载属性为 {@link ReloadSettings#INTELLIGENT}，将会在内容更改后自动重载。
     * </p>
     *
     * @param file {@link File}
     * @return {@link Json}
     */
    public Json get(File file) {
        return SimplixBuilder.fromFile(file)
                .setDataType(DataType.SORTED)
                .setConfigSettings(ConfigSettings.PRESERVE_COMMENTS)
                .setReloadSettings(ReloadSettings.INTELLIGENT)
                .createJson();
    }
}
