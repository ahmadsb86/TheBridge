package me.bzBear.Bridge;

public class xyz {
	public int x;
	public int y;
	public int z;
	public xyz(int a, int b, int c) {
		x = a;
		y = b;
		z = c;
	}
	public static Boolean between2D(xyz a, xyz b, double x, double y) {
		if(x > a.x && x < b.x && y > a.y && y < b.y) {
			return true;
		}
		return false;
	}
}
