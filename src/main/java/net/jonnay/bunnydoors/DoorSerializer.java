package net.jonnay.bunnydoors;

import java.io.*;
import java.util.*;
import org.bukkit.entity.Player;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.FileConfiguration;

public class DoorSerializer {

	private FileConfiguration serializedDoors = null;
	private File serializedDoorsFile = null;

	private BunnyDoors plugin;
	
	public DoorSerializer(BunnyDoors p) {
		plugin = p;
	}

	public void reload() {
		if (serializedDoorsFile == null) {
			serializedDoorsFile = new File(plugin.getDataFolder(), "lockedDoors.yml");
		}
		serializedDoors = YamlConfiguration.loadConfiguration(serializedDoorsFile);
 
		// Look for defaults in the jar
		InputStream defConfigStream = plugin.getResource("serializedDoors.yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			serializedDoors.setDefaults(defConfig);
		}
	}

	public FileConfiguration get() {
		if (serializedDoors == null) {
			reload();
		}
		return serializedDoors;
	}

	public void save() {
		if (serializedDoors == null || serializedDoorsFile == null) {
			return;
		}
		try {
			serializedDoors.save(serializedDoorsFile);
		} catch (java.io.IOException ex) {
			plugin.log.severe("Could not save config to " + serializedDoorsFile+ ex);
			ex.printStackTrace();
		}
	}

	public String getDoorKey(String id) {
		return serializedDoors.getString(id+".key");
	}

	public String getDoorLocker(String id) {
		return serializedDoors.getString(id+".locker");
	}

	
	public void setDoorToKey(String id, String keyholder, String key) {
		serializedDoors.set(id+".key", key);
		serializedDoors.set(id+".locker", keyholder);
		save();
	}

	public void removeDoor(String id) {
		serializedDoors.set(id, null);
		save();
	}
}
