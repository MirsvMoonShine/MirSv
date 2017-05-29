package com.mirsv.moonshine.Welcome;

import org.bukkit.plugin.RegisteredServiceProvider;

import com.mirsv.MirPlugin;

import net.milkbowl.vault.permission.Permission;

public class Welcome extends MirPlugin {
	public static Permission per = null;

	public Welcome(String pluginname) {
		super(pluginname);

		setupPermission();

		getConfig().addDefault("Welcome.defaultWelcomeMessage", "{player}님 서버에 오신 것을 환영합니다!");
		getConfig().addDefault("Welcome.groupWelcomeMessage.default", "{player}님 서버에 오신 것을 환영합니다!");
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