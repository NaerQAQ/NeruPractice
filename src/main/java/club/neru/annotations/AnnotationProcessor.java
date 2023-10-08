package club.neru.annotations;

import club.neru.NeruPractice;
import lombok.experimental.UtilityClass;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * 该类是用于处理注解的工具类。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/7/31
 */
@UtilityClass
public class AnnotationProcessor {
    /**
     * 获取带有指定注解的类集合。
     *
     * @param annotation 要查找的注解类的 Class 对象
     * @return 包含所有带有指定注解的类的集合
     */
    public static Set<Class<?>> getClassesWithAnnotation(Class<? extends Annotation> annotation) {
        // 创建 Reflections 对象扫描指定包下的所有类，并使用 getTypesAnnotatedWith 方法获取带有指定注解的类集合
        return new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(NeruPractice.class.getPackage().getName())))
                .getTypesAnnotatedWith(annotation);
    }
}
