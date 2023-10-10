package club.neru.serialization.interfaces;

import club.neru.annotations.AnnotationProcessor;
import club.neru.annotations.AutoRegisterTypeAdapter;
import club.neru.utils.common.QuickUtils;
import club.neru.utils.common.enums.ConsoleMessageTypeEnum;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Set;

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
     * 普通的 {@link Gson} 对象。
     */
    Gson NORMAL_GSON = new GsonBuilder().create();

    /**
     * 配置好的 {@link Gson} 对象。
     */
    Gson GSON = getGson();

    /**
     * 获取配置好的 {@link Gson} 对象。
     *
     * <p>
     * 将自动注册使用 {@link AutoRegisterTypeAdapter} 注解的类型适配器。
     * </p>
     *
     * @return 配置好的 {@link Gson} 对象
     */
    static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Set<Class<?>> classesWithAnnotation = AnnotationProcessor.getClassesWithAnnotation(AutoRegisterTypeAdapter.class);

        classesWithAnnotation.forEach(aClass -> {
            String className = aClass.getName();

            try {
                Object typeAdapter = aClass.getDeclaredConstructor().newInstance();
                Class<?> typeClass = (Class<?>) aClass.getField("TYPE").get(null);

                /*
                 * 在经历了一个小时的 "declares multiple JSON fields named c" 后发现
                 *
                 * registerTypeAdapter 只能处理指定的类型 不会涉及到该类型的子类或父类
                 * registerTypeHierarchyAdapter 的不同之处在于，它会处理指定类型以及其所有的子类
                 *
                 * 之前一直用 registerTypeAdapter, ItemStack 无法序列化是因为没有 CraftItemStack 的适配器
                 */
                gsonBuilder.registerTypeHierarchyAdapter(typeClass, typeAdapter);

                QuickUtils.sendMessage(
                        ConsoleMessageTypeEnum.NORMAL,
                        "Type adapter successfully registered: <class_name>.",
                        "<class_name>", className
                );
            } catch (Exception exception) {
                String message = exception.getMessage();

                QuickUtils.sendMessage(
                        ConsoleMessageTypeEnum.ERROR,
                        "Unable to register type adapter: <class_name>, message: <message>.",
                        "<class_name>", className,
                        "message", message
                );
            }
        });

        return gsonBuilder
                .serializeNulls()
                .create();
    }

    /**
     * 从 Json 字符串反序列化为对象。
     *
     * @param json  Json 字符串
     * @param clazz 对象类
     * @param <T>   对象类型
     * @return 反序列化后的对象
     */
    static <T> T fromJson(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }

    /**
     * 将对象序列化为 Json 字符串。
     *
     * @return 表示对象的 Json 字符串
     */
    default String toJson() {
        return GSON.toJson(this);
    }
}
