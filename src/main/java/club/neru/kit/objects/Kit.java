package club.neru.kit.objects;

import club.neru.arena.copy.interfaces.ArenaParentInterface;
import club.neru.arena.copy.objects.ArenaParent;
import club.neru.kit.interfaces.KitInterfaces;
import club.neru.serialization.interfaces.SerializableInterface;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

/**
 * 装备包对象。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/10
 */
@Getter
@Setter
@Accessors(chain = true)
public class Kit implements KitInterfaces, SerializableInterface {
    /**
     * 装备包名。
     */
    private String name;

    /**
     * 是否允许建筑。
     */
    private boolean canBuild;

    /**
     * 此装备包可用的竞技场名称。
     */
    private ConcurrentLinkedQueue<String> availableArenaNames;

    /**
     * 获取此装备包可用的竞技场。
     *
     * @return 此装备包可用的 {@link ArenaParent} 对象
     */
    public ConcurrentLinkedQueue<ArenaParent> getAvailableArenas() {
        return availableArenaNames.stream()
                .map(ArenaParentInterface::getArenaParent)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(ConcurrentLinkedQueue::new));
    }
}
