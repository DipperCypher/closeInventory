package dev.dipper.close;

import org.bukkit.plugin.java.JavaPlugin;

import dev.dipper.close.command.slots;
import dev.dipper.close.event.InventoryListener;
import dev.dipper.close.manager.slotManager;

public class Inventory extends JavaPlugin {
    private static Inventory instance;
    private slotManager slotManager;

    @Override
    public void onEnable() {
        instance = this;
        slotManager = new slotManager(this);

        getCommand("slots").setExecutor(new slots(slotManager));

        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
    }

    public Inventory getInstance() {
        return instance;
    }

    public slotManager slotManager() {
        return slotManager;
    }
}