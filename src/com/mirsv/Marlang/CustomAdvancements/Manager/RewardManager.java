package com.mirsv.Marlang.CustomAdvancements.Manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.mirsv.Marlang.CustomAdvancements.CustomAchievement;
import com.mirsv.Marlang.CustomAdvancements.List.AchievementList;

import net.milkbowl.vault.economy.Economy;

public class RewardManager {
	
	public static Economy eco = null;
	RegisteredServiceProvider <Economy> rspe = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
	static RewardManager carm = new RewardManager();
	
	public static void rewardPlayer(Player p, AchievementList s) {
		if(CustomAchievement.getRewarded(p, s)) return;
		
		if(s.getID().equals("startofjourney")) {
			giveMoney(p, 100);
		}
	}
	
	public static void giveMoney(Player p, Integer i) {
		eco = carm.rspe.getProvider();
		
		eco.depositPlayer(p, i);
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l당신에게 &e&l" + i + "&f&l원이 지급되었습니다!"));
	}
	
}
