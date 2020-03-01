package com.mirsv.function.list.daybreak;

import com.mirsv.function.AbstractFunction;
import com.mirsv.function.list.daybreak.achievements.list.base.AchStartOfJourney;
import com.mirsv.util.math.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TutorialManager extends AbstractFunction implements Listener {

	public TutorialManager() {
		super("튜토리얼 매니저", "1.0.0");
	}

	@Override
	protected void onEnable() {
		registerListener(this);
	}

	private static final World spawnWorld = Bukkit.getWorld("SpawnWorld");
	private static final Location spawnLocation = new Location(spawnWorld, 0.5, 53, 0.5, -180, 0);
	private static final Cuboid tutorial = new Cuboid(spawnWorld, 1500, 150, -51, 1000, 50, 250);

	@EventHandler(priority = EventPriority.HIGH)
	private void onTeleport(PlayerTeleportEvent e) {
		Player player = e.getPlayer();
		Location loc = player.getLocation();
		if (tutorial.isInCuboid(loc) && !canBypass(loc.getBlockX(), loc.getBlockZ()) && !player.isOp()) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	private void onMove(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		Location loc = player.getLocation();
		if (tutorial.isInCuboid(loc) && canBypass(loc.getBlockX(), loc.getBlockZ())) {
			player.teleport(spawnLocation);
			AchStartOfJourney.instance.achieve(player);
		}
	}

	private boolean canBypass(int x, int z) {
		return x >= 1106 && x <= 1108 && z >= 193 && z <= 195;
	}

	@Override
	protected void onDisable() {}

}
