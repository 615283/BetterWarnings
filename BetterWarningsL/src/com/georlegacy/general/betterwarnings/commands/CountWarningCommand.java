/*	COPYRIGHT JAMES CONWAY (615283) 2018 (C)
 *  This code is mine and is not to be used for any personal or other gain.
 *  If you wish to fork off of this GitHub page, feel free but you MUST keep my Copyright notices and name in the code where it is now.
 *  If you compile this code, even if you have edited it, do not share it with others on Bukkit and Spigot, keep it to GitHub.
 */

package com.georlegacy.general.betterwarnings.commands;

import static com.georlegacy.general.betterwarnings.VarClass.msgPrefix;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.georlegacy.general.betterwarnings.MainClass;

//Command Class for command to count a player's warnings (/warnings)
public class CountWarningCommand implements CommandExecutor {
	
	//Constructor to allow use of methods from MainClass()
	private static MainClass plugin;

	public CountWarningCommand(MainClass plugin) {
	    CountWarningCommand.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender warner, Command cmd, String label, String[] args) {
		//Checking that CommandSender has the correct permission
		if (warner.hasPermission("betterwarnings.viewwarns")) {	
			//If CommandSender has not provided any arguments
			if (args.length == 0) {
				warner.sendMessage(msgPrefix + ChatColor.RED + "Please enter the name of the player whose warnings you wish to count!");
				return true;
			}
			else {
				String playerToBeWarnedPlayerName = args[0];
				Player playertin = Bukkit.getPlayer(playerToBeWarnedPlayerName);
				//If player is online
				if (playertin != null) {	
					try {
						warner.sendMessage(msgPrefix + ChatColor.GOLD + playertin.getName() + "" + ChatColor.YELLOW + " currently has " + ChatColor.GOLD + plugin.countLines(playerToBeWarnedPlayerName) + ChatColor.YELLOW + " warnings.");
					} catch (IOException e) {
						e.printStackTrace();
						warner.sendMessage(msgPrefix + ChatColor.RED + "That player does not have any warnings on record.");
					}
						return true;
				}
				//If player is not online but their UUID is stored in config file
				if (plugin.getConfig().getConfigurationSection("uuids").getKeys(false).contains(playerToBeWarnedPlayerName)) {
					String playerUUID = (String) plugin.getConfig().get("uuids." + playerToBeWarnedPlayerName);
					try {
						warner.sendMessage(msgPrefix + ChatColor.GOLD + playerToBeWarnedPlayerName + "" + ChatColor.YELLOW + " currently has " + ChatColor.GOLD + plugin.offlineCountLines(playerUUID) + ChatColor.YELLOW + " warnings.");
					} catch (IOException e) {
						e.printStackTrace();
						warner.sendMessage(msgPrefix + ChatColor.RED + "That player does not have any warnings on record.");
					}
					return true;
				}
				//Player has never joined the server of their UUID is not in config.yml
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
