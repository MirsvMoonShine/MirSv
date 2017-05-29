package com.mirsv.moonshine.hungry;


import java.util.*;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class HungryListener implements Listener {
	private final FileConfiguration plugin;
	public static HashMap < Player, Integer > hungry = new HashMap < Player, Integer > ();

	public HungryListener(FileConfiguration config) {
		this.plugin = config;
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if (plugin.getBoolean("enable.Hungry", true)) {
			Player p = e.getEntity().getPlayer();
			int hunger = p.getFoodLevel();

			hungry.put(p, hunger);
		}
	}

	@EventHandler
	public void RespawnEvent(PlayerRespawnEvent e) {
		if (plugin.getBoolean("enable.Hungry", true)) {
			Player p = e.getPlayer();
			if (hungry.containsKey(p)) {
				int hunger = hungry.get(p);

				Timer time = new Timer();
				TimerTask task = new TimerTask() {

					@Override
					public void run() {
						p.setFoodLevel(hunger);
						hungry.remove(p);
					}

				};

				time.schedule(task, 1000);
			}
		}
	}
}