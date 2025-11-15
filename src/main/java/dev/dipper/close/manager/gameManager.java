package dev.dipper.close.manager;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import dev.dipper.close.Inventory;
import dev.dipper.close.Items.items;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
public class gameManager {
    private Inventory plugin;
    private int slotCount;
    private int countdown;
    private int setCount;
    private boolean gameStarted = false;

    private BukkitTask closeTask;

    public gameManager(Inventory plugin) {
        this.plugin = plugin;
    }

    // start the game
    public void start(Player player, int time) {
        if (gameStarted) {
            player.sendMessage(ChatColor.RED + "Game is already going!");
            return;
        }

        setCount = time;

        if (countdown >= 5) {
            player.sendMessage(ChatColor.RED + "Time Needs tobe at least 5s or more");
            return;
        }

        gameStarted = true;
        countdown = setCount;

        Bukkit.getOnlinePlayers().stream().forEach(p -> p.sendMessage(ChatColor.AQUA + "GAME STARTED"));
        plugin.getLogger().info("Game Started");
 

        if (closeTask != null && !closeTask.isCancelled()) {
            closeTask.cancel();
        }

        closeTask =  new BukkitRunnable() {
            @Override
            public void run() {
                if (countdown <= 0) {
                    countdown = setCount;
                    runGiveItem(player);
                    return;
                }

                String countdownString = Timer(countdown);
                Bukkit.getOnlinePlayers().stream().forEach(p -> p.spigot()
                .sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.AQUA + "" + countdownString)));

                countdown--;
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    private String Timer(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%2d:%02d", minutes, seconds);
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
        slotCount = 0;
        setCount = 0;
        countdown = setCount;

        Bukkit.getOnlinePlayers().stream().forEach(p -> p.sendMessage(ChatColor.RED + "GAME ENDED"));
        plugin.getLogger().info("Game Ended");
    }

    // give items
    private void runGiveItem(Player player) {
        ItemStack lockedslot = items.lockedSlot();
        player.getInventory().setItem(slotCount, lockedslot);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
        plugin.getLogger().info("Removed slot" + slotCount);
        
        int randomNumber = ThreadLocalRandom.current().nextInt(1, 41);
        slotCount = randomNumber;
    }
}
