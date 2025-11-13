package dev.dipper.close.event;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import net.md_5.bungee.api.ChatColor;

public class events implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();
        
        if (clicked == null) {
            return;
        }

        if (clicked.getType() == Material.AIR) {
            return;
        }

        if (!clicked.hasItemMeta()) {
            return;
        }

        ItemMeta meta = clicked.getItemMeta();
        if (meta == null || !meta.hasDisplayName()) {
            return;
        }

        if (meta.getDisplayName().equals(ChatColor.AQUA + "LOCKED")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlayer(BlockPlaceEvent event) {
        Block block = event.getBlock();
        
        if (block.getType() == Material.LIGHT_BLUE_STAINED_GLASS) {
            event.setCancelled(true);
        }
    }
}
