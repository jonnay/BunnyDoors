package net.jonnay.bunnydoors;

import org.bukkit.entity.Player;

public class BunnyPermissionKey extends BunnyKey {


	public BunnyPermissionKey(String name) {
		super(name);
	}
	
	public static String keyToPermission(String k) {
		return "bunnydoors.key."+k;
	}

	public static boolean hasKey(String k, Player p) {
		BunnyDoors.Debug("Checking "+p.getName()+
						 "for " + keyToPermission(k) + ": " +
						 (p.hasPermission (keyToPermission(k)) ? "Yes" : "No"));
		return p.hasPermission(keyToPermission(k));
	}
	
	public boolean hasKey(Player p) {
		return hasKey(this.getName(), p);
	}

	public boolean useKey(Player p) {
		return true; //NoOp
	}
}
