package com.mirsv.Marlang;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mirsv.MirPlugin;

public class Commands extends MirPlugin implements CommandExecutor{
	
	public Commands() {
		getCommand("�ȳ�", this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("�ȳ�")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c�ֿܼ����� ����� �� ���� ��ɾ��Դϴ�!"));
				return true;
			}
			Player p = (Player) sender;
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a�ȳ� " + p.getName() + "!"));
			return true;
		}
		return true;
	}
	
	
	
}
