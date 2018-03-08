/*	COPYRIGHT JAMES CONWAY (615283) 2018 (C)
 *  This code is mine and is not to be used for any personal or other gain.
 *  If you wish to fork off of this GitHub page, feel free but you MUST keep my Copyright notices and name in the code where it is now.
 *  If you compile this code, even if you have edited it, do not share it with others on Bukkit and Spigot, keep it to GitHub.
 */

package com.georlegacy.general.betterwarnings.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HelpCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-"));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7BetterWarnings &8V2.1.6 &7by &9615&f283"));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9615&f283&1.net"));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7/warn <playername> <warning> &8- &7Assigns a warning to a player"));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7/warnings <playername> &8- &7Counts the warnings of a player"));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7/listwarnings <playername> &8- &7Lists the warnings of a player"));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7/clearwarnings <playername> &8- &7Clears &lALL &7warnings currently assigned to a player"));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7/clearwarning <playername> <warningnumber> [-s] &8- &7Clears a single warning of a player, add -s to suppress notification to player"));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-"));
		return false;
	}

}
