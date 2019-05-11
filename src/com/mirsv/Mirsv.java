package com.mirsv;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import com.mirsv.function.AbstractFunction;
import com.mirsv.function.Functions;
import com.mirsv.util.Messager;
import com.mirsv.util.thread.ThreadUtil;

/**
 * 미르서버 종합 플러그인
 * @author Cokes_86, DayBreak, CatNote
 * @renewal DayBreak
 */
public class Mirsv extends JavaPlugin {
	
	private static Mirsv plugin;
	
	public static Mirsv getPlugin() {
		return plugin;
	}
	
	@Override
	public void onEnable() {
		plugin = this;
		
		getConfig().options().copyDefaults(true);
		
		getCommand("Mirsv").setExecutor(new mainCommand());
		
		StringJoiner joiner = new StringJoiner(ChatColor.translateAlternateColorCodes('&', ", "));
		for(AbstractFunction f : InitFunctions()) joiner.add(f.getName());
		
		Messager.sendMessage("활성화된 기능: " + joiner.toString());
		ThreadUtil.onEnable();
		
		Messager.sendMessage("플러그인이 활성화되었습니다.");
	}
	
	@Override
	public void onDisable() {
		ThreadUtil.onDisable();
	}
	
	private List<AbstractFunction> InitFunctions() {
		List<AbstractFunction> list = new ArrayList<AbstractFunction>();
		
		for (Functions f: Functions.values()) {
			if (getConfig().getBoolean("enable." + f.toString(), true)) {
				getConfig().set("enable." + f.toString(), true);
				AbstractFunction function = f.getFunction();
				function.Enable();
				list.add(function);
			} else {
				getConfig().set("enable." + f.toString(), false);
			}
		}
		
		return list;
	}
}