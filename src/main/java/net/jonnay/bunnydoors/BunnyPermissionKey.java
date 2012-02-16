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
	
	public boolean has(Player p) {
		return hasKey(this.getName(), p);
	}

	public boolean use(Player p) {
		return true; //NoOp
	}

	public boolean grant(Playper p) {
				
		if (BunnyDoors.hasExtendedPermissionSupport) {
			BunnyDoors.permissions.playerAdd(p, keyToPermission(this.getName()));

			if (BunnyDoors.hasExtendedSpoutSupport && SpoutManager.getPlayer(p).isSpoutCraftEnabled()) {
				BunnyDoors.Debug("Sending notification for "+key);
				SpoutManager.getPlayer(p).sendNotification("Key Find!", key, org.bukkit.Material.IRON_DOOR);
			} else {
				p.sendMessage("You found a key!  You found the "+key+" key!");
			}
			return true;
						  
		} else {
			p.sendMessage("You can't have a key given to you!  Tell your server admin to install vault!");
			log.severe("No Vault support!  Can't give player "+p.getName()+" the BunnyKey "+key);
			return false;
		}  
	}
}
