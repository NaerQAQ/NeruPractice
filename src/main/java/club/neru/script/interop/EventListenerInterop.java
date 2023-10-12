package club.neru.script.interop;

import club.neru.Mochi;
import club.neru.script.interfaces.InteropInterface;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

/**
 * 允许 JavaScript 处理 Bukkit 事件。
 *
 * @author 2000000 / NaerQAQ
 * @since 2023/4/28
 */
@Getter
@Setter
@Accessors(chain = true)
public class EventListenerInterop implements InteropInterface {
    /**
     * 监听列表。
     */
    @Getter
    private static final ConcurrentLinkedQueue<EventListenerInterop> eventListeners =
            new ConcurrentLinkedQueue<>();

    /**
     * 是否传递已取消的事件。
     */
    private boolean ignoreCancelled = false;

    /**
     * 当监听器监听到事件时要执行的代码。
     */
    private Consumer<Event> executor;

    /**
     * 事件优先级。
     */
    private EventPriority eventPriority = EventPriority.NORMAL;

    /**
     * 监听事件类。
     */
    private Class<? extends Event> event;

    /**
     * 实例。
     */
    private Listener listener = new Listener() {
    };

    /**
     * 注册事件监听。
     */
    @Override
    public void register() {
        eventListeners.add(this);

        Bukkit.getPluginManager().registerEvent(
                event,
                listener,
                eventPriority,
                (listener, event) -> executor.accept(event),
                Mochi.getInstance()
        );
    }

    /**
     * 注销事件监听。
     */
    @Override
    public void unregister() {
        eventListeners.remove(this);
        HandlerList.unregisterAll(listener);
    }
}
