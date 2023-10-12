// noinspection JSUnusedGlobalSymbols,JSUnresolvedReference

/**
 * @fileoverview
 * 玩家数据处理监听器。
 *
 * @version        1.0
 * @since          2023/10/12
 * @author         NaerQAQ
 */

/**
 * 获取特定上下文的名称。
 *
 * @returns {string | null} - 返回上下文的名称，null 则表示该 js 的上下文。
 */
const getContext = () => null;

// Bukkit
const EventPriority = Java.type("org.bukkit.event.EventPriority");
const PlayerJoinEvent = Java.type("org.bukkit.event.player.PlayerJoinEvent");
const PlayerQuitEvent = Java.type("org.bukkit.event.player.PlayerQuitEvent");

// 脚本交互
const EventListenerInterop = Java.type("club.neru.script.interop.EventListenerInterop");

// 工具
const QuickUtils = Java.type("club.neru.utils.common.QuickUtils");

// 线程调度
const Scheduler = Java.type("club.neru.thread.Scheduler");
const SchedulerExecutionMode = Java.type("club.neru.thread.enums.SchedulerExecutionMode");
const SchedulerTypeEnum = Java.type("club.neru.thread.enums.SchedulerTypeEnum");

// 数据
const PlayerDataHandler = Java.type("club.neru.player.playerdata.PlayerDataHandler");

// 处理程序
const AccessServiceHandler = Java.type("club.neru.player.accessservice.AccessServiceHandler");

/**
 * 脚本初始化完成后自动执行的方法。
 */
function initializationComplete() {
    // 玩家进入
    new EventListenerInterop()
        .setEvent(PlayerJoinEvent)
        .setExecutor(onPlayerJoin)
        .setEventPriority(EventPriority.LOWEST)
        .register();

    // 玩家退出
    new EventListenerInterop()
        .setEvent(PlayerQuitEvent)
        .setExecutor(onPlayerQuit)
        .register();
}

/**
 * 处理玩家进入事件
 *
 * @param event - PlayerJoinEvent
 */
function onPlayerJoin(event) {
    const player = event.getPlayer();
    const uuid = player.getUniqueId();

    // 异步执行，获取玩家数据并处理进入坐标等
    new Scheduler()
        .setSchedulerTypeEnum(SchedulerTypeEnum.RUN)
        .setSchedulerExecutionMode(SchedulerExecutionMode.ASYNC)
        .setRunnable(() => {
            PlayerDataHandler.get(uuid);
            AccessServiceHandler.getAccessServiceData().apply(player);
        })
        .run();
}

/**
 * 处理玩家退出事件
 *
 * @param event - PlayerQuitEvent
 */
function onPlayerQuit(event) {
    const player = event.getPlayer();
    const uuid = player.getUniqueId();

    // 异步执行，删除数据
    new Scheduler()
        .setSchedulerTypeEnum(SchedulerTypeEnum.RUN)
        .setSchedulerExecutionMode(SchedulerExecutionMode.ASYNC)
        .setRunnable(() => PlayerDataHandler.remove(uuid))
        .run();
}
