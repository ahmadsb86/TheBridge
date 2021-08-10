package me.bzBear.Bridge;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import org.bukkit.ChatColor;

public class PlayerEvents implements Listener {
	Plugin plugin;
	Boolean enabled = false;
	Game g;
	List<BridgePlayer> bp = new ArrayList<BridgePlayer>();

	public PlayerEvents(Plugin p, Game game, List<BridgePlayer> inbp) {
		plugin = p;
		g = game;
		bp = inbp;
	}

	public void set(List<BridgePlayer> e) {
		bp = e;
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {

		BridgePlayer e = new BridgePlayer(event.getPlayer(), Color.FUCHSIA, null, plugin);

		for (BridgePlayer bridgeP : bp) {
			if (bridgeP.bp == event.getPlayer()) {
				e = bridgeP;
			}
		}

		if (e.c == Color.FUCHSIA) {
			Bukkit.getLogger().severe("No such player found PlayerEvents.Java 57");
			return;
		}

		e.g.gm.playerLeave(e.bp);

	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		if (enabled) {
			if (BridgePlayer.getBP(bp, event.getPlayer()) != null) {
				BridgePlayer e = BridgePlayer.getBP(bp, event.getPlayer());
				if (e.bp.getLocation().getY() < g.map.bridgeLevel - 10) {
					
					e.lastDamager = e;
					
					if (e.isBlue()) {
						if (xyz.between2D(g.map.redGoalNW, g.map.redGoalSE, e.getPlayer().getLocation().getX(),
								e.getPlayer().getLocation().getZ())) {
							g.score(e);
							return;
						}
					} else {

						if (xyz.between2D(g.map.blueGoalNW, g.map.blueGoalSE, e.getPlayer().getLocation().getX(),
								e.getPlayer().getLocation().getZ())) {
							g.score(e);

							return;
						}

					}

					for (PotionEffect effect : e.bp.getActivePotionEffects())
						e.bp.removePotionEffect(effect.getType());

					e.bp.getInventory().clear();

					ChatColor c = ChatColor.BLUE;
					if (e.isBlue()) {
						c = ChatColor.BLUE;
						e.bp.teleport(g.map.blueSpawn);

					} else {
						c = ChatColor.RED;
						e.bp.teleport(g.map.redSpawn);
					}

					e.bp.setHealth(20);
					g.giveItems(e.bp, 11);
					

					for (BridgePlayer p : g.p) {
						p.bp.playSound(p.bp.getLocation(), Sound.NOTE_PLING, 1, 1);
						p.bp.sendMessage(c + e.bp.getDisplayName() + ChatColor.WHITE + " was killed by "
								+ e.lastDamager.bp.getDisplayName());
					}

				}
			}
		}

	}

	@EventHandler
	public void playerShoot(ProjectileLaunchEvent event) {
		if (enabled) {
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
					if (p.getInventory().getItem(8) != arrow) {
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
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (enabled) {
			Entity damager = event.getDamager();
			if (damager instanceof Arrow) { // check if the damager is an arrow
				Arrow arrow = (Arrow) damager;
				if (arrow.getShooter() instanceof Player) {
					BridgePlayer shooter = BridgePlayer.getBP(bp, (Player) arrow.getShooter());
					Entity entityHit = event.getEntity();
					if (entityHit instanceof Player) {
						Player playerHit = (Player) entityHit;
						BridgePlayer victim = BridgePlayer.getBP(bp, playerHit);
						if (shooter.isBlue() != victim.isBlue()) {
							event.setDamage(3);
						} else {
							event.setCancelled(true);
						}
						arrow.remove();
					}
				}
			}
			if (damager instanceof Player) {
				Player playerHit = (Player) event.getEntity();
				ItemStack item = ((Player) damager).getItemInHand();
				BridgePlayer e = BridgePlayer.getBP(bp, playerHit);
				if(item.getType() == Material.IRON_SWORD) {
					if(playerHit.getHealth() <= event.getDamage()) {
						event.setCancelled(true);
						
						for (PotionEffect effect : e.bp.getActivePotionEffects())
							e.bp.removePotionEffect(effect.getType());
						e.bp.getInventory().clear();
						ChatColor c = ChatColor.BLUE;
						ChatColor opp = ChatColor.BLUE;
						if (e.isBlue()) {
							c = ChatColor.BLUE;
							e.bp.teleport(g.map.blueSpawn);
							opp = ChatColor.RED;
						} else {
							c = ChatColor.RED;
							e.bp.teleport(g.map.redSpawn);
							opp = ChatColor.BLUE;
						}
						e.bp.setHealth(20);
						g.giveItems(e.bp, 11);
						for (BridgePlayer p : g.p) {
							p.bp.playSound(p.bp.getLocation(), Sound.NOTE_PLING, 1, 1);
							if(e.lastDamager == e) {
								p.bp.sendMessage(c + e.bp.getDisplayName() + ChatColor.WHITE + " died");
										
							}
							p.bp.sendMessage(c + e.bp.getDisplayName() + ChatColor.WHITE + " was killed by "
									+ opp + e.lastDamager.bp.getDisplayName());
						}
						
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerClick(PlayerInteractEvent event) {
		if (enabled) {
			Player player = event.getPlayer();
			if ((event.getAction() == Action.RIGHT_CLICK_BLOCK) || (event.getAction() == Action.RIGHT_CLICK_AIR)) {
				Bukkit.broadcastMessage("rmb");
				//TODO remove
				if (player.getItemInHand().getType() == Material.GOLDEN_APPLE) {
					player.removePotionEffect(PotionEffectType.REGENERATION);
					player.setHealth(20);
				}
			}
		}
	}

	public void enable() {
		enabled = true;
	}

	public void disable() {
		enabled = false;
	}

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if (enabled) {
			if (event.getSlotType() == InventoryType.SlotType.ARMOR) {
				event.setCancelled(true);
			}
		}

	}

}
