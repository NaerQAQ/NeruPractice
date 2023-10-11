package club.neru.serialization.strategy.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 序列化字段排除注解。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/11
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExclusionField {
}
