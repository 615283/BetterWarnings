/*	COPYRIGHT JAMES CONWAY (615283) 2018 (C)
 *  This code is mine and is not to be used for any personal or other gain.
 *  If you wish to fork off of this GitHub page, feel free but you MUST keep my Copyright notices and name in the code where it is now.
 *  If you compile this code, even if you have edited it, do not share it with others on Bukkit and Spigot, keep it to GitHub.
 */

package com.georlegacy.general.betterwarnings.commands;

import static com.georlegacy.general.betterwarnings.VarClass.msgPrefix;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.georlegacy.general.betterwarnings.MainClass;

//Command Class for command that lists a player's warnings (/listwarnings)
public class ListWarningCommand implements CommandExecutor {
	
	//Constructor to provide access to methods in MainClass()
	private static MainClass plugin;

	public ListWarningCommand(MainClass plugin) {
	    ListWarningCommand.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender warner, Command cmd, String label, String[] args) {
		//Checking that the CommandSender has permission
		if (warner.hasPermission("betterwarnings.listwarns")) {
			//Checking that the CommandSender has provided arguments
			if (args.length == 0) {
				warner.sendMessage(msgPrefix + ChatColor.RED + "Please enter the name of the player whose warnings you wish to view!");
				return true;
			}
			else {
				String warned = args[0];
				Player warnedPlayer = Bukkit.getPlayer(warned);
				//Checking if the player is online
				if (warnedPlayer != null) {
					warner.sendMessage(msgPrefix + ChatColor.YELLOW + "Reading the list of warnings for " + ChatColor.GOLD + warnedPlayer.getName() + ChatColor.YELLOW + ":");
					plugin.getTextFromFile(warned, warner).forEach(warner::sendMessage);
					return true;
				}
				//Checking if the player has their UUID stored in the configuration file
				else if (plugin.getConfig().getConfigurationSection("uuids").getKeys(false).contains(warned)) {
					String puuid = (String) plugin.getConfig().get("uuids." + warned); 
					warner.sendMessage(msgPrefix + ChatColor.YELLOW + "Reading the list of warnings for " + ChatColor.GOLD + args[0] + ChatColor.YELLOW + ":");
					plugin.offlineGetTextFromFile(puuid, warner).forEach(warner::sendMessage);
					return true;
				}
				//The player has never joined the server or their UUID is not stored in config.yml
				else {
					warner.sendMessage(msgPrefix + ChatColor.RED + "The player " + ChatColor.DARK_RED + args[0] + ChatColor.RED + " has never joined the server!");
					return true;
				}
			}
		}
		//CommandSender does not have permission to run the command
		else {
			warner.sendMessage(msgPrefix + ChatColor.RED + "You do not have the permission to use this command!");
			return true;
		}
	}

}
