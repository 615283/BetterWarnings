/*	COPYRIGHT JAMES CONWAY (615283) 2018 (C)
 *  This code is mine and is not to be used for any personal or other gain.
 *  If you wish to fork off of this GitHub page, feel free but you MUST keep my Copyright notices and name in the code where it is now.
 *  If you compile this code, even if you have edited it, do not share it with others on Bukkit and Spigot, keep it to GitHub.
 */

package com.georlegacy.general.betterwarnings.commands;

import com.georlegacy.general.betterwarnings.VarClass;
import static com.georlegacy.general.betterwarnings.ColorUtil.color;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

//Class for /betterwarnings (AKA help/info command)
public class HelpCommand implements CommandExecutor {

	//Constructor to provide access to non-static variables in VarClass()
	private VarClass vc;

	public HelpCommand(VarClass vc) {
		this.vc = vc;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		//Sending help and info to CommandSender
		Player p = (Player) sender;
		sender.sendMessage(color("&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-", p));
		sender.sendMessage(color("&8&lBetterWarnings &7&lon &8&lV" + vc.plVersion + " &7&lby &8&l615283 &7&l615283.net", p));
		sender.sendMessage(color("&7/warn <playername> <warning> &8- &7Assigns a warning to a player", p));
		sender.sendMessage(color("&7/warnings <playername> &8- &7Counts the warnings of a player", p));
		sender.sendMessage(color("&7/listwarnings <playername> &8- &7Lists the warnings of a player", p));
		sender.sendMessage(color("&7/clearwarnings <playername> &8- &7Clears &lALL &7warnings currently assigned to a player", p));
		sender.sendMessage(color("&7/clearwarning <playername> <warningnumber> [-s] &8- &7Clears a single warning of a player, add -s to suppress notification to player", p));
		sender.sendMessage(color("&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-&0-&f-", p));
		return true;
	}
}
