package net.xeniks.trade.utils;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemHelper
{
    private final List<String> lore;
    private final ItemStack item;
    private final ItemMeta meta;

    public ItemHelper(final ItemStack item) {
        this.lore = new ArrayList<String>();
        this.item = item;
        this.meta = item.getItemMeta();
    }

    public ItemHelper(final Material mat) {
        this(mat, 1);
    }

    public ItemHelper(final Material mat, final int amount) {
        this(mat, amount, (short)0);
    }

    public ItemHelper(final Material mat, final short data) {
        this(mat, 1, data);
    }

    public ItemHelper(final Material mat, final int amount, final short data) {
        this.lore = new ArrayList<String>();
        this.item = new ItemStack(mat, amount, data);
        this.meta = this.item.getItemMeta();
    }

    public ItemHelper addLores(final List<String> lores) {
        this.lore.addAll(ChatHelper.fixColors(lores));
        return this;
    }

    public ItemHelper addLore(final String lore) {
        this.lore.add(ChatHelper.fixColors(lore));
        return this;
    }

    public ItemHelper addEnchantment(final Enchantment enchant, final int level) {
        this.meta.removeEnchant(enchant);
        this.meta.addEnchant(enchant, level, true);
        return this;
    }

    public ItemHelper setAmount(final int amount) {
        this.item.setAmount(amount);
        return this;
    }

    public ItemHelper setTitle(final String title) {
        this.meta.setDisplayName(ChatHelper.fixColors(title));
        return this;
    }
    public ItemHelper setTitleWithoutColors(final String title) {
        this.meta.setDisplayName(title);
        return this;
    }
    public ItemHelper setHeadOwner(final String owner) {
        SkullMeta meta = (SkullMeta) this.meta;
        meta.setOwner(owner);
        return this;
    }

    public ItemHelper setColor(final Color color) {
        if (!this.item.getType().name().contains("LEATHER_")) {
            throw new IllegalArgumentException("Can only dye leather armor!");
        }
        ((LeatherArmorMeta)this.meta).setColor(color);
        return this;
    }

    public ItemHelper removePotionEffects() {
        this.meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_POTION_EFFECTS });
        return this;
    }

    public ItemHelper setGlow() {
        this.meta.addEnchant(Enchantment.DURABILITY, 1, true);
        this.meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
        return this;
    }
    public ItemHelper hideAtributes() {
        this.meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        return this;
    }

    public ItemStack build() {
        if (!this.lore.isEmpty()) {
            this.meta.setLore((List)this.lore);
        }
        this.item.setItemMeta(this.meta);
        return this.item;
    }
}
