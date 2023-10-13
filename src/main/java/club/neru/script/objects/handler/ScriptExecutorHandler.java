package club.neru.script.objects.handler;

import club.neru.script.objects.objects.CustomContext;
import club.neru.script.objects.objects.ScriptExecutor;

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
    public static final ConcurrentLinkedQueue<ScriptExecutor> SCRIPT_EXECUTORS =
            new ConcurrentLinkedQueue<>();

    /**
     * 添加 {@link ScriptExecutor} 对象。
     *
     * @param scriptExecutor {@link ScriptExecutor}
     */
    public static void addScriptExecutor(ScriptExecutor scriptExecutor) {
        SCRIPT_EXECUTORS.add(scriptExecutor);
    }

    /**
     * 删除 {@link ScriptExecutor} 对象。
     *
     * @param scriptExecutor {@link ScriptExecutor}
     */
    public static void removeScriptExecutor(ScriptExecutor scriptExecutor) {
        SCRIPT_EXECUTORS.remove(scriptExecutor);
    }

    /**
     * 调用所有脚本的指定函数。
     *
     * @param function 函数名称
     */
    public static void invoke(String function) {
        SCRIPT_EXECUTORS.forEach(
                scriptExecutor -> scriptExecutor.invoke(function)
        );
    }

    /**
     * 使用指定 {@link CustomContext} 对象调用所有脚本的指定函数。
     *
     * @param function      函数名称
     * @param customContext 指定 {@link CustomContext} 对象
     */
    public static void invoke(String function, CustomContext customContext) {
        SCRIPT_EXECUTORS.forEach(
                scriptExecutor -> scriptExecutor.invoke(function, customContext)
        );
    }
}
