package com.mirsv.Marlang;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mirsv.MirPlugin;

public class Commands extends MirPlugin implements CommandExecutor{
	
	public Commands() {
		getCommand("안녕", this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("안녕")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c콘솔에서는 사용할 수 없는 명령어입니다!"));
				return true;
			}
			Player p = (Player) sender;
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a안녕 " + p.getName() + "!"));
			return true;
		}
		return true;
	}
	
	
	
}
