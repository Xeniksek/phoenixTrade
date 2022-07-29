package net.xeniks.trade.trade;

import lombok.Getter;
import net.xeniks.trade.exception.TradeNotFoundException;
import org.bukkit.entity.Player;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TradeFactory {
    @Getter private List<Trade> trades = new ArrayList<>();
    public void createTrade(Player player, Player target){
        Trade trade = new Trade(player, target);
        trades.add(trade);
    }
    public Optional<Trade> findTradeByPlayers(Player player, Player target){
        return trades.stream()
                .filter(filter -> filter.getLeftSidePlayer().equals(player))
                .filter(filter -> filter.getRightSidePlayer().equals(target))
                .findFirst();
    }
    public void removeTrade(Player player, Player target){
        findTradeByPlayers(player, target).ifPresent(trade -> {
            trades.remove(trade);
        });
    }
}
