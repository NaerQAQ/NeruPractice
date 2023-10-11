package club.neru.serialization.adapter;

import club.neru.register.annotations.AutoRegisterTypeAdapter;
import club.neru.serialization.interfaces.BukkitObjectSerializableInterface;
import com.google.gson.*;
import org.bukkit.Location;

import java.lang.reflect.Type;

/**
 * {@link Location} 类型适配器。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/9
 */
@AutoRegisterTypeAdapter
@SuppressWarnings("unused")
public class LocationAdapter implements JsonSerializer<Location>, JsonDeserializer<Location>, BukkitObjectSerializableInterface {
    /**
     * 该适配器支持类型的类。
     */
    public final static Class<?> TYPE = Location.class;

    /**
     * 将 {@link Location} 对象序列化为 {@link JsonElement} 对象。
     *
     * @param location                 {@link Location}
     * @param type                     序列化的目标类型
     * @param jsonSerializationContext 用于序列化的上下文环境
     * @return {@link JsonElement}
     */
    @Override
    public JsonElement serialize(Location location, Type type, JsonSerializationContext jsonSerializationContext) {
        return serialize(location);
    }

    /**
     * 将 {@link JsonElement} 对象反序列化为 {@link Location} 对象。
     *
     * @param jsonElement                {@link JsonElement}
     * @param type                       反序列化的目标类型
     * @param jsonDeserializationContext 用于反序列化的上下文环境
     * @return {@link Location}
     * @throws JsonParseException 如果反序列化失败则抛出此异常
     */
    @Override
    public Location deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return deserialize(jsonElement);
    }
}
