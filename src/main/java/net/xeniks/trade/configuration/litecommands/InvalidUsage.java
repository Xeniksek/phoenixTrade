package net.xeniks.trade.configuration.litecommands;

import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.handle.InvalidUsageHandler;
import dev.rollczi.litecommands.scheme.Scheme;
import net.xeniks.trade.utils.ChatHelper;
import org.bukkit.command.CommandSender;

import java.util.List;

public class InvalidUsage implements InvalidUsageHandler<CommandSender> {

    @Override
    public void handle(CommandSender sender, LiteInvocation invocation, Scheme scheme) {
        List<String> schemes = scheme.getSchemes();

        if (schemes.size() == 1) {
            sender.sendMessage(ChatHelper.fixColors(" &7Poprawne użycie komendy: &c" + schemes.get(0)));
            return;
        }
        sender.sendMessage(ChatHelper.fixColors(" &7Poprawne użycie komendy: "));
        for (String sch : schemes) {
            sender.sendMessage(ChatHelper.fixColors("&8 → &c" + sch));
        }
    }

}