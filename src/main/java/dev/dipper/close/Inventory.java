package dev.dipper.close;

import org.bukkit.plugin.java.JavaPlugin;

import dev.dipper.close.command.slot;
import dev.dipper.close.event.events;
import dev.dipper.close.manager.gameManager;

public class Inventory extends JavaPlugin {
    private static Inventory instance;
    private gameManager slotManager;

    @Override
    public void onEnable() {
        instance = this;
        slotManager = new gameManager(this);

        getCommand("slot").setExecutor(new slot(slotManager));

        getServer().getPluginManager().registerEvents(new events(), this);
    }

    public Inventory getInstance() {
        return instance;
    }

    public gameManager slotManager() {
        return slotManager;
    }
}