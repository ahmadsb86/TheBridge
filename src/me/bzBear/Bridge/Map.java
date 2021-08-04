package me.bzBear.Bridge;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class Map {
	String name;
	World world;
	Location redSpawn;
	Location blueSpawn;
	int bridgeLevel;
	int buildLim;
	Map(String a, int x1, int y1, int z1, int x2, int y2, int z2, int l, int bl) {
		name = a;
		world = Bukkit.getWorld(name);
		redSpawn = new Location(world, x1,y1,z1, 90, 0);
		blueSpawn = new Location(world, x2,y2,z2, -90, 0);
		bridgeLevel = l;
		buildLim = bl;
	}
	
	
	//Urban is 26 and 93
}
