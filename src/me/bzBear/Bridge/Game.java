package me.bzBear.Bridge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Game extends BukkitRunnable {
	public String world = "";
	public int players;
	MapProtect protector;

	public Game(String a, int b) {
		world = a;
		players = b;
		protector = new MapProtect(world);
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
			giveItems(p.get(i));
			
			if(i<players/2) {
				giveArmor(p.get(i), Color.BLUE);
			}
			else {
				giveArmor(p.get(i), Color.RED);
			}
			
			p.get(i).sendMessage(ChatColor.translateAlternateColorCodes('&', "&4\nGame Starting..."));
		}
	}

	public void registerPlayer(Player e) {
		p.add(e);
		for (PotionEffect effect : e.getActivePotionEffects())
			e.removePotionEffect(effect.getType());
		e.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200000, 2));
		fullClear(e);
	}

	public void unregisterPlayer(Player e) {
		fullClear(e);
		p.remove(e);
	}
	
	public void fullClear(Player e) {
		e.getInventory().clear();
		e.getInventory().setHelmet(new ItemStack(Material.AIR));
		e.getInventory().setChestplate(new ItemStack(Material.AIR));
		e.getInventory().setLeggings(new ItemStack(Material.AIR));
		e.getInventory().setBoots(new ItemStack(Material.AIR));
	}

	public void giveItems(Player p) {
		ItemStack a = new ItemStack(Material.IRON_SWORD);
		ItemStack b = new ItemStack(Material.BOW);
		ItemStack c = new ItemStack(Material.DIAMOND_PICKAXE);
		c.addEnchantment(Enchantment.DIG_SPEED, 3);
		ItemStack d = new ItemStack(Material.STAINED_CLAY, 64, (short) 14);
		ItemStack e = new ItemStack(Material.GOLDEN_APPLE, 8);
		ItemStack f = new ItemStack(Material.ARROW);

		ItemMeta itemMeta = e.getItemMeta();
		itemMeta.setDisplayName("hel apple go brrrr");
		itemMeta.setLore(Arrays.asList("pls", "no", "spam me"));
		e.setItemMeta(itemMeta);

		ItemMeta itemMeta1 = a.getItemMeta();
		itemMeta1.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6Katana"));
		a.setItemMeta(itemMeta1);

		p.getInventory().setItem(0, a);
		p.getInventory().setItem(1, b);
		p.getInventory().setItem(2, c);
		p.getInventory().setItem(3, d);
		p.getInventory().setItem(4, d);
		p.getInventory().setItem(5, e);
		p.getInventory().setItem(8, f);
	}
	
	public void giveArmor(Player p, Color c) {
		
		p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c, "&4Shirt", "fuck shirts"," me and my homies be livin shirtless", "like blood gangstas"));
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
		for (int i = 0; i < p.size(); i++) {

			for (PotionEffect effect : p.get(i).getActivePotionEffects())
				p.get(i).removePotionEffect(effect.getType());
			p.get(i).getInventory().clear();
			giveItems(p.get(i));
		}
	}
	

}
