package com.mirsv.Marlang.CustomAdvancements;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import com.mirsv.MirPlugin;

public class CustomAdvancementsGUI extends MirPlugin implements Listener{
	
	static CustomAdvancementsGUI cagui = new CustomAdvancementsGUI();
	
	public CustomAdvancementsGUI() {
		getListener(this);
	}
	
	Inventory mainGUI = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "도전과제");
	
	public static void openMainGUI(Player p) {
		
		p.openInventory(cagui.mainGUI);
	}
	
}
