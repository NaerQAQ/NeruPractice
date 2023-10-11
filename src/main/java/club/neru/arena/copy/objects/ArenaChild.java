package club.neru.arena.copy.objects;

import club.neru.arena.copy.enums.ArenaState;
import club.neru.arena.copy.interfaces.ArenaChildInterface;
import club.neru.arena.copy.interfaces.ArenaParentInterface;
import club.neru.serialization.interfaces.SerializableInterface;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 子竞技场对象。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/9
 */
@Getter
@Setter
@Accessors(chain = true)
public class ArenaChild extends ArenaImpl implements ArenaChildInterface, SerializableInterface {
    /**
     * 竞技场状态。
     */
    private ArenaState arenaState;

    /**
     * 获取母竞技场。
     *
     * @return 母竞技场对象
     */
    @Override
    public ArenaParent getArenaParent() {
        return ArenaParentInterface.getArenaParent(getArenaParentName());
    }

    /**
     * 清空该子竞技场。
     */
    @Override
    public void clean() {
        super.clean();
    }

    /**
     * 重置该子竞技场。
     */
    public void reset() {
        setArenaState(ArenaState.RESETTING);

        clean();
        getArenaParent()
                .copy(getArenaParentNumber());

        setArenaState(ArenaState.AVAILABLE);
    }
}
