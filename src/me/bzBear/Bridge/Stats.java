package me.bzBear.Bridge;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class Stats {
	int currentWs = 0;
	int bestWs = 0;
	int dailyWs = 0;
	int wins = 0;
	int losses = 0;
	int matchesPlayed = 0;
	int totalKills = 0;
	FileConfiguration config;
	BridgePlayer bp;
	Plugin p;

	public Stats(Plugin inPlugin, BridgePlayer input) {
		p = inPlugin;
		config = p.getConfig();
		bp = input;
		
		p.getConfig().options().copyDefaults(true);
		config.addDefault(bp.getPlayer().getUniqueId() + ".wins", 1);
		config.addDefault(bp.getPlayer().getUniqueId() + ".currentWs", 10);
		config.addDefault(bp.getPlayer().getUniqueId() + ".bestWs", 3123);
		config.addDefault(bp.getPlayer().getUniqueId() + ".dailyWs", 323);
		config.addDefault(bp.getPlayer().getUniqueId() + ".losses", 423);
		config.addDefault(bp.getPlayer().getUniqueId() + ".matchesPlayed", 301239219);
		config.addDefault(bp.getPlayer().getUniqueId() + ".totalKills", 31233);
		config.options().copyDefaults(true);
		p.saveConfig();
	}
	
	public int wins() {
		return config.getInt(bp.getPlayer().getUniqueId() + ".wins");
	}
	
	public int currentWs() {
		return config.getInt(bp.getPlayer().getUniqueId() + ".currentWs");
	}
	
	public int bestWs() {
		return config.getInt(bp.getPlayer().getUniqueId() + ".bestWs");
	}
	
	public int dailyWs() {
		return config.getInt(bp.getPlayer().getUniqueId() + ".dailyWs");
	}
	
	public int losses() {
		return config.getInt(bp.getPlayer().getUniqueId() + ".losses");
	}
	
	public int matchesPlayed() {
		return config.getInt(bp.getPlayer().getUniqueId() + ".matchesPlayed");
	}
	
	public int totalKills() {
		return config.getInt(bp.getPlayer().getUniqueId() + ".totalKills");
	}

}
