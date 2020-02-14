package com.mirsv.function.list.daybreak.tutorial.list;

import com.mirsv.Mirsv;
import com.mirsv.function.list.daybreak.tutorial.TutorialManager;
import com.mirsv.function.list.daybreak.tutorial.list.Tutorial.TutorialSequence;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class SpawnShow extends TutorialSequence implements Listener {

	private static final Location location = new Location(Bukkit.getWorld("spawn"), -16.5, 180, 0.5, -90, 30);

	public SpawnShow(Tutorial tutorial) {
		tutorial.super();
	}

	@Override
	public void start() {
		tutorial.player.sendTitle(
				ChatColor.translateAlternateColorCodes('&', "&6이 곳은 &c스폰&6입니다."),
				ChatColor.translateAlternateColorCodes('&', "&f다음으로 넘어가려면 &c웅크리세요&f."),
				10, 20000, 0);
		tutorial.player.setGameMode(GameMode.SPECTATOR);
		tutorial.player.teleport(location);
		Bukkit.getPluginManager().registerEvents(this, Mirsv.getPlugin());
	}

	@EventHandler
	private void onMove(PlayerMoveEvent e) {
		if (e.getPlayer().equals(tutorial.player)) {
			e.setCancelled(true);
			tutorial.player.sendTitle(
					ChatColor.translateAlternateColorCodes('&', "&6이 곳은 &c스폰&6입니다."),
					ChatColor.translateAlternateColorCodes('&', "&f다음으로 넘어가려면 &c웅크리세요&f."),
					0, 20000, 0);
		}
	}

	@EventHandler
	private void onGameModeChange(PlayerGameModeChangeEvent e) {
		if (e.getPlayer().equals(tutorial.player)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	private void onTeleport(PlayerTeleportEvent e) {
		if (e.getPlayer().equals(tutorial.player)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	private void onSneak(PlayerToggleSneakEvent e) {
		if (e.getPlayer().equals(tutorial.player) && e.isSneaking()) {
			tutorial.next();
		}
	}

	@Override
	public void stop() {
		HandlerList.unregisterAll(this);
		tutorial.player.teleport(TutorialManager.SPAWN);
		tutorial.player.setGameMode(GameMode.SURVIVAL);
		tutorial.player.resetTitle();
		tutorial.player.sendTitle("", "", 0, 0, 0);
	}

	@Override
	public void reset() {
		HandlerList.unregisterAll(this);
	}

	@Override
	public TutorialSequence next() {
		return new SpawnCommand(tutorial);
	}

}
