package club.neru.commands;

import club.neru.register.annotations.AutoRegisterCommand;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;

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
        // TODO: arena 指令
    }
}
