package net.jonnay.bunnydoors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import org.getspout.spoutapi.event.inventory.InventoryOpenEvent;
import org.getspout.spoutapi.event.inventory.InventoryCloseEvent;
import org.getspout.spoutapi.event.screen.*;

import org.getspout.spoutapi.gui.Screen;

import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.SpoutManager;

import java.util.Arrays;
import java.util.Hashtable;
         
public class BunnyKeyInventoryListener implements Listener {

	private BunnyDoors plugin;

	private static final int RUN_CLEAR_ON = 60 * 10;  // clear the hashmap of old player entries every 10 mins
	
	private static Hashtable<Player, Integer> NotificationMap = new Hashtable<Player, Integer>();
	private static int LastClear;
	
	public BunnyKeyInventoryListener(BunnyDoors p) {
		plugin = p;
		BunnyDoors.Debug("registering inventorylistener");
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);  // yup.  Thanks Sleaker.  This is a better place to do this.
		
	}

	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent event) {
		BunnyDoors.Debug("Opened Inventory:"+event.getInventory().getName());
		if (event.getInventory().getName().equals("Inventory")) {
			showKeys(event.getPlayer());
		}
	} 

	void showKeys(Player p) {
		if (SpoutManager.getPlayer(p).isSpoutCraftEnabled()) {
			sendSpoutNotification(p);
		} else {
			if (playerShouldBeBugged(p)) {
				sendVanillaNotification(p);
			}
		}
	}

	private boolean playerShouldBeBugged(Player p) {
		if (plugin.getConfig().getInt("notifyVanillaClientsOn",250) == 0) {
			BunnyDoors.Debug("Not notifying vanilla clients.");
			return false;
		}
		
		if (!NotificationMap.containsKey(p)) {
			NotificationMap.put(p, getUnixEpoc());
			BunnyDoors.Debug("Not in notify list, notifying and putitng on list");
			return true;
		}
		
		if (getUnixEpoc() - NotificationMap.get(p) > plugin.getConfig().getInt("notifyVanillaClientsOn", 250)) {
			NotificationMap.put(p, getUnixEpoc());
			BunnyDoors.Debug("Past notification time. Hassling.");
			return true;
		} else {
			BunnyDoors.Debug("Not past Notification time as set inside of hashmap. Not hassling.");
			return false;
		}
	}

	private int getUnixEpoc() {
		return (int)(System.currentTimeMillis() / 1000L);
	}

	private void sendSpoutNotification(Player p) {
		BunnyDoors.Debug("Found Spout, sending popups");
		
		String msg = "";
		
		// Strike 2 other location:  BunnyKeysCommandExecutor(anon.list)
		for (String key: BunnyPermissionKey.getKeysForPlayer(p)) {
			if (BunnyPermissionKey.hasKey(key,p)) {
				msg += key + " ";
			}
			SpoutManager.getPlayer(p).sendNotification("Keys You Hold:", msg, org.bukkit.Material.WOOD_DOOR);
		}
	}
	
	private void sendVanillaNotification( Player p) {
		// send message with list of keys
		BunnyDoors.Debug("Sending standard keys to player:"+Arrays.toString(BunnyPermissionKey.getKeysForPlayer(p).toArray()) );
		// getKeys().toArray ... bad.
		p.sendMessage("You are also holding the following keys: "+Arrays.toString(BunnyPermissionKey.getKeysForPlayer(p).toArray()));
	}

	
		/*
	void showKey(Player p, String key) {
		SpoutManager.getPlayer(p).sendNotification("Key", key, 
		}*/
}
