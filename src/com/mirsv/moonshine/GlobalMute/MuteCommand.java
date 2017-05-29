package com.mirsv.moonshine.GlobalMute;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class MuteCommand implements CommandExecutor {
	public static boolean chat = true;
	private final FileConfiguration config;

	public MuteCommand(FileConfiguration con) {
		this.config = con;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
		if (config.getBoolean("enable.GlobalMute", true)) {
			if (!(sender instanceof Player)) {
				if (chat) {
					chat = false;
					Bukkit.broadcastMessage("��a[�̸�����] ��ü ��Ʈ.");
				} else {
					chat = true;
					Bukkit.broadcastMessage("��a[�̸�����] ��ü ��Ʈ ����.");
				}
			} else {
				Player p = (Player) sender;

				if (!p.hasPermission("mirsv.admin")) {
					p.sendMessage("��a[�̸�����] ��c�޹̼� �� ����");
					return true;
				}

				if (chat) {
					chat = false;
					Bukkit.broadcastMessage("��a[�̸�����] ��ü ��Ʈ.");
				} else {
					chat = true;
					Bukkit.broadcastMessage("��a[�̸�����] ��ü ��Ʈ ����.");
				}
			}
		}
		return true;
	}
}