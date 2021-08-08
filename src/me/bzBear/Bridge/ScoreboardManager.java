package me.bzBear.Bridge;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import org.bukkit.event.Listener;

import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.*;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    


public class ScoreboardManager implements Listener {
	static Plugin plugin;
	org.bukkit.scoreboard.ScoreboardManager manager = Bukkit.getScoreboardManager();
	Scoreboard board;
	Scoreboard empty;
	Objective obj;
	Objective waiting;
	List<BridgePlayer> players = new ArrayList<BridgePlayer>();

	ScoreboardManager(Plugin plug, List<BridgePlayer> in) {
		plugin = plug;
		players = in;

		manager = Bukkit.getScoreboardManager();
		board = manager.getNewScoreboard();

		empty = manager.getNewScoreboard();

	}

	public void set(BridgePlayer player) {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy/MM/dd");  
		LocalDateTime now = LocalDateTime.now();
		
		if(board.getObjective("test") != null) {
			board.getObjective("test").unregister();
		}
		obj = board.registerNewObjective("test", "dummy");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "DUELS");

		Score a = obj.getScore("");
		Score b = obj.getScore(ChatColor.GRAY + "" + dtf.format(now) + ChatColor.DARK_GRAY + " m89o");
		Score c = obj.getScore(" ");
		Score d = obj.getScore(ChatColor.WHITE + "Time Left: " + ChatColor.GREEN + "13:30");
		Score e = obj.getScore("  ");
		Score f = obj.getScore(ChatColor.translateAlternateColorCodes('&', "&9[B] " + scoreCircles(ChatColor.BLUE, player.g.blueScore)));
		Score g = obj.getScore(ChatColor.translateAlternateColorCodes('&', "&4[R] " + scoreCircles(ChatColor.RED, player.g.redScore)));
		Score h = obj.getScore("   ");
		Score i = obj.getScore(ChatColor.WHITE + "Kills: " + ChatColor.GREEN + player.kills);
		Score j = obj.getScore(ChatColor.WHITE + "Goals: " + ChatColor.GREEN + player.goals);
		Score k = obj.getScore("    ");
		Score l = obj.getScore(ChatColor.WHITE + "Mode: " + ChatColor.GREEN + "The Bridge Duels");
		Score m = obj.getScore(ChatColor.WHITE + "Daily Streak: " + ChatColor.GREEN + player.stats.currentWs());
		Score n = obj.getScore(ChatColor.WHITE + "Best Daily Streak: " + ChatColor.GREEN + player.stats.dailyWs());
		Score o = obj.getScore("     ");
		Score p = obj.getScore(ChatColor.YELLOW + "www.sweatsunited.net");

		a.setScore(16);
		b.setScore(15);
		c.setScore(14);
		d.setScore(13);
		e.setScore(12);
		f.setScore(11);
		g.setScore(10);
		h.setScore(9);
		i.setScore(8);
		j.setScore(7);
		k.setScore(6);
		l.setScore(5);
		m.setScore(4);
		n.setScore(3);
		o.setScore(2);
		p.setScore(1);



		player.bp.setScoreboard(board);

	}

	public void remove(BridgePlayer e) {
		e.bp.setScoreboard(empty);

	}
	
	public String scoreCircles(ChatColor c, int i) {
		if(i == 0) {
			return "&f⬤⬤⬤⬤⬤";
		}
		if(i == 1) {
			return c + "⬤&f⬤⬤⬤⬤";
		}
		if(i == 2) {
			return c + "⬤⬤&f⬤⬤⬤";
		}
		if(i == 3) {
			return c + "⬤⬤⬤&f⬤⬤";
		}
		if(i == 4) {
			return c + "⬤⬤⬤⬤&f⬤";
		}
		if(i > 4) {
			return c +"⬤⬤⬤⬤⬤";
		}
		
		return c +"⬤⬤⬤⬤⬤";
	}

}
