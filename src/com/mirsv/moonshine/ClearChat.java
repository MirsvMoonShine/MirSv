package com.mirsv.moonshine;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mirsv.MirPlugin;

public class ClearChat extends MirPlugin implements CommandExecutor{
	String prefix = ChatColor.GOLD + "[" + ChatColor.GREEN + "�̸�����" + ChatColor.GOLD + "] " + ChatColor.RESET;
	
	public ClearChat() {
		getCommand("clearchat", this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
		if (((sender instanceof Player)) && (getConfig().getBoolean("enable.ClearChat", true))) {
			Player p = (Player) sender;

			if (p.hasPermission("mirsv.admin")) {
				for (int i = 0; i < 60; i++) {
					Bukkit.broadcastMessage(" ");
				}
				Bukkit.broadcastMessage(prefix + p.getName() + "���� ä���� û���Ͽ����ϴ�.");
			}
		}
		return false;
	}
}