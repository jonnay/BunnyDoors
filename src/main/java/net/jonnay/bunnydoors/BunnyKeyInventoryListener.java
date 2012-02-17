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
         
public class BunnyKeyInventoryListener implements Listener {

	private BunnyDoors plugin;

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
			BunnyDoors.Debug("Found Spout, sending popups");
			
			String msg = "";

			// Strike 2 other location:  BunnyKeysCommandExecutor(anon.list)
			for (String key: BunnyPermissionKey.getKeysForPlayer(p)) {
				if (BunnyPermissionKey.hasKey(key,p)) {
					msg += key + " ";
				}
				SpoutManager.getPlayer(p).sendNotification("Keys You Hold:", msg, org.bukkit.Material.WOOD_DOOR);
			}
		} else {
			// send message with list of keys
			BunnyDoors.Debug("Sending standard keys to player:"+Arrays.toString(BunnyPermissionKey.getKeysForPlayer(p).toArray()) );
			// getKeys().toArray ... bad.
			p.sendMessage("You are also holding the following keys: "+Arrays.toString(BunnyPermissionKey.getKeysForPlayer(p).toArray()));
		}
	}
	/*
	void showKey(Player p, String key) {
		SpoutManager.getPlayer(p).sendNotification("Key", key, 
		}*/
}
