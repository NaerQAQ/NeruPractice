package club.neru.script.objects.handler;

import club.neru.script.objects.objects.NamedContext;
import lombok.Getter;
import org.graalvm.polyglot.Context;

import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 命名的 {@link Context} 对象处理程序。
 *
 * @author NaerQAQ / 2000000
 * @version 1.0
 * @see NamedContext
 * @since 2023/10/12
 */
public class NamedContextHandler {
    /**
     * 所有的 {@link NamedContextHandler} 对象。
     */
    @Getter
    private static final ConcurrentLinkedQueue<NamedContext> namedContexts =
            new ConcurrentLinkedQueue<>();

    /**
     * 获取指定名称的 {@link NamedContextHandler} 对象。
     *
     * @param name 指定名称
     * @return 指定名称的 {@link NamedContextHandler} 对象
     */
    public static NamedContext getNamedContext(String name) {
        return namedContexts.stream()
                .filter(namedContext -> namedContext.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * 获取指定名称 {@link NamedContextHandler} 对象的 {@link Context} 对象。
     *
     * @param name 指定名称
     * @return 指定名称 {@link NamedContextHandler} 对象的 {@link Context} 对象
     */
    public static Context getContext(String name) {
        return Optional.ofNullable(getNamedContext(name))
                .map(NamedContext::getContext)
                .orElse(null);
    }

    /**
     * 添加 {@link NamedContext} 对象。
     *
     * @param name    名称
     * @param context {@link Context} 对象
     */
    public static void addContext(String name, Context context) {
        namedContexts.add(
                new NamedContext()
                        .setName(name)
                        .to(NamedContext.class)
                        .setContext(context)
        );
    }

    /**
     * 删除指定名称的 {@link NamedContext} 对象。
     *
     * @param name 名称
     */
    public static void removeContext(String name) {
        namedContexts.removeIf(context -> context.getName().equals(name));
    }
}
