package me.bzBear.Bridge;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class BridgePlayer {
	Player bp;
	Color c;
	Stats stats;
	Game g;
	BridgePlayer lastDamager;
	int goals;
	int kills;

	public BridgePlayer(Player inPlayer, Color inColor, Game ing, Plugin plugin) {
		bp = inPlayer;
		c = inColor;
		g = ing;
		stats = new Stats(plugin, this);
		lastDamager = this;
	}

	public Player getPlayer() {
		return bp;
	}

	public Boolean isBlue() {
		if (c == Color.BLUE) {
			return true;
		} else {
			return false;
		}
	}

	public Stats getStats() {
		return stats;
	}

	public static BridgePlayer getBP(List<BridgePlayer> test, Player subject) {
		for (BridgePlayer bridgeP : test) {
			if (bridgeP.bp.getUniqueId() == subject.getUniqueId()) {
				return bridgeP;
			}
		}
		Bukkit.getLogger().severe("No such player found BridgePlayer.java 56");
		Bukkit.broadcastMessage("no");
		return null;
	}

}
