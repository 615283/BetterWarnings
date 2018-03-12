/*	COPYRIGHT JAMES CONWAY (615283) 2018 (C)
 *  This code is mine and is not to be used for any personal or other gain.
 *  If you wish to fork off of this GitHub page, feel free but you MUST keep my Copyright notices and name in the code where it is now.
 *  If you compile this code, even if you have edited it, do not share it with others on Bukkit and Spigot, keep it to GitHub.
 */

package com.georlegacy.general.betterwarnings.commands;

import static com.georlegacy.general.betterwarnings.VarClass.msgPrefix;
import com.georlegacy.general.betterwarnings.MainClass;
import com.georlegacy.general.betterwarnings.VarClass;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

//Command Class for warning issuing to players (/warn)
public class WarningCommand implements CommandExecutor {
	
	//Constructor for accessing methods in MainClass()
	private static MainClass plugin;

	public WarningCommand(MainClass plugin) {
	    WarningCommand.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender warner, Command cmd, String label, String[] args) {
		//Checking for permission to issue a warning to a player
		if (warner.hasPermission("betterwarnings.warn")) {
			//If CommandSender has not provided any args
			if (!(args.length >= 2)) {
				warner.sendMessage(msgPrefix + ChatColor.RED + "Please enter the name of the player you wish to assign a warning to and the warning!");
				return true;
			}
			else {
				//If player to be warned is online
				Player playerToBeWarnedPlayer = Bukkit.getPlayer(args[0]);
				if (playerToBeWarnedPlayer != null) {
					String playerToBeWarned = args[0];
					//StringBuilder to build multi-word string from args[]
					StringBuilder warning = new StringBuilder();
						for(int i = 1; i < args.length; i++) {
							warning.append(args[i] + " ");
							warning.toString();
						}
							plugin.logToFile(VarClass.getTime() + " : " + warning + " ", playerToBeWarned);
							warner.sendMessage(msgPrefix + ChatColor.GREEN + playerToBeWarnedPlayer.getName() + " has been assigned the following warning: " + ChatColor.RED + warning);
							playerToBeWarnedPlayer.sendMessage(msgPrefix + ChatColor.RED + "You have been assigned the following behaviour warning: " + ChatColor.DARK_RED + warning);
				}
				//If player isn't online but has joined server before
				else if (plugin.getConfig().getConfigurationSection("uuids").getKeys(false).contains(args[0])) {
					String puuid = (String) plugin.getConfig().get("uuids." + args[0]);
					StringBuilder warning = new StringBuilder();
					for(int i = 1; i < args.length; i++) {
						warning.append(args[i] + " ");
						warning.toString();
					}
					plugin.offlineLogToFile(VarClass.getTime() + " : " + warning + " ", puuid);
					warner.sendMessage(msgPrefix + ChatColor.GREEN + args[0] + " has been assigned the following warning: " + ChatColor.RED + warning);
					return true;
				}
				//Player hasn't joined or UUID has not been logged to config.yml
				else {
					warner.sendMessage(msgPrefix + ChatColor.RED + "The player " + ChatColor.DARK_RED + args[0] + ChatColor.RED + " has never joined the server!");
				}
				return true;
			}
		}
		//CommandSender does not have permission to run the command
		else {
			warner.sendMessage(msgPrefix + ChatColor.RED + "You do not have the permission to use this command!");
			return true;
		}
	}

}
