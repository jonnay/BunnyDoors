package net.jonnay.bunnydoors;

import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class BunnyDoor {
	// can be updated to include chests, and trapdoors. 
	public static boolean isDoor (Block b) {
		return ((b.getType() == Material.WOODEN_DOOR) ||
				(b.getType() == Material.IRON_DOOR_BLOCK));
	}

	public static boolean isTwoBlockDoor(Block b) {
		return ((b.getType() == Material.WOODEN_DOOR) ||
				(b.getType() == Material.IRON_DOOR_BLOCK));
	}

	protected static BunnyDoors plugin;
	
	// not happy about this...
	public static void registerPlugin(BunnyDoors p) {
		plugin = p;
	}

	/**
	 * Returns the "id block" of a door block.
	 * The id block is defined as the bottom half of the door.
	 * Returns null if the passed in block isn't a door
	 */
	public static Block getIdBlockFromBlock(Block b) {
		if (!isDoor(b)) {
			BunnyDoors.Debug("Block recieved isn't a door.");
			return null;
		}

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

	
	// This is where we can cache doors. 
	/**
	 * Factory
	 * use getFromBlock(Block b) to actually construct a new door object.
	 * This ensures that the door will always have a consistent ID.
	 */
	public static BunnyDoor getFromBlock(Block b) {
		Block idBlock = getIdBlockFromBlock(b);

		if (null != idBlock) 
			return new BunnyDoor(idBlock);
		else
			return null;
	}


	private String id;
	private String key;
	private String locker;
	
	
	private BunnyDoor(Block b) {
		id = createIdFromBlock(b);
		key = BunnyDoor.plugin.doorSerializer.getDoorKey(id);
	}

	private String createIdFromBlock(Block b) {
		return ""+
			"x"+b.getX()+
			"y"+b.getY()+
			"z"+b.getZ()+
			"W"+b.getWorld().getName();
	}
	
	public String getId() {
		return id;
	}
   
	public String getKey() {
		return key;
	}
	
	public boolean isLocked(Player p) {
		String key = getKey();
		if (key == null) {
			BunnyDoors.Debug("No key for door "+id);
			return false;
		}
		
		boolean perm = p.hasPermission("bunnydoors.key."+key);

		BunnyDoors.Debug("checking door "+id+" for key:"+key+" bunnydoors.key."+key+" is: "+perm);

		return !perm;  // if the keyholder doesn't have permission, then it IS locked.
	}

	// if we cache doors, invalidate here
	public boolean lock(Player p, String key) {
		String locker = p.getName();
		BunnyDoor.plugin.doorSerializer.setDoorToKey(id, locker, key);
		return true;
	}

	// if we cache doors, invalidate here
	public boolean unlock() {
		BunnyDoor.plugin.doorSerializer.removeDoor(id);
		return true;
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
