package com.mirsv.function.list.daybreak;

import com.mirsv.function.AbstractFunction;
import com.mirsv.util.math.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class CombatManager extends AbstractFunction implements Listener {

	public CombatManager() {
		super("CombatManager", "1.0.0");
	}

	@Override
	protected void onEnable() {
		registerListener(this);
	}

	@Override
	protected void onDisable() {

	}

	private final World spawnWorld = Bukkit.getWorld("spawn");
	private final Cuboid colosseum = new Cuboid(spawnWorld, 10, 160, -79, 67, 195, -136);

	@EventHandler
	private void onMove(PlayerMoveEvent e) {
		if (colosseum.isInCuboid(e.getTo())) {
			Material type = e.getTo().getBlock().getType();
			if (type == Material.LAVA) {
				Player player = e.getPlayer();
				if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)
				e.getPlayer().setHealth(0);
			}
		}
	}

}
