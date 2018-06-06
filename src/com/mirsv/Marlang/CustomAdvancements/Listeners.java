package com.mirsv.Marlang.CustomAdvancements;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.mirsv.MirPlugin;

public class Listeners extends MirPlugin implements Listener{
	
	public Listeners() {
		getListener(this);
	}

	@EventHandler
	public void onCustomAdvancementClear(CustomAdvancementClearEvent e) {
		Player p = e.getPlayer();
		
		if(e.getAdvancement().equals(AdvancementsList.StartOfJourney)) {
			p.sendMessage("보상 지급 테스트 성공!");
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
	}
	
}
