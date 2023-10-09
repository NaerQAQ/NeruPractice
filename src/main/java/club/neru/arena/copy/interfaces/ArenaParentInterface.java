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
    default ConcurrentLinkedQueue<ArenaChild> getArenaChildren() {
        // TODO: 获取子竞技场
        return null;
    }

    /**
     * 复制竞技场。
     *
     * <p>
     * 复制逻辑:
     * <pre>
     *  Z ------------------------------------->
     * X  A (1,1)  A-Copy1 (1,2)  A-Copy2 (1,3)
     * |  B (2,1)  B-Copy1 (2,2)  B-Copy1 (2,3)
     * |  C (3,1)  C-Copy1 (3,2)  C-Copy1 (3,3)
     * |  D (4,1)  D-Copy1 (4,2)  D-Copy1 (4,3)
     * V
     * </pre>
     * </p>
     */
    default void copy() {
        // TODO: 复制子竞技场
    }
}
