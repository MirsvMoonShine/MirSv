package com.mirsv;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

public class MirPlugin {
	protected Mirsv pm;
	public static FileConfiguration config;

	public MirPlugin() {
		pm = (Mirsv) Bukkit.getPluginManager().getPlugin("Mirsv");
		config = pm.getConfig();
	}

	public FileConfiguration getConfig() {
		return config;
	}

	public void saveConfig() {
		pm.saveConfig();
	}

	public void getListener(Listener lis) {
		pm.getServer().getPluginManager().registerEvents(lis, pm);
	}

	public void getCommand(String name, CommandExecutor ex) {
		pm.getServer().getPluginCommand(name).setExecutor(ex);
	}
}