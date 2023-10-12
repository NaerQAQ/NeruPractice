package club.neru.script;

import club.neru.Mochi;
import club.neru.io.file.utils.IOUtils;
import club.neru.script.interop.EventListenerInterop;
import club.neru.script.objects.handler.CustomContextHandler;
import club.neru.script.objects.handler.ScriptExecutorHandler;
import club.neru.script.objects.objects.ScriptExecutor;
import club.neru.utils.common.QuickUtils;
import club.neru.utils.common.enums.ConsoleMessageTypeEnum;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

/**
 * 脚本处理程序。
 *
 * @author NaerQAQ / 2000000
 * @version 1.0
 * @since 2023/10/12
 */
public class ScriptHandler {
    /**
     * 脚本所在文件夹。
     */
    public static final String SCRIPT_PATH =
            Mochi.getDataFolderAbsolutePath() + "/script";

    /**
     * 字符常量。
     */
    public static final String JS = "js";

    /**
     * 获取上下文的函数名称。
     */
    public static final String GET_CONTEXT_FUNCTION = "getContext";

    /**
     * 获取所有脚本文件。
     *
     * @return 所有脚本文件
     */
    public static ConcurrentLinkedQueue<File> getScriptFiles() {
        return IOUtils.getFiles(SCRIPT_PATH).stream()
                .filter(file -> file.getName().endsWith(".js"))
                .collect(Collectors.toCollection(ConcurrentLinkedQueue::new));
    }

    /**
     * 获取指定文件名的脚本文件。
     *
     * @param fileName 文件名
     * @return 脚本文件
     */
    public static File getScriptFile(String fileName) {
        return getScriptFiles().stream()
                .filter(file -> file.getName().equalsIgnoreCase(fileName))
                .findFirst()
                .orElse(null);
    }

    /**
     * 注册所有脚本。
     */
    @SneakyThrows
    public static void registerScript() {
        Mochi mochi = Mochi.getInstance();
        File scriptsFolder = new File(SCRIPT_PATH);

        // 如果没有 script 文件夹则写入默认脚本
        if (!scriptsFolder.exists()) {
            FileUtils.forceMkdir(scriptsFolder);

            File file = new File(SCRIPT_PATH + "/player-data-listener.js");

            InputStream inputStream = mochi.getClass().getClassLoader()
                    .getResourceAsStream("script/player-data-listener.js");

            if (inputStream != null) {
                FileUtils.copyInputStreamToFile(inputStream, file);
            }
        }

        /*
         * Bukkit 使用自己的类加载器加载插件
         * 要正常使用 Graal 则需要将上下文类加载器设置为正确的类加载器
         * 也许存有风险，在设置后并没有在执行完毕后设置回去，尽管在我测试时似乎没有问题
         */
        Thread.currentThread().setContextClassLoader(
                mochi.getClass().getClassLoader()
        );

        ConcurrentLinkedQueue<File> scriptFiles = getScriptFiles();

        scriptFiles.forEach(scriptFile -> {
            String scriptName = scriptFile.getName();

            try {
                ScriptExecutorHandler.addScriptExecutor(
                        new ScriptExecutor(scriptFile)
                );

                QuickUtils.sendMessage(
                        ConsoleMessageTypeEnum.NORMAL,
                        "Script successfully registered: <script_name>.",
                        "<script_name>", scriptName
                );
            } catch (Exception exception) {
                exception.printStackTrace();
                String message = exception.getMessage();

                QuickUtils.sendMessage(
                        ConsoleMessageTypeEnum.ERROR,
                        "Unable to register script: <script_name>, message: <message>.",
                        "<script_name>", scriptName,
                        "<message>", message
                );
            }
        });

        // 注册完毕后调用所有 Script 的 initializationComplete 函数
        ScriptExecutorHandler.invoke("initializationComplete");
    }

    /**
     * 重载脚本。
     */
    public static void reloadScript() {
        // 监听器注销
        EventListenerInterop.getEventListeners().forEach(EventListenerInterop::unregister);

        // 脚本清空
        ScriptExecutorHandler.SCRIPT_EXECUTORS.clear();

        // 上下文清空
        CustomContextHandler.CUSTOM_CONTEXTS.forEach(CustomContextHandler::removeCustomContext);

        // 再次调用注册方法
        registerScript();
    }
}
