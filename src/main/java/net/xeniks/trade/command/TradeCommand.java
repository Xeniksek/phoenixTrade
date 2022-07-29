package net.xeniks.trade.command;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.Name;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;
import net.xeniks.trade.Main;
import net.xeniks.trade.trade.TradeRequestFactory;
import net.xeniks.trade.trade.inventories.TradeInventory;
import net.xeniks.trade.utils.ChatHelper;
import org.bukkit.entity.Player;

@Section(route = "trade")
public class TradeCommand {

    @Execute(required = 1)
    public void sendTradeRequest(Player sender, @Arg @Name("target") Player target){
        if(sender.getName().equalsIgnoreCase(target.getName())){
            sender.sendMessage(ChatHelper.fixColors(Main.getConfiguration().getPlayerSameNameAsTarget().replace("{SENDER}", sender.getName()).replace("{TARGET}", target.getName())));
            return;
        }
        if(Main.getTradeRequestFactory().isTradeRequest(sender.getUniqueId(), target.getUniqueId())){
            sender.sendMessage(ChatHelper.fixColors(Main.getConfiguration().getAlreadyRequestedTradeMessage().replace("{SENDER}", sender.getName()).replace("{TARGET}", target.getName())));
            return;
        }
        Main.getTradeRequestFactory().createTradeRequest(sender.getUniqueId(), target.getUniqueId());
        sender.sendMessage(ChatHelper.fixColors(Main.getConfiguration().getPlayerTradeRequestMessage().replace("{SENDER}", sender.getName()).replace("{TARGET}", target.getName())));
        target.sendMessage(ChatHelper.fixColors(Main.getConfiguration().getTargetTradeRequestMessage().replace("{SENDER}", sender.getName()).replace("{TARGET}", target.getName())));
    }
    @Execute(route = "accept", required = 1)
    public void acceptTradeRequest(Player sender, @Arg @Name("target") Player target){
        if(sender.getName().equalsIgnoreCase(target.getName())){
            sender.sendMessage(ChatHelper.fixColors(Main.getConfiguration().getPlayerSameNameAsTarget().replace("{SENDER}", sender.getName()).replace("{TARGET}", target.getName())));
            return;
        }
        if(Main.getTradeRequestFactory().isTradeRequest(sender.getUniqueId(), target.getUniqueId())){
            Main.getTradeFactory().createTrade(sender, target);
            TradeInventory.openInventory(sender, target);
            return;
        }
        sender.sendMessage(ChatHelper.fixColors(Main.getConfiguration().getTradeNotFound().replace("{SENDER}", sender.getName()).replace("{TARGET}", target.getName())));
    }
    @Execute(route = "decline", required = 1)
    public void declineTradeRequest(Player sender, @Arg @Name("target") Player target){
        if(sender.getName().equalsIgnoreCase(target.getName())){
            sender.sendMessage(ChatHelper.fixColors(Main.getConfiguration().getPlayerSameNameAsTarget().replace("{SENDER}", sender.getName()).replace("{TARGET}", target.getName())));
            return;
        }
        if(Main.getTradeRequestFactory().isTradeRequest(sender.getUniqueId(), target.getUniqueId())){
            sender.sendMessage(ChatHelper.fixColors(Main.getConfiguration().getPlayerTradeDeclineMessage().replace("{SENDER}", sender.getName()).replace("{TARGET}", target.getName())));
            target.sendMessage(ChatHelper.fixColors(Main.getConfiguration().getTargetTradeDeclineMessage().replace("{SENDER}", sender.getName()).replace("{TARGET}", target.getName())));
            Main.getTradeRequestFactory().removeTradeRequest(sender.getUniqueId(), target.getUniqueId());
            return;
        }
        sender.sendMessage(ChatHelper.fixColors(Main.getConfiguration().getTradeNotFound().replace("{SENDER}", sender.getName()).replace("{TARGET}", target.getName())));
    }
}
