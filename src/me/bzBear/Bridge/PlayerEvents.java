package me.bzBear.Bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class PlayerEvents implements Listener {
	Plugin plugin;
	Boolean enabled = false;
	Game g;

	public PlayerEvents(Plugin p, Game game) {
		plugin = p;
		g = game;
	}
	

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {

		if(enabled) {
			if(e.getPlayer().getLocation().getY() < g.map.bridgeLevel - 10) {
				
				Player fell = (Player) e.getPlayer();
				if(Game.isBlue(fell)) {
					if(xyz.between2D(g.map.redGoalNW, g.map.redGoalSE, e.getPlayer().getLocation().getX(),e.getPlayer().getLocation().getZ())) {
						g.score(fell);
						return;
					}
				}
				else {
					
					if(xyz.between2D(g.map.blueGoalNW, g.map.blueGoalSE, e.getPlayer().getLocation().getX(),e.getPlayer().getLocation().getZ())) {
						g.score(fell);
						return;
					}
					
				}
				
				ChatColor c = ChatColor.BLUE;
				if(Game.isBlue(fell)) {
					c = ChatColor.BLUE;
					fell.teleport(g.map.blueSpawn);
				}
				else {
					c = ChatColor.RED;
					fell.teleport(g.map.blueSpawn);
				}
				
				
				for(Player p: g.p) {
					p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);
					if(Game.isBlue(fell)) {
						p.sendMessage(c + fell.getDisplayName() + ChatColor.WHITE + " died");
					}
					else {
						p.sendMessage(c + fell.getDisplayName() + ChatColor.WHITE + " died");
					}
				}
				
			}
		}

	}



	@EventHandler
	public void playerShoot(ProjectileLaunchEvent event) {
		if(enabled) {
			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				@Override
				public void run() {

					Arrow a = (Arrow) event.getEntity();
					Player p = (Player) a.getShooter();
					p.getInventory().setItem(8, new ItemStack(Material.AIR));
					p.setLevel(3);

				}
			}, 1);
			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				@Override
				public void run() {

					Arrow a = (Arrow) event.getEntity();
					Player p = (Player) a.getShooter();
					p.setLevel(2);

				}
			}, 20);
			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				@Override
				public void run() {

					Arrow a = (Arrow) event.getEntity();
					Player p = (Player) a.getShooter();
					p.setLevel(1);

				}
			}, 40);
			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				@Override
				public void run() {

					Arrow a = (Arrow) event.getEntity();
					Player p = (Player) a.getShooter();
					p.setLevel(0);
					ItemStack arrow = new ItemStack(Material.ARROW);
					if(p.getInventory().getItem(8) != arrow) {
						p.getInventory().setItem(8, new ItemStack(Material.ARROW));
					}
				}
			}, 60);


			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				@Override
				public void run() {
					Arrow a = (Arrow) event.getEntity();
					a.remove();
				}
			}, 60);


		}

	}

	@EventHandler
	public void onPlayerClick(PlayerInteractEvent event){
		if(enabled) {
			Player player = event.getPlayer();
			if ((event.getAction() == Action.RIGHT_CLICK_BLOCK) || (event.getAction() == Action.RIGHT_CLICK_AIR)){
				if (player.getItemInHand().getType() == Material.GOLDEN_APPLE) {
					player.removePotionEffect(PotionEffectType.REGENERATION);
					player.setHealth(20);			
				}
			}}
	}

	public void enable() {
		enabled = true;
	}

	public void disable() {
		enabled = false;
	}

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(enabled) {
			if (event.getSlotType() == InventoryType.SlotType.ARMOR) {
				event.setCancelled(true);
			}}

	}

}
