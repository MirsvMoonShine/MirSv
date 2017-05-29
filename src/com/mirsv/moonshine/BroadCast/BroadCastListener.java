package com.mirsv.moonshine.BroadCast;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class BroadCastListener implements Listener {

	public BroadCastListener(FileConfiguration Config) {
		super();
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		if (BroadCast.getConfig().getBoolean("enable.BroadCast", true)) {
			Player player = e.getPlayer();
			if (BroadCast.BCadmins.contains(player.getName())) {
				e.setCancelled(true);
				String Prefix = BroadCast.getConfig().getString("BroadCast.Prefix").replaceAll("&", "¡×");
				String ChatColor = BroadCast.getConfig().getString("BroadCast.ChatColor").replaceAll("&", "¡×");
				String rawmsg = e.getMessage();
				Bukkit.broadcastMessage(Prefix + " " + ChatColor + rawmsg);
			}
		}
	}
}