package net.jonnay.bunnydoors;

import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.inventory.PlayerInventory;

public class BunnyOneTimeKey extends BunnyKey {

	private int materialId;

	public BunnyOneTimeKey(String name, int materialId) {
		super(name);
		this.materialId = materialId;
	}
	
	public boolean has(Player p) {
		ItemStack stack = p.getInventory().getItemInHand();
		return (stack.getTypeId() == this.materialId);
	}

	public boolean use(Player p) {
		ItemStack stack = p.getInventory().getItemInHand();
		p.getInventory().removeItem(new ItemStack(this.materialId, 1));
		return true;
	}

	public boolean grant(Player p) {
		p.getInventory().addItem(new ItemStack(this.materialId, 1));
		return true;
	}
}
