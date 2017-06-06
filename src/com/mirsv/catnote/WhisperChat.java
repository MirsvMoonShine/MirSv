package com.mirsv.catnote;

import com.mirsv.MirPlugin;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class WhisperChat extends MirPlugin implements Listener, CommandExecutor {
	public WhisperChat() {
		getCommand("wc", this);
		getListener(this);
	}

	HashMap < String, String > Target = new HashMap < String, String > ();
	String prefix = ChatColor.GOLD + "[" + ChatColor.GREEN + "�̸�����" + ChatColor.GOLD + "] " + ChatColor.RESET;
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		String s = event.getMessage().substring(1);
		if(s.equalsIgnoreCase("tc") || s.equalsIgnoreCase("nc") || s.equalsIgnoreCase("lc") || s.equalsIgnoreCase("g")) {
			Target.remove(event.getPlayer().getName());
		}
	}
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if ((getConfig().getBoolean("enable.WhisperChat")) && ((sender instanceof Player))) {
			Player player = (Player) sender;
			if (args.length == 0) {
				if (Target.containsKey(player.getName())) {
					Target.remove(player.getName());
					player.sendMessage(prefix + ChatColor.AQUA + "�ӼӸ� ä���� �����մϴ�.");
				} else {
					player.sendMessage(ChatColor.GOLD + "[Towny] " + ChatColor.AQUA + "/wc [�г���] : [�г���]�� �ڵ����� �ӼӸ� ä��");
				}
			} else {
				boolean isExist = false;
				for (Player p: org.bukkit.Bukkit.getOnlinePlayers()) {
					if (args[0].equalsIgnoreCase(p.getName())) {
						isExist = true;
						break;
					}
				}
				if (!isExist) {
					player.sendMessage(ChatColor.GOLD + "[Towny] " + ChatColor.RED + "�������� �ʴ� �÷��̾��Դϴ�.");
					return false;
				}
				if(player.getName().equalsIgnoreCase(args[0])) {
					player.sendMessage(ChatColor.GOLD + "[Towny] " + ChatColor.RED + "�ڱ� �ڽŰ� �ӼӸ��� �� �����ϴ�.");
					return false;
				}
				if (Target.containsKey(player.getName())) Target.remove(player.getName());
				Target.put(player.getName(), args[0]);
				player.sendMessage(ChatColor.GOLD + "[Towny] " + ChatColor.DARK_GREEN + "��� ����: whisper");
				player.sendMessage(ChatColor.GOLD + "[Towny] " + ChatColor.DARK_GREEN + "[TownyChat] You are now talking in " + ChatColor.WHITE + "whisper" + ChatColor.DARK_GREEN + " with " + ChatColor.WHITE + args[0]);
			}
		}
		return false;
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		if (Target.containsKey(event.getPlayer().getName())) {
			event.setCancelled(true);
			Bukkit.getPlayer(Target.get(event.getPlayer().getName())).sendMessage("[" + ChatColor.DARK_AQUA + "WC" + ChatColor.WHITE + "] " + event.getPlayer().getName() + ": " + ChatColor.GOLD + event.getMessage());
			event.getPlayer().sendMessage("[" + ChatColor.DARK_AQUA + "WC" + ChatColor.WHITE + "] " + event.getPlayer().getName() + ": " + ChatColor.GOLD + event.getMessage());
		}
	}
}