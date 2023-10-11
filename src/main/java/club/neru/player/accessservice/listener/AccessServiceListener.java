package club.neru.player.accessservice.listener;

import club.neru.player.accessservice.AccessServiceHandler;
import club.neru.player.accessservice.data.AccessServiceData;
import club.neru.player.playerdata.PlayerDataHandler;
import club.neru.register.annotations.AutoRegisterListener;
import club.neru.thread.Scheduler;
import club.neru.thread.enums.SchedulerExecutionMode;
import club.neru.thread.enums.SchedulerTypeEnum;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

/**
 * 玩家数据处理监听器。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/7
 */
@AutoRegisterListener
public class AccessServiceListener implements Listener {
    /**
     * 玩家进入游戏时的数据处理。
     *
     * @param event {@link PlayerJoinEvent}
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        new Scheduler()
                .setSchedulerTypeEnum(SchedulerTypeEnum.RUN)
                .setSchedulerExecutionMode(SchedulerExecutionMode.ASYNC)
                .setRunnable(() -> {
                    PlayerDataHandler.get(uuid);

                    AccessServiceData accessServiceData = AccessServiceHandler.getAccessServiceData();
                    if (accessServiceData != null) {
                        accessServiceData.apply(player);
                    }
                })
                .run();
    }

    /**
     * 玩家退出游戏时的数据处理。
     *
     * @param event {@link PlayerQuitEvent}
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        new Scheduler()
                .setSchedulerTypeEnum(SchedulerTypeEnum.RUN)
                .setSchedulerExecutionMode(SchedulerExecutionMode.ASYNC)
                .setRunnable(() -> PlayerDataHandler.remove(uuid))
                .run();
    }
}
