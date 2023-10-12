package club.neru.script.objects.objects;

import club.neru.basic.impl.ObjectNameImpl;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.graalvm.polyglot.Context;

/**
 * 命名的 {@link Context} 对象。
 *
 * @author NaerQAQ / 2000000
 * @version 1.0
 * @since 2023/10/12
 */
@Getter
@Setter
@Accessors(chain = true)
public class NamedContext extends ObjectNameImpl {
    /**
     * {@link Context} 对象。
     */
    private Context context;
}
