package club.neru.arena.copy.interfaces;

import club.neru.arena.copy.objects.ArenaChild;

import java.util.concurrent.ConcurrentLinkedQueue;

public interface ArenaParentInterface extends ArenaInterface {
    /**
     * 是否允许建筑。
     *
     * @return 是否允许建筑
     */
    boolean canBuild();

    /**
     * 获取最低死亡高度。
     *
     * @return 最低死亡高度
     */
    int getMinDeathHeight();

    /**
     * 获取最高建筑高度。
     *
     * @return 最高建筑高度
     */
    int getMaxBuildHeight();

    /**
     * 获取该竞技场的所有子竞技场对象。
     *
     * <p>
     * 使用 {@link ConcurrentLinkedQueue} 容器保证线程安全。
     * </p>
     *
     * @return {@link ArenaChild}
     */
    ConcurrentLinkedQueue<ArenaChild> getArenaChildren();
}
