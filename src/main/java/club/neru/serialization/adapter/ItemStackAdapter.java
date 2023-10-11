package club.neru.serialization.adapter;

import club.neru.serialization.annotations.AutoRegisterTypeAdapter;
import club.neru.serialization.interfaces.BukkitObjectSerializableInterface;
import com.google.gson.*;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;

/**
 * {@link ItemStack} 类型适配器。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/9
 */
@AutoRegisterTypeAdapter
@SuppressWarnings("unused")
public class ItemStackAdapter implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack>, BukkitObjectSerializableInterface {
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
        return serialize(itemStack);
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
        return deserialize(jsonElement);
    }
}
