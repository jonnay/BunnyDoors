package net.jonnay.bunnydoors;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;


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

	if (subArg.equalsIgnoreCase("lockall")) {
			lockall(sender);
			return true;
		} else if (subArg.equalsIgnoreCase("unlockall")) {
			unlockall(sender);
			return true;
		} else if (subArg.equalsIgnoreCase("help")) {
			usage(sender);
			return true;
		} else {
			usage(sender);
			return false;
		}
	}

	private void usage(CommandSender s) {
		s.sendMessage(usageLine("lockall", "Locks all doors"));
		s.sendMessage(usageLine("unlockall","unlock all doors"));
		s.sendMessage(usageLine("help","(You're looking at it .. chum!"));
	}

	private String usageLine(String cmd, String desc) {
		return (ChatColor.WHITE + "/bunnydoor " + cmd + " " +
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
	
}


