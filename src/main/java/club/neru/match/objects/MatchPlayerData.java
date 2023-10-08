package club.neru.match.objects;

import club.neru.utils.serialization.SerializableInterface;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 比赛内玩家数据对象。
 *
 * <p>
 * 该对象将允许序列化与反序列化，以方便后续添加回放。
 * </p>
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/8
 */
@Getter
@Setter
@Accessors(chain = true)
public class MatchPlayerData implements SerializableInterface {
    /**
     * 攻击数。
     */
    private int hits;

    /**
     * 暴击攻击数。
     */
    private int criticalHits;

    /**
     * 攻击伤害。
     */
    private double attackDamage;

    /**
     * 受攻击数。
     */
    private int hitsReceived;

    /**
     * 受攻击伤害。
     */
    private double damageReceived;

    /**
     * 真实受攻击伤害。
     */
    private double finalDamageReceived;

    /**
     * 瞄准率。
     */
    private double aimAccuracy;

    /**
     * wTap 触发率。
     */
    private double wTapTriggerRate;

    /**
     * Jump Reset 触发率。
     */
    private double jumpResetTriggerRate;

    /**
     * 药水 miss 率。
     */
    private double potionMissRate;

    /**
     * 对手 {@link MatchPlayerData} 对象。
     *
     * <p>
     * 使用 {@link ConcurrentLinkedQueue} 容器保证线程安全。
     * </p>
     */
    private ConcurrentLinkedQueue<MatchPlayerData> opponentPlayerProfiles;
}
