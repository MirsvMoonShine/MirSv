package com.mirsv.moonshine.ClearChat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ClearChatCommand implements CommandExecutor{
	private final FileConfiguration plugin;

	public ClearChatCommand(FileConfiguration fileConfiguration){
		this.plugin = fileConfiguration;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
		if (((sender instanceof Player)) && (this.plugin.getBoolean("enable.ClearChat", true))) {
			Player p = (Player)sender;

			if (p.hasPermission("mirsv.admin")) {
				for (int i = 0; i < 60; i++) {
					Bukkit.broadcastMessage(" ");
				}
				Bukkit.broadcastMessage("��a[�̸�����] " + p.getName() + "���� ä���� û���Ͽ����ϴ�.");
			}
		}
		return false;
	}
}