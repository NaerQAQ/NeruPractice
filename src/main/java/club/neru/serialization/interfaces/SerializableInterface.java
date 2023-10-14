package club.neru.serialization.interfaces;

import club.neru.annotations.AnnotationProcessor;
import club.neru.serialization.annotations.AutoAddSerializationExclusionStrategy;
import club.neru.serialization.annotations.AutoRegisterTypeAdapter;
import club.neru.utils.common.text.QuickUtils;
import club.neru.utils.common.text.enums.ConsoleMessageTypeEnum;
import com.google.gson.ExclusionStrategy;
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
    Gson NORMAL_GSON = new GsonBuilder()
            .serializeNulls()
            .create();

    /**
     * 配置好的 {@link Gson} 对象。
     */
    Gson GSON = getGson();

    /**
     * 获取配置好的 {@link Gson} 对象。
     *
     * @return 配置好的 {@link Gson} 对象
     */
    static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        handleAdapter(gsonBuilder);
        handleSerializationExclusionStrategy(gsonBuilder);

        return gsonBuilder
                .serializeNulls()
                .create();
    }

    /**
     * 注册类型适配器。
     *
     * <p>
     * 将自动注册使用 {@link AutoRegisterTypeAdapter} 注解的类型适配器。
     * </p>
     *
     * @return 注册好类型适配器的 {@link GsonBuilder} 对象
     */
    @SuppressWarnings("UnusedReturnValue")
    static GsonBuilder handleAdapter(GsonBuilder gsonBuilder) {
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

        return gsonBuilder;
    }

    /**
     * 添加序列化排除策略。
     *
     * <p>
     * 将自动添加使用 {@link AutoAddSerializationExclusionStrategy} 注解的排除策略。
     * </p>
     *
     * @return 添加好排除策略的 {@link GsonBuilder} 对象
     */
    @SuppressWarnings("UnusedReturnValue")
    static GsonBuilder handleSerializationExclusionStrategy(GsonBuilder gsonBuilder) {
        Set<Class<?>> classesWithAnnotation = AnnotationProcessor.getClassesWithAnnotation(AutoAddSerializationExclusionStrategy.class);

        classesWithAnnotation.forEach(aClass -> {
            String className = aClass.getName();

            try {
                Object exclusionStrategyObject = aClass.getDeclaredConstructor().newInstance();
                ExclusionStrategy exclusionStrategy = (ExclusionStrategy) exclusionStrategyObject;

                gsonBuilder.addSerializationExclusionStrategy(exclusionStrategy);
                gsonBuilder.addDeserializationExclusionStrategy(exclusionStrategy);

                QuickUtils.sendMessage(
                        ConsoleMessageTypeEnum.NORMAL,
                        "Serialization exclusion strategy successfully added: <class_name>.",
                        "<class_name>", className
                );
            } catch (Exception exception) {
                String message = exception.getMessage();

                QuickUtils.sendMessage(
                        ConsoleMessageTypeEnum.ERROR,
                        "Unable to add serialization exclusion strategy: <class_name>, message: <message>.",
                        "<class_name>", className,
                        "message", message
                );
            }
        });

        return gsonBuilder;
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
