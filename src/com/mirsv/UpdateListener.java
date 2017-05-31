package com.mirsv;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class UpdateListener implements Listener {
	final Plugin pm;
	String prefix = ChatColor.GOLD + "[" + ChatColor.GREEN + "미르서버" + ChatColor.GOLD + "] " + ChatColor.RESET;

	public UpdateListener(Mirsv p) {
		this.pm = p;
	}

	public String getLastPluginVersion() {
		try {
			HttpURLConnection con = (HttpURLConnection) new URL("http://www.mirsv.com/mirsvplugin/version.txt").openConnection();
			return new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
		} catch (Exception e) {
			System.out.println(prefix+ChatColor.AQUA+"최신버전 체크 실패.");
		}
		return null;
	}

	@EventHandler
	public void join(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (p.hasPermission("mirsv.admin")) {
			String lastversion = this.getLastPluginVersion();
			String version = pm.getDescription().getVersion();
			if (!lastversion.equals(version)) {
				p.sendMessage(prefix+ChatColor.AQUA+"종합 플러그인 최신버전 발견 (현버전: " + version + ",최신버전: " + lastversion + ")");
				p.sendMessage(prefix+ChatColor.AQUA+"바로 다운받기: http://www.mirsv.com/mirsvplugin/Mirsv.jar");
			}
		}
	}
}