package net.jonnay.bunnydoors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.List;
import org.bukkit.block.Block;

public class BunnyKeysCommandExecutor extends BunnyCommandExecutor {

	protected String getMainCommand() {
		return "bunnykey";
	}
	
	final BunnyDoors plugin;

	public BunnyKeysCommandExecutor(BunnyDoors p) {
		plugin = p;
		
		
		addSubExecutor("list", new PlayerSubExecutor() {
				protected String needsPerm() {
					return "bunnydoors.keycmd.list";
				}
				
				public boolean run(CommandSender s, String[] args) {
					String out = "";
					Player p = (Player) s;

					// Strike 2 ... other location: BunnykeyInventoryListner.showKeys
					for (String key : plugin.getKeys()) {
						if (plugin.playerHasKey(key, p)) {
							out += key + " ";
						} 
					}
					if (out == "") {
						out = "(none)";
					}
					
					s.sendMessage("Keys: "+out);

					return true;
				}
				public void usage(CommandSender s) {
					s.sendMessage(usageLine("list","List keys that you have in your posession"));
				}
			});

		addSubExecutor("give", new SubExecutor() {
				protected String needsPerm() {
					return "bunnydoors.keycmd.admin.give";
				}
				
				
				public boolean run(CommandSender s, String[] args) {
					if (args.length < 3) {
						return error(s, "Not enough arguments");
					}

					BunnyDoors.Debug("Checking key "+args[2]);
					
					if (!plugin.isValidKey(args[2])) {
						return error(s, args[2] + " is not a valid key");
					}

					BunnyDoors.Debug("Checking If Player "+args[1]+"Exists");
					
					Player target = Bukkit.getServer().getPlayerExact(args[1]);
					if (target == null) {
						return error(s, "Not a valid Player name");
					}

					if (plugin.grantKey(target, args[2])) {
						error(s, "Could not grant the key!  Check the logs for the reason why!");
					}
					
					return true;
				}
				
				public void usage(CommandSender s) {
					s.sendMessage(usageLine("give","user","key","Gives <key> to <user>"));
				}
			});

		addSubExecutor("put", new SubExecutor() {
				protected String needsPerm() {
					return "bunnydoors.keycmd.admin.put";
				}

				public boolean run(CommandSender s, String[] args) {
					Player p = (Player) s;
					Block b = p.getTargetBlock(null, 10);

					if (!BunnyChest.isChest(b)) {
						error(s, "You aren't looking at a chest!");
					}
					
					if (!plugin.hasExtendedPermissionSupport) {
						error(s, "You need Vault installed so that users can take keys!");
						s.sendMessage("The key has been added, but install Vault so that your users can take it.");
					}

					if (!plugin.isValidKey(args[1])) {
						error(s, args[1] + " is not a valid key");
						return false;
					}

					
					BunnyChest c = (BunnyChest) BunnyDoor.getFromBlock(b);
					c.addTreasureKey(args[1]);
					s.sendMessage("Put the key "+args[1]+" in the chest.");
					return true;
				}

				public void usage(CommandSender s) {
					s.sendMessage(usageLine("put", "key", "Put <key> in the chest so players can find it."));
				}			
			});

		addSubExecutor("take", new SubExecutor() {
				protected String needsPerm() {
					return "bunnydoors.kkeycmd.admin.put";
				}

				public boolean run(CommandSender s, String[] args) {
					Player p = (Player) s;
					Block b = p.getTargetBlock(null, 10);

					if (!BunnyChest.isChest(b)) {
						error(s, "You aren't looking at a chest!");
					}

					BunnyChest c = (BunnyChest) BunnyDoor.getFromBlock(b);
					c.removeTreasureKey();
					s.sendMessage("Removed the key from the chest.");
					return true;
				}

				public void usage(CommandSender s) {
					s.sendMessage(usageLine("take", "key", "Removes the key from the treasure chest."));
				}			
			});
		
		addSubExecutor("add", new SubExecutor() {
				protected String needsPerm() {
					return "bunnydoors.keycmd.admin.add";
				}
				
				
				public boolean run(CommandSender s, String[] args) {
					if (args.length < 2) {
						usage(s);
						return false;
					}

					List<String> keys = plugin.getKeys();
					keys.add(args[1]);
					
					plugin.getConfig().set("keys", keys);
					plugin.saveConfig();

					s.sendMessage("Added key "+args[1]);
					return true;
				}

				public void usage(CommandSender s) {
					s.sendMessage(usageLine("add", "key", "Add key to global keys")); 
				}
			});

		addSubExecutor("listall", new SubExecutor() {
				protected String needsPerm() {
					return "bunnydoors.keycmds.listall";
				}
				

				public boolean run(CommandSender s, String[] args) {
					s.sendMessage(plugin.getKeys().toString());
					return true;
				}

				public void usage(CommandSender s) {
					s.sendMessage(usageLine("listall", "List all available keys"));
				}
				
			});
			
	}
	

	
}
