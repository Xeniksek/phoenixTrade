package net.xeniks.trade.trade.inventories;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import net.kyori.adventure.text.Component;
import net.xeniks.trade.Main;
import net.xeniks.trade.exception.TradeNotFoundException;
import net.xeniks.trade.utils.ChatHelper;
import net.xeniks.trade.utils.ItemHelper;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class TradeInventory {
    public static void openInventory(Player player, Player target){
        Main.getTradeFactory().findTradeByPlayers(player, target).ifPresentOrElse(trade -> {
            Gui gui = Gui.gui()
                    .title(Component.text(ChatHelper.fixColors("&aWYMIANA")))
                    .rows(6)
                    .disableItemDrop()
                    .create();
            ItemStack grayStainedGlassPane = new ItemHelper(Material.GRAY_STAINED_GLASS_PANE).setTitle("&8► &cPuste pole &8◄").build();
            ItemStack oakSign = new ItemHelper(Material.OAK_SIGN).setTitle("&b&lAKTUALNIE TRWA WYMIANA")
                    .addLores(Arrays.asList("","&fLewa strona:","&8←- &c" + player.getName(), "", "&fPrawa strona:","&8-→ &6" + target.getName(), "")).build();
            gui.setItem(1, 5, ItemBuilder.from(oakSign).asGuiItem());
            gui.setItem(2, 5, ItemBuilder.from(grayStainedGlassPane).asGuiItem());
            gui.setItem(3, 5, ItemBuilder.from(grayStainedGlassPane).asGuiItem());
            gui.setItem(4, 5, ItemBuilder.from(grayStainedGlassPane).asGuiItem());
            gui.setItem(5, 5, ItemBuilder.from(grayStainedGlassPane).asGuiItem());
            ItemStack redStainedGlassPane = new ItemHelper(Material.RED_STAINED_GLASS_PANE).setTitle("&c&lBRAK GOTOWOŚCI!")
                    .addLores(Arrays.asList("", " &8→ &7Kliknij, aby zmienić gotowość! &8←", "")).build();
            gui.getFiller().fillBetweenPoints(6, 1, 6, 9, ItemBuilder.from(redStainedGlassPane).asGuiItem());
            ItemStack clock = new ItemHelper(Material.CLOCK).setTitle("&6&lCZAS DO SFINALIZOWANIA WYMIANY")
                    .addLores(Arrays.asList("",trade.isBothSideAccepted() ? "&8→ &aPozostało: &7" + trade.getTradeFinalizationTimer() + " sekund. &8←" : "&8→ &cOczekiwanie na gotowość obu stron! &8←","")).build();
            gui.setItem(6, 5, ItemBuilder.from(clock).asGuiItem());
            gui.setDefaultTopClickAction(event -> {
                Main.getTradeConfiguration().inventoryClickEventConfiguration(event, trade, gui, player, target);
            });
            gui.setDragAction(event -> {
                Main.getTradeConfiguration().inventoryDragEventConfiguration(event, trade, player, target);
            });
            gui.setCloseGuiAction(event -> {
                Main.getTradeConfiguration().inventoryCloseEventConfiguration(event, trade, gui, player, target);

            });
            gui.open(player);
            gui.open(target);

        }, () -> {
            throw new TradeNotFoundException();
        });
    }
}
