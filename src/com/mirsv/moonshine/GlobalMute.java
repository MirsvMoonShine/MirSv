package com.mirsv.moonshine;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.mirsv.MirPlugin;

public class GlobalMute extends MirPlugin implements Listener, CommandExecutor {
	public static boolean chat = true;

	public GlobalMute() {
		getCommand("gmute", this);
		getListener(this);
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		if ((chat == false) && (getConfig().getBoolean("enable.GlobalMute", true))) {
			if (e.getPlayer().hasPermission("mirsv.admin")) {
				return;
			}
			e.setCancelled(true);
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
		if (config.getBoolean("enable.GlobalMute", true)) {
			if (!(sender instanceof Player)) {
				if (chat) {
					chat = false;
					Bukkit.broadcastMessage("§a[미르서버] 전체 뮤트.");
				} else {
					chat = true;
					Bukkit.broadcastMessage("§a[미르서버] 전체 뮤트 해제.");
				}
			} else {
				Player p = (Player) sender;

				if (!p.hasPermission("mirsv.admin")) {
					p.sendMessage("§a[미르서버] §c펄미션 미 보유");
				} else {
					if (chat) {
						chat = false;
						Bukkit.broadcastMessage("§a[미르서버] 전체 뮤트.");
					} else {
						chat = true;
						Bukkit.broadcastMessage("§a[미르서버] 전체 뮤트 해제.");
					}
				}
			}
		}
		return true;
	}
}