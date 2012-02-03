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

	/*
	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent event) {
		BunnyDoors.Debug("Opened Inventory");
		showKeys(event.getPlayer());
	} */

	@EventHandler
	public void onScreenOpen(ScreenOpenEvent e) {
		BunnyDoors.Debug("screen opened");
		Player p = e.getPlayer();
		String keylist = Arrays.toString(plugin.getKeys().toArray());
		if (e.getScreenType() == org.getspout.spoutapi.gui.ScreenType.PLAYER_INVENTORY) {
			BunnyKeyGui somePopup = new BunnyKeyGui(plugin, p, keylist);
			Screen s = e.getScreen();
			if (s == null)
				BunnyDoors.Debug("screen is null");
			
			if (SpoutManager.getPlayer(p).isSpoutCraftEnabled()) {
				s.attachWidget(plugin, somePopup);
			}
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		BunnyDoors.Debug("Closed Inventory");
	}

	void showKeys(Player p, InventoryOpenEvent e) {
		if (SpoutManager.getPlayer(p).isSpoutCraftEnabled()) {
			BunnyDoors.Debug("Found Spout, sending popups");
			//for (String key: plugin.getKeys()) {
			//	showKey(p, key);
			//}
			showKeyGui(p, Arrays.toString(plugin.getKeys().toArray()), e);
		} else {
			// send message with list of keys
			BunnyDoors.Debug("Sending standard keys to player:"+Arrays.toString(plugin.getKeys().toArray()) );
			p.sendMessage("You are also holding the following keys: "+Arrays.toString(plugin.getKeys().toArray()));
		}
	}
	
	void showKeyGui(Player p, String keylist, InventoryOpenEvent e) {
		BunnyDoors.Debug("Sending Popup for "+keylist);

		/*
		GenericPopup somePopup = new GenericPopup();
		GenericLabel label = new GenericLabel(key+" key");
		label.setX(0)
		     .setY(0)
			 .setWidth(200)
			 .setHeight(40);
		label.setAuto(false);
		
		somePopup.attachWidget(plugin, label);
		*/
		/*
		GenericTexture texture = new GenericTexture();
		texture.setUrl("http://dev.bukkit.org/media/images/38/254/default-key.png");
		texture.setWidth(24).setHeight(24); // The size as the downloaded image.
		somePopup.attachWidget(plugin, texture);
		*/

		BunnyKeyGui somePopup = new BunnyKeyGui(plugin, p, keylist);
		
		//SpoutManager.getPlayer(p).getCurrentScreen().attachWidget(plugin, somePopup);
		//SpoutManager.getPlayer(p).getMainScreen().attachPopupScreen(somePopup);
		

		//SpoutManager.getPlayer(p).getCurrentScreen().attachPopupScreen(somePopup);
	}
}
