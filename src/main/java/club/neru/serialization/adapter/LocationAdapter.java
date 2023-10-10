package club.neru.serialization.adapter;

import club.neru.register.annotations.AutoRegisterTypeAdapter;
import com.google.gson.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

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
public class LocationAdapter implements JsonSerializer<Location>, JsonDeserializer<Location> {
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
        if (location == null) {
            return null;
        }

        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("world", location.getWorld().getName());
        jsonObject.addProperty("x", location.getX());
        jsonObject.addProperty("y", location.getY());
        jsonObject.addProperty("z", location.getZ());
        jsonObject.addProperty("yaw", location.getYaw());
        jsonObject.addProperty("pitch", location.getPitch());

        return jsonObject;
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
        if (jsonElement == null || !jsonElement.isJsonObject()) {
            return (null);
        }

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        World world = Bukkit.getWorld(jsonObject.get("world").getAsString());

        double x = jsonObject.get("x").getAsDouble();
        double y = jsonObject.get("y").getAsDouble();
        double z = jsonObject.get("z").getAsDouble();

        float yaw = jsonObject.get("yaw").getAsFloat();
        float pitch = jsonObject.get("pitch").getAsFloat();

        return new Location(world, x, y, z, yaw, pitch);
    }
}
