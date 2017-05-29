package com.mirsv.moonshine.BroadCast;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class BroadCastListener implements CommandExecutor,Listener {
	private final FileConfiguration p;

	public BroadCastListener(FileConfiguration ServerConfig) {
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
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		if (BroadCast.getConfig().getBoolean("enable.BroadCast", true)) {
			Player player = e.getPlayer();
			if (BroadCast.BCadmins.contains(player.getName())) {
				e.setCancelled(true);
				String Prefix = BroadCast.getConfig().getString("BroadCast.Prefix").replaceAll("&", "§");
				String ChatColor = BroadCast.getConfig().getString("BroadCast.ChatColor").replaceAll("&", "§");
				String rawmsg = e.getMessage();
				Bukkit.broadcastMessage(Prefix + " " + ChatColor + rawmsg);
			}
		}
	}
}