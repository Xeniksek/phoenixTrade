package net.xeniks.trade.configuration;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import lombok.Getter;
import net.xeniks.trade.Main;
import net.xeniks.trade.trade.Trade;
import net.xeniks.trade.utils.ChatHelper;
import net.xeniks.trade.utils.ItemHelper;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class TradeConfiguration {
    @Getter public List<Integer> leftSideSlots = Arrays.asList(0, 1, 2, 3,
            9, 10, 11, 12,
            18, 19, 20, 21,
            27, 28, 29, 30,
            36, 37, 38, 39);
    @Getter public List<Integer> leftSideAcceptSlots = Arrays.asList(45, 46, 47, 48);
    @Getter public List<Integer> rightSideSlots = Arrays.asList(5, 6, 7, 8,
            14, 15, 16, 17,
            23, 24, 25, 26,
            32, 33, 34, 35,
            41, 42, 43, 44);
    @Getter public List<Integer> rightSideAcceptSlots = Arrays.asList(50, 51, 52, 53);
    public void inventoryClickEventConfiguration(InventoryClickEvent event, Trade trade, Gui gui, Player player, Player target){
        event.setCancelled(true);
        if(trade.isBothSideAccepted()) {
            if(event.getWhoClicked().equals(player)) {
                if (!Main.getTradeConfiguration().getLeftSideAcceptSlots().contains(event.getSlot())) {
                    event.setCancelled(true);
                    return;
                }
            }
            if(event.getWhoClicked().equals(target)) {
                if (!Main.getTradeConfiguration().getRightSideAcceptSlots().contains(event.getSlot())) {
                    event.setCancelled(true);
                    return;
                }
            }
        }
        if(event.getClick() == ClickType.DOUBLE_CLICK){
            event.setCancelled(true);
            return;
        }
        if(event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.RIGHT && event.getClick() == ClickType.DOUBLE_CLICK){
            event.setCancelled(true);
            return;
        }
        if(event.getWhoClicked().equals(player)) {
            for (int configurationSlot : Main.getTradeConfiguration().getLeftSideSlots()) {
                if (event.getSlot() == configurationSlot) {
                    event.setCancelled(false);
                }
            }
            if (Main.getTradeConfiguration().getLeftSideAcceptSlots().contains(event.getSlot())) {
                trade.setLeftSideAccepted(!trade.isLeftSideAccepted());
                trade.setBothSideAccepted(false);
                if(trade.isLeftSideAccepted() && trade.isRightSideAccepted()){
                    trade.setBothSideAccepted(true);
                    trade.startTradeFinalization(gui);
                }
                for (int acceptSlot : Main.getTradeConfiguration().getLeftSideAcceptSlots()) {
                    ItemStack redStainedGlassPane = new ItemHelper(Material.RED_STAINED_GLASS_PANE).setTitle("&c&lBRAK GOTOWOŚCI!")
                            .addLores(Arrays.asList("", " &8→ &7Kliknij, aby zmienić gotowość! &8←", "")).build();
                    ItemStack greenStainedGlassPane = new ItemHelper(Material.GREEN_STAINED_GLASS_PANE).setTitle("&a&lPEŁNA GOTOWOŚCI!")
                            .addLores(Arrays.asList("", " &8→ &7Kliknij, aby zmienić gotowość! &8←", "")).build();
                    gui.updateItem(acceptSlot, ItemBuilder.from(trade.isLeftSideAccepted() ? greenStainedGlassPane : redStainedGlassPane).asGuiItem());
                }
            }
        }
        if(event.getWhoClicked().equals(target)){
            for(int configurationSlot : Main.getTradeConfiguration().getRightSideSlots()){
                if(event.getSlot() == configurationSlot){
                    event.setCancelled(false);
                }
            }
            if (Main.getTradeConfiguration().getRightSideAcceptSlots().contains(event.getSlot())) {
                trade.setRightSideAccepted(!trade.isRightSideAccepted());
                trade.setBothSideAccepted(false);
                if(trade.isLeftSideAccepted() && trade.isRightSideAccepted()){
                    trade.setBothSideAccepted(true);
                    trade.startTradeFinalization(gui);
                }
                for (int acceptSlot : Main.getTradeConfiguration().getRightSideAcceptSlots()) {
                    ItemStack redStainedGlassPane = new ItemHelper(Material.RED_STAINED_GLASS_PANE).setTitle("&c&lBRAK GOTOWOŚCI!")
                            .addLores(Arrays.asList("", " &8→ &7Kliknij, aby zmienić gotowość! &8←", "")).build();
                    ItemStack greenStainedGlassPane = new ItemHelper(Material.GREEN_STAINED_GLASS_PANE).setTitle("&a&lPEŁNA GOTOWOŚCI!")
                            .addLores(Arrays.asList("", " &8→ &7Kliknij, aby zmienić gotowość! &8←", "")).build();
                    gui.updateItem(acceptSlot, ItemBuilder.from(trade.isRightSideAccepted() ? greenStainedGlassPane : redStainedGlassPane).asGuiItem());
                }
            }
        }
    }
    public void inventoryDragEventConfiguration(InventoryDragEvent event, Trade trade, Player player, Player target){
        if(trade.isBothSideAccepted()){
            return;
        }
        if(event.getWhoClicked().equals(player)){
            for(int configurationSlot : Main.getTradeConfiguration().getRightSideSlots()){
                for(int inventorySlot : event.getInventorySlots()) {
                    if (inventorySlot == configurationSlot) {
                        event.setCancelled(true);
                    }
                }
            }
        }
        if(event.getWhoClicked().equals(target)){
            for(int configurationSlot : Main.getTradeConfiguration().getLeftSideSlots()){
                for(int inventorySlot : event.getInventorySlots()) {
                    if (inventorySlot == configurationSlot) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
    public void inventoryCloseEventConfiguration(InventoryCloseEvent event, Trade trade, Gui gui, Player player, Player target){
        if(trade.isBothSideAccepted()){
            return;
        }
        if(event.getPlayer().equals(player)){
            for(ItemStack itemStack : trade.getRightSideItems(gui)){
                target.getInventory().addItem(itemStack);
            }
            gui.close(target);
        }
        if(event.getPlayer().equals(target)){
            for(ItemStack itemStack : trade.getLeftSideItems(gui)) {
                player.getInventory().addItem(itemStack);
            }
            gui.close(player);
        }
        player.sendTitle(ChatHelper.fixColors(Main.getConfiguration().getTradeFinishedTitle()), ChatHelper.fixColors(Main.getConfiguration().getTradeCancelledSubTitle()));
        target.sendTitle(ChatHelper.fixColors(Main.getConfiguration().getTradeFinishedTitle()), ChatHelper.fixColors(Main.getConfiguration().getTradeCancelledSubTitle()));
        Main.getTradeFactory().removeTrade(player, target);
    }
}
