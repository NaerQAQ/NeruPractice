package club.neru.arena.copy.objects;

import club.neru.arena.copy.interfaces.ArenaChildInterface;
import club.neru.utils.serialization.SerializableInterface;
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
}
