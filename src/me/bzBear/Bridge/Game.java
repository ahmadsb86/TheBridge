package me.bzBear.Bridge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Game extends BukkitRunnable {
	public Map map;
	public int players;
	MapProtect protector;
	Plugin plugin;
	GameManager gm;

	public Game(Map a, int b, Plugin c, GameManager d) {
		plugin = c;
		gm = d;
		map = a;
		players = b;
		protector = new MapProtect(map);
		protector.enable();
		Bukkit.getPluginManager().registerEvents(protector, plugin);

	}

	List<Player> p = new ArrayList<>();

	@Override
	public void run() {
		// a
	}

	public void start() {
		for (int i = 0; i < p.size(); i++) {
			for (PotionEffect effect : p.get(i).getActivePotionEffects())
				p.get(i).removePotionEffect(effect.getType());
			p.get(i).getInventory().clear();


			if(i<players/2) {
				giveArmor(p.get(i), Color.BLUE);
				giveItems(p.get(i), 11);
				p.get(i).teleport(map.blueSpawn);
			}
			else{
				giveArmor(p.get(i), Color.RED);
				giveItems(p.get(i), 14);
				p.get(i).teleport(map.redSpawn);
			}

			p.get(i).sendMessage(ChatColor.translateAlternateColorCodes('&', "&4\nGame Starting..."));
			gameStartTitle(p.get(i));
			cages();
			
		}
	}
	
	
	public void end() {
		for(Player e: p) {
			unregisterPlayer(e);
		}
		protector.rebuild();
		protector.disable();
	}

	public void make() {
		protector.enable();
	}

	public void registerPlayer(Player e) {
		e.teleport(map.redSpawn);
		p.add(e);
		for (PotionEffect effect : e.getActivePotionEffects())
			e.removePotionEffect(effect.getType());
		e.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200000, 2));
		e.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200000, 10));
		fullClear(e);		
		e.setCanPickupItems(false);
	}

	public void unregisterPlayer(Player e) {
		for (PotionEffect effect : e.getActivePotionEffects())
			e.removePotionEffect(effect.getType());
		fullClear(e);
		p.remove(e);
		e.teleport(gm.hub);
	}

	public void fullClear(Player e) {
		e.getInventory().clear();
		e.getInventory().setHelmet(new ItemStack(Material.AIR));
		e.getInventory().setChestplate(new ItemStack(Material.AIR));
		e.getInventory().setLeggings(new ItemStack(Material.AIR));
		e.getInventory().setBoots(new ItemStack(Material.AIR));
	}

	public void giveItems(Player p, int color) {
		ItemStack a = new ItemStack(Material.IRON_SWORD);
		ItemStack b = new ItemStack(Material.BOW);
		b.addEnchantment(Enchantment.ARROW_INFINITE, 1);
		ItemStack c = new ItemStack(Material.DIAMOND_PICKAXE);
		c.addEnchantment(Enchantment.DIG_SPEED, 3);
		ItemStack d = new ItemStack(Material.STAINED_CLAY, 64, (short) color);
		ItemStack e = new ItemStack(Material.GOLDEN_APPLE, 8);
		ItemStack f = new ItemStack(Material.ARROW);

		ItemMeta itemMeta = e.getItemMeta();
		itemMeta.setDisplayName("hel apple go brrrr");
		itemMeta.setLore(Arrays.asList("pls", "no", "spam me"));
		e.setItemMeta(itemMeta);

		ItemMeta itemMeta1 = a.getItemMeta();
		itemMeta1.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6Katana"));
		a.setItemMeta(itemMeta1);

		a.getItemMeta().spigot().setUnbreakable(true);
		b.getItemMeta().spigot().setUnbreakable(true);
		c.getItemMeta().spigot().setUnbreakable(true);

		p.getInventory().setItem(0, a);
		p.getInventory().setItem(1, b);
		p.getInventory().setItem(2, c);
		p.getInventory().setItem(3, d);
		p.getInventory().setItem(4, d);
		p.getInventory().setItem(5, e);
		p.getInventory().setItem(8, f);
	}

	public void giveArmor(Player p, Color c) {

		if(c == Color.BLUE) {
			p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c, "&4Blue Shirt", "fuck shirts"," me and my homies be livin shirtless", "like blood gangstas"));
		}
		else {
			p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c, "&4Shirt", "fuck shirts"," me and my homies be livin shirtless", "like blood gangstas"));
		}
		p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c, "&4Pants", "fuck pants"," me and my homies be livin naked", "like blood gangstas"));
		p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c, "&4foots", "fuck boots"," me and my homies be livin bare foot", "like blood gangstas"));


	}

	public static ItemStack getColorArmor(Material m, Color c,  String name, String... lore) {
		ItemStack i = new ItemStack(m, 1);
		LeatherArmorMeta meta = (LeatherArmorMeta) i.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		meta.setColor(c);

		List<String> list = new ArrayList<>();
		for (String s : lore) list.add(ChatColor.translateAlternateColorCodes('&', s));
		meta.setLore(list);

		i.setItemMeta(meta);
		return i;
	}

	public void reset() {

		protector.rebuild();

		for (int i = 0; i < p.size(); i++) {

			for (PotionEffect effect : p.get(i).getActivePotionEffects())
				p.get(i).removePotionEffect(effect.getType());

			p.get(i).getInventory().clear();

			if(p.get(i).getInventory().getChestplate().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&4Blue Shirt"))) {
				giveItems(p.get(i), 11);
				p.get(i).teleport(map.blueSpawn);
			}
			else {
				giveItems(p.get(i), 14);
				p.get(i).teleport(map.redSpawn);
			}

		}


	}
	
	public void gameStartTitle(Player p) {
		PacketUtils.sendActionBar(p, ChatColor.YELLOW + "Game Starting");
		PacketUtils.sendTitle(p, ChatColor.GREEN + "Game starting in", "3 seconds", 0, 20, 0);
		p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {

				
				PacketUtils.sendTitle(p, ChatColor.GREEN + "Game starting in", "2 seconds", 0, 20, 0);
				p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
				

			}
		}, 20);
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {

				
				PacketUtils.sendTitle(p, ChatColor.GREEN + "Game starting in", "1 second", 0, 20, 0);
				p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
				
				

			}
		}, 40);
		
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {

				
				PacketUtils.sendTitle(p, ChatColor.GREEN + "", "", 0, 20, 0);
				p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);
				
				

			}
		}, 60);
	}
	
	@SuppressWarnings("deprecation")
	public void cages() {
//		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), map.blueCageReplaceCmd);
//		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), map.redCageReplaceCmd);
		
		for(int x = map.blueCageRemoveLoc.x; x <= map.blueCageRemoveLoc.x+map.blueCageRemoveSize.x; x++) {
			for(int y = map.blueCageRemoveLoc.y; y <= map.blueCageRemoveLoc.y+map.blueCageRemoveSize.y; y++) {
				for(int z = map.blueCageRemoveLoc.z; z <= map.blueCageRemoveLoc.z+map.blueCageRemoveSize.z; z++) {
					Block paste = map.world.getBlockAt(x,y,z);
					xyz pastediff = map.blueCageDiff;
					Block copy = map.world.getBlockAt(x+pastediff.x,y+pastediff.y,z+pastediff.z);
							
					paste.setType(copy.getType());
					paste.setData(copy.getData());
					
				}
			}
		}
		
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {
				
				
//				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), map.blueCageRemove);
//				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), map.redCageRemove);
				
				for(int x = map.blueCageRemoveLoc.x; x <= map.blueCageRemoveLoc.x+map.blueCageRemoveSize.x; x++) {
					for(int y = map.blueCageRemoveLoc.y; y <= map.blueCageRemoveLoc.y+map.blueCageRemoveSize.y; y++) {
						for(int z = map.blueCageRemoveLoc.z; z <= map.blueCageRemoveLoc.z+map.blueCageRemoveSize.z; z++) {
							map.world.getBlockAt(x,y,z).setType(Material.AIR);
						}
					}
				}
				
				for(Entity e: map.world.getEntities()) {
					if(e instanceof Item) {
						e.remove();
					}
				}
				

			}
		}, 60);
	}




}
