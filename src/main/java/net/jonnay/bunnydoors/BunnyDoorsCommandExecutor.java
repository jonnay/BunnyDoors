package net.jonnay.bunnydoors;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.block.Block;

public class BunnyDoorsCommandExecutor implements CommandExecutor {
	BunnyDoors plugin;
	
	public BunnyDoorsCommandExecutor(BunnyDoors p) {
		plugin = p;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!cmd.getName().equalsIgnoreCase("bunnydoor")) {
			return false;
		}
		
		if (args.length < 1) {
			usage(sender);
			return false;
		}

		String subArg = args[0];

		// TODO ::  Check if the sender is a player, and then start going through commands.
		// The check up here should reduce code duplication, and makes more conceptual sense to me anyhow.
		
		if (subArg.equalsIgnoreCase("lockall")) {
			lockall(sender);
			return true;
		} else if (subArg.equalsIgnoreCase("unlockall")) {
			unlockall(sender);
			return true;
		} else if (subArg.equalsIgnoreCase("info")) {
			return info(sender);
		} else if (subArg.equalsIgnoreCase("help")) {
			usage(sender);
			return true;
		} else if (subArg.equalsIgnoreCase("lock")) {
			if (args.length < 2) {
				sender.sendMessage(ChatColor.RED+" lock needs a key!");
				return false;
			}

			if (!plugin.isValidKey(args[1])) {
				sender.sendMessage(ChatColor.RED+" not a valid key.  Valid keys are: "+getValidKeysAsString());
				return false;
			}
			
			return lock(sender, args[1]);
		} else if (subArg.equalsIgnoreCase("unlock")) {
			return unlock(sender);
		} else if (subArg.equalsIgnoreCase("reload")) {
			return reload(sender);
		} else {
			usage(sender);
			return false;
		}
	}

	// this probably needs to use a stringbuilder?
	private String getValidKeysAsString() {
		String out = "";
		for (String key : plugin.getKeys()) {
			out = out + key + " ";
		}
		return out;
	}

	private boolean hasPerm(CommandSender sender, String perm) {
		return ((sender instanceof ConsoleCommandSender) ||
				((sender instanceof Player) && ((Player) sender).hasPermission("bunnydoors."+perm)));
	} 

	private boolean reload(CommandSender s) {
		if (!hasPerm(s, "admin.reload"))
			return false;

		plugin.reloadConfig();
		plugin.doorSerializer.reload();

		s.sendMessage("Reloaded configuration");
		
		return true;
	}
	
	private void usage(CommandSender s) {
		if (hasPerm(s, "lock")) {
			s.sendMessage(usageLine("lockall", "Locks all the doors!"));
			s.sendMessage(usageLine("unlockall","Unlock all the doors!"));
			s.sendMessage(usageLine("lock", "key", "Locks a door with the given key"));
			s.sendMessage(usageLine("unlock", "Unlock a door"));
		}
		if (hasPerm(s, "admin.reload")) {
			s.sendMessage(usageLine("reload", "Reload the configuration."));
		}
		s.sendMessage(usageLine("info", "Shows info about a door"));
		s.sendMessage(usageLine("help","(You're looking at it .. chum!"));
		
	}

	private String usageLine(String cmd, String desc) {
		return (ChatColor.WHITE + "/bunnydoor " + cmd + " " +
				ChatColor.BLUE + " - " +
				ChatColor.LIGHT_PURPLE + " " + desc); 
	}

	private String usageLine(String cmd, String arg, String desc) {
		return (ChatColor.WHITE + "/bunnydoor " + cmd + " " +
				ChatColor.YELLOW + "<" + arg + "> " + 
				ChatColor.BLUE + " - " +
				ChatColor.LIGHT_PURPLE + " " + desc); 
	}
	
	private void lockall(CommandSender sender) {
		Player p = (sender instanceof Player) ? ((Player) sender) : null;

		sender.sendMessage(ChatColor.WHITE+"Locking ALL THE DOORS!");

		plugin.lockAll(p);
	}

	private void unlockall(CommandSender sender) {
		Player p = (sender instanceof Player) ? ((Player) sender) : null;

		sender.sendMessage(ChatColor.WHITE+"Unlocking ALL THE DOORS!");

		plugin.unlockAll(p);
	}

	private boolean info(CommandSender sender) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED+" You must be a player to do this!");
			return false;
		}

		Player p = (Player) sender;
		
		Block b = p.getTargetBlock(null, 10);

		String msg = "Info: "+
			ChatColor.LIGHT_PURPLE + "Meta: " + ChatColor.WHITE + Byte.toString(b.getData())+"  "+
			ChatColor.LIGHT_PURPLE + "World: " + ChatColor.WHITE + b.getWorld().getName()+"  "+
			ChatColor.LIGHT_PURPLE + "x: " + ChatColor.WHITE + b.getX()+" "+
			ChatColor.LIGHT_PURPLE + "y: " + ChatColor.WHITE + b.getY()+" "+
			ChatColor.LIGHT_PURPLE + "z: " + ChatColor.WHITE + b.getZ()+" ";

		sender.sendMessage(msg);
		
		BunnyDoor d = BunnyDoor.getFromBlock(b);
		if (!d.isNative()) {
			sender.sendMessage(ChatColor.WHITE + "Not A Bunny Door");
		} else {
			sender.sendMessage(ChatColor.LIGHT_PURPLE + "Bunny Door");
			sender.sendMessage(ChatColor.LIGHT_PURPLE + "ID: " + ChatColor.WHITE + d.getId() + "  " );
			sender.sendMessage(ChatColor.LIGHT_PURPLE + "Key: " + ChatColor.WHITE + d.getKey()+ "  ");
			sender.sendMessage(ChatColor.LIGHT_PURPLE + "Locked By: " + ChatColor.WHITE + d.getLocker());
		}

		return true;
	}

	private boolean lock(CommandSender sender, String key) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED+" You must be a player to do this");
			return false;
		}
		
		Player p = (Player) sender;
		Block b = p.getTargetBlock(null, 10);

		if (!BunnyDoor.isDoor(b)) {
			sender.sendMessage(ChatColor.RED+" You aren't looking at a door!");
			return false;
		}

		BunnyDoor d = BunnyDoor.getFromBlock(b);
		
		if (d.lock(p, key)) {
			sender.sendMessage(ChatColor.LIGHT_PURPLE+" Door Locked!");
			return true;
		} else {
			sender.sendMessage(ChatColor.RED+" Door not locked due to error.  Check the logs.");
			return false;
		}
	}

 
	private boolean unlock(CommandSender sender) {
		//sigh  Obviously this needs refactoring.
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED+" You must be a player to do this");
			return false;
		}
		Player p = (Player) sender;
		Block b = p.getTargetBlock(null, 10);

		if (!BunnyDoor.isDoor(b)) {
			sender.sendMessage(ChatColor.RED+" You aren't looking at a door!");
			return false;
		}

		BunnyDoor d = BunnyDoor.getFromBlock(b);
		
		if (d.unlock()) {
			sender.sendMessage(ChatColor.LIGHT_PURPLE+" Door UnLocked!");
			return true;
		} else {
			sender.sendMessage(ChatColor.RED+" Door not locked due to error.  Check the logs.");
			return false;
		}
	}
}


