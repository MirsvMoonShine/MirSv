package com.mirsv.moonshine.Welcome;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class WelcomeListener implements Listener {
	final FileConfiguration plugin;
	public WelcomeListener(FileConfiguration fileConfiguration) {
		this.plugin = fileConfiguration;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (plugin.getBoolean("enable.Welcome")) {
			Player p = e.getPlayer();
			String[] group = Welcome.per.getPlayerGroups(p);
			String groupmes = null;
			for (String g: group) {
				if (plugin.getString("Welcome.groupWelcomeMessage." + g) != null)
					groupmes = plugin.getString("Welcome.groupWelcomeMessage." + g).replace("{player}", p.getName()).replaceAll("&", "¡×");
			}

			if (groupmes == null) {
				groupmes = plugin.getString("Welcome.defaultWelcomeMessage").replace("{player}", p.getName()).replaceAll("&", "¡×");
			}

			e.setJoinMessage(groupmes);
		}
	}
}