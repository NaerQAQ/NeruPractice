package club.neru.register.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解用于标记监听器类，使其能够自动注册。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/7/31
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoRegisterListener {
}
