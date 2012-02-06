package net.jonnay.bunnydoors;

import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.material.Door;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.Effect;


public class BunnyChest extends BunnyDoor {
	
	protected static BunnyDoors plugin;

	protected String treasureKey;
	
	public static boolean isChest(Block b) {
		return (b.getType() == Material.CHEST);
	}

	public static Block getIdBlockFromBlock(Block b) {
				
		if (!isChest(b)) {
			BunnyDoors.Debug("Block recieved isn't a chest");
			return b;
		}
		
		Block s = b.getRelative(BlockFace.SOUTH);
		Block w = b.getRelative(BlockFace.WEST);
		Block n = b.getRelative(BlockFace.NORTH);
		Block e = b.getRelative(BlockFace.EAST);

		if (isChest(s))
			return s;
		else if (isChest(w))
			return w;
		else
			return b;
	}
	
	public BunnyChest(Block b) {
		setId(createIdFromBlock(getIdBlockFromBlock(b)));
		setKey(BunnyDoor.plugin.doorSerializer.getDoorKey(id));
		setLocker(BunnyDoor.plugin.doorSerializer.getDoorLocker(id));
		treasureKey = BunnyDoor.plugin.doorSerializer.getTreasureKey(id);
		setBlock(getIdBlockFromBlock(b));
	}
	
	protected void setDoorState(boolean state) {
	}

	public boolean hasTreasureKey() {
		return (treasureKey != null);
	}

	public String getTreasureKey() {
		return treasureKey;
	}
	
	public void addTreasureKey(String key) {
		this.treasureKey = key;
		this.setNative(false);
		BunnyDoor.plugin.doorSerializer.setTreasureKey(id, key);
	}

	public void removeTreasureKey() {
		this.treasureKey = null;
		BunnyDoor.plugin.doorSerializer.removeTreasureKey(id);
	}
}
