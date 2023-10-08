package club.neru.commands;

import club.neru.annotations.AutoRegisterCommand;
import club.neru.utils.common.QuickUtils;
import club.neru.utils.common.enums.ConsoleMessageTypeEnum;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@AutoRegisterCommand
@SuppressWarnings("unused")
public class TestCommand {
    @Command(
            name = "practice",
            aliases = {"prac", "neru", "nerupractice"},
            async = true,
            senderType = Command.SenderType.BOTH
    )
    public void practiceCommand(CommandArguments arguments) {
        CommandSender sender = arguments.getSender();

        if (sender instanceof Player) {
            Player player = (Player) sender;
            QuickUtils.sendMessageByKeys(player, "practice");
            return;
        }

        QuickUtils.sendMessageByKey(
                ConsoleMessageTypeEnum.NO_PREFIX, "practice"
        );
    }
}
