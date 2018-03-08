/*	COPYRIGHT JAMES CONWAY (615283) 2018 (C)
 *  This code is mine and is not to be used for any personal or other gain.
 *  If you wish to fork off of this GitHub page, feel free but you MUST keep my Copyright notices and name in the code where it is now.
 *  If you compile this code, even if you have edited it, do not share it with others on Bukkit and Spigot, keep it to GitHub.
 */

package com.georlegacy.general.betterwarnings.commands;

import static com.georlegacy.general.betterwarnings.VarClass.msgPrefix;

import java.io.File;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.georlegacy.general.betterwarnings.MainClass;

public class ClearWarningsCommand implements CommandExecutor {
	
	private static MainClass plugin;

	public ClearWarningsCommand(MainClass plugin) {
	    ClearWarningsCommand.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender warner, Command cmd, String label, String[] args) {
		if (warner.hasPermission("betterwarnings.clearwarns")) {
			if (args.length != 0) {
				String name = args[0];
				Player player = Bukkit.getPlayer(name);
				if (player != null) {
					//warner.sendMessage(ChatColor.RED + "Are you sure you wish to remove " + ChatColor.BOLD + "ALL " + ChatColor.RESET + "" + ChatColor.RED + "of the warnings currently assigned to " + ChatColor.DARK_RED + player.getName() + ChatColor.RED + "?");
					//warner.sendMessage(ChatColor.RED + "To confirm, type " + ChatColor.DARK_RED + "/cwconfirm");
					UUID puuid = player.getUniqueId();
					File fileToBeDeleted = new File(plugin.getDataFolder(), puuid + ".yml");
					fileToBeDeleted.delete();
					warner.sendMessage(msgPrefix + ChatColor.GREEN + "All warnings assigned to " + ChatColor.DARK_GREEN + player.getName() + ChatColor.GREEN + " have been deleted!");
					return true;
				}
				if (plugin.getConfig().getConfigurationSection("uuids").getKeys(false).contains(args[0])) {
					String puuid = (String) plugin.getConfig().get("uuids." + args[0]);
					File fileToBeDeleted = new File(plugin.getDataFolder(), puuid + ".yml");
					fileToBeDeleted.delete();
					warner.sendMessage(msgPrefix + ChatColor.GREEN + "All warnings assigned to " + ChatColor.DARK_GREEN + args[0] + ChatColor.GREEN + " have been deleted!");
					return true;
				}
				else {
					warner.sendMessage(msgPrefix + ChatColor.RED + "The player " + ChatColor.DARK_RED + args[0] + ChatColor.RED + " has never joined the server!");
				}
			}
		}
		else {
			warner.sendMessage(msgPrefix + ChatColor.RED + "You do not have the permission to use this command!");
			return true;
		}
		return false;
	}

}
