package net.xeniks.trade.trade;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import lombok.Getter;
import lombok.Setter;
import net.xeniks.trade.Main;
import net.xeniks.trade.utils.ChatHelper;
import net.xeniks.trade.utils.ItemHelper;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Trade {
    @Getter private Player leftSidePlayer;
    @Getter private Player rightSidePlayer;
    @Getter @Setter private boolean leftSideAccepted;
    @Getter @Setter private boolean rightSideAccepted;
    @Getter @Setter private boolean bothSideAccepted;
    @Getter private int tradeFinalizationTimer;

    public Trade(Player leftSidePlayer, Player rightSidePlayer){
        this.leftSidePlayer = leftSidePlayer;
        this.rightSidePlayer = rightSidePlayer;
        this.bothSideAccepted = false;
        this.tradeFinalizationTimer = 0;
    }
    public List<ItemStack> getLeftSideItems(Gui gui){
        List<ItemStack> itemStacks = new LinkedList<>();
        for(int slot : Main.getTradeConfiguration().getLeftSideSlots()) {
            ItemStack itemStack = gui.getInventory().getItem(slot);
            if (itemStack != null) {
                itemStacks.add(itemStack);
            }
        }
        return itemStacks;
    }
    public List<ItemStack> getRightSideItems(Gui gui){
        List<ItemStack> itemStacks = new LinkedList<>();
        for(int slot : Main.getTradeConfiguration().getRightSideSlots()){
            ItemStack itemStack = gui.getInventory().getItem(slot);
            if (itemStack != null) {
                itemStacks.add(itemStack);
            }
        }
        return itemStacks;
    }
    public void startTradeFinalization(Gui gui){
        tradeFinalizationTimer = 5;
        new BukkitRunnable(){
            @Override
            public void run() {
                if(!bothSideAccepted){
                    ItemStack clock = new ItemHelper(Material.CLOCK).setTitle("&6&lCZAS DO SFINALIZOWANIA WYMIANY")
                            .addLores(Arrays.asList("", isBothSideAccepted() ? "&8→ &aPozostało: &7" + getTradeFinalizationTimer() + " sekund. &8←" : "&8→ &cOczekiwanie na gotowość obu stron!","")).build();
                    gui.updateItem(6, 5, ItemBuilder.from(clock).asGuiItem());
                    leftSidePlayer.playSound(leftSidePlayer.getLocation(), Sound.ENTITY_VILLAGER_NO, 2F, 1F);
                    rightSidePlayer.playSound(rightSidePlayer.getLocation(), Sound.ENTITY_VILLAGER_NO, 2F, 1F);
                    this.cancel();
                    return;
                }
                if(tradeFinalizationTimer <= 0){
                   for(ItemStack itemStack : getLeftSideItems(gui)){
                       rightSidePlayer.getInventory().addItem(itemStack);
                   }
                    for(ItemStack itemStack : getRightSideItems(gui)){
                        leftSidePlayer.getInventory().addItem(itemStack);
                    }
                    gui.close(leftSidePlayer);
                    gui.close(rightSidePlayer);
                    leftSidePlayer.sendTitle(ChatHelper.fixColors(Main.getConfiguration().getTradeFinishedTitle()), ChatHelper.fixColors(Main.getConfiguration().getTradeFinishedSubTitle()));
                    rightSidePlayer.sendTitle(ChatHelper.fixColors(Main.getConfiguration().getTradeFinishedTitle()), ChatHelper.fixColors(Main.getConfiguration().getTradeFinishedSubTitle()));
                    Main.getTradeFactory().removeTrade(leftSidePlayer, rightSidePlayer);
                    this.cancel();
                    return;
                }
                ItemStack clock = new ItemHelper(Material.CLOCK, tradeFinalizationTimer).setTitle("&6&lCZAS DO SFINALIZOWANIA WYMIANY")
                        .addLores(Arrays.asList("", isBothSideAccepted() ? "&8→ &aPozostało: &7" + getTradeFinalizationTimer() + " sekund. &8←" : "&8→ &cOczekiwanie na gotowość obu stron!","")).build();
                gui.updateItem(6, 5, ItemBuilder.from(clock).asGuiItem());
                tradeFinalizationTimer--;
                leftSidePlayer.playSound(leftSidePlayer.getLocation(), Sound.BLOCK_AMETHYST_CLUSTER_BREAK, 2F, tradeFinalizationTimer);
                rightSidePlayer.playSound(rightSidePlayer.getLocation(), Sound.BLOCK_AMETHYST_CLUSTER_BREAK, 2F, tradeFinalizationTimer);
            }
        }.runTaskTimerAsynchronously(Main.getInstance(), 20L, 20L);
    }
}
