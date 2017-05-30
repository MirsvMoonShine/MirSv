package com.mirsv.moonshine.BroadCast;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.mirsv.MirPlugin;

public class BroadCast extends MirPlugin implements CommandExecutor,Listener {
	public ArrayList < String > BCadmins = new ArrayList < String > ();

	public BroadCast() {
		if (getConfig().getString("BroadCast.Prefix") == null)
			getConfig().set("BroadCast.Prefix", "&6[&4공지&6]");
		if (getConfig().getString("BroadCast.ChatColor") == null)
			getConfig().set("BroadCast.ChatColor", "&a");
		saveConfig();

		getCommand("bc", this);
		getListener(this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args) {
		if (getConfig().getBoolean("enable.BroadCast", true)) {
			if ((sender instanceof Player)) {
				Player p = (Player) sender;
				if (p.hasPermission("mirsv.admin") || p.isOp()) {
					if (!BCadmins.contains(p.getName())) {
						BCadmins.add(p.getName());
						p.sendMessage("공지채팅이 활성화되었습니다.");
					} else {
						BCadmins.remove(p.getName());
						p.sendMessage("공지채팅이 비활성화되었습니다.");
					}
				}
			}
		}
		return false;
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		if (getConfig().getBoolean("enable.BroadCast", true)) {
			Player player = e.getPlayer();
			if (BCadmins.contains(player.getName())) {
				e.setCancelled(true);
				String Prefix = getConfig().getString("BroadCast.Prefix").replaceAll("&", "§");
				String ChatColor = getConfig().getString("BroadCast.ChatColor").replaceAll("&", "§");
				String rawmsg = e.getMessage();
				Bukkit.broadcastMessage(Prefix + " " + ChatColor + rawmsg);
			}
		}
	}
}