package me.bzBear.Bridge;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class MapProtect implements Listener {

	boolean enabled = true;
	public String map = "";
	HashMap<Location, Material> placed = new HashMap<>();
	HashMap<Location, Material> braked = new HashMap<>();

	public MapProtect(String a) {
		map = a;
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (enabled) {
			org.bukkit.block.Block b = event.getBlock();
			if (b.getType() != Material.STAINED_CLAY) {
				event.setCancelled(true);
				event.getPlayer()
						.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4bruh imagine trying to grief"));
			} else {
				braked.put(b.getLocation(), b.getType());
			}
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (enabled) {
			org.bukkit.block.Block b = event.getBlock();
			if (b.getY() > 99) {
				event.setCancelled(true);
				event.getPlayer()
						.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4bruh imagine trying to skybase"));
				return;
			}

			switch (map) {
			case "Urban":
				if (b.getX() > 26 || b.getX() < -26) {
					event.setCancelled(true);
					event.getPlayer().sendMessage(
							ChatColor.translateAlternateColorCodes('&', "&4bruh imagine trying to spam bloc"));
					return;
				}
				break;
			default:
				Bukkit.broadcastMessage(ChatColor.BLACK + "ERROR: WORLD NOT FOUND; MAPPROTECT.JAVA LINE 47");
				return;
			}

			placed.put(b.getLocation(), b.getType());
		}
	}

	@SuppressWarnings("deprecation")
	public void rebuild() {
		for (HashMap.Entry<Location, Material> me : placed.entrySet()) {
			Bukkit.getWorld(map).getBlockAt(me.getKey()).setType(Material.AIR);
			;
		}
		for (HashMap.Entry<Location, Material> me : braked.entrySet()) {
			Block b = Bukkit.getWorld(map).getBlockAt(me.getKey());
			if (b.getY() < 93) {
				b.setType(me.getValue());
				if (b.getX() > 0) {
					b.setData((byte) 14);

				} else if (b.getX() < 0) {
					b.setData((byte) 11);
				}
			}
		}
	}

	@EventHandler
	public void onHungerDeplete(FoodLevelChangeEvent e) {
		if (enabled) {
			e.setCancelled(true);
			Player p = (Player) e.getEntity();
			p.setFoodLevel(20);
		}

	}

	@EventHandler
	public void onEntityDamageEvent(final EntityDamageEvent e) {
		if (enabled) {

			if (e.getCause() == org.bukkit.event.entity.EntityDamageEvent.DamageCause.FALL) {
				e.setCancelled(true);
			}
		}
	}

	public void disable() {
		enabled = false;
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/gamerule randomtickspeed 3");
	}

	public void enable() {
		enabled = true;
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/gamerule randomtickspeed 10000");
	}
}