package club.neru.script.objects.handler;

import club.neru.script.objects.objects.CustomContext;
import org.mozilla.javascript.Context;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 命名的 {@link Context} 对象处理程序。
 *
 * @author NaerQAQ / 2000000
 * @version 1.0
 * @see CustomContext
 * @since 2023/10/12
 */
public class CustomContextHandler {
    /**
     * 所有的 {@link CustomContextHandler} 对象。
     */
    public static final ConcurrentLinkedQueue<CustomContext> CUSTOM_CONTEXTS =
            new ConcurrentLinkedQueue<>();

    /**
     * 获取指定名称的 {@link CustomContextHandler} 对象。
     *
     * @param name 指定名称
     * @return 指定名称的 {@link CustomContextHandler} 对象
     */
    public static CustomContext getCustomContext(String name) {
        return CUSTOM_CONTEXTS.stream()
                .filter(customContext -> customContext.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * 添加 {@link CustomContext} 对象。
     *
     * @param customContext {@link CustomContext} 对象
     */
    public static void addCustomContext(CustomContext customContext) {
        CUSTOM_CONTEXTS.add(customContext);
    }

    /**
     * 删除指定名称的 {@link CustomContext} 对象。
     *
     * @param name 名称
     */
    public static void removeCustomContext(String name) {
        removeCustomContext(getCustomContext(name));
    }

    /**
     * 删除指定的 {@link CustomContext} 对象。
     *
     * @param customContext {@link CustomContext} 对象
     */
    public static void removeCustomContext(CustomContext customContext) {
        if (customContext == null) {
            return;
        }

        // 释放资源
        customContext.getContext().close();

        CUSTOM_CONTEXTS.remove(customContext);
    }
}
