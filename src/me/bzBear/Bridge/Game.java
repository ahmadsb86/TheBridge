package me.bzBear.Bridge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.GameMode;
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

import me.bzBear.Bridge.GameStateEnum.GameState;

public class Game {
	public Map map;
	public int players;
	MapProtect protector;
	PlayerEvents pe;
	Plugin plugin;
	GameManager gm;
	GameState state = GameState.EMPTY;
	ScoreboardManager sbm;
	int blueScore = 0;
	int redScore = 0;
	int t;
	List<BridgePlayer> p = new ArrayList<>();

	public Game(Map a, int b, Plugin c, GameManager d) {
		plugin = c;
		gm = d;
		map = a;
		players = b;
		protector = new MapProtect(map);
		pe = new PlayerEvents(c, this, p);
		Bukkit.getPluginManager().registerEvents(pe, plugin);
		Bukkit.getPluginManager().registerEvents(protector, plugin);

	}

	public void start() {

		sbm = new ScoreboardManager(plugin, p);
		pe.bp = p;

		for (int i = 0; i < p.size(); i++) {

			Player bp = p.get(i).bp;

			for (PotionEffect effect : bp.getActivePotionEffects())
				bp.removePotionEffect(effect.getType());
			bp.getInventory().clear();

			if (p.get(i).isBlue()) {
				giveArmor(bp, Color.BLUE);
				giveItems(bp, 11);
				bp.teleport(map.blueSpawn);
			} else {
				giveArmor(bp, Color.RED);
				giveItems(bp, 14);
				bp.teleport(map.redSpawn);
			}

			bp.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4\nGame Starting..."));

			
			sbm.set(p.get(i));

		}
		
		state = GameState.CAGE;
		gameStartTitle();
		cages();

	}

	public void end() {

		for (int i = 0; i < p.size(); i++) {
			BridgePlayer e = p.get(i);
			unregisterPlayer(e);
		}
		blueScore = 0;
		redScore = 0;
		protector.rebuild();
		protector.disable();
		pe.disable();
		state = GameState.EMPTY;
	}

	public void make() {
		protector.enable();
		pe.enable();
	}

	public void registerPlayer(Player e) {

		e.teleport(map.redSpawn);

		if (p.size()%2 == 0) {
			p.add(new BridgePlayer(e, Color.BLUE, this, plugin));

		} else {
			p.add(new BridgePlayer(e, Color.RED, this, plugin));
		}
		
		pe.set(p);

		for (PotionEffect effect : e.getActivePotionEffects())
			e.removePotionEffect(effect.getType());
		e.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200000, 2));
		e.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200000, 10));
		fullClear(e);
		e.setCanPickupItems(false);

		if (p.size() == players) {
			state = GameState.FULL;
		} else {
			state = GameState.WAITING;
		}

	}

	public void unregisterPlayer(BridgePlayer e) {
		for (PotionEffect effect : e.bp.getActivePotionEffects())
			e.bp.removePotionEffect(effect.getType());
		fullClear(e.bp);
		p.remove(e);
		e.bp.setGameMode(GameMode.SURVIVAL);
		if (sbm != null) {
			sbm.remove(e);
		}

		e.bp.teleport(gm.hub);

		if (state == GameState.WAITING && p.size() == 0) {
			state = GameState.EMPTY;
		}

		if (state == GameState.FULL) {
			state = GameState.WAITING;
		}
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

		if (c == Color.BLUE) {
			p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c, "&4Blue Shirt", "fuck shirts",
					" me and my homies be livin shirtless", "like blood gangstas"));
		} else {
			p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c, "&4Shirt", "fuck shirts",
					" me and my homies be livin shirtless", "like blood gangstas"));
		}
		p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c, "&4Pants", "fuck pants",
				" me and my homies be livin naked", "like blood gangstas"));
		p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c, "&4foots", "fuck boots",
				" me and my homies be livin bare foot", "like blood gangstas"));

	}

	public ItemStack getColorArmor(Material m, Color c, String name, String... lore) {
		ItemStack i = new ItemStack(m, 1);
		LeatherArmorMeta meta = (LeatherArmorMeta) i.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		meta.setColor(c);

		List<String> list = new ArrayList<>();
		for (String s : lore)
			list.add(ChatColor.translateAlternateColorCodes('&', s));
		meta.setLore(list);

		i.setItemMeta(meta);
		return i;
	}

	public void score(BridgePlayer scorer) {

		Player e = scorer.bp;



		scorer.goals ++;
		if(scorer.isBlue()) {
			blueScore ++;
		}
		else {
			redScore ++;
		}


		for(BridgePlayer b: p) {
			sbm.set(b);
		}
		if(scorer.goals > 4) {
			finish(scorer);
			return;
		}

		cages();

		for (BridgePlayer a : p) {

			for (PotionEffect effect : scorer.bp.getActivePotionEffects())
				scorer.bp.removePotionEffect(effect.getType());

			if (scorer.isBlue()) {
				ChatMessage.scored(scorer);
				fullClear(e);
				giveItems(e, 11);
				giveArmor(e, Color.BLUE);
				e.teleport(map.blueSpawn);

			} else {
				// a
				ChatMessage.scored(scorer);
				fullClear(e);
				giveItems(e, 14);
				giveArmor(e, Color.RED);
				e.teleport(map.redSpawn);
			}

		}
		if (scorer.isBlue()) {
			GameTitle(5, ChatColor.BLUE + e.getDisplayName() + " Scored");
		} else {
			GameTitle(5, ChatColor.RED + e.getDisplayName() + " Scored");
		}
	}


	public void finish(BridgePlayer finisher) {

		for(BridgePlayer b: p) {
			if(b.isBlue() == finisher.isBlue()) {
				b.bp.setGameMode(GameMode.SPECTATOR);
			}

			ChatMessage.finish(b, this, finisher);

		}



		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {
				for(BridgePlayer b: p) {
					gm.playerLeave(b.bp);
				}
			}
		}, 100);

	}


	public void reset() {

		protector.rebuild();

		for (int i = 0; i < p.size(); i++) {

			Player bp = p.get(i).bp;

			for (PotionEffect effect : bp.getActivePotionEffects())
				bp.removePotionEffect(effect.getType());

			bp.getInventory().clear();

			if (p.get(i).isBlue()) {
				giveItems(bp, 11);
				bp.teleport(map.blueSpawn);
			} else {
				giveItems(bp, 14);
				bp.teleport(map.redSpawn);
			}

		}

	}

	public void gameStartTitle() {
		for(BridgePlayer ibp: p) {
			PacketUtils.sendActionBar(ibp.bp, ChatColor.YELLOW + "Game Starting");
		}
		GameTitle(5, ChatColor.GREEN + "");
	}

	@SuppressWarnings("deprecation")
	public void cages() {

		for(BridgePlayer b: p) {
			b.bp.setGameMode(GameMode.ADVENTURE);
		}
		
		state = GameState.CAGE;

		for (int x = map.redCageRemoveLoc.x; x <= map.redCageRemoveLoc.x + map.redCageRemoveSize.x; x++) {
			for (int y = map.redCageRemoveLoc.y; y <= map.redCageRemoveLoc.y + map.redCageRemoveSize.y; y++) {
				for (int z = map.redCageRemoveLoc.z; z <= map.redCageRemoveLoc.z + map.redCageRemoveSize.z; z++) {
					Block paste = map.world.getBlockAt(x, y, z);
					xyz pastediff = map.redCageDiff;
					Block copy = map.world.getBlockAt(x + pastediff.x, y + pastediff.y, z + pastediff.z);

					paste.setType(copy.getType());
					paste.setData(copy.getData());

				}
			}
		}

		for (int x = map.blueCageRemoveLoc.x; x <= map.blueCageRemoveLoc.x + map.blueCageRemoveSize.x; x++) {
			for (int y = map.blueCageRemoveLoc.y; y <= map.blueCageRemoveLoc.y + map.blueCageRemoveSize.y; y++) {
				for (int z = map.blueCageRemoveLoc.z; z <= map.blueCageRemoveLoc.z + map.blueCageRemoveSize.z; z++) {
					Block paste = map.world.getBlockAt(x, y, z);
					xyz pastediff = map.blueCageDiff;
					Block copy = map.world.getBlockAt(x + pastediff.x, y + pastediff.y, z + pastediff.z);

					paste.setType(copy.getType());
					paste.setData(copy.getData());

				}
			}
		}

		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {

				for (int x = map.redCageRemoveLoc.x; x <= map.redCageRemoveLoc.x + map.redCageRemoveSize.x; x++) {
					for (int y = map.redCageRemoveLoc.y; y <= map.redCageRemoveLoc.y + map.redCageRemoveSize.y; y++) {
						for (int z = map.redCageRemoveLoc.z; z <= map.redCageRemoveLoc.z
								+ map.redCageRemoveSize.z; z++) {
							map.world.getBlockAt(x, y, z).setType(Material.AIR);
						}
					}
				}

				for (int x = map.blueCageRemoveLoc.x; x <= map.blueCageRemoveLoc.x + map.blueCageRemoveSize.x; x++) {
					for (int y = map.blueCageRemoveLoc.y; y <= map.blueCageRemoveLoc.y
							+ map.blueCageRemoveSize.y; y++) {
						for (int z = map.blueCageRemoveLoc.z; z <= map.blueCageRemoveLoc.z
								+ map.blueCageRemoveSize.z; z++) {
							map.world.getBlockAt(x, y, z).setType(Material.AIR);
						}
					}
				}

				for (Entity e : map.world.getEntities()) {
					if (e instanceof Item) {
						e.remove();
					}
				}

				state = GameState.RUNNING;
				for(BridgePlayer b: p) {
					b.bp.setGameMode(GameMode.SURVIVAL);
				}

			}
		}, 100);
	}

	public BridgePlayer getBP(Player input) {
		for (BridgePlayer bridgeP : p) {
			if (bridgeP.bp.getUniqueId() == input.getUniqueId()) {
				return bridgeP;
			}
		}
		return null;
	}

	public void GameTitle(int seconds, String title) {

		t = seconds;

		int id = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
			@Override
			public void run() {

				for (BridgePlayer e : p) {
					PacketUtils.sendTitle(e.bp, title, "Cages opening in " + t + "...", 0, 20, 0);
					e.bp.playSound(e.bp.getLocation(), Sound.CLICK, 1, 1);
				}

				t = t - 1;

			}
		}, 0, 20).getTaskId();

		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {
				Bukkit.getScheduler().cancelTask(id);
				for (BridgePlayer e : p) {
					PacketUtils.sendTitle(e.bp, "", "", 0, 20, 0);
					e.bp.playSound(e.bp.getLocation(), Sound.NOTE_PLING, 1, 1);
				}

			}
		}, seconds * 20 + 1);
	}

}
