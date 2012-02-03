package net.jonnay.bunnydoors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import org.getspout.spoutapi.event.inventory.InventoryOpenEvent;
import org.getspout.spoutapi.event.inventory.InventoryCloseEvent;

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
		BunnyDoors.Debug("Closed Inventory");
		showKeys(event.getPlayer());
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		BunnyDoors.Debug("Closed Inventory");
	}

	void showKeys(Player p) {
		if (SpoutManager.getPlayer(p).isSpoutCraftEnabled()) {
			BunnyDoors.Debug("Found Spout, sending popups");
			for (String key: plugin.getKeys()) {
				showKey(p, key);
			}
		} else {
			// send message with list of keys
			p.sendMessage("You are also holding the following keys: "+Arrays.toString(plugin.getKeys().toArray()));
		}
	}
	
	void showKey(Player p, String key) {
		BunnyDoors.Debug("Sending Popup for "+key);
		GenericPopup somePopup = new GenericPopup();
		GenericLabel label = new GenericLabel(key+" key");
		somePopup.attachWidget(plugin, label);

		GenericTexture texture = new GenericTexture();
		texture.setUrl("http://dev.bukkit.org/media/images/38/254/default-key.png");
		texture.setWidth(24).setHeight(24); // The size as the downloaded image.
		somePopup.attachWidget(plugin, texture);
		
		SpoutManager.getPlayer(p).getCurrentScreen().attachWidget(plugin, somePopup);
		//SpoutManager.getPlayer(p).getMainScreen().attachWidget(plugin, somePopup);
	}
}
