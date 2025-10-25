package dev.dipper.close.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class InventoryListener implements Listener {
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        if (clicked == null && !clicked.hasItemMeta()) return;

        ItemMeta meta = clicked.getItemMeta();

        if (meta.hasDisplayName() && meta.getDisplayName().equals(ChatColor.AQUA + "LOCKED")) {
            event.setCancelled(true);
        }
    }
}
