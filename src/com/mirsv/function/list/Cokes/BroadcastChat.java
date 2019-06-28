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
		super("����ä��", "1.0", "���� ���ϰ� �÷��̾�鿡�� ������ ������ �� �ֽ��ϴ�.");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args) {
		if ((sender instanceof Player)) {
			Player p = (Player) sender;
			if (p.hasPermission("mirsv.admin") || p.isOp()) {
				if (!BCadmins.contains(p.getUniqueId())) {
					BCadmins.add(p.getUniqueId());
					p.sendMessage(Messager.getPrefix()+"����ä���� Ȱ��ȭ�Ǿ����ϴ�.");
				} else {
					BCadmins.remove(p.getUniqueId());
					p.sendMessage(Messager.getPrefix()+"����ä���� ��Ȱ��ȭ�Ǿ����ϴ�.");
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
			
			Messager.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&c[&6����&c] &f" + e.getMessage()));
			for(Player p : Bukkit.getServer().getOnlinePlayers()) {
				p.sendTitle(ChatColor.translateAlternateColorCodes('&', "&c[&6����&c]"), ChatColor.translateAlternateColorCodes('&', e.getMessage()), 20, 60, 20);
				p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 10);
			}
		}
	}

}