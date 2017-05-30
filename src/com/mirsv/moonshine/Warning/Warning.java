package com.mirsv.moonshine.Warning;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.mirsv.MirPlugin;

public class Warning extends MirPlugin implements Listener {
	static FileConfiguration warning;
	static HashMap < String, Boolean > loadboolean = new HashMap < String, Boolean > ();

	public Warning() {
		config();

		File prefixListFile = new File("plugins/" + pm.getDescription().getName() + "/Warning/Warning.yml");
		warning = YamlConfiguration.loadConfiguration(prefixListFile);
		try {
			if (!prefixListFile.exists()) {
				warning.save(prefixListFile);
			}
			warning.load(prefixListFile);
		} catch (Exception localException) {}

		getCommand("warning", new WarningCommand(this));
		getListener(this);
	}

	public void config() {
		if (getConfig().getInt("Warning.maxwarning") == 0) {
			getConfig().set("Warning.maxwarning", 5);
			saveConfig();

			for (int i = 1; i <= getConfig().getInt("Warning.maxwarning"); i++) {
				String[] a = {
					"w [username] 경고 " + i + "회"
				};
				getConfig().set("Warning.warnCommand" + i, a);
				saveConfig();
			}
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (getConfig().getBoolean("enable.Warning", true)) {
			Player player = e.getPlayer();
			int warn = Warning.warning.getInt(player.getName());
			if (warn > 0)
				player.sendMessage(ChatColor.GREEN + "[미르서버] 당신의 경고 횟수: " + warn);
			if (Warning.loadboolean.getOrDefault(player.getName(), false)) {
				warnCommand(player, warn);
				Warning.loadboolean.put(player.getName(), false);
			}
		}
	}

	public void warnCommand(Player player, int warn) {
		List < String > command = getConfig().getStringList("Warning.warnCommand" + warn);
		if (!command.isEmpty())
			for (String Command: command)
				if (Command != null) {
					String result = Command.replace("[username]", player.getName());
					this.pm.getServer().getScheduler().runTask(pm, new Runnable() {
						@Override
						public void run() {
							pm.getServer().dispatchCommand(pm.getServer().getConsoleSender(), result);
						}
					});
				}
	}
}