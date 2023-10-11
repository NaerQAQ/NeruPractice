package club.neru.commands;

import club.neru.basic.interfaces.ReflectCommandInterface;
import club.neru.commands.annotations.AutoRegisterCommand;
import club.neru.kit.interfaces.KitInventoryInterface;
import club.neru.kit.objects.KitInventory;
import club.neru.utils.common.QuickUtils;
import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.Require;
import com.jonahseguin.drink.annotation.Sender;
import org.bukkit.entity.Player;

/**
 * 装备包库存命令。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/11
 */
@AutoRegisterCommand(
        command = "KitInventory"
)
@SuppressWarnings("unused")
public class KitInventoryCommand {
    @Command(
            name = "",
            desc = "Kit inventory command.",
            async = true
    )
    @Require("practice.command.kitInventory")
    public void kitInventoryCommand(
            @Sender Player player
    ) {
        QuickUtils.sendMessageByKey(player, "kit-inventory-command-help");
    }

    @Command(
            name = "create",
            desc = "Create kit inventory.",
            async = true
    )
    public void create(
            @Sender Player player,

            String kitInventoryName
    ) {
        new KitInventory()
                .setName(kitInventoryName)
                .to(KitInventory.class)
                .write();

        QuickUtils.sendMessageByKey(
                player,
                "kit-inventory-create-done"
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
            String kitInventoryName,
            String value
    ) {
        KitInventory kitInventory = KitInventoryInterface
                .getKitInventory(kitInventoryName);

        boolean result = ReflectCommandInterface.execute(
                player, kitInventory, method, value
        );

        QuickUtils.sendMessageByKey(
                player,
                "kit-inventory-command-done",
                "<result>", String.valueOf(result)
        );
    }
}
