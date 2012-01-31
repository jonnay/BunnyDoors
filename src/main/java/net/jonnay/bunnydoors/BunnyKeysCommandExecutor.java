package net.jonnay.bunnydoors;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class BunnyKeysCommandExecutor extends BunnyCommandExecutor {

	protected String mainCommand = "bunnykey";
	
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
						usage(s);
						return false;
					}

					if (plugin.isValidKey(args[2])) {
						s.sendMessage("not a valid key.");
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

					s.sendMessage("Unimplemented");
					
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
