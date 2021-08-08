package me.bzBear.Bridge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.bzBear.Bridge.GameStateEnum.GameState;
import net.md_5.bungee.api.ChatColor;

public class GameManager {
	MapManager mm = new MapManager();
	List<Game> games = new ArrayList<Game>();
	Plugin plugin;
	HashMap<Player, Game> gamers = new HashMap<Player, Game>();
	Location hub = new Location(Bukkit.getWorld("world"), 0, 100, 0);

	public GameManager(Plugin a){
		plugin = a;
		games.add(new Game(mm.Urban, 2, a, this)); 
	}


	public Game findOpenLobby() {
		for(Game g: games) {
			if(g.state == GameState.WAITING || g.state == GameState.EMPTY) return g;
		}
		Bukkit.broadcastMessage(games.get(0).state.toString());
		return null;
	}

	public void QueuePlayer(Player p) {
		
		Game g = findOpenLobby();
		if(g == null) {
			p.sendMessage(ChatColor.RED + "No open lobbies. Please try again in a bit");
			return;
		}
		if(gamers.containsKey(p)) {
			p.sendMessage(ChatColor.YELLOW+("Leaving current game and joining another..."));
			playerLeave(p);
		}
		if(g.p.size() == 0) {
			Bukkit.broadcastMessage(ChatColor.YELLOW + "Making Game...");
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
		if(gamers.containsKey(p)) {
			Game g = gamers.get(p);
			g.unregisterPlayer(g.getBP(p));
			gamers.remove(p);
			if(g.p.size() == 0) {
				g.end();
			}
		}
		else {
			p.sendMessage(ChatColor.RED + "You are not in a game.");
		}
	}

	public void forceReset(Player p) {
		if (gamers.containsKey(p)) {
			Game g = gamers.get(p);
			g.reset();
		}
		else {
			p.sendMessage(ChatColor.RED + "You are not in a game.");
		}
	}
}
