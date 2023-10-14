package club.neru.script.interop.listener;

import club.neru.script.interfaces.InteropInterface;
import club.neru.script.interop.listener.objects.EasyEventListenerData;
import club.neru.script.interop.listener.objects.EventRegistrationInfo;
import club.neru.thread.Scheduler;
import club.neru.thread.enums.SchedulerExecutionMode;
import club.neru.thread.enums.SchedulerTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.event.Event;

import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

/**
 * 简易事件监听器交互。
 *
 * <p>
 * 与 {@link EventListenerInterop} 的区别在于，后者会为每个监听事件
 * 创建一个新的监听器，而前者仅在多次重复监听同一事件时创建一个监听器。
 * </p>
 *
 * @param <T> 事件类型的泛型参数，必须是 {@link Event} 或其子类。
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/14
 */
@Getter
@Setter
@Accessors(chain = true)
@SuppressWarnings("unchecked")
public class EasyEventListenerInterop<T extends Event> implements InteropInterface {
    /**
     * 所有简易监听器。
     */
    public static final ConcurrentLinkedQueue<EasyEventListenerInterop<?>> EASY_EVENT_LISTENERS =
            new ConcurrentLinkedQueue<>();

    /**
     * 所有简易监听器数据。
     */
    public static final ConcurrentLinkedQueue<EasyEventListenerData> EASY_EVENT_LISTENERS_DATA =
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
     * 通过名称获取简易事件监听器数据。
     *
     * @param name 名称
     * @return 简易事件监听器数据
     */
    public static EasyEventListenerData getEasyEventListenerDataWithName(String name) {
        return EASY_EVENT_LISTENERS_DATA.stream()
                .filter(data ->
                        data.getEventListener().getEventRegistrationInfo().getName().equals(name)
                )
                .findFirst()
                .orElse(null);
    }

    /**
     * 处理事件的方法。
     *
     * @param event 事件对象
     */
    public void accept(Event event) {
        executor.accept((T) event);
    }

    /**
     * 注册事件监听。
     */
    public void register() {
        EASY_EVENT_LISTENERS.add(this);

        // 在已有监听器中通过 EventListenerInterop 获取 EventRegistrationInfo 一样的对象
        Optional<EasyEventListenerData> optionalData = EASY_EVENT_LISTENERS_DATA.stream()
                .filter(data ->
                        data.getEventListener().getEventRegistrationInfo().equals(
                                eventRegistrationInfo
                        )
                )
                .findFirst();

        // 找到了则代表有 EasyEventListenerData 对象，将其添加进与之关联的简易事件监听器
        if (optionalData.isPresent()) {
            optionalData.get().getEasyEventListeners().add(this);
        } else {
            // 如果没找到则创建一个新提供服务的 EventListenerInterop
            EventListenerInterop<T> eventListenerInterop = new EventListenerInterop<T>()
                    .setEventRegistrationInfo(eventRegistrationInfo)
                    // 从中获取所有 EventRegistrationInfo 一样的对象
                    .setExecutor(event -> EASY_EVENT_LISTENERS_DATA.stream()
                            .filter(data ->
                                    data.getEventListener().getEventRegistrationInfo().equals(
                                            eventRegistrationInfo
                                    )
                            )
                            // 遍历异步调用 accept
                            .forEach(data -> data.getEasyEventListeners().forEach(easyEventListenerInterop -> new Scheduler()
                                            .setSchedulerTypeEnum(SchedulerTypeEnum.RUN)
                                            .setSchedulerExecutionMode(SchedulerExecutionMode.ASYNC)
                                            .setRunnable(() -> easyEventListenerInterop.accept(event))
                                            .run()
                                    )
                            )
                    );

            // 创建一个新队列并添加进去
            ConcurrentLinkedQueue<EasyEventListenerInterop<?>> easyEventListenerInterops =
                    new ConcurrentLinkedQueue<>();
            easyEventListenerInterops.add(this);

            // 创建一个新的 EasyEventListenerData 对象
            EasyEventListenerData newEasyEventListenerData = new EasyEventListenerData()
                    .setEventListener(eventListenerInterop)
                    .setEasyEventListeners(easyEventListenerInterops);

            // 注册提供服务的常规事件监听器对象，并且添加 newEasyEventListenerData
            eventListenerInterop.register();
            EASY_EVENT_LISTENERS_DATA.add(newEasyEventListenerData);
        }
    }

    /**
     * 注销事件监听。
     */
    public void unregister() {
        Optional.ofNullable(getEasyEventListenerData())
                .ifPresent(listenerData -> listenerData.remove(this));
    }

    /**
     * 获取简易事件监听器数据。
     *
     * @return 简易事件监听器数据
     */
    public EasyEventListenerData getEasyEventListenerData() {
        return getEasyEventListenerDataWithName(eventRegistrationInfo.getName());
    }
}
