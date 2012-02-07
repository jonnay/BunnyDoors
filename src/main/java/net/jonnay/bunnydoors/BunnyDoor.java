package net.jonnay.bunnydoors;

import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.material.Door;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.Effect;

//TODO Refactor me soon?
// This is the 'ideal' object model.  It might be too heavy.
// - abstract BunnyLockable,
//   - BunnyTrapDoor
//   - abstract BunnyMultiBlockUnlockable
//     - abstract BunnyTallUnlockable
//       - BunnyWoodDoor,
//       - BunnyIronDoor
//     - abstract BunnyWideUnlockable
//       - BunnyCjest 


public abstract class BunnyDoor {
	
	protected static BunnyDoors plugin;

	// can be updated to include chests, and trapdoors. 
	public static boolean isDoor (Block b) {
		return (BunnyTwoBlockDoor.isTwoBlockDoor(b) || BunnyChest.isChest(b));
	}
	
	// not happy about this...
	public static void registerPlugin(BunnyDoors p) {
		plugin = p;
	}

	
	// This is where we can cache doors. 
	/**
	 * Factory
	 * use getFromBlock(Block b) to actually construct a new door object.
	 * This ensures that the door will always have a consistent ID.
	 */
	public static BunnyDoor getFromBlock(Block b) {

		if (BunnyTwoBlockDoor.isTwoBlockDoor(b)) {
			Block idBlock = BunnyTwoBlockDoor.getIdBlockFromBlock(b);
			return new BunnyTwoBlockDoor(idBlock);
		} else if (BunnyChest.isChest(b)) {
			Block idBlock = BunnyChest.getIdBlockFromBlock(b);
			return new BunnyChest(idBlock);
		} else {
			BunnyDoors.log.severe("Can't construct the door from the block!");
			return null;
		}
	}


	protected String id;
	protected String key;
	protected String locker;
	protected Block block;
	protected boolean nativeDoor;
	
	protected String createIdFromBlock(Block b) {
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

	public void setId(String id) {
		this.id = id;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setLocker(String locker) {
		this.locker = locker;
	}

	public void setBlock(Block b) {
		this.block = b;
	}

	public void setNative(boolean n) {
		this.nativeDoor = n;
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
		boolean perm = plugin.playerHasKey(key, p);

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

	protected abstract void setDoorState(boolean state);
	
	public void open() {
		setDoorState(true);
	}
	
	public void close() {
		setDoorState(false);
		
	}
}
