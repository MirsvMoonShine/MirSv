package com.mirsv.Marlang.CustomAdvancements;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public enum AdvancementTypes {
	
	����(ChatColor.translateAlternateColorCodes('&', "���������� �޼��߽��ϴ�")),
	����(ChatColor.translateAlternateColorCodes('&', "������ �����߽��ϴ�"));
	
	String cm;
	
	AdvancementTypes(String clearMessage) {
		this.cm = clearMessage;
	}

	public String getClearMessage() {
		return cm;
	}
	
}
