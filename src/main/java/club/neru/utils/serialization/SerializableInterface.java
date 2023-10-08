package club.neru.utils.serialization;

import com.google.gson.GsonBuilder;

/**
 * 序列化接口。
 *
 * <p>
 * 该接口提供将对象序列化为 Json 字符串以及 Json 字符串反序列化为对象的方法。
 * </p>
 *
 * @author NaerQAQ
 * @version 1.0
 * @see GsonBuilder
 * @since 2023/10/8
 */
public interface SerializableInterface {
    /**
     * 从 JSON 字符串反序列化为对象。
     *
     * @param json  JSON 字符串
     * @param clazz 对象类
     * @param <T>   对象类型
     * @return 反序列化后的对象
     */
    static <T> T fromJson(String json, Class<T> clazz) {
        return new GsonBuilder().create().fromJson(json, clazz);
    }

    /**
     * 将对象序列化为 JSON 字符串。
     *
     * @return 表示对象的 JSON 字符串
     */
    default String toJson() {
        return new GsonBuilder().create().toJson(this);
    }
}
