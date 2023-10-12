package club.neru.script.objects.objects;

import club.neru.basic.impl.ObjectNameImpl;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

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
public class CustomContext extends ObjectNameImpl {
    /**
     * {@link Context} 对象。
     */
    private Context context;

    /**
     * {@link ScriptableObject} 对象。
     */
    private ScriptableObject scriptableObject;
}
