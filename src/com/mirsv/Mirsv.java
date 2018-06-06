package com.mirsv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.mirsv.catnote.BingoGamble;
import com.mirsv.catnote.NoPickup;
import com.mirsv.moonshine.Party.Party;
import com.mirsv.moonshine.Party.PartyMain;

public class Mirsv extends JavaPlugin {
	PluginManager pm = getServer().getPluginManager();
	ArrayList < String > plugins = new ArrayList < String > ();
	String prefix = ChatColor.GOLD + "[" + ChatColor.GREEN + "미르서버" + ChatColor.GOLD + "] " + ChatColor.RESET;
	public PluginLists lists;
	Logger log = Bukkit.getLogger();
	Timer t;
	
	@Override
	public void onEnable() {
		getConfig().options().copyDefaults(true);
		saveConfig();

		getCommand("Mirsv").setExecutor(new mainCommand(this));
		System.out.println("[미르서버] 종합 플러그인 가동");
		InstallPlugins();
		
		String[] plu = (String[]) this.plugins.toArray(new String[this.plugins.size()]);
		String plugin = "";
		if (plu.length > 0) {
			plugin = plu[0];
			for (int i = 1; i < plu.length; i++) {
				plugin = plugin + ", " + plu[i];
			}
		}
		System.out.println("[미르서버] 가동 된 플러그인들: " + plugin);
		t = new Timer();
		t.schedule(new TimerTask() {
			public void run() {
				NoPickup.Save();
				PartyMain.Save();
				BingoGamble.Save();
				log.info(prefix + "AutoSave Complete!");
			}
		}, 3600000, 3600000);
	}
	@Override
	public void onDisable() {
		NoPickup.Save();
		PartyMain.Save();
		BingoGamble.Save();
		t.cancel();
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