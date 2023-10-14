package club.neru.commands;

import club.neru.commands.annotations.AutoRegisterCommand;
import club.neru.utils.common.text.QuickUtils;
import club.neru.utils.common.text.enums.ConsoleMessageTypeEnum;
import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.Sender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * 竞技场命令。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/9
 */
@AutoRegisterCommand(
        command = "practice",
        aliases = {"mochi", "prac", "neru", "nerupractice"}
)
@SuppressWarnings("unused")
public class PracticeCommand {
    @Command(
            name = "",
            desc = "Practice command.",
            async = true
    )
    public void practiceCommand(
            @Sender CommandSender sender
    ) {
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
