package com.mirsv.function.list.daybreak.tutorial.list;

import com.mirsv.Mirsv;
import com.mirsv.function.list.daybreak.tutorial.TutorialManager;
import com.mirsv.function.list.daybreak.tutorial.list.Mine.MineWarp;
import com.mirsv.function.list.daybreak.tutorial.list.Tutorial.TutorialSequence;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class AchievementTutorial {

	//391 111 2
	//383 118 -6

	public static class AchievementShow extends TutorialSequence implements Listener {

		private static final Location location = new Location(Bukkit.getWorld("spawn"), 387.5, 111, -54.5, 0, 0);

		public AchievementShow(Tutorial tutorial) {
			tutorial.super();
		}

		@Override
		public void start() {
			sendTitle("&6이 곳은 &c채집 월드&6입니다.");
			tutorial.player.setGameMode(GameMode.SPECTATOR);
			tutorial.player.teleport(location);
			Bukkit.getPluginManager().registerEvents(this, Mirsv.getPlugin());
		}

		@EventHandler
		private void onMove(PlayerMoveEvent e) {
			if (e.getPlayer().equals(tutorial.player)) {
				e.setCancelled(true);
				sendTitle("&6이 곳은 &c채집 월드&6입니다.");
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
			return new Mine.MineCommand(tutorial);
		}

	}

	public static class MineCommand extends TutorialSequence implements Listener {

		public MineCommand(Tutorial tutorial) {
			tutorial.super();
		}

		@Override
		public void start() {
			sendTitle("&c/채집 &6명령어 또는");
			tutorial.player.setGameMode(GameMode.SPECTATOR);
			Bukkit.getPluginManager().registerEvents(this, Mirsv.getPlugin());
		}

		@EventHandler
		private void onMove(PlayerMoveEvent e) {
			if (e.getPlayer().equals(tutorial.player)) {
				e.setCancelled(true);
				sendTitle("&c/채집 &6명령어 또는");
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
			return new MineWarp(tutorial);
		}

	}

}
