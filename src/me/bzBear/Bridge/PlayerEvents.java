package me.bzBear.Bridge;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class PlayerEvents implements Listener {
	Plugin plugin;

	public PlayerEvents(Plugin p) {
		plugin = p;
	}

	@EventHandler
	public void playerShoot(ProjectileLaunchEvent event) {
		Bukkit.broadcastMessage("wow");
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {
				Bukkit.broadcastMessage(event.getEntity().getType().toString());
				if (event.getEntity().getType() == EntityType.PLAYER) {
					Bukkit.broadcastMessage("a");
					Player p = (Player) event.getEntity();
					Bukkit.broadcastMessage("b");
					p.getInventory().setItem(8, new ItemStack(Material.ARROW));
					p.sendMessage(ChatColor.AQUA + "You have received an arrow!");
				}
			}
		}, 20);
	}

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if (event.getSlotType() == InventoryType.SlotType.ARMOR) {
			event.setCancelled(true);
		}

	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {

		Entity damager = event.getDamager();

		if (damager instanceof Arrow) {

			Arrow arrow = (Arrow) damager;
			if (arrow.getShooter() instanceof Player) {
				Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
					@Override
					public void run() {
						Player p = (Player) arrow.getShooter();
						p.getInventory().setItem(8, new ItemStack(Material.ARROW));
						p.sendMessage(ChatColor.AQUA + "You have received an arrow!");
					}
				}, 20);
			}

			Entity entityHit = event.getEntity();
			if (entityHit instanceof Player) {
				Player playerHit = (Player) entityHit;
				// playerHit here is the player who got hit
			}

		}

	}
}
