package net.jonnay.bunnydoors;

import org.bukkit.Material;
import org.bukkit.block.Block;

import org.bukkit.event.block.Action;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;


// Block

public class DoorListener implements Listener {

	public static final int DOOR_OPEN_TIME = 20 * 10; // (20 Ticks per second, times 10 seconds)

	private class DoorCloser implements Runnable {
		BunnyDoor door;
		
		public DoorCloser(BunnyDoor d) {
			door = d;
		}

		public void run() {
			door.close();
		}
	}

	
	private BunnyDoors plugin; 
	
	public DoorListener(BunnyDoors p) {
		plugin = p;
	}
	
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent event) {
		BunnyDoors.Debug("Player action:"+event.getAction());
		
		if (event.getAction() != Action.LEFT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		
		Block block = event.getClickedBlock();
		Material material = block.getType();
		Player player = event.getPlayer();
		
		BunnyDoors.Debug("Door?"+ (BunnyDoor.isDoor(block) ? "Yes" : "No"));
		if (!BunnyDoor.isDoor(block))
			return;
		
		BunnyDoors.Debug("Action: "+event.getAction());

		//event might be cancelled?
		if (event.isCancelled()) {
			BunnyDoors.Debug("Event was Cancelled");
			return;
		}

		// Not sure about the indirection here.. Refactor later?
		BunnyDoors.Debug("Locked?"+ (plugin.isLocked(block, player) ? "Yes" : "no"));

		BunnyDoor d = BunnyDoor.getFromBlock(block);
		if (d.isLocked()) {
			if (d.canPlayerOpen(player)) {
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new DoorCloser(d), DOOR_OPEN_TIME);
			} else {
				plugin.sendLockedMessage(player, d.getKey().getName() );
				event.setCancelled(true);
				return;
			}
		}

		if (BunnyChest.isChest(block)) {
			BunnyDoors.Debug("Checking Chest");
			BunnyChest c = (BunnyChest) d;
			if (c.hasTreasureKey()) {
				BunnyKey.get(c.getTreasureKey()).grant(player);		  
			}
		}
		
		return;
    }	
}


