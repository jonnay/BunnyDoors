package net.jonnay.bunnydoors;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.block.Block;

import java.util.List;

import com.smilingdevil.devilstats.api.DevilStats;

import net.milkbowl.vault.Vault;
import org.bukkit.plugin.RegisteredServiceProvider;
import net.milkbowl.vault.permission.Permission;

public class BunnyDoors extends JavaPlugin {
	public static final boolean DEBUG = false;
	public static final Logger log = Bukkit.getLogger();

	private boolean allLocked = false;
	private BunnyDoorsCommandExecutor myExecutor;

	DevilStats stats;
	
	public DoorSerializer doorSerializer; 

	public boolean hasExtendedPermissionSupport;
	public static Permission permissions;

	
	public static void Debug(String message) {
		if (DEBUG)
			log.info("(DEBUG) "+message);
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
		getCommand("bunnykey").setExecutor(new BunnyKeysCommandExecutor(this));
		getServer().getPluginManager().registerEvents(new DoorListener(this), this);

		this.getConfig().options().copyDefaults(true);

		doorSerializer = new DoorSerializer(this);
		doorSerializer.reload();

		BunnyDoor.registerPlugin(this);

		if (this.getConfig().getBoolean("devilstats", true)) {
			try {
				stats = new DevilStats(this);
				stats.startup();
			} catch (Exception e) {
				log.warning("Can't load devilStats.");
				e.printStackTrace();
			}
		}
        saveConfig();

		hasExtendedPermissionSupport = this.setupPermissions();
		
		System.out.println(this.toString() + " enabled");
	}



	public static String keyToPermission(String key) {
		return "bunnydoors.key."+key;
	}
	
	public boolean playerHasKey(String key, Player p) {
		return p.hasPermission(keyToPermission(key));
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
		if (d.isLocked()) {			
			return true;
		} else {
			return false;
		}
	}

	public void sendLockedMessage(Player player, String key) {
		player.sendMessage("Sorry, That door is locked, you need the "+key+" key to open it.");
		player.sendMessage("Use /bunnykey list to get a list of your keys!");
	}
	
	public Boolean setupPermissions()
    {
		try {
			
			RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
			permissions = rsp.getProvider();
			return permissions != null;
			
		} catch (Exception e) {
			log.warning("Trying to enable extended permission support through vault failed.  You wont be able to give keys to users.");
			if (DEBUG)
				e.printStackTrace();

			return false;
		}
    }   

	

	/**
	   Keys on teh client?  MAYBE!!! (after lots of work... maybe)
	private void installMessagagingAPi() {
		//Bukkit.getMessenger().registerOutgoingPluginChannel(this, MESSAGING_CHANNEL);

		class MyPluginMessageListener implements PluginMessageListener {
			public void onPluginMessageReceived(String channel, Player player, byte[] message) {
				// Do something with this message from the player
			}
		}

		somePlayer.sendPluginMessage(plugin, channel, message);
		
	}
   	*/
	   

}	
