package me.bzBear.Bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

public class PlayerEvents implements Listener {
	Plugin plugin;

	public PlayerEvents(Plugin p) {
		plugin = p;
	}

	@EventHandler
	public void playerShoot(ProjectileLaunchEvent event) {
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
	
	@EventHandler
	public void onPlayerClick(PlayerInteractEvent event){
		Player player = event.getPlayer();
		if ((event.getAction() == Action.RIGHT_CLICK_BLOCK) || (event.getAction() == Action.RIGHT_CLICK_AIR)){
			if (player.getItemInHand().getType() == Material.GOLDEN_APPLE) {
				player.removePotionEffect(PotionEffectType.REGENERATION);
				player.setHealth(20);			
			}
		}
	}
	
	


	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if (event.getSlotType() == InventoryType.SlotType.ARMOR) {
			event.setCancelled(true);
		}

	}

	// @EventHandler
	// public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
	//
	// Entity damager = event.getDamager();
	//
	// if (damager instanceof Arrow) {
	//
	// Arrow arrow = (Arrow) damager;
	// if (arrow.getShooter() instanceof Player) {
	// Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
	// @Override
	// public void run() {
	// Player p = (Player) arrow.getShooter();
	// p.getInventory().setItem(8, new ItemStack(Material.ARROW));
	// p.sendMessage(ChatColor.AQUA + "You have received an arrow!");
	// }
	// }, 20);
	// }
	//
	// Entity entityHit = event.getEntity();
	// if (entityHit instanceof Player) {
	// Player playerHit = (Player) entityHit;
	// // playerHit here is the player who got hit
	// }
	//
	// }
	//
	// }
}
