package me.bzBear.Bridge;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import net.md_5.bungee.api.ChatColor;

public class GameManager {
	MapManager mm = new MapManager();
	Game Urban;
	Plugin plugin;
	HashMap<Player, Game> gamers = new HashMap<Player, Game>();
	Location hub = new Location(Bukkit.getWorld("world"), 0, 100, 0);
	
	public GameManager(Plugin a){
		plugin = a;
		Urban = new Game(mm.Urban, 2, a, this);
	}
	
	
	public Game findOpenLobby() {
		return Urban;
	}

	public void QueuePlayer(Player p) {
		Game g = findOpenLobby();
		if(gamers.containsKey(p)) {
			p.sendMessage(ChatColor.YELLOW+("Leaving current game and joining another..."));
			playerLeave(p);
		}
		if(g.p.size() == 0) {
			g.make();
		}
		g.registerPlayer(p);
		gamers.put(p, g);
	}
	
	public void startGame(Player p) {
		Game g = gamers.get(p);
		g.start();
	}
	
	public void playerLeave(Player p) {
		Game g = gamers.get(p);
		g.unregisterPlayer(p);
		gamers.remove(p);
		if(g.p.size() == 0) {
			g.end();
		}
	}
	
	public void forceReset(Player p) {
		Game g = gamers.get(p);
		g.reset();
	}
}
