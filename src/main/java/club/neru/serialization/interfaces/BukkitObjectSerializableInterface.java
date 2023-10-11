package club.neru.serialization.interfaces;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import lombok.Cleanup;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

/**
 * 序列化接口。
 *
 * <p>
 * 该接口主要针对 Bukkit 对象的序列化与反序列化。
 * </p>
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/11
 */
public interface BukkitObjectSerializableInterface {
    /**
     * 序列化。
     *
     * @param object 要序列化的对象
     * @return {@link JsonPrimitive}，如果失败则返回 {@code null}
     * @see BukkitObjectOutputStream
     */
    default JsonPrimitive serialize(Object object) {
        Gson normalGson = SerializableInterface.NORMAL_GSON;

        try {
            @Cleanup ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            @Cleanup BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeObject(object);

            byte[] bytes = outputStream.toByteArray();
            byte[] encode = Base64.getEncoder().encode(bytes);

            return new JsonPrimitive(normalGson.toJson(encode));
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * 反序列化。
     *
     * @param jsonElement {@link JsonElement}
     * @param <T>         反序列化后的对象类型
     * @return 反序列化后的对象，如果失败则返回 {@code null}
     * @see BukkitObjectInputStream
     */
    @SuppressWarnings("unchecked")
    default <T> T deserialize(JsonElement jsonElement) {
        Gson normalGson = SerializableInterface.NORMAL_GSON;

        String jsonString = jsonElement.getAsString();

        byte[] decode = normalGson.fromJson(jsonString, byte[].class);
        byte[] bytes = Base64.getDecoder().decode(decode);

        try {
            @Cleanup ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            @Cleanup BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

            return (T) dataInput.readObject();
        } catch (Exception exception) {
            return null;
        }
    }
}
