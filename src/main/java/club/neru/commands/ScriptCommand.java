package club.neru.commands;

import club.neru.commands.annotations.AutoRegisterCommand;
import club.neru.script.ScriptHandler;
import club.neru.utils.common.QuickUtils;
import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.Require;
import com.jonahseguin.drink.annotation.Sender;
import org.bukkit.entity.Player;

/**
 * 脚本命令。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/12
 */
@AutoRegisterCommand(
        command = "Script"
)
@SuppressWarnings("unused")
public class ScriptCommand {
    @Command(
            name = "",
            desc = "Script command.",
            async = true
    )
    @Require("practice.command.script")
    public void scriptCommand(
            @Sender Player player
    ) {
        QuickUtils.sendMessageByKey(player, "script-command-help");
    }

    @Command(
            name = "reload",
            desc = "Reload scripts.",
            async = true
    )
    public void reload(
            @Sender Player player
    ) {
        ScriptHandler.reloadScript();

        QuickUtils.sendMessageByKey(
                player,
                "script-reload-done"
        );
    }
}
