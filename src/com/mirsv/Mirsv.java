package com.mirsv;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Mirsv extends JavaPlugin {
	PluginManager pm = getServer().getPluginManager();
	ArrayList < String > plugins = new ArrayList < String > ();
	public PluginLists lists;

	public void onEnable() {
		getConfig().options().copyDefaults(true);
		saveConfig();

		getCommand("Mirsv").setExecutor(new mainCommand(this));
		System.out.println("미르서버 종합 플러그인 가동");
		InstallPlugins();

		String[] plu = (String[]) this.plugins.toArray(new String[this.plugins.size()]);
		String plugin = "";
		if (plu.length > 0) {
			plugin = plu[0];
			for (int i = 1; i < plu.length; i++) {
				plugin = plugin + ", " + plu[i];
			}
		}
		System.out.println("가동 된 플러그인들: " + plugin);

		if (getConfig().getBoolean("Update", true)) {
			pm.registerEvents(new UpdateListener(this), this);
		}
	}

	public void InstallPlugins() {
		plugins.clear();

		PluginLists[] plugin = PluginLists.values();
		for (PluginLists plu: plugin) {
			if (getConfig().getBoolean("enable." + plu.getPluginName(), true)) {
				plugins.add(ChatColor.GREEN + plu.getPluginName());
				plu.getPlugin();
				getConfig().set("enable." + plu.getPluginName(), true);
			} else {
				plugins.add(ChatColor.RED + plu.getPluginName());
				getConfig().set("enable." + plu.getPluginName(), false);
			}
		}
	}
}