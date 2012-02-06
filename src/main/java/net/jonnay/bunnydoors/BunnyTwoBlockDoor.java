package net.jonnay.bunnydoors;

import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.material.Door;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.Effect;


public class BunnyTwoBlockDoor extends BunnyDoor {
	
	protected static BunnyDoors plugin;

	public static boolean isTwoBlockDoor(Block b) {
		return ((b.getType() == Material.WOODEN_DOOR) ||
				(b.getType() == Material.IRON_DOOR_BLOCK));
	}

	public static Block getIdBlockFromBlock(Block b) {
				
		if (!isTwoBlockDoor(b)) {
			BunnyDoors.Debug("Block recieved isn't a 2 block door");
			return b;
		}
		
		Block down = b.getRelative(BlockFace.DOWN);
		Block up = b.getRelative(BlockFace.UP);

		if (isDoor(down)) {
			BunnyDoors.Debug("Returning lower block from getIdBlock");
			return down;
		} else if (isDoor(up)) {
			BunnyDoors.Debug("Returning this block from getIdBlock");
			return b;
		} else {
			BunnyDoors.Debug("Arg!  Up block and Down block both aren't doors!");
			// This should never happen?
			throw new RuntimeException("[BunnyDoors] Unreachable state!  Double Door block, but can't find other half!");
		}
	}
	
	public BunnyTwoBlockDoor(Block b) {
		setId(createIdFromBlock(b));
		setKey(BunnyDoor.plugin.doorSerializer.getDoorKey(id));
		setLocker(BunnyDoor.plugin.doorSerializer.getDoorLocker(id));		
		setBlock(b);
	}
	
	protected void setDoorState(boolean state) {
		///  Snarfed from http://forums.bukkit.org/threads/opening-a-door.56454/ bergerkiller
		Block above = block.getRelative(BlockFace.UP);

		Door door = (Door) block.getType().getNewData(block.getData());
		Door doorabove = (Door) above.getType().getNewData(above.getData());

		door.setOpen(state);
		doorabove.setOpen(state);

		block.setData(door.getData(), true);
		above.setData(doorabove.getData(), true);
		
		block.getWorld().playEffect(block.getLocation(), Effect.DOOR_TOGGLE, 0);
	}
}
