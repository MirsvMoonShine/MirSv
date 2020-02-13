package com.mirsv;

import com.mirsv.function.AbstractFunction;
import com.mirsv.function.Functions;
import com.mirsv.util.Messager;
import com.mirsv.util.thread.ThreadUtil;
import net.luckperms.api.LuckPerms;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.dynmap.DynmapAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * 미르서버 종합 플러그인
 *
 * @author Cokes_86, DayBreak, CatNote
 * @renewal DayBreak
 */
public class Mirsv extends JavaPlugin {

	private static Mirsv plugin;

	private LuckPerms luckPerms;
	private Economy economy;

	private DynmapAPI dynMap = null;

	public static Mirsv getPlugin() {
		return plugin;
	}

	@Override
	public void onEnable() {
		plugin = this;

		getConfig().options().copyDefaults(true);

		getCommand("Mirsv").setExecutor(new MainCommand());

		StringJoiner joiner = new StringJoiner(ChatColor.translateAlternateColorCodes('&', ", "));
		for (AbstractFunction f : initFunctions()) joiner.add(f.getName());

		Messager.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9┌─┬─┐ &3┬ &b┌──┐ "));
		Messager.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9│ │ │ &3│ &b│──┘ &9M&3i&br&fServer v" + this.getDescription().getVersion()));
		Messager.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9│   │ &3│ &b│↘  &7플러그인이 최신 버전입니다."));
		Messager.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9│   │ &3┴ &b│ ↘ "));

		Messager.sendMessage("활성화된 기능: " + joiner.toString());
		ThreadUtil.onEnable();

		Messager.sendMessage("플러그인이 활성화되었습니다.");

		if (Bukkit.getServicesManager().isProvidedFor(LuckPerms.class)) {
			luckPerms = Bukkit.getServicesManager().getRegistration(LuckPerms.class).getProvider();
		} else {
			Bukkit.getPluginManager().disablePlugin(this);
		}

		if (Bukkit.getServicesManager().isProvidedFor(Economy.class)) {
			economy = Bukkit.getServicesManager().getRegistration(Economy.class).getProvider();
		} else {
			Bukkit.getPluginManager().disablePlugin(this);
		}

		if (Bukkit.getPluginManager().getPlugin("dynmap") != null) {
			dynMap = (DynmapAPI) Bukkit.getPluginManager().getPlugin("dynmap");
		}
	}

	@Override
	public void onDisable() {
		ThreadUtil.onDisable();
	}

	private List<AbstractFunction> initFunctions() {
		List<AbstractFunction> list = new ArrayList<>();

		for (Functions functions : Functions.values()) {
			if (getConfig().getBoolean("enable." + functions.toString(), true)) {
				getConfig().set("enable." + functions.toString(), true);
				AbstractFunction function = functions.getFunction();
				function.Enable();
				list.add(function);
			} else {
				getConfig().set("enable." + functions.toString(), false);
			}
		}

		return list;
	}

	public LuckPerms getPermAPI() {
		return luckPerms;
	}
	public Economy getEconomy() {
		return economy;
	}

	public DynmapAPI getDynmapAPI() {
		return dynMap;
	}
}