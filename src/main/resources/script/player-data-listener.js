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
function getContext() {
    return null;
}

// Bukkit
const EventPriority = Packages.org.bukkit.event.EventPriority;
const PlayerJoinEvent = Packages.org.bukkit.event.player.PlayerJoinEvent;
const PlayerQuitEvent = Packages.org.bukkit.event.player.PlayerQuitEvent;

// 脚本交互
const EventListenerInterop = Packages.club.neru.script.interop.listener.EventListenerInterop;
const EventRegistrationInfo = Packages.club.neru.script.interop.listener.objects.EventRegistrationInfo;

// 工具
const QuickUtils = Packages.club.neru.utils.common.QuickUtils;

// 线程调度
const Scheduler = Packages.club.neru.thread.Scheduler;
const SchedulerExecutionMode = Packages.club.neru.thread.enums.SchedulerExecutionMode;
const SchedulerTypeEnum = Packages.club.neru.thread.enums.SchedulerTypeEnum;

// 数据
const PlayerDataHandler = Packages.club.neru.player.playerdata.PlayerDataHandler;

// 处理程序
const AccessServiceHandler = Packages.club.neru.player.accessservice.AccessServiceHandler;

/**
 * 脚本初始化完成后自动执行的方法。
 */
function initializationComplete() {
    // 玩家进入监听器的信息
    const playerJoinEventRegistrationInfo = new EventRegistrationInfo()
        .setEventClass(PlayerJoinEvent)
        .setEventPriority(EventPriority.LOWEST);

    // 玩家进入
    new EventListenerInterop()
        .setExecutor(onPlayerJoin)
        .setEventRegistrationInfo(playerJoinEventRegistrationInfo)
        .register();

    // 玩家退出监听器的信息
    const playerQuitEventRegistrationInfo = new EventRegistrationInfo()
        .setEventClass(PlayerQuitEvent);

    // 玩家退出
    new EventListenerInterop()
        .setExecutor(onPlayerQuit)
        .setEventRegistrationInfo(playerQuitEventRegistrationInfo)
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
