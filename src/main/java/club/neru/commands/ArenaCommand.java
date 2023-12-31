package club.neru.commands;

import club.neru.arena.interfaces.ArenaParentInterface;
import club.neru.arena.objects.ArenaParent;
import club.neru.basic.interfaces.ReflectCommandInterface;
import club.neru.commands.annotations.AutoRegisterCommand;
import club.neru.utils.common.text.QuickUtils;
import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.Require;
import com.jonahseguin.drink.annotation.Sender;
import org.bukkit.entity.Player;

/**
 * 竞技场地图命令。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/9
 */
@AutoRegisterCommand(
        command = "arena"
)
@SuppressWarnings("unused")
public class ArenaCommand {
    @Command(
            name = "",
            desc = "Arena command.",
            async = true
    )
    @Require("practice.command.arena")
    public void arenaCommand(
            @Sender Player player
    ) {
        QuickUtils.sendMessageByKey(player, "arena-command-help");
    }

    @Command(
            name = "create",
            desc = "Create arena.",
            async = true
    )
    public void create(
            @Sender Player player,

            String arenaName
    ) {
        new ArenaParent()
                .setName(arenaName)
                .to(ArenaParent.class)
                .write();

        QuickUtils.sendMessageByKey(
                player,
                "arena-create-done"
        );
    }

    @Command(
            name = "command",
            desc = "Other commands.",
            async = true
    )
    public void command(
            @Sender Player player,

            String method,
            String arenaName,
            String value
    ) {
        ArenaParent arenaParent = ArenaParentInterface
                .getArenaParent(arenaName);

        boolean result = ReflectCommandInterface.execute(
                player, arenaParent, method, value
        );

        QuickUtils.sendMessageByKey(
                player,
                "arena-command-done",
                "<result>", String.valueOf(result)
        );
    }
}
