package com.mirsv.moonshine;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mirsv.MirPlugin;

public class ClearChat extends MirPlugin implements CommandExecutor{

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
				Bukkit.broadcastMessage("��a[�̸�����] " + p.getName() + "���� ä���� û���Ͽ����ϴ�.");
			}
		}
		return false;
	}
}