package com.mirsv.moonshine;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.mirsv.MirPlugin;

public class Hungry extends MirPlugin implements Listener {
	HashMap < Player, Integer > hungry = new HashMap < Player, Integer > ();

	public Hungry() {
		getListener(this);
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if (getConfig().getBoolean("enable.Hungry", true)) {
			Player p = e.getEntity().getPlayer();
			int hunger = p.getFoodLevel();

			hungry.put(p, hunger);
		}
	}

	@EventHandler
	public void RespawnEvent(PlayerRespawnEvent e) {
		if (getConfig().getBoolean("enable.Hungry", true)) {
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