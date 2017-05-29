package com.mirsv.moonshine.Warning;

import java.io.File;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.mirsv.MirPlugin;

public class Warning extends MirPlugin {
	static FileConfiguration warning;
	static HashMap < String, Boolean > loadboolean = new HashMap < String, Boolean > ();

	public Warning(String pluginname) {
		super(pluginname);
		config();

		File prefixListFile = new File("plugins/" + pm.getDescription().getName() + "/Warning/Warning.yml");
		warning = YamlConfiguration.loadConfiguration(prefixListFile);
		try {
			if (!prefixListFile.exists()) {
				warning.save(prefixListFile);
			}
			warning.load(prefixListFile);
		} catch (Exception localException) {}

		getCommand("warning", new WarningCommand(this, getConfig()));
		getListener(new WarningListener(getConfig()));
	}

	public void config() {
		if (getConfig().getInt("Warning.maxwarning") == 0) {
			getConfig().set("Warning.maxwarning", 5);
			saveConfig();

			for (int i = 1; i <= getConfig().getInt("Warning.maxwarning"); i++) {
				String[] a = {
					"w [username] °æ°í " + i + "È¸"
				};
				getConfig().set("Warning.warnCommand" + i, a);
				saveConfig();
			}
		}
	}
}