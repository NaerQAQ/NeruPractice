package club.neru.io.file.interfaces;

import de.leonhard.storage.Json;

/**
 * Json 持久化存储接口。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/11
 */
public interface JsonPersistableInterface {
    /**
     * 写入键值。
     *
     * @param string 写入内容
     */
    default void write(String string) {
        getJson().set(getJsonKey(), string);
    }

    /**
     * 需要重写的写入方法。
     */
    void write();

    /**
     * 获取实现该接口对象的 {@link Json} 实例。
     *
     * @return {@link Json}
     */
    Json getJson();

    /**
     * 需要写入的键值。
     *
     * @return 键值
     */
    String getJsonKey();
}
