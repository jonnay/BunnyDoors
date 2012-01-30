package net.jonnay.bunnydoors;

import java.util.logging.Logger;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.block.Block;
import java.util.List;
import com.smilingdevil.devilstats.api.DevilStats;

public class BunnyDoors extends JavaPlugin {
	public static final boolean DEBUG = true;
	public static final Logger log = Logger.getLogger("Minecraft");

	private boolean allLocked = false;
	private BunnyDoorsCommandExecutor myExecutor;

	DevilStats stats;
	
	public DoorSerializer doorSerializer; 
	
	public static void Debug(String message) {
		if (DEBUG)
			log.info("[BunnyDoor] (DEBUG) "+message);
	}

	public void onDisable() {
		doorSerializer.save();
	}

	public List<String> getKeys() {
		return getConfig().getStringList("keys");
	}
	
	public void onEnable() {

		myExecutor = new BunnyDoorsCommandExecutor(this);
		getCommand("bunnydoor").setExecutor(myExecutor);
		
		getServer().getPluginManager().registerEvents(new DoorListener(this), this);

		this.getConfig().options().copyDefaults(true);
        saveConfig();

		doorSerializer = new DoorSerializer(this);
		doorSerializer.reload();

		BunnyDoor.registerPlugin(this);
		
		try {
			stats = new DevilStats(this);
			stats.startup();
		} catch (Exception e) {
			log.warning("Can't load devilStats.");
			e.printStackTrace();
		}
		
		System.out.println(this.toString() + " enabled");
	}

	private boolean keyholderHasAllPerms(Player keyholder) {
		return ((keyholder != null) &&
				(keyholder.hasPermission("bunnydoors.admin.alldoors")));
				 
	}

	public boolean isValidKey(String key) {
		return getKeys().contains(key);
	}

	public boolean lock(Block door, Player keyholder, String key) {
		if (!isValidKey(key)) {
			return false;
		}

		if (!BunnyDoor.isDoor(door)) {
			return false;
		}

		BunnyDoor d = BunnyDoor.getFromBlock(door);
		d.lock(keyholder, key);
		
		return true;
	}

	public boolean unlock(Block door) {
		if (! BunnyDoor.isDoor(door)) {
			return false;
		}

		BunnyDoor d = BunnyDoor.getFromBlock(door);
		d.unlock();

		return true;
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

	public boolean isLocked(Block door, Player keyholder) {
		if (allLocked) {
			keyholder.sendMessage("All Doors are locked!");
			return true;
		}

		BunnyDoor d = BunnyDoor.getFromBlock(door);
		if (d.isLocked(keyholder)) {
			sendLockedMessage(keyholder, d.getKey() );
			return true;
		} else {
			return false;
		}
	}

	public void sendLockedMessage(Player player, String key) {
		player.sendMessage("Sorry, That door is locked, you need the "+key+" key to open it.");
		player.sendMessage("Use /bunnydoor keys to get a list of your keys!");
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
