package dev.dipper.close.Items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class items {
    public static ItemStack lockedSlot() {
        final ItemStack slot = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS);
        final ItemMeta slotMeta = slot.getItemMeta();
        slotMeta.setDisplayName(ChatColor.AQUA + "LOCKED");
        slot.setItemMeta(slotMeta);
        return slot;
    }
}
