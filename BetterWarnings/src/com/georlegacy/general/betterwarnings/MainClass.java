/*	COPYRIGHT JAMES CONWAY (615283) 2018 (C)
 *  This code is mine and is not to be used for any personal or other gain.
 *  If you wish to fork off of this GitHub page, feel free but you MUST keep my Copyright notices and name in the code where it is now.
 *  If you compile this code, even if you have edited it, do not share it with others on Bukkit and Spigot, keep it to GitHub.
 */

package com.georlegacy.general.betterwarnings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import static com.georlegacy.general.betterwarnings.VarClass.msgPrefix;
import com.georlegacy.general.betterwarnings.commands.*;

public class MainClass extends JavaPlugin{
	@Override
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new JoinListener(this), this);
		registerConfig();
		metricsLog();
		
		getCommand("warn").setExecutor(new WarningCommand(this));
		getCommand("warning").setExecutor(new CountWarningCommand(this));
		getCommand("listwarnings").setExecutor(new ListWarningCommand(this));
		getCommand("clearwarnings").setExecutor(new ClearWarningsCommand(this));
		getCommand("clearwarning").setExecutor(new ClearSingleWarningCommand(this));
		getCommand("betterwarnings").setExecutor(new HelpCommand());
	}
	
	//metrics logging
	public void metricsLog() {
	    try {
	        Metrics metrics = new Metrics(this);
	        metrics.start();
	        getLogger().info(ChatColor.LIGHT_PURPLE + "Succesfully logged data to plugin metrics.");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	//config registering
	private void registerConfig() {
		getConfig().options().copyDefaults();
		if (!(getConfig().isConfigurationSection("uuids"))) {
			getLogger().info("INFO: Configuration section for player UUIDs does not exist! Creating!");
			getConfig().createSection("uuids");
			if (getConfig().isConfigurationSection("uuids")) {
				getLogger().info("INFO: Configuration section for player UUIDs has succesfully been created!");
			}
			else {
				getLogger().info("ERROR: Configuration section failed to create! Contact developer ASAP!");
			}
		}
		saveConfig();
	}
	
	@Override
	public void onDisable() {
		saveConfig();
	}
	
	//method for clearing single warning from player warnings file
	public void clearSingleWarning(CommandSender sender, Player player, int index, boolean isSilent) throws Exception {
		try {
			String filename = player.getUniqueId().toString() + ".yml";
			File file = new File(getDataFolder(), filename);
			if (!(file.exists())) {
				file.createNewFile();
			}
			if (file.exists()) {BufferedReader br=new BufferedReader(new FileReader(file));
				StringBuffer sb=new StringBuffer("");
				StringBuffer sbc=new StringBuffer("");
				String warning = null;
				Integer linenumber = 1;
				String line;
				Integer numlines = 1;
				while((line=br.readLine())!=null) {
					if(linenumber!=index)
						sb.append(line+"\n");
					else {
						warning = line;
					}
					linenumber++;
					sbc.append(line+"\n");
				}
				if(index+numlines<=linenumber) {
						br.close();
						FileWriter fw = new FileWriter(file);
						file.delete();
						file.createNewFile();
						fw.write("");
						fw.flush();
						fw.write(sb.toString());
						fw.close();	
						String[] warningSplit = warning.split(": ");
						sender.sendMessage(msgPrefix + ChatColor.YELLOW + "The warning " + ChatColor.GOLD + warningSplit[1].toString().replace(" ", "") + ChatColor.YELLOW + " has been removed from " + ChatColor.GOLD + player.getName() + ChatColor.YELLOW + ".");
						if (isSilent==false) {
							player.sendMessage(msgPrefix + ChatColor.YELLOW + "The warning " + ChatColor.GOLD + warningSplit[1].toString().replace(" ", "") + ChatColor.YELLOW + " has been removed from your record by " + ChatColor.GOLD + sender.getName() + ".");
							sender.sendMessage(msgPrefix + ChatColor.GOLD + player.getName() + ChatColor.YELLOW + " has been sent a notification regarding the removal of their warning.");
						}
						else if (isSilent==true) {
							sender.sendMessage(msgPrefix + ChatColor.YELLOW + "The " + ChatColor.GOLD + "-s" + ChatColor.YELLOW + " paramater was detected, not sending a notification to " + ChatColor.GOLD + player.getName() + ChatColor.YELLOW + ".");
						}
				}
				else {
					sender.sendMessage(msgPrefix + ChatColor.RED + "The player " + ChatColor.DARK_RED + player.getName() + ChatColor.RED + " does not have that many warnings on record.");
				}
				
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//method to log player uuid to config file
	public void logUUID(Player player) {
		String path = "uuids." + player.getName();
		if (!(this.getConfig().contains(path))) {
			getLogger().info("INFO: " + player.getName() + " is joining for first time! Logging UUID to config file!");
			getConfig().set("uuids." + player.getName(), player.getUniqueId().toString());
			saveConfig();
		}
	}
	
	//method to log warning to file
	public void logToFile (String message, String playerToBeWarned) {
        try
        {
            File dataFolder = getDataFolder();
            if(!dataFolder.exists())
            {
                dataFolder.mkdir();
            }
            Player player = Bukkit.getPlayer(playerToBeWarned);
            UUID puuid = player.getUniqueId();
            File saveTo = new File(getDataFolder(), puuid + ".yml");
            if (!saveTo.exists())
            {
                saveTo.createNewFile();
            }
            FileWriter fw = new FileWriter(saveTo, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(message);
            pw.flush();
            pw.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
	
	//offline method to log warning to file
	public void offlineLogToFile (String message, String puuid) {
        try
        {
            File dataFolder = getDataFolder();
            if(!dataFolder.exists())
            {
                dataFolder.mkdir();
            }
            File saveTo = new File(getDataFolder(), puuid + ".yml");
            if (!saveTo.exists())
            {
                saveTo.createNewFile();
            }
            FileWriter fw = new FileWriter(saveTo, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(message);
            pw.flush();
            pw.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
	
	//method to count lines (warnings) in a players .yml file
	public int countLines(String playerName) throws IOException {
		Player player = Bukkit.getPlayer(playerName);
		UUID puuid = player.getUniqueId();
		File file = new File(getDataFolder(), puuid + ".yml"); 
		if (!(file.exists())) {
			file.createNewFile();
		}
		BufferedReader reader = new BufferedReader(new FileReader(file));
		int lines = 0;
		try {
			while (reader.readLine() != null) lines++;
		} catch (IOException e) {
			e.printStackTrace();
		}
		reader.close();
		return lines;
	}
	
	//offline method to count lines (warnings) in a players .yml file
	public int offlineCountLines(String playerUUID) throws IOException {
		File file = new File(getDataFolder(), playerUUID + ".yml"); 
		BufferedReader reader = new BufferedReader(new FileReader(file));
		int lines = 0;
		try {
			while (reader.readLine() != null) lines++;
		} catch (IOException e) {
			e.printStackTrace();
		}
		reader.close();
		return lines;
	}
	
	//method to read players warnings file
	public List<String> getTextFromFile(String playerFileName, CommandSender warner) {
		Player player = Bukkit.getPlayer(playerFileName);
		UUID puuid = player.getUniqueId();
		File file = new File(getDataFolder(), puuid + ".yml");
        if (!file.exists()) {
            warner.sendMessage(msgPrefix + ChatColor.DARK_RED + "That player does not have any warnings on record.");
            return null;
        }
        List<String> text = new ArrayList<String>();
        try (Scanner scanner = new Scanner(file)) {
        	int ln=1;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line == null) {
                    continue;
                }
                text.add(msgPrefix + ChatColor.GOLD + "" + ln + ") " + ChatColor.YELLOW + line);
                ln++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return text;
    }
	
	//offline reading method
	public List<String> offlineGetTextFromFile(String playerUUID, CommandSender warner) {
		File file = new File(getDataFolder(), playerUUID + ".yml");
        if (!file.exists()) {
            warner.sendMessage(msgPrefix + ChatColor.DARK_RED + "That player does not have any warnings on record.");
            return null;
        }
        List<String> text = new ArrayList<String>();
        try (Scanner scanner = new Scanner(file)) {
        	int ln=1;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line == null) {
                    continue;
                }
                text.add(msgPrefix + ChatColor.GOLD + "" + ln + ") " + ChatColor.YELLOW + line);
                ln++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return text;
    }
	
	//method to gain time for logging warning
	public String getTime() {
	    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    return strDate;
	}

}
