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
		getCommand("����", this);
	}
	
	static MainGUI mg = new MainGUI();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c�ֿܼ����� ����� �� ���� ��ɾ��Դϴ�!"));
			return true;
		}
		
		Player p = (Player) sender;
		
		if(label.equalsIgnoreCase("����")) {
			mg.openGUI(p);
		}
		
		return true;
	}
	
}
