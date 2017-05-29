package com.mirsv.moonshine.Welcome;

import org.bukkit.plugin.RegisteredServiceProvider;

import com.mirsv.MirPlugin;

import net.milkbowl.vault.permission.Permission;

public class Welcome extends MirPlugin {
	public static Permission per = null;

	public Welcome(String pluginname) {
		super(pluginname);

		setupPermission();

		getConfig().addDefault("Welcome.defaultWelcomeMessage", "{player}�� ������ ���� ���� ȯ���մϴ�!");
		getConfig().addDefault("Welcome.groupWelcomeMessage.default", "{player}�� ������ ���� ���� ȯ���մϴ�!");
		getConfig().options().copyDefaults(true);
		saveConfig();

		getListener(new WelcomeListener(getConfig()));
	}

	public boolean setupPermission() {
		RegisteredServiceProvider < Permission > chatProvider = MirPlugin.pm.getServer().getServicesManager().getRegistration(Permission.class);
		if (chatProvider != null) {
			per = (Permission) chatProvider.getProvider();
		}
		return per != null;
	}
}