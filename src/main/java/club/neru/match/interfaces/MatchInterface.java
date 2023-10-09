package club.neru.match.interfaces;

import club.neru.arena.copy.objects.ArenaChild;
import club.neru.match.objects.MatchPlayerData;
import club.neru.thread.Scheduler;
import club.neru.thread.enums.SchedulerExecutionMode;
import club.neru.thread.enums.SchedulerTypeEnum;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 比赛接口。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/8
 */
public interface MatchInterface {
    /**
     * 获取本局比赛的 UUID.
     *
     * @return UUID
     */
    String getMatchUUID();

    /**
     * 获取本场比赛的子地图。
     *
     * @return {@link ArenaChild}
     */
    ArenaChild getArenaChild();

    /**
     * 开始比赛。
     *
     * <p>
     * 调用 {@link #init()} 方法后将以 {@code 5ticks} 的间隔允许 {@link #checkEndConditions()} 方法进行结束检查。
     * 若为 {@code true} 则执行 {@link #end()} 方法结束对局。
     * </p>
     */
    default void start() {
        Scheduler.builder()
                .setSchedulerTypeEnum(SchedulerTypeEnum.RUN)
                .setSchedulerExecutionMode(SchedulerExecutionMode.ASYNC)
                .setRunnable(this::init)
                .build()
                .run();

        Scheduler.builder()
                .setSchedulerTypeEnum(SchedulerTypeEnum.TIMER)
                .setSchedulerExecutionMode(SchedulerExecutionMode.ASYNC)
                .setDelay(0)
                .setPeriod(5)
                .setBukkitRunnable(new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (checkEndConditions()) {
                            end();
                            cancel();
                        }
                    }
                })
                .build()
                .run();
    }

    /**
     * 获取玩家比赛数据对象。
     *
     * @param uuid 玩家 {@link UUID}
     * @return {@link MatchPlayerData}
     */
    default MatchPlayerData getPlayerProfile(UUID uuid) {
        Map<UUID, MatchPlayerData> playerProfileMap = getPlayerProfileMap();
        MatchPlayerData matchPlayerData = playerProfileMap.get(uuid);

        if (matchPlayerData != null) {
            return matchPlayerData;
        }

        matchPlayerData = new MatchPlayerData();
        playerProfileMap.put(uuid, matchPlayerData);
        return matchPlayerData;
    }

    /**
     * 该哈希表将存储玩家 {@link UUID} 与玩家比赛数据对象 {@link MatchPlayerData}.
     *
     * <p>
     * 使用 {@link ConcurrentHashMap} 容器保证线程安全。
     * </p>
     *
     * @return 玩家比赛数据对象哈希表
     */
    ConcurrentHashMap<UUID, MatchPlayerData> getPlayerProfileMap();

    /**
     * 初始化比赛。
     */
    void init();

    /**
     * 结束比赛。
     */
    void end();

    /**
     * 比赛结束判断。
     *
     * @return 若为 {@code true} 则以为结束比赛。
     */
    boolean checkEndConditions();
}
