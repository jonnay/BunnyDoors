package net.jonnay.bunnydoors;

import org.bukkit.Material;
import org.bukkit.block.Block;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;

// Block

public class DoorListener implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent event) {
		BunnyDoor.Debug("Player action:"+event.getAction());
		
		if (event.getAction() != Action.LEFT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		
		Block block = event.getClickedBlock();
		Material material = block.getType();

		BunnyDoor.Debug("Door?"+ (isDoor(block) ? "Yes" : "No"));
		if (isDoor(block))
			return;
		
		BunnyDoor.Debug("Action: "+event.getAction());

		//event might be cancelled?
		if (event.isCancelled()) {
			BunnyDoor.Debug("Event was Cancelled");
			return;
		}

		BunnyDoor.Debug("Locked?"+ (isLocked(block) ? "Yes" : "no"));
		if (isLocked(block)) {
			event.setCancelled(true);
			return;
		}
		
    }

	private boolean isDoor(Block block) {
		return ((block.getType() == Material.WOODEN_DOOR) ||
				(block.getType() == Material.IRON_DOOR_BLOCK));
	}

	private boolean isLocked(Block block) {
		return false;
	}

	private void actOnDoor(Block mainblock) {
		/*
		///  Snarfed from http://forums.bukkit.org/threads/opening-a-door.56454/ bergerkiller
		Block b = mainblock.getRelative(face);
		Material type = b.getType();
		if (Util.isDoor(type)) {
			Door door = (Door) type.getNewData(b.getData());
			if (toggled != door.isOpen()) {
				door.setOpen(toggled);
				Block above = b.getRelative(BlockFace.UP);
				Block below = b.getRelative(BlockFace.DOWN);
				if (Util.isDoor(above.getType())) {
					b.setData(door.getData(), true);
					door.setTopHalf(true);
					above.setData(door.getData(), true);
				} else if (Util.isDoor(below.getType())) {
					door.setTopHalf(false);
					below.setData(door.getData(), true);
					door.setTopHalf(true);
					b.setData(door.getData(), true);
				}
				b.getWorld().playEffect(b.getLocation(), Effect.DOOR_TOGGLE, 0);
			}
		}
		*/
	}
}


