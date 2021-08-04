package me.bzBear.Bridge;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class Main extends JavaPlugin{
	
	Game g = new Game("Urban", 2);
	Location mapSpawn = new Location(Bukkit.getWorld("Urban"), 30, 96, 0);
	Location hub = new Location(Bukkit.getWorld("world"), 0, 100, 0);
	
	
	@Override
	public void onEnable(){
		Bukkit.getPluginManager().registerEvents(new PlayerEvents(this), this);
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
				  Bukkit.getPluginManager().registerEvents(g.protector, this);
				  g.protector.enable();
				  p.teleport(mapSpawn);
				  g.registerPlayer(p);
				  break;
				  
			  case "skip":
				  p.teleport(mapSpawn);
				  g.start();
				  break;
				  
			  case "leave":
				  g.protector.disable();
				  p.teleport(hub);
				  for (PotionEffect effect : p.getActivePotionEffects())
				        p.removePotionEffect(effect.getType());
				  g.unregisterPlayer(p);
				  break;
				  
			  case "reset":
				  g.protector.rebuild();
				  g.reset();
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

