package com.mirsv.Marlang.CustomAdvancements;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.mirsv.MirPlugin;
import com.palmergames.bukkit.towny.event.NewTownEvent;
import com.palmergames.bukkit.towny.event.TownAddResidentEvent;

public class Listeners extends MirPlugin implements Listener{
	
	public Listeners() {
		getListener(this);
	}

	@EventHandler
	public void onCustomAdvancementClear(CustomAdvancementClearEvent e) {
		Player p = e.getPlayer();
		AdvancementsList s = e.getAdvancement();
		
		CustomAdvancements.showClear(p, s);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
	}
	
	@EventHandler
	public void onTownRegister(TownAddResidentEvent e) {
		Player p = Bukkit.getPlayer(e.getResident().getName());
		
		//CustomAdvancements.clearCustomAdvancements(p, AdvancementsList.Community);
	}
	
	@EventHandler
	public void onTownCreate(NewTownEvent e) {
		Player p = Bukkit.getPlayer(e.getTown().getMayor().getName());
		
		//CustomAdvancements.clearCustomAdvancements(p, AdvancementsList.Community);
	}
	
}
