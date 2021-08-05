package me.bzBear.Bridge;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin{
	
	GameManager gm;
	Location hub;
	
	
	@Override
	public void onEnable(){
		Bukkit.getPluginManager().registerEvents(new PlayerEvents(this), this);
		gm = new GameManager(this);
		hub =  new Location(Bukkit.getWorld("world"), 0, 100, 0);
    }
	
	@Override 
	public void onDisable() {
		//a
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
			
			
			  default:
				  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2--------------------------\n--------------------------\nThe Bridge by bz\n&5/tb play: &6Join a game\n&5/tb skip: &6Start game and skip timer\n&5/tb help: &6Show this Message\n--------------------------\n--------------------------"));
			}
			
			return true;
		}
		
		return false;
	}
	
}

