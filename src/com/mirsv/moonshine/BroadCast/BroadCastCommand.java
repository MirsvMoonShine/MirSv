package com.mirsv.moonshine.BroadCast;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class BroadCastCommand implements CommandExecutor {
	private final FileConfiguration p;

	public BroadCastCommand(FileConfiguration ServerConfig) {
		this.p = ServerConfig;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args) {
		if (p.getBoolean("enable.BroadCast", true)) {
			if ((sender instanceof Player)) {
				Player p = (Player) sender;
				if (p.hasPermission("mirsv.admin") || p.isOp()) {
					if (!BroadCast.BCadmins.contains(p.getName())) {
						BroadCast.BCadmins.add(p.getName());
						p.sendMessage("공지채팅이 활성화되었습니다.");
					} else {
						BroadCast.BCadmins.remove(p.getName());
						p.sendMessage("공지채팅이 비활성화되었습니다.");
					}
				}
			}
		}
		return false;
	}
}