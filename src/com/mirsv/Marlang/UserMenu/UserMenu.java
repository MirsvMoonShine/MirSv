package com.mirsv.Marlang.UserMenu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.mirsv.MirPlugin;

import net.milkbowl.vault.permission.Permission;

public class UserMenu extends MirPlugin implements CommandExecutor{
	
	public UserMenu() {
		getCommand("유저", this);
	}
	
	static MainGUI mg = new MainGUI();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c콘솔에서는 사용할 수 없는 명령어입니다!"));
			return true;
		}
		
		Player p = (Player) sender;
		
		if(label.equalsIgnoreCase("유저")) {
			mg.openGUI(p);
		}
		
		return true;
	}
	
}
