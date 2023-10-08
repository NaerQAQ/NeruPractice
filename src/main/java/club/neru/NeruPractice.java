package club.neru;

import club.neru.register.RegisterManager;
import club.neru.utils.QuickUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

/**
 * 该类继承 {@link JavaPlugin}，插件主类。
 *
 * @author NaerQAQ / 2000000
 * @version 1.0
 * @since 2023/10/7
 */
public class NeruPractice extends JavaPlugin {
    @Getter
    @Setter
    private static NeruPractice instance;

    @Getter
    @Setter
    private static Double serverVersion;

    @Getter
    @Setter
    private static String dataFolderAbsolutePath;

    /**
     * 插件开启。
     *
     * @author 2000000
     */
    @Override
    public void onEnable() {
        setInstance(this);
        setDataFolderAbsolutePath(getDataFolder().getAbsolutePath());

        setServerVersion(Double.parseDouble(
                Arrays.toString(
                                StringUtils.substringsBetween(
                                        getServer().getClass().getPackage().getName(), ".v", "_R"
                                ))
                        .replace("_", "0")
                        .replace("[", "")
                        .replace("]", "")
        ));

        // 注册
        RegisterManager.init();
    }

    /**
     * 插件卸载。
     *
     * @author 2000000
     */
    @Override
    public void onDisable() {
        QuickUtils.sendMsg("Plugin has been disabled.");
    }
}