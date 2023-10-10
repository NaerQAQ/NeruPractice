package club.neru.serialization.adapter;

import club.neru.annotations.AutoRegisterTypeAdapter;
import club.neru.serialization.interfaces.SerializableInterface;
import com.google.gson.*;
import lombok.Cleanup;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.Base64;

/**
 * {@link ItemStack} 类型适配器。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/9
 */
@AutoRegisterTypeAdapter
@SuppressWarnings("unused")
public class ItemStackAdapter implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {
    /**
     * 该适配器支持类型的类。
     */
    public final static Class<?> TYPE = ItemStack.class;

    /**
     * 将 {@link ItemStack} 对象序列化为 {@link JsonElement} 对象。
     *
     * @param itemStack                {@link ItemStack}
     * @param type                     序列化的目标类型
     * @param jsonSerializationContext 用于序列化的上下文环境
     * @return {@link JsonElement}
     */
    @Override
    public JsonElement serialize(ItemStack itemStack, Type type, JsonSerializationContext jsonSerializationContext) {
        Gson normalGson = SerializableInterface.NORMAL_GSON;

        try {
            @Cleanup ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            @Cleanup BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeObject(itemStack);

            byte[] bytes = outputStream.toByteArray();
            byte[] encode = Base64.getEncoder().encode(bytes);

            return new JsonPrimitive(normalGson.toJson(encode));
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * 将 {@link JsonElement} 对象反序列化为 {@link ItemStack} 对象。
     *
     * @param jsonElement                {@link JsonElement}
     * @param type                       反序列化的目标类型
     * @param jsonDeserializationContext 用于反序列化的上下文环境
     * @return {@link ItemStack}
     * @throws JsonParseException 如果反序列化失败则抛出此异常
     */
    @Override
    public ItemStack deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Gson normalGson = SerializableInterface.NORMAL_GSON;

        String jsonString = jsonElement.getAsString();

        byte[] decode = normalGson.fromJson(jsonString, byte[].class);
        byte[] bytes = Base64.getDecoder().decode(decode);

        try {
            @Cleanup ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            @Cleanup BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

            return (ItemStack) dataInput.readObject();
        } catch (Exception exception) {
            return new ItemStack(Material.AIR);
        }
    }
}
