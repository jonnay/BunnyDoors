package net.jonnay.bunnydoors;

import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

import java.util.ArrayList;
import java.util.List;

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

	public static List<String> getKeysForPlayer(Player p) {
		ArrayList<String> out = new ArrayList<String>();
		for (String key : BunnyKey.all().keySet()) {
			if ((BunnyKey.all().get(key) instanceof BunnyPermissionKey) &&
				BunnyPermissionKey.hasKey(key, p)) {
				out.add(key);
			}
		}
		return out;
	}

	
	public boolean has(Player p) {
		return BunnyPermissionKey.hasKey(this.getName(), p);
	}

	public boolean use(Player p) {
		return true; //NoOp
	}

	public boolean grant(Player p) {
				
		if (BunnyDoors.hasExtendedPermissionSupport) {
			BunnyDoors.permissions.playerAdd(p, keyToPermission(this.getName()));

			if (BunnyDoors.hasExtendedSpoutSupport && SpoutManager.getPlayer(p).isSpoutCraftEnabled()) {
				BunnyDoors.Debug("Sending notification for "+getName());
				SpoutManager.getPlayer(p).sendNotification("Key Find!", getName(), org.bukkit.Material.IRON_DOOR);
			} else {
				p.sendMessage("You found a key!  You found the "+getName()+" key!");
			}
			return true;
						  
		} else {
			p.sendMessage("You can't have a key given to you!  Tell your server admin to install vault!");
			BunnyDoors.log.severe("No Vault support!  Can't give player "+p.getName()+" the BunnyKey "+getName());
			return false;
		}  
	}
}
