package club.neru.commands;

import club.neru.annotations.AutoRegisterCommand;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;
import org.bukkit.command.CommandSender;

/**
 * 竞技场地图命令。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/9
 */
@AutoRegisterCommand
@SuppressWarnings("unused")
public class ArenaCommand {
    @Command(
            name = "arena",
            async = true,
            senderType = Command.SenderType.PLAYER
    )
    public void practiceCommand(CommandArguments arguments) {
        CommandSender sender = arguments.getSender();


    }
}
