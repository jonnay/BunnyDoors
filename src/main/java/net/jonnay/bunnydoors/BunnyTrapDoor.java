package net.jonnay.bunnydoors;

import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.material.TrapDoor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.Effect;


public class BunnyTrapDoor extends BunnyDoor {
	
	protected static BunnyDoors plugin;

	public static boolean isTrapDoor(Block b) {
		return ((b.getType() == Material.TRAP_DOOR));
	}

	public static Block getIdBlockFromBlock(Block b) {
		return b;
	}
	
	public BunnyTrapDoor(Block b) {
		setId(createIdFromBlock(getIdBlockFromBlock(b)));
		setKey(BunnyDoor.plugin.doorSerializer.getDoorKey(id));
		setLocker(BunnyDoor.plugin.doorSerializer.getDoorLocker(id));		
		setBlock(getIdBlockFromBlock(b));
	}
	
	protected void setDoorState(boolean state) {

		TrapDoor door = (TrapDoor) block.getType().getNewData(block.getData());

		// fuck you java.  Learn type inference you bitch.
		byte data = door.getData();
		if (state) {
			data = (byte) (data | ((byte) 0x4));
		} else {
			data = (byte) (data & ((byte) ~0x4));
		}

		door.setData(data);
		block.setData(door.getData(), true);
		
		block.getWorld().playEffect(block.getLocation(), Effect.DOOR_TOGGLE, 0);
	}
}
