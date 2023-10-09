package club.neru.arena.copy.objects;

import club.neru.arena.copy.interfaces.ArenaParentInterface;
import club.neru.utils.serialization.SerializableInterface;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 母竞技场对象。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/9
 */
@Getter
@Setter
@Accessors(chain = true)
public class ArenaParent extends ArenaImpl implements ArenaParentInterface, SerializableInterface {
    /**
     * 是否允许建筑。
     */
    private boolean canBuild;

    /**
     * 最低死亡高度。
     */
    private int minDeathHeight;

    /**
     * 最高建筑高度。
     */
    private int maxBuildHeight;
}
