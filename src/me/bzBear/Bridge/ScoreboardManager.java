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

public class ScoreboardManager implements Listener {
	static Plugin plugin;
	org.bukkit.scoreboard.ScoreboardManager manager = Bukkit.getScoreboardManager();
	Scoreboard board;
	Scoreboard empty;
	Objective obj;
	Objective waiting;
	List<Player> players = new ArrayList<Player>();

	ScoreboardManager(Plugin plug, List<Player> in) {
		plugin = plug;
		players = in;

		manager = Bukkit.getScoreboardManager();
		board = manager.getNewScoreboard();

		empty = manager.getNewScoreboard();

	}

	public void set() {


		obj = board.registerNewObjective("test", "dummy");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "DUELS");

		Score a = obj.getScore("");
		Score b = obj.getScore(ChatColor.GRAY + "24/1/3" + ChatColor.DARK_GRAY + " m89o");
		Score c = obj.getScore(" ");
		Score d = obj.getScore(ChatColor.WHITE + "Time Left: " + ChatColor.GREEN + "13:30");
		Score e = obj.getScore("  ");
		Score f = obj.getScore(ChatColor.translateAlternateColorCodes('&', "&9[B] &9⬤⬤&f⬤⬤⬤"));
		Score g = obj.getScore(ChatColor.translateAlternateColorCodes('&', "&4[R] &4⬤&f⬤⬤⬤⬤"));
		Score h = obj.getScore("   ");
		Score i = obj.getScore(ChatColor.WHITE + "Kills: " + ChatColor.GREEN + "0");
		Score j = obj.getScore(ChatColor.WHITE + "Goals: " + ChatColor.GREEN + "0");
		Score k = obj.getScore("    ");
		Score l = obj.getScore(ChatColor.WHITE + "Mode: " + ChatColor.GREEN + "The Bridge Duels");
		Score m = obj.getScore(ChatColor.WHITE + "Daily Streak: " + ChatColor.GREEN + "0");
		Score n = obj.getScore(ChatColor.WHITE + "Best Daily Streak: " + ChatColor.GREEN + "0");
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



		for (Player player : players) {
			player.setScoreboard(board);
		}

	}

	public void remove(Player e) {
		e.setScoreboard(empty);

	}

}
