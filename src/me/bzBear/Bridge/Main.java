package me.bzBear.Bridge;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.games647.scoreboardstats.ScoreboardStats;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;

import me.bzBear.Bridge.GameStateEnum.GameState;


public class Main extends JavaPlugin{

	GameManager gm;
	Location hub;
	Boolean useHolographicDisplays;
	Hologram hologram;


	@Override
	public void onEnable(){
		
		FileManager.loadConfig(this);
		
		useHolographicDisplays = Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays");

		if (useHolographicDisplays == false) {
			getLogger().severe("*** HolographicDisplays is not installed or not enabled. ***");
			getLogger().severe("*** This plugin will not work correctly. ***");
		}

		gm = new GameManager(this);
		hub =  new Location(Bukkit.getWorld("world"), 0, 100, 0);
	}

	@Override 
	public void onDisable() {
		if(hologram != null) {
			hologram.delete();
		}
	}
	
	

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(label.equalsIgnoreCase("tb")) {

			Player p = (Player) sender;

			switch(args[0]) {

			case "play":
				gm.QueuePlayer(p);
				p.sendMessage(ChatColor.AQUA + "Joined Game");
				break;

			case "skip":
				gm.startGame(p);
				break;

			case "leave":	
				if(gm.gamers.containsKey(p)) {
					
				}
				else {
					p.sendMessage(ChatColor.RED + "Error: Unable to find you in a game");
					break;
				}
				Game gamus = gm.gamers.get(p);
				
				
				if(gamus.state == GameState.RUNNING) {
					if(gamus.getBP(p) != null) {
						gm.playerLeave(p);
					}
					else {
						p.sendMessage(ChatColor.RED + "Error: Unable to find you in a game");
					}
				}
				else {
					p.sendMessage(ChatColor.RED + "You must be in a running game and not in a cage to run this command");
				}
				p.sendMessage(ChatColor.AQUA + "Game Left");
				break;

			case "reset":
				gm.forceReset(p);
				p.sendMessage(ChatColor.AQUA + "Reset Complete");
				break;

			case "createLb":
				if(useHolographicDisplays) {
					hologram = HologramsAPI.createHologram(this, p.getLocation());
					hologram.appendTextLine(ChatColor.AQUA + "All-Time Winstreak Leaderboards");
					hologram.appendTextLine(ChatColor.GRAY + "-------------------------------");
					TextLine textLine = hologram.appendTextLine(ChatColor.translateAlternateColorCodes('&', "&2zeBeeZee &1 - &4 999999"));
				}
				else {
					p.sendMessage("Holographic Displays plugin needs to be installed and enabled.");
				}
				break;
				
			case "destroyLb":
				if(hologram == null) {
					p.sendMessage(ChatColor.RED + "No hologram set.");
				}
				hologram.delete();
				break;
				
			case "score":
				if(gm.gamers.containsKey(p)) {
					Game gaem = gm.gamers.get(p);
					if(gaem.state == GameState.RUNNING) {
						if(gaem.getBP(p) != null) {
							gaem.score(gaem.getBP(p));
						}
						else {
							p.sendMessage(ChatColor.RED + "Error: Unable to find you in a game");
						}
					}
					else {
						p.sendMessage(ChatColor.RED + "You must be in a running game and not in a cage to run this command");
					}
					
				}
				else {
					p.sendMessage(ChatColor.RED + "You are not in a game");
				}
				break;

			default:
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2--------------------------\n--------------------------\nThe Bridge by bz\n&5/tb play: &6Join a game\n&5/tb skip: &6Start game and skip timer\n&5/tb help: &6Show this Message\n--------------------------\n--------------------------"));
			}

			return true;
		}

		return false;
	}

}

