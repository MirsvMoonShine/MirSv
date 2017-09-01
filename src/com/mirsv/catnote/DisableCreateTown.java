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
	String prefix = ChatColor.GOLD + "[" + ChatColor.GREEN + "�̸�����" + ChatColor.GOLD + "] " + ChatColor.RESET;
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
			player.sendMessage(prefix + ChatColor.RED + "������ ������忡���� ���� �����մϴ�. ������ �ִ� 10�е��� ī�信�� ��Ģ�� �а� ���ñ� �ٶ��ϴ�.");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "jail " + player.getName() + " ����1 10m");
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
		if(Array[0].substring(1).equalsIgnoreCase("����") && Array[1].equalsIgnoreCase("�ż�")) {
			Player player = event.getPlayer();
			RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
			Economy economy = economyProvider.getProvider();
			if(player.getWorld().getName().equalsIgnoreCase("world")) return;
			event.setCancelled(true);
			player.sendMessage(prefix + ChatColor.RED + "������ ������忡���� ���� �����մϴ�. ������ �ִ� 10�е��� ī�信�� ��Ģ�� �а� ���ñ� �ٶ��ϴ�.");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "jail " + player.getName() + " ����1 10m");
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
