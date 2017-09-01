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
		if(Array[0].substring(1).equalsIgnoreCase("t") && Array[1].equalsIgnoreCase("new")) {
			Player player = event.getPlayer();
			RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
			Economy economy = economyProvider.getProvider();
			if(player.getWorld().getName().equalsIgnoreCase("world")) return;
			event.setCancelled(true);
			player.sendMessage(prefix + ChatColor.RED + "마을은 건축월드에서만 생성 가능합니다. 감옥에 있는 10분동안 카페에서 규칙을 읽고 오시기 바랍니다.");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "jail " + player.getName() + " 감옥1 10m");
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
			player.sendMessage(prefix + ChatColor.RED + "마을은 건축월드에서만 생성 가능합니다. 감옥에 있는 10분동안 카페에서 규칙을 읽고 오시기 바랍니다.");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "jail " + player.getName() + " 감옥1 10m");
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
