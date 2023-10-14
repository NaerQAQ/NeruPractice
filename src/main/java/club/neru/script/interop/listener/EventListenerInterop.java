package club.neru.script.interop.listener;

import club.neru.Mochi;
import club.neru.script.interfaces.InteropInterface;
import club.neru.script.interop.listener.objects.EventRegistrationInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

/**
 * 常规事件监听器交互。
 *
 * @param <T> 事件类型的泛型参数，必须是 {@link Event} 或其子类。
 * @author 2000000 / NaerQAQ
 * @since 2023/4/28
 */
@Getter
@Setter
@Accessors(chain = true)
public class EventListenerInterop<T extends Event> implements InteropInterface {
    /**
     * 监听列表。
     */
    public static final ConcurrentLinkedQueue<EventListenerInterop<?>> EVENT_LISTENERS =
            new ConcurrentLinkedQueue<>();

    /**
     * 执行的函数式接口。
     */
    private Consumer<T> executor;

    /**
     * 监听器数据。
     */
    private EventRegistrationInfo eventRegistrationInfo;

    /**
     * 注册事件监听。
     */
    @Override
    @SuppressWarnings("unchecked")
    public void register() {
        EVENT_LISTENERS.add(this);

        Bukkit.getPluginManager().registerEvent(
                eventRegistrationInfo.getEventClass(),
                eventRegistrationInfo.getListener(),
                eventRegistrationInfo.getEventPriority(),
                (listener, event) -> executor.accept((T) event),
                Mochi.getInstance(),
                eventRegistrationInfo.isIgnoreCancelled()
        );
    }

    /**
     * 注销事件监听。
     */
    @Override
    public void unregister() {
        EVENT_LISTENERS.remove(this);
        HandlerList.unregisterAll(eventRegistrationInfo.getListener());
    }
}
