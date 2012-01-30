package net.jonnay.bunnydoors;

import java.util.logging.Logger;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.block.Block;


public class BunnyDoors extends JavaPlugin {
	public static final boolean DEBUG = true;
	public static final Logger log = Logger.getLogger("Minecraft");

	private boolean allLocked = false;
	private BunnyDoorsCommandExecutor myExecutor;
	public static void Debug(String message) {
		if (DEBUG)
			log.info("[BunnyDoor] (DEBUG) "+message);
	}
	
	public void onDisable() {
	}
	
	public void onEnable() {

		myExecutor = new BunnyDoorsCommandExecutor(this);
		getCommand("bunnydoor").setExecutor(myExecutor);
		
		getServer().getPluginManager().registerEvents(new DoorListener(this), this);

	}

	private boolean keyholderHasAllPerms(Player keyholder) {
		return ((keyholder != null) &&
				(keyholder.hasPermission("bunnydoors.admin.alldoors")));
				 
	}
	
	public void lockAll(Player keyholder) {
		if (keyholderHasAllPerms(keyholder)) {
			allLocked = true;
		}
	}

	public void unlockAll(Player keyholder) {
		if (keyholderHasAllPerms(keyholder)) {
			allLocked = false;
		}
	}

	public boolean isLocked(Block door) {
		return allLocked;
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
