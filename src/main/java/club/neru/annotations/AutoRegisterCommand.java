package club.neru.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解用于标记指令类，使其能够自动注册。
 *
 * <p>
 * 带有此注解的指令类将在运行时被自动识别并注册，无需手动。
 * </p>
 *
 * <p>
 * 这是一个运行时可见的注解，因此它将在程序运行时保留，并可以通过反射机制访问。
 * </p>
 *
 * <p>
 * 注意: 该注解只能应用于类。
 * </p>
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/8
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoRegisterCommand {
}
