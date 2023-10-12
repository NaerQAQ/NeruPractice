package club.neru.script.objects.handler;

import club.neru.script.objects.objects.ScriptExecutor;
import lombok.Getter;
import org.graalvm.polyglot.Context;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 脚本执行器处理程序。
 *
 * @author NaerQAQ / 2000000
 * @version 1.0
 * @see ScriptExecutor
 * @since 2023/10/12
 */
public class ScriptExecutorHandler {
    /**
     * 所有的 {@link ScriptExecutorHandler} 对象。
     */
    @Getter
    private static final ConcurrentLinkedQueue<ScriptExecutor> scriptExecutors =
            new ConcurrentLinkedQueue<>();

    /**
     * 添加 {@link ScriptExecutor} 对象。
     *
     * @param scriptExecutor {@link ScriptExecutor}
     */
    public static void addScriptExecutor(ScriptExecutor scriptExecutor) {
        scriptExecutors.add(scriptExecutor);
    }

    /**
     * 删除 {@link ScriptExecutor} 对象。
     *
     * @param scriptExecutor {@link ScriptExecutor}
     */
    public static void removeScriptExecutor(ScriptExecutor scriptExecutor) {
        scriptExecutors.remove(scriptExecutor);
    }

    /**
     * 调用所有脚本的指定函数。
     *
     * @param function 函数名称
     * @return 结果
     */
    public static void invoke(String function) {
        getScriptExecutors().forEach(
                scriptExecutor -> scriptExecutor.invoke(function)
        );
    }

    /**
     * 使用 {@link ScriptExecutor#invokeWithNormalContext(String)} )} 调用所有脚本的指定函数。
     *
     * @param function 函数名称
     * @return 结果
     */
    public static void invokeWithNormalContext(String function) {
        getScriptExecutors().forEach(
                scriptExecutor -> scriptExecutor.invokeWithNormalContext(function)
        );
    }

    /**
     * 使用指定 {@link Context} 对象调用所有脚本的指定函数。
     *
     * @param function 函数名称
     * @param context  指定 {@link Context} 对象
     * @return 结果
     */
    public static void invoke(String function, Context context) {
        getScriptExecutors().forEach(
                scriptExecutor -> scriptExecutor.invoke(function, context)
        );
    }
}
