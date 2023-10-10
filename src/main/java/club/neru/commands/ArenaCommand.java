package club.neru.commands;

import club.neru.arena.copy.interfaces.ArenaParentInterface;
import club.neru.arena.copy.objects.ArenaParent;
import club.neru.basic.interfaces.ReflectCommandInterface;
import club.neru.register.annotations.AutoRegisterCommand;
import club.neru.utils.common.QuickUtils;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;
import org.bukkit.entity.Player;

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
            allowInfiniteArgs = true,
            senderType = Command.SenderType.PLAYER
    )
    @SuppressWarnings("DataFlowIssue")
    public void practiceCommand(CommandArguments arguments) {
        Player player = arguments.getSender();

        if (arguments.getArgumentsLength() < 1) {
            help(player);
            return;
        }

        String method = arguments.getArgument(0);
        String arenaName = arguments.getArgument(1);
        String value = arguments.getArgument(2);

        // create
        if (value == null) {
            if (!method.equalsIgnoreCase("create")) {
                help(player);
                return;
            }

            new ArenaParent()
                    .setName(arenaName)
                    .toArenaParent()
                    .write();

            QuickUtils.sendMessageByKey(
                    player,
                    "arena-create-done"
            );

            return;
        }

        // 对于现有的操作
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

    /**
     * 向玩家展示帮助信息。
     *
     * @param player 玩家对象
     */
    private void help(Player player) {
        QuickUtils.sendMessageByKey(player, "arena-command-help");
    }
}
