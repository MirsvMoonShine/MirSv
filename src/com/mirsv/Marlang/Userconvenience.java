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
		getCommand("아침", this);
		getCommand("저녁", this);
		getCommand("비끄기", this);
	}
	
	final int price = 500;
	
	public static Economy eco = null;
	RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c콘솔에서는 사용할 수 없는 명령어입니다!"));
		}
		
		eco = rsp.getProvider();
		Player p = (Player) sender;
		
		if(label.equalsIgnoreCase("아침")) {
			if(!(eco.has(p, price))) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&l아침&f&l으로 바꾸려면 " + price + "원이 필요합니다!"));
				return true;
			} else if(eco.has(p, price)) {
				eco.withdrawPlayer(p, price);
				p.getWorld().setTime(0);
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b&l[" + p.getName() + "]&f&l님이 " + price + "원을 소모해 &a&l[" + p.getWorld().getName() + "]&f&l의 시간을 &e&l[아침]&f&l으로 바꿨습니다!"));
				return true;
			}
		}
		if(label.equalsIgnoreCase("저녁")) {
			if(!(eco.has(p, price))) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&l저녁&f&l으로 바꾸려면 " + price + "원이 필요합니다!"));
				return true;
			} else if(eco.has(p, price)) {
				eco.withdrawPlayer(p, price);
				p.getWorld().setTime(14000);
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b&l[" + p.getName() + "]&f&l님이 " + price + "원을 소모해 &a&l[" + p.getWorld().getName() + "]&f&l의 시간을 &7&l[저녁]&f&l으로 바꿨습니다!"));
				return true;
			}
		}
		if(label.equalsIgnoreCase("비끄기")) {
			if(!(eco.has(p, price))) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9&l비&f&l를 끄려면 " + price + "원이 필요합니다!"));
			} else if(eco.has(p, price)) {
				eco.withdrawPlayer(p, price);
				p.getWorld().setStorm(false);
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b&l[" + p.getName() + "]&f&l님이 " + price + "원을 소모해 &a&l[" + p.getWorld().getName() + "]&f&l에서 &9&l[비]&f&l를 껐습니다!"));
				return true;
			}
		}
		return true;
	}
	
}
