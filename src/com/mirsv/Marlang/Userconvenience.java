package com.mirsv.Marlang;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.mirsv.MirPlugin;

import net.milkbowl.vault.economy.Economy;

public class Userconvenience extends MirPlugin implements CommandExecutor{
	
	public Userconvenience() {
		getCommand("��ħ", this);
		getCommand("����", this);
		getCommand("�����", this);
	}
	
	final int price = 500;
	
	public static Economy eco = null;
	RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c�ֿܼ����� ����� �� ���� ��ɾ��Դϴ�!"));
		}
		
		eco = rsp.getProvider();
		Player p = (Player) sender;
		
		if(label.equalsIgnoreCase("��ħ")) {
			if(!(eco.has(p, price))) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&l��ħ&f&l���� �ٲٷ��� " + price + "���� �ʿ��մϴ�!"));
				return true;
			} else if(eco.has(p, price)) {
				eco.withdrawPlayer(p, price);
				p.getWorld().setTime(0);
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b&l[" + p.getName() + "]&f&l���� " + price + "���� �Ҹ��� &a&l[" + p.getWorld().getName() + "]&f&l�� �ð��� &e&l[��ħ]&f&l���� �ٲ���ϴ�!"));
				return true;
			}
		}
		if(label.equalsIgnoreCase("����")) {
			if(!(eco.has(p, price))) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&l����&f&l���� �ٲٷ��� " + price + "���� �ʿ��մϴ�!"));
				return true;
			} else if(eco.has(p, price)) {
				eco.withdrawPlayer(p, price);
				p.getWorld().setTime(14000);
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b&l[" + p.getName() + "]&f&l���� " + price + "���� �Ҹ��� &a&l[" + p.getWorld().getName() + "]&f&l�� �ð��� &7&l[����]&f&l���� �ٲ���ϴ�!"));
				return true;
			}
		}
		if(label.equalsIgnoreCase("�����")) {
			if(!(eco.has(p, price))) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9&l��&f&l�� ������ " + price + "���� �ʿ��մϴ�!"));
			} else if(eco.has(p, price)) {
				eco.withdrawPlayer(p, price);
				p.getWorld().setStorm(false);
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b&l[" + p.getName() + "]&f&l���� " + price + "���� �Ҹ��� &a&l[" + p.getWorld().getName() + "]&f&l���� &9&l[��]&f&l�� �����ϴ�!"));
				return true;
			}
		}
		return true;
	}
	
}
