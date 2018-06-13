package com.mirsv.Marlang.CustomAdvancements.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public enum AchievementType {
	
	발전(ChatColor.translateAlternateColorCodes('&', "발전과제를 달성했습니다")),
	도전(ChatColor.translateAlternateColorCodes('&', "도전을 완료했습니다!"));
	
	String ClearMessage;
	
	AchievementType(String clearMessage) {
		this.ClearMessage = clearMessage;
	}

	public String getClearMessage() {
		return ClearMessage;
	}
	
}
