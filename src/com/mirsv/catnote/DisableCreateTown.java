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
import com.mirsv.moonshine.Warning.Warning;
import com.mirsv.moonshine.Warning.WarningCommand;

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
		if(Array[0].substring(1).equalsIgnoreCase("t") && Array[1].equalsIgnoreCase("new")) {
			Player player = event.getPlayer();
			RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
			Economy economy = economyProvider.getProvider();
			if(player.getWorld().getName().equalsIgnoreCase("world")) return;
			event.setCancelled(true);
			player.sendMessage(prefix + ChatColor.RED + "마을 생성 규칙 위반으로 경고 1회 추가");
			Warning warning = new Warning();
			warning.warnCommand(player, 1);
			WarningCommand wc = new WarningCommand(warning);
			wc.addWarning(player);
			double Money = economy.getBalance(Bukkit.getOfflinePlayer(player.getUniqueId()));
			economy.withdrawPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()), Money);
			Timer timer = new Timer();
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					economy.depositPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()), Money);
				}
			};
			timer.schedule(task, 3000);
		}
		if(Array[0].substring(1).equalsIgnoreCase("마을") && Array[1].equalsIgnoreCase("신설")) {
			Player player = event.getPlayer();
			RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
			Economy economy = economyProvider.getProvider();
			if(player.getWorld().getName().equalsIgnoreCase("world")) return;
			event.setCancelled(true);
			player.sendMessage(prefix + ChatColor.RED + "마을 생성 규칙 위반으로 경고 1회 추가");
			Warning warning = new Warning();
			warning.warnCommand(player, 1);
			WarningCommand wc = new WarningCommand(warning);
			wc.addWarning(player);
			double Money = economy.getBalance(Bukkit.getOfflinePlayer(player.getUniqueId()));
			economy.withdrawPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()), Money);
			Timer timer = new Timer();
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					economy.depositPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()), Money);
				}
			};
			timer.schedule(task, 3000);
		}
	}
}
