/*	COPYRIGHT JAMES CONWAY (615283) 2018 (C)
 *  This code is mine and is not to be used for any personal or other gain.
 *  If you wish to fork off of this GitHub page, feel free but you MUST keep my Copyright notices and name in the code where it is now.
 *  If you compile this code, even if you have edited it, do not share it with others on Bukkit and Spigot, keep it to GitHub.
 */

package com.georlegacy.general.betterwarnings;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
	
	public JoinListener(MainClass plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		mainClass = plugin;
	}
    
	MainClass mainClass;
	
	@EventHandler
	public void playerJoinEvent(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		mainClass.logUUID(player);
	}
}
