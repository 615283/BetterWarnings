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

//Command Class for ClearSingleWarning command (/clearwarning)
public class ClearSingleWarningCommand implements CommandExecutor {
	
	//Constructor for accessing methods in MainClass()
	private static MainClass plugin;

	public ClearSingleWarningCommand(MainClass plugin) {
	    ClearSingleWarningCommand.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender warner, Command cmd, String label, String[] args) {
		//Checking if CommandSender has permission
		if (warner.hasPermission("betterwarnings.clearwarn")) {
			//Checking that the CommandSender has provided enough args
			if (args.length >= 2) {
				Player target = Bukkit.getPlayer(args[0]);
				boolean isSilent;
				//Checking args[1] (Second argument) is a number
				if (args[1].matches("\\d+")) {
					int index = Integer.parseInt(args[1]);
					if (target != null) {
						//Seeing if there is a third argument then checking if it matches the parameter -s
						if (args.length >= 3) {
							if (args[2].equalsIgnoreCase("-s")) {
								try {
									//Defining isSilent boolean for calling the method
									isSilent=true;
									plugin.clearSingleWarning(warner, target, index, isSilent);
									return true;
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							else {
								try {
									//Defining isSilent boolean for calling the method
									isSilent=false;
									plugin.clearSingleWarning(warner, target, index, isSilent);
									return true;
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
						else {
							try {
								//Defining isSilent boolean for calling the method
								isSilent=false;
								plugin.clearSingleWarning(warner, target, index, isSilent);
								return true;
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
				//If args[1] is not a number
				else {
					warner.sendMessage(msgPrefix + args[1] + ChatColor.RED + "is not a valid number.");
					return true;
				}
		}
		//If CommandSender does not have permission to run the command
		else {
			warner.sendMessage(msgPrefix + ChatColor.RED + "You do not have the permission to use this command!");
			return true;
		}
		return false;
	}

}
