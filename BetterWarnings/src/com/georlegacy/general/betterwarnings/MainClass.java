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
import java.util.ArrayList;
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
		
		//Registering all commands to classes
		getCommand("warn").setExecutor(new WarningCommand(this));
		getCommand("warnings").setExecutor(new CountWarningCommand(this));
		getCommand("listwarnings").setExecutor(new ListWarningCommand(this));
		getCommand("clearwarnings").setExecutor(new ClearWarningsCommand(this));
		getCommand("clearwarning").setExecutor(new ClearSingleWarningCommand(this));
		getCommand("betterwarnings").setExecutor(new HelpCommand(new VarClass(this)));
	}
	
	//Plugin Metrics data collection and logging (called in onEnable)
	public void metricsLog() {
	    try {
	        Metrics metrics = new Metrics(this);
	        metrics.start();
	        getLogger().info(ChatColor.LIGHT_PURPLE + "Succesfully logged data to plugin metrics.");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	//Method to log player's UUID on their first join, called in JoinListener()
		public void logUUID(Player player) {
			String path = "uuids." + player.getName();
			if (!(this.getConfig().contains(path))) {
				getLogger().info("INFO: " + player.getName() + " is joining for first time! Logging UUID to config file!");
				getConfig().set("uuids." + player.getName(), player.getUniqueId().toString());
				saveConfig();
			}
		}
		
	
	//Registering config, mainly for first time to create section for UUID storage
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
	
	//Method called in ClearSingleWarningCommand() for online player
	//Clears a single warning using an index number, shown in getTextFromFile() method
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
						//Checking for isSilent boolean parameter
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
	
	//Method for logging a new warning to a player's YML file, named after their UUID
	public void logToFile (String message, String playerToBeWarned) {
        try
        {
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
	
	//Offline version of method above (logging player's new warning to file)
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
	
	//Method to count lines (warnings) in a players .yml file
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
	
	//Offline method to count lines (warnings) in a players .yml file
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
	
	//Method to list a player's warnings on record (in .yml file)
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
	
	//Offline method to list a player's warnings on record (in .yml file)
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
}
