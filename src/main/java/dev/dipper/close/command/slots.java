package dev.dipper.close.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.dipper.close.manager.slotManager;

public class slots implements CommandExecutor {
    private slotManager slotManager;

    public slots(slotManager slotManager) {
        this.slotManager = slotManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String Label, String[] arg) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;

        if (arg.length <0) {
            sendUsge(player);
            return true;
        }
        
        switch (arg[0].toLowerCase()) {
            case "start":
                slotManager.start(player);
                break;
            
            case "stop":
                slotManager.stop(player);
                break;
                
            case "current":
                slotManager.getCurrent(player);
                break;    

            default:
                sendUsge(player);
                break;
        }
        return true;
    }
    
    public void sendUsge(Player player) {
        player.sendMessage(ChatColor.RED + "Slots commad usge:");
        player.sendMessage(ChatColor.RED + "- /slot start");
        player.sendMessage(ChatColor.RED + "- /slot stop");
        player.sendMessage(ChatColor.RED + "- /slot current");
    }
}
