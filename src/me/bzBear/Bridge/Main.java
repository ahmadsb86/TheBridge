package me.bzBear.Bridge;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;


public class Main extends JavaPlugin{

	GameManager gm;
	Location hub;
	Boolean useHolographicDisplays;


	@Override
	public void onEnable(){

		useHolographicDisplays = Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays");

		if (useHolographicDisplays == false) {
			getLogger().severe("*** HolographicDisplays is not installed or not enabled. ***");
			getLogger().severe("*** This plugin will not work correctly. ***");

			Bukkit.broadcastMessage(ChatColor.RED +"HolographicDisplays is not installed or not enabled. Leaderboards will not work");
		}


		gm = new GameManager(this);
		hub =  new Location(Bukkit.getWorld("world"), 0, 100, 0);
	}

	@Override 
	public void onDisable() {
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
				gm.playerLeave(p);
				p.sendMessage(ChatColor.AQUA + "Game Left");
				break;

			case "reset":
				gm.forceReset(p);
				p.sendMessage(ChatColor.AQUA + "Reset Complete");
				break;

			case "leaderboards":
				if(useHolographicDisplays) {
					Hologram hologram = HologramsAPI.createHologram(this, p.getLocation());
					TextLine textLine = hologram.appendTextLine(ChatColor.translateAlternateColorCodes('&', "&2zeBeeZee &1 - &4 999999"));
				}
				else {
					p.sendMessage("Holographic Displays plugin needs to be installed and enabled.");
				}
				
			case "score":
				if(gm.gamers.containsKey(p)) {
					gm.gamers.get(p).score(p);
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

