package com.mirsv.Marlang.CustomAdvancements;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public enum AdvancementTypes {
	
	발전(ChatColor.translateAlternateColorCodes('&', "발전과제를 달성했습니다")),
	도전(ChatColor.translateAlternateColorCodes('&', "도전을 성공했습니다"));
	
	String cm;
	
	AdvancementTypes(String clearMessage) {
		this.cm = clearMessage;
	}

	public String getClearMessage() {
		return cm;
	}
	
}
