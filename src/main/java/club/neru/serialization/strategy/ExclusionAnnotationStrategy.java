package club.neru.serialization.strategy;

import club.neru.serialization.annotations.AutoAddSerializationExclusionStrategy;
import club.neru.serialization.strategy.annotations.ExclusionField;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * 序列化排除策略。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/11
 */
@SuppressWarnings("unused")
@AutoAddSerializationExclusionStrategy
public class ExclusionAnnotationStrategy implements ExclusionStrategy {
    /**
     * 是否应该将字段排除在序列化之外。
     *
     * <p>
     * 将排除使用 {@link ExclusionField} 注解的字段。
     * </p>
     *
     * @param fieldAttributes 被考虑字段的属性
     * @return 是否排除该字段
     */
    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        return fieldAttributes.getAnnotation(ExclusionField.class) != null;
    }

    /**
     * 是否应该将类排除在序列化之外。
     *
     * @param aClass 正在考虑的类
     * @return 始终为 {@code false}
     */
    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        return false;
    }
}
