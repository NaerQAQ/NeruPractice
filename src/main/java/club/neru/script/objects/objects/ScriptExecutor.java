package club.neru.script.objects.objects;

import club.neru.basic.impl.ObjectNameImpl;
import club.neru.script.ScriptHandler;
import club.neru.script.objects.handler.NamedContextHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.io.FileUtils;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 脚本执行器。
 *
 * @author NaerQAQ / 2000000
 * @version 1.0
 * @since 2023/10/12
 */
@Getter
@Setter
@Accessors(chain = true)
public class ScriptExecutor extends ObjectNameImpl {
    /**
     * 脚本文件。
     */
    private final File scriptFile;

    /**
     * 脚本内容。
     */
    private final String scriptCode;

    /**
     * 脚本指定的 {@link Context} 对象。
     */
    private final Context context;

    /**
     * 普通的 {@link Context} 对象。
     */
    private final Context normalContext =
            ScriptHandler.buildNormalContext();

    /**
     * 构造器。
     *
     * @param file 脚本文件
     * @throws IOException 读取脚本文件时出现异常
     */
    public ScriptExecutor(File file) throws IOException {
        scriptFile = file;
        scriptCode = FileUtils.readFileToString(
                scriptFile, StandardCharsets.UTF_8
        );
        context = getContext();
    }

    /**
     * 调用指定函数。
     *
     * @param function 函数名称
     * @return 结果
     */
    public Value invoke(String function) {
        return invoke(function, context);
    }

    /**
     * 使用 {@link #normalContext} 对象调用指定函数。
     *
     * @param function 函数名称
     * @return 结果
     */
    public Value invokeWithNormalContext(String function) {
        return invoke(function, normalContext);
    }

    /**
     * 使用指定 {@link Context} 对象调用指定函数。
     *
     * @param function 函数名称
     * @param context  指定 {@link Context} 对象
     * @return 结果
     */
    public Value invoke(String function, Context context) {
        return context.eval(
                ScriptHandler.JS, function
        );
    }

    /**
     * 获取该脚本应该使用的 {@link Context} 对象。
     *
     * @return 该脚本应该使用的 {@link Context} 对象
     */
    private Context getContext() {
        // 预加载到 normalContext
        normalContext.eval(ScriptHandler.JS, scriptCode);

        // 执行 getContext() 函数
        Value result = invokeWithNormalContext(
                ScriptHandler.GET_CONTEXT_FUNCTION
        );

        // 如果返回的是 null 则直接使用 normalContext
        if (result == null) {
            return normalContext;
        }

        // 如果有指定 context 名称
        String name = result.asString();
        Context context = NamedContextHandler.getContext(name);

        // 如果靠这个名称找到了对应 context
        if (context != null) {
            return context;
        }

        // 如果没有找到则创建并预加载
        context = ScriptHandler.buildNormalContext();
        context.eval(ScriptHandler.JS, scriptCode);

        // 添加
        NamedContextHandler.addContext(name, context);

        return context;
    }
}
