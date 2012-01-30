package net.jonnay.bunnydoors;

import org.bukkit.Material;
import org.bukkit.block.Block;

import org.bukkit.event.block.Action;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.entity.Player;

// Block

public class DoorListener implements Listener {

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
		if (plugin.isLocked(block, player)) {
			event.setCancelled(true);
			return;
		}
		
    }

}


