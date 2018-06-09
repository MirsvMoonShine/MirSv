package com.mirsv.Marlang.CustomAdvancements.Reward;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.mirsv.Marlang.CustomAdvancements.AdvancementsList;
import com.mirsv.Marlang.CustomAdvancements.CustomAdvancements;

import net.milkbowl.vault.economy.Economy;

public class CustomAdvancementRewardManager {
	
	public static Economy eco = null;
	RegisteredServiceProvider <Economy> rspe = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
	static CustomAdvancementRewardManager carm = new CustomAdvancementRewardManager();
	
	public static void rewardPlayer(Player p, AdvancementsList s) {
		if(CustomAdvancements.getRewarded(p, s)) return;
		
		if(s.getAdvid().equals("startofjourney")) {
			giveMoney(p, 100);
		}
	}
	
	public static void giveMoney(Player p, Integer i) {
		eco = carm.rspe.getProvider();
		
		eco.depositPlayer(p, i);
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l당신에게 &e&l" + i + "&f&l원이 지급되었습니다!"));
	}
	
}
