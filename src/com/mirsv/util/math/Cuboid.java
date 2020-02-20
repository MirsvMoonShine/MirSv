package com.mirsv.util.math;

import org.bukkit.Location;
import org.bukkit.World;

public class Cuboid {

	private final World world;
	private final double minX, minY, minZ, maxX, maxY, maxZ;

	public Cuboid(World world, double ax, double ay, double az, double bx, double by, double bz) {
		this.world = world;
		this.minX = Math.min(ax, bx);
		this.minY = Math.min(ay, by);
		this.minZ = Math.min(az, bz);
		this.maxX = Math.max(ax, bx);
		this.maxY = Math.max(ay, by);
		this.maxZ = Math.max(az, bz);
	}

	public boolean isInCuboid(Location location) {
		return location.getWorld().equals(world) && location.getX() <= maxX && location.getX() >= minX && location.getY() <= maxY && location.getY() >= minY && location.getZ() <= maxZ && location.getZ() >= minZ;
	}

}
