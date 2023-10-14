package club.neru.commands;

import club.neru.basic.interfaces.ReflectCommandInterface;
import club.neru.commands.annotations.AutoRegisterCommand;
import club.neru.player.accessservice.AccessServiceHandler;
import club.neru.player.accessservice.data.AccessServiceData;
import club.neru.utils.common.text.QuickUtils;
import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.Require;
import com.jonahseguin.drink.annotation.Sender;
import org.bukkit.entity.Player;

/**
 * 进入服务命令。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/11
 */
@AutoRegisterCommand(
        command = "AccessService"
)
@SuppressWarnings("unused")
public class AccessServiceCommand {
    @Command(
            name = "",
            desc = "Access service command.",
            async = true
    )
    @Require("practice.command.accessService")
    public void accessServiceCommand(
            @Sender Player player
    ) {
        QuickUtils.sendMessageByKey(player, "access-service-command-help");
    }

    @Command(
            name = "command",
            desc = "Other commands.",
            async = true
    )
    public void command(
            @Sender Player player,

            String method,
            String value
    ) {
        AccessServiceData accessServiceData =
                AccessServiceHandler.getAccessServiceData();

        boolean result;

        if (accessServiceData == null) {
            result = false;
        } else {
            result = ReflectCommandInterface.execute(
                    player, accessServiceData, method, value
            );
        }

        QuickUtils.sendMessageByKey(
                player,
                "access-service-command-done",
                "<result>", String.valueOf(result)
        );
    }
}
