package net.jonnay.bunnydoors;

import java.util.logging.Logger;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.java.JavaPlugin;


public class BunnyDoors extends JavaPlugin {
	public static final boolean DEBUG = true;
	public static final Logger log = Logger.getLogger("Minecraft");

	public static void Debug(String message) {
		if (DEBUG)
			log("[BunnyDoor] (DEBUG) "+message);
	}
	
	public void onDisable() {
	}
	
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new DoorListener(), this);
	}

	/*
	public Boolean setupPermissions()
    {
        RegisteredServiceProvider<net.milkbowl.vault.permission.Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }
	*/
}	
