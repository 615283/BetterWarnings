/*	COPYRIGHT JAMES CONWAY (615283) 2018 (C)
 *  This code is mine and is not to be used for any personal or other gain.
 *  If you wish to fork off of this GitHub page, feel free but you MUST keep my Copyright notices and name in the code where it is now.
 *  If you compile this code, even if you have edited it, do not share it with others on Bukkit and Spigot, keep it to GitHub.
 */

package com.georlegacy.general.betterwarnings;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.ChatColor;

public class VarClass {
	
	//Constructor for accessing methods in MainClass() and defining variable plVersion
		private static MainClass plugin;
		public String plVersion;

		public VarClass(MainClass plugin) {
		    VarClass.plugin = plugin;
		    plVersion = plugin.getDescription().getVersion();
		}
	
	//Method to get time as format shown below for TimeStamp in warnings
	public static String getTime() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Date now = new Date();
		String strDate = sdfDate.format(now);
	    return strDate;
	}
	
	//Message prefix used in most messages send to a CommandSender
	public static final String msgPrefix = ChatColor.translateAlternateColorCodes('&', "&8[&7BetterWarnings&8] ");
}
