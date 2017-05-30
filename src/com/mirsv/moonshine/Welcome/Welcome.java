package com.mirsv.moonshine.Welcome;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.mirsv.MirPlugin;

import net.milkbowl.vault.permission.Permission;

public class Welcome extends MirPlugin implements Listener{
	public static Permission per = null;

	public Welcome() {
		setupPermission();

		getConfig().addDefault("Welcome.defaultWelcomeMessage", "{player}�� ������ ���� ���� ȯ���մϴ�!");
		getConfig().addDefault("Welcome.groupWelcomeMessage.default", "{player}�� ������ ���� ���� ȯ���մϴ�!");
		getConfig().options().copyDefaults(true);
		saveConfig();

		getListener(this);
	}

	public boolean setupPermission() {
		RegisteredServiceProvider < Permission > chatProvider = pm.getServer().getServicesManager().getRegistration(Permission.class);
		if (chatProvider != null) {
			per = (Permission) chatProvider.getProvider();
		}
		return per != null;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (getConfig().getBoolean("enable.Welcome")) {
			Player p = e.getPlayer();
			String[] group = per.getPlayerGroups(p);
			String groupmes = null;
			for (String g: group) {
				if (getConfig().getString("Welcome.groupWelcomeMessage." + g) != null)
					groupmes = getConfig().getString("Welcome.groupWelcomeMessage." + g).replace("{player}", p.getName()).replaceAll("&", "��");
			}

			if (groupmes == null) {
				groupmes = getConfig().getString("Welcome.defaultWelcomeMessage").replace("{player}", p.getName()).replaceAll("&", "��");
			}

			e.setJoinMessage(groupmes);
		}
	}
}