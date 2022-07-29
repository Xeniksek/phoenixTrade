package net.xeniks.trade.configuration.litecommands;

import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.command.permission.LitePermissions;
import dev.rollczi.litecommands.handle.PermissionHandler;
import net.xeniks.trade.utils.ChatHelper;
import org.bukkit.command.CommandSender;

public class PermissionMessage implements PermissionHandler<CommandSender> {

    @Override
    public void handle(CommandSender sender, LiteInvocation invocation, LitePermissions litePermissions) {
        sender.sendMessage(ChatHelper.fixColors("&4Błąd! &cNie masz permisji do tej komendy!"));
    }
}