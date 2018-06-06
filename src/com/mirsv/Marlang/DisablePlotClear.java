package com.mirsv.Marlang;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.mirsv.MirPlugin;

public class DisablePlotClear extends MirPlugin implements Listener{
	
	public DisablePlotClear() {
		getListener(this);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCommand(PlayerCommandPreprocessEvent e) throws InterruptedException {
		if(!getConfig().getBoolean("enable.DisablePlotClear")) {
			return;
		}
		String[] Array = e.getMessage().split(" ");
		if(Array[0].substring(1).equalsIgnoreCase("plot") && Array[1].equalsIgnoreCase("clear")) {
			Player p = e.getPlayer();
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c명령어 사용이 거부되었습니다."));
			e.setCancelled(true);
		}
		if(Array[0].substring(1).equalsIgnoreCase("토지") && Array[1].equalsIgnoreCase("초기화")) {
			Player p = e.getPlayer();
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c명령어 사용이 거부되었습니다."));
			e.setCancelled(true);
		}
	}
	
}
