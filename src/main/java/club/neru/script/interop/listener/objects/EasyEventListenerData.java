package club.neru.script.interop.listener.objects;

import club.neru.script.interop.listener.EasyEventListenerInterop;
import club.neru.script.interop.listener.EventListenerInterop;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 简易事件监听器数据。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/14
 */
@Getter
@Setter
@Accessors(chain = true)
public class EasyEventListenerData {
    /**
     * 提供服务的常规事件监听器对象。
     */
    private EventListenerInterop<?> eventListener;

    /**
     * 与之关联的简易事件监听器。
     */
    private ConcurrentLinkedQueue<EasyEventListenerInterop<?>>
            easyEventListeners;

    /**
     * 从关联队列中移除一个简易事件监听器。
     *
     * @param eventListenerInterop 待移除的简易事件监听器
     */
    public void remove(EasyEventListenerInterop<?> eventListenerInterop) {
        easyEventListeners.remove(eventListenerInterop);
        EasyEventListenerInterop.EASY_EVENT_LISTENERS.remove(eventListenerInterop);

        // 空则意味着 eventListener 没有任何用处，不再执行任何东西
        if (easyEventListeners.isEmpty()) {
            // 注销这个无用的监听器
            eventListenerInterop.unregister();
        }
    }
}
