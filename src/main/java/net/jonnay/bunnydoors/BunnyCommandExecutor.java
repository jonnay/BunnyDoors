package net.jonnay.bunnydoors;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.block.Block;
import java.util.HashMap;

public abstract class BunnyCommandExecutor implements CommandExecutor {

	protected abstract class SubExecutor {
		protected String permission = "";

		public abstract boolean run(CommandSender s, String[] args);
		public abstract void usage(CommandSender s); 

		public boolean permitted(CommandSender s) {
			return (isConsole(s) || hasPerm(s, permission));
		}

		private boolean isConsole(CommandSender s) {
			return (s instanceof ConsoleCommandSender);
		}

		private boolean hasPerm(CommandSender s, String perm) {
			Player p = (Player) s;
			return p.hasPermission(perm);
		}
	
		
		protected String usageLine(String cmd, String desc) {
			return (ChatColor.WHITE + "/" + getMainCommand() + " " + cmd + " " +
					ChatColor.BLUE + " - " +
					ChatColor.LIGHT_PURPLE + " " + desc); 
		}

		protected String usageLine(String cmd, String arg, String desc) {
			return usageLine(cmd + ChatColor.YELLOW + "<" + arg + "> ",
							 desc);
		}

		protected String usageLine(String cmd, String arg, String arg1, String desc) {
			return usageLine(cmd, arg + ChatColor.YELLOW + "<" + arg1 + "> ", desc);
		}
	}

	protected abstract class PlayerSubExecutor extends SubExecutor {}

	protected String mainCommand;
	
	HashMap<String, SubExecutor> subExecutors = new HashMap<String, SubExecutor>();
	
	abstract protected String getMainCommand();

	public void addSubExecutor(String sub, SubExecutor ex) {
		subExecutors.put(sub, ex);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		BunnyDoors.Debug("Running "+cmd.getName());
		if (!cmd.getName().equalsIgnoreCase(getMainCommand())) {
			BunnyDoors.Debug("BunnyCommandExecutor... the cmd "+cmd.getName()+"isn't equal to "+getMainCommand());
			return false;
		}
		
		if (args.length < 1) {
			usage(sender);
			return false;
		}

		String subArg = args[0];
		SubExecutor ex = subExecutors.get(subArg);

		if (ex == null) {
			BunnyDoors.Debug("Could not find executor "+subArg+" inside of executor list");
			usage(sender);
			return false;
		} else {
			if ((ex instanceof PlayerSubExecutor) &&
				(sender instanceof ConsoleCommandSender)) {
				sender.sendMessage("That command can't be run from the console");
				return false;
			}
			
			return ex.run(sender, args);
		}
	}

	public void usage(CommandSender s) {
		BunnyDoors.Debug("Usage on BunnyCommandExecutor");
		for (SubExecutor ex : subExecutors.values()) {
			ex.usage(s);
		}
	}	
}


