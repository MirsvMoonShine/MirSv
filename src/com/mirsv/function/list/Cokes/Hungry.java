package com.mirsv.function.list.Cokes;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.mirsv.function.AbstractFunction;

public class Hungry extends AbstractFunction implements Listener {

	@Override
	protected void onEnable() {
		registerListener(this);
	}

	@Override
	protected void onDisable() {}
	
	private HashMap<Player, Integer> hungry = new HashMap<Player, Integer>();

	public Hungry() {
		super("�����", "1.0", "���� �� �������ص� ������� �ױ� ���� �����ϰ� �����մϴ�.");
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player p = e.getEntity().getPlayer();
		int hunger = p.getFoodLevel();

		hungry.put(p, hunger);
	}

	@EventHandler
	public void RespawnEvent(PlayerRespawnEvent e) {
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