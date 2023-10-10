package club.neru.commands;

import club.neru.register.annotations.AutoRegisterCommand;
import club.neru.utils.common.QuickUtils;
import club.neru.utils.common.enums.ConsoleMessageTypeEnum;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * 竞技场命令。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/9
 */
@AutoRegisterCommand
@SuppressWarnings("unused")
public class PracticeCommand {
    @Command(
            name = "practice",
            aliases = {"mochi", "prac", "neru", "nerupractice"},
            async = true,
            senderType = Command.SenderType.BOTH
    )
    public void practiceCommand(CommandArguments arguments) {
        CommandSender sender = arguments.getSender();

        if (sender instanceof Player) {
            Player player = (Player) sender;
            QuickUtils.sendMessageByKey(player, "practice");
            return;
        }

        QuickUtils.sendMessageByKey(
                ConsoleMessageTypeEnum.NO_PREFIX, "practice"
        );
    }
}
