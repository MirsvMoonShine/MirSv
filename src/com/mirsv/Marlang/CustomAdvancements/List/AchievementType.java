package com.mirsv.Marlang.CustomAdvancements.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public enum AchievementType {
	
	����(ChatColor.translateAlternateColorCodes('&', "���������� �޼��߽��ϴ�")),
	����(ChatColor.translateAlternateColorCodes('&', "������ �Ϸ��߽��ϴ�!"));
	
	String ClearMessage;
	
	AchievementType(String clearMessage) {
		this.ClearMessage = clearMessage;
	}

	public String getClearMessage() {
		return ClearMessage;
	}
	
}
