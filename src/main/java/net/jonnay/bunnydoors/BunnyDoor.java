package net.jonnay.bunnydoors;

import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.material.Door;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.Effect;

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
	private Block block;
	private boolean nativeDoor;
	
	private BunnyDoor(Block b) {
		id = createIdFromBlock(b);
		key = BunnyDoor.plugin.doorSerializer.getDoorKey(id);
		locker = BunnyDoor.plugin.doorSerializer.getDoorLocker(id);		
		block = b;
		if (key == null)
			nativeDoor = true;
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

	public String getLocker() {
		return locker;
	}

	public boolean isNative() {
		return nativeDoor;
	}
	
	public boolean isLocked() {
		String key = getKey();
		if (key == null) {
			BunnyDoors.Debug("No key for door "+id);
			return false;
		}
		return true;
	}

	public boolean canPlayerOpen(Player p) {
		String key = getKey();
		if (key == null) {
			return true;
		}
		boolean perm = p.hasPermission("bunnydoors.key."+key);

		BunnyDoors.Debug("checking door "+id+" for key:"+key+" bunnydoors.key."+key+" is: "+perm);

		return perm;  // if the keyholder doesn't have permission, then it IS locked.
	}

	// if we cache doors, invalidate here
	public boolean lock(Player p, String key) {
		String locker = p.getName();
		BunnyDoor.plugin.doorSerializer.setDoorToKey(id, locker, key);
		nativeDoor = false;
		this.locker = locker;
		this.key = key;
		return true;
	}

	// if we cache doors, invalidate here
	public boolean unlock() {
		BunnyDoor.plugin.doorSerializer.removeDoor(id);
		return true;
	}

	private boolean setDoorState(boolean state) {
		///  Snarfed from http://forums.bukkit.org/threads/opening-a-door.56454/ bergerkiller
		Door door = (Door) block.getType().getNewData(block.getData());
		door.setOpen(state);

		Block above = block.getRelative(BlockFace.UP);

		block.setData(door.getData(), true);
		above.setData(door.getData(), true);
		
		block.getWorld().playEffect(block.getLocation(), Effect.DOOR_TOGGLE, 0);
		return true;
	}
	
	public void open() {
		setDoorState(true);
	}
	
	public void close() {
		setDoorState(false);
		
	}
}
