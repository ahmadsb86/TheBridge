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
	
//	String blueCageReplaceCmd;
//	String redCageReplaceCmd;
//	String blueCageRemove;
//	String redCageRemove;
//	Map(String a, int x1, int y1, int z1, int x2, int y2, int z2, int l, int bl, String b, String c, String d, String e) {
//		name = a;
//		world = Bukkit.getWorld(name);
//		redSpawn = new Location(world, x1,y1,z1, 90, 0);
//		blueSpawn = new Location(world, x2,y2,z2, -90, 0);
//		bridgeLevel = l;
//		buildLim = bl;
//		blueCageReplaceCmd = b;
//		redCageReplaceCmd = c;
//		blueCageRemove = d;
//		redCageRemove = e;
//	}
	
	String blueCageReplaceCmd;
	String redCageReplaceCmd;
	xyz blueCageRemoveLoc;
	xyz blueCageRemoveSize;
	xyz blueCageDiff;
	Map(String a, int x1, int y1, int z1, int x2, int y2, int z2, int l, int bl, String b, String c, xyz d, xyz e, xyz f) {
		name = a;
		world = Bukkit.getWorld(name);
		redSpawn = new Location(world, x1,y1,z1, 90, 0);
		blueSpawn = new Location(world, x2,y2,z2, -90, 0);
		bridgeLevel = l;
		buildLim = bl;
		blueCageReplaceCmd = b;
		redCageReplaceCmd = c;
		blueCageRemoveLoc = d;
		blueCageRemoveSize = e;
		blueCageDiff = f;
	}
	

}
