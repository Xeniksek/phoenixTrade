package net.xeniks.trade.exception;

public class TradeNotFoundException extends RuntimeException{
    public TradeNotFoundException(){
        super("Trade not found by specific players.");
    }
}
