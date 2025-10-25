package dev.dipper.close.manager;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import dev.dipper.close.Inventory;
import dev.dipper.close.Items.items;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
public class slotManager {
    private Inventory plugin;
    private int slotTimer = 30;
    private int slotCount;
    private boolean gameStarted = false;

    private BukkitTask closeTask;

    public slotManager(Inventory plugin) {
        this.plugin = plugin;
    }

    // start the game
    public void start(Player player) {
        if (gameStarted) {
            player.sendMessage(ChatColor.RED + "Game is already going!");
            return;
        }

        if (slotTimer <= 0) {
            player.sendMessage(ChatColor.RED + "Invalid time");
            return;
        }

        gameStarted = true;
        Bukkit.getOnlinePlayers().stream().forEach(p -> p.sendMessage(ChatColor.AQUA + "GAME STARTED"));
        plugin.getLogger().info("Game Started");

        closeTask = new BukkitRunnable() {
            @Override
            public void run() {
                new BukkitRunnable() {
                    int countdown = 5;

                    @Override
                    public void run() {
                        if (countdown <= 0) {
                            runGiveItem(player);
                            this.cancel();
                            return;
                        }

                        final int current = countdown;
                        Bukkit.getOnlinePlayers().forEach(p ->p.spigot()
                        .sendMessage(ChatMessageType.ACTION_BAR,TextComponent.fromLegacyText(ChatColor.YELLOW + "Countdown: " + ChatColor.WHITE + current)));

                        plugin.getLogger().info("Countdown: " + countdown);
                        countdown --;
                    }
                }.runTaskTimer(plugin, 0L,20L);
            }
        }.runTaskTimer(plugin, slotTimer * 20L, slotTimer * 20L);
    }

    // stop the game
    public void stop(Player player) {
        if (!gameStarted) {
            player.sendMessage(ChatColor.RED + "A game is not going");
            return;
        }

        if (closeTask == null) {
            return;
        }

        closeTask.cancel();
        closeTask = null;
        gameStarted = false;

        Bukkit.getOnlinePlayers().stream().forEach(p -> p.sendMessage(ChatColor.RED + "GAME ENDED"));
        plugin.getLogger().info("Game Ended");
    }

    // give items
    public void runGiveItem(Player player) {
        ItemStack lockedslot = items.lockedSlot();
        player.getInventory().setItem(slotCount, lockedslot);
        
        int randomNumber = ThreadLocalRandom.current().nextInt(1, 41);
        slotCount = randomNumber;
    }

    // get the current slot
    public void getCurrent(Player player) {
        player.sendMessage(ChatColor.AQUA + "Slot Count: " + ChatColor.WHITE + slotCount);
    }

    // set the slotCount
    public void setslotCount(Player player, int slotCount) {
        this.slotCount = slotCount;
        player.sendMessage(ChatColor.AQUA + "Set Count: " + ChatColor.WHITE + slotCount);
    }
}
