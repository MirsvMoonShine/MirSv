package com.mirsv.function.list.Cokes;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.mirsv.Mirsv;
import com.mirsv.function.AbstractFunction;
import com.mirsv.util.Messager;

public class BroadcastChat extends AbstractFunction implements CommandExecutor, Listener {

	@Override
	protected void onEnable() {
		registerCommand("bc", this);
		registerListener(this);
	}

	@Override
	protected void onDisable() {}
	
	ArrayList<UUID> BCadmins = new ArrayList<UUID>();

	public BroadcastChat() {
		super("공지채팅", "1.0", "보다 편리하게 플레이어들에게 공지를 전달할 수 있습니다.");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args) {
		if ((sender instanceof Player)) {
			Player p = (Player) sender;
			if (p.hasPermission("mirsv.admin") || p.isOp()) {
				if (!BCadmins.contains(p.getUniqueId())) {
					BCadmins.add(p.getUniqueId());
					p.sendMessage(Messager.getPrefix()+"공지채팅이 활성화되었습니다.");
				} else {
					BCadmins.remove(p.getUniqueId());
					p.sendMessage(Messager.getPrefix()+"공지채팅이 비활성화되었습니다.");
				}
			}
		}
		return false;
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		Player player = e.getPlayer();
		if (BCadmins.contains(player.getUniqueId())) {
			e.setCancelled(true);
			Messager.sendMessage(e.getMessage());
			
			Messager.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&c[&6공지&c] &f" + e.getMessage()));
			for(Player p : Bukkit.getServer().getOnlinePlayers()) {
				p.sendTitle(ChatColor.translateAlternateColorCodes('&', "&c[&6공지&c]"), ChatColor.translateAlternateColorCodes('&', e.getMessage()), 20, 60, 20);
				p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 10);
			}
		}
	}

}