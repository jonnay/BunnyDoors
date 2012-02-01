package net.jonnay.bunnydoors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.List;

public class BunnyKeysCommandExecutor extends BunnyCommandExecutor {

	protected String getMainCommand() {
		return "bunnykey";
	}
	
	final BunnyDoors plugin;

	public BunnyKeysCommandExecutor(BunnyDoors p) {
		plugin = p;
		
		
		addSubExecutor("list", new PlayerSubExecutor() {
				String permission = "bunnydoors.keycmds.list";
				public boolean run(CommandSender s, String[] args) {
					String out = "";
					Player p = (Player) s;
					
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
				String permission = "bunnydoors.keycmds.admin.give";
				
				public boolean run(CommandSender s, String[] args) {
					if (args.length < 3) {
						return error(s, "Not enough arguments");
					}

					if (plugin.isValidKey(args[2])) {
						return error(s, args[2] + " is not a valid key");
					}

					Player target = Bukkit.getServer().getPlayer(args[1]);
					if (target == null) {
						return error(s, "Not a valid Player name");
					}

					if (plugin.hasExtendedPermissionSupport) {
						plugin.permissions.playerAdd(target, BunnyDoors.keyToPermission(args[2]));
						s.sendMessage("Added "+args[2]+" to "+args[1]);
					}
					
					return true;
				}
				
				public void usage(CommandSender s) {
					s.sendMessage(usageLine("give","user","key","Gives <key> to <user>"));
				}
			});

		addSubExecutor("add", new SubExecutor() {
				String permission = "bunnydoors.keycmds.admin.add";
				
				public boolean run(CommandSender s, String[] args) {
					if (args.length < 2) {
						usage(s);
						return false;
					}

					List<String> keys = plugin.getKeys();
					keys.add(args[1]);
					
					plugin.getConfig().set("keys", keys);
					plugin.saveConfig();
					return true;
				}

				public void usage(CommandSender s) {
					s.sendMessage(usageLine("add", "key", "Add key to global keys")); 
				}
			});

		addSubExecutor("listall", new SubExecutor() {
				String permission = "bunnydoors.keycmds.listall";

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
