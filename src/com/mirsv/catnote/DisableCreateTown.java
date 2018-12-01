package com.mirsv.catnote;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.mirsv.MirPlugin;

import net.milkbowl.vault.economy.Economy;

public class DisableCreateTown extends MirPlugin implements Listener {
	HashMap <UUID, Long> Executor = new HashMap <UUID, Long>();
	String prefix = ChatColor.GOLD + "[" + ChatColor.GREEN + "미르서버" + ChatColor.GOLD + "] " + ChatColor.RESET;
	public DisableCreateTown() {
		getListener(this);
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCommand(PlayerCommandPreprocessEvent event) throws InterruptedException {
		String[] Array = event.getMessage().split(" ");
		if(Array.length < 2) return;
		if((Array[0].substring(1).equalsIgnoreCase("t") || Array[0].substring(1).equalsIgnoreCase("town") || Array[0].substring(1).equalsIgnoreCase("마을")) 
				&& (Array[1].equalsIgnoreCase("new") || Array[1].equalsIgnoreCase("create") || Array[1].equalsIgnoreCase("설립") || Array[1].equalsIgnoreCase("신설"))) {
			Player player = event.getPlayer();
			RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
			Economy economy = economyProvider.getProvider();
			if(!player.getWorld().getName().equalsIgnoreCase("world")) {
				event.setCancelled(true);
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f[ &6타우니 &f] &b마을&f은 &6건축월드&f에서만 만들 수 있습니다!"));
			}
		}
	}
}
