package com.mirsv.Marlang.Util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.permission.Permission;

public class checkPermission {
	
	Permission perm = null;
	
	public boolean setupPermission() {
		RegisteredServiceProvider <Permission> p = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);
		if(p != null) perm = (Permission) p.getProvider();
		return perm != null;
	}
	
	public checkPermission() {
		setupPermission();
	}
	
	public boolean isSponsor (Player p) {
		String[] group = perm.getPlayerGroups(p);
		Boolean isSponsor = false;
		for(String g: group) {
			if(g.equalsIgnoreCase("vip") || g.equalsIgnoreCase("vipp")
			|| g.equalsIgnoreCase("vvip")|| g.equalsIgnoreCase("mvp")
			|| g.equalsIgnoreCase("mvpp")|| g.equalsIgnoreCase("heper")) {
				isSponsor = true;
				break;
			}
		}
		return isSponsor;
	}
	
	public boolean isNewbie (Player p) {
		String[] group = perm.getPlayerGroups(p);
		Boolean isNewbie = false;
		for(String g: group) {
			if(!g.equalsIgnoreCase("default") && !g.equalsIgnoreCase("vip")
			&& !g.equalsIgnoreCase("vipp") && !g.equalsIgnoreCase("vvip")
			&& !g.equalsIgnoreCase("mvp") && !g.equalsIgnoreCase("mvpp")
			&& !g.equalsIgnoreCase("heper") && !g.equalsIgnoreCase("admin")) {
				isNewbie = true;
				break;
			}
		}
		return isNewbie;
	}
	
}
