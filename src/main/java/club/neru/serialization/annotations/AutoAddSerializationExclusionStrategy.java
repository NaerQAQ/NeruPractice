package club.neru.serialization.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解用于标记序列化排除策略，并自动添加。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/11
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoAddSerializationExclusionStrategy {
}
