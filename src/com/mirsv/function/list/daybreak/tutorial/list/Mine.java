package com.mirsv.function.list.daybreak.tutorial.list;

import com.mirsv.Mirsv;
import com.mirsv.function.list.daybreak.tutorial.TutorialManager;
import com.mirsv.function.list.daybreak.tutorial.list.Nether.NetherShow;
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
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Mine {

	public static class MineShow extends TutorialSequence implements Listener {

		private static final Location location = new Location(Bukkit.getWorld("mine"), 0, 180, 0, 180, 90);

		public MineShow(Tutorial tutorial) {
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
			return new MineCommand(tutorial);
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

	public static class MineWarp extends TutorialSequence implements Listener {

		private static final Location location = new Location(Bukkit.getWorld("spawn"), 9.5, 154, -7.5, 45, -10);

		private final BukkitRunnable turnAround;

		public MineWarp(Tutorial tutorial) {
			tutorial.super();
			this.turnAround = new BukkitRunnable() {
				float startAngle = 45;
				float targetAngle = -135;
				float offset = (targetAngle - startAngle) / 40;
				int count = 0;
				@Override
				public void run() {
					if (++count <= 40) {
						Location location = tutorial.player.getLocation();
						location.setYaw(location.getYaw() + offset);
						location.setPitch(-10);
						tutorial.player.teleport(location, TeleportCause.PLUGIN);
					} else cancel();
				}
			};
		}

		@Override
		public void start() {
			sendTitle("&f워프존을 이용해 이동할 수 있습니다.");
			tutorial.player.setGameMode(GameMode.SPECTATOR);
			tutorial.player.teleport(location);
			turnAround.runTaskTimer(Mirsv.getPlugin(), 50, 1);
			Bukkit.getPluginManager().registerEvents(this, Mirsv.getPlugin());
		}

		@EventHandler
		private void onMove(PlayerMoveEvent e) {
			if (e.getPlayer().equals(tutorial.player)) {
				e.setCancelled(true);
				sendTitle("&f워프존을 이용해 이동할 수 있습니다.");
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
			if (e.getPlayer().equals(tutorial.player) && e.getCause() != TeleportCause.PLUGIN) {
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
			if (!turnAround.isCancelled()) turnAround.cancel();
			HandlerList.unregisterAll(this);
			tutorial.player.teleport(TutorialManager.SPAWN);
			tutorial.player.setGameMode(GameMode.SURVIVAL);
			tutorial.player.resetTitle();
			tutorial.player.sendTitle("", "", 0, 0, 0);
		}

		@Override
		public void reset() {
			if (!turnAround.isCancelled()) turnAround.cancel();
			HandlerList.unregisterAll(this);
		}

		@Override
		public TutorialSequence next() {
			return new NetherShow(tutorial);
		}

	}

}
