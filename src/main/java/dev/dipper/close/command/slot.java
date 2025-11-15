package dev.dipper.close.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.dipper.close.manager.gameManager;

public class slot implements CommandExecutor {
    private gameManager slotManager;

    public slot(gameManager slotManager) {
        this.slotManager = slotManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String Label, String[] arg) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;

        if (arg.length == 0) {
            sendUsge(player);
            return true;
        }
        
        switch (arg[0].toLowerCase()) {
            case "start":
                if (arg.length < 2) {
                    sendUsge(player);
                    return true;
                }

                try {
                    int newCount = Integer.parseInt(arg[1]);
                    Bukkit.getLogger().info("count set to " + newCount);
                    slotManager.start(player, newCount);
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Invalid number");
                }
                break;
            
            case "stop":
                slotManager.stop(player);
                break;

            default:
                sendUsge(player);
                break;
        }
        return true;
    }
    
    public void sendUsge(Player player) {
        player.sendMessage(ChatColor.RED + "Slots commad usge:");
        player.sendMessage(ChatColor.RED + "- /slot start <time>");
        player.sendMessage(ChatColor.RED + "- /slot stop");
    }
}
