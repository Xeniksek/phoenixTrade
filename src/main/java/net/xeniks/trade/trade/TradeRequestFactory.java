package net.xeniks.trade.trade;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Getter;

import java.time.Duration;
import java.util.UUID;

public class TradeRequestFactory {
    @Getter private Cache<UUID, UUID> tradeRequests = Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofSeconds(30L))
            .build();

    public void createTradeRequest(UUID player, UUID target){
        tradeRequests.put(player, target);
        tradeRequests.put(target, player);
    }
    public boolean isTradeRequest(UUID player, UUID target){
        if(tradeRequests.asMap().containsKey(player) && tradeRequests.asMap().containsKey(target)){
            return true;
        }
        return false;
    }
    public void removeTradeRequest(UUID player, UUID target){
        tradeRequests.invalidate(target);
    }
}
