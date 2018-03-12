package com.georlegacy.general.betterwarnings;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ColorUtil {
	
	public static String color(String string, Player p) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

}
