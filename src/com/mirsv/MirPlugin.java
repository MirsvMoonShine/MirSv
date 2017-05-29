package com.mirsv;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class MirPlugin {
	private final String plugin;
	protected static Plugin pm;
	public static FileConfiguration config;

	public MirPlugin(String pluginname) {
		this.plugin = pluginname;
		pm = Bukkit.getPluginManager().getPlugin("Mirsv");
		config = pm.getConfig();
	}

	public static FileConfiguration getConfig() {
		return config;
	}

	public static void saveConfig() {
		pm.saveConfig();
	}

	public String getPluginName() {
		return plugin;
	}

	public void getListener(Listener lis) {
		pm.getServer().getPluginManager().registerEvents(lis, pm);
	}

	public void getCommand(String name, CommandExecutor ex) {
		pm.getServer().getPluginCommand(name).setExecutor(ex);
	}
}