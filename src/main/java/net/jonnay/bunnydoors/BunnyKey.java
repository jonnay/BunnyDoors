package net.jonnay.bunnydoors;

import java.util.HashMap;
import org.bukkit.entity.Player;

public abstract class BunnyKey {

	private String name;
	private static HashMap<String, BunnyKey> keyring = new HashMap<String, BunnyKey>();

	public static void add(String name, BunnyKey key) {
	    keyring.put(name, key);		
	}

	public static BunnyKey get(String name) {
		return keyring.get(name);
	}

	public static HashMap<String, BunnyKey> all() {
		return keyring;
	}
	
	BunnyKey(String n) {
		name = n;
	}

	public String getName() {
		return name;
	}
	
	/**
	 * Checks to see if the player p has the key
	 */
	public abstract boolean hasKey(Player p);

	/**
	 * called when the player uses the key on a valid door
	 */
	public abstract boolean useKey(Player p);
	
}
