package com.mirsv.function.list.CatNote;

import java.util.HashMap;
import java.util.UUID;

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

import com.mirsv.function.AbstractFunction;
import com.mirsv.util.Messager;

public class WhisperChat extends AbstractFunction implements Listener, CommandExecutor {
	
	@Override
	protected void onEnable() {
		registerCommand("wc", this);
		registerListener(this);
	}
	
	@Override
	protected void onDisable() {}
	
	public WhisperChat() {
		super("�ӼӸ�ä��", "1.0", "�ӼӸ� ����� �������� ä���� ġ�� �� ������", "�ӼӸ��� ���� �� �ֽ��ϴ�.");
	}
	
	HashMap < UUID, UUID > Target = new HashMap < UUID, UUID > ();
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		if(!Target.containsKey(event.getPlayer().getUniqueId())) return;
		String[] s = event.getMessage().split(" ");
		if(s[0].equalsIgnoreCase("/tc") || s[0].equalsIgnoreCase("/nc") || s[0].equalsIgnoreCase("/lc") || s[0].equalsIgnoreCase("/pc") || s[0].equalsIgnoreCase("/g") || s[0].equalsIgnoreCase("/admin") || s[0].equalsIgnoreCase("/mod") || s[0].equalsIgnoreCase("/a") || s[0].equalsIgnoreCase("/m") || s[0].equalsIgnoreCase("/l") || s[0].equalsIgnoreCase("/mst")) {
			event.getPlayer().sendMessage(Messager.getPrefix() + ChatColor.WHITE + Bukkit.getOfflinePlayer(Target.get(event.getPlayer().getUniqueId())).getName() + ChatColor.AQUA + "�Կ��� �ӼӸ��� ������ �ʽ��ϴ�.");			
			Target.remove(event.getPlayer().getUniqueId());
		}
		if(s.length > 1 && s[0].equalsIgnoreCase("/party") && s[1].equalsIgnoreCase("chat")) {
			event.getPlayer().sendMessage(Messager.getPrefix() + ChatColor.WHITE + Bukkit.getOfflinePlayer(Target.get(event.getPlayer().getUniqueId())).getName() + ChatColor.AQUA + "�Կ��� �ӼӸ��� ������ �ʽ��ϴ�.");			
			Target.remove(event.getPlayer().getUniqueId());
		}
	}
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(args.length == 0) {
				if(Target.containsKey(player.getUniqueId())) {
					player.sendMessage(Messager.getPrefix() + ChatColor.WHITE + Bukkit.getOfflinePlayer(Target.get(player.getUniqueId())).getName() + ChatColor.AQUA + "�Կ��� �ӼӸ��� ������ �ʽ��ϴ�.");
					Target.remove(player.getUniqueId());
				}
				else {
					player.sendMessage(Messager.getPrefix() + ChatColor.AQUA + "��� ���: /wc <�г���>");
				}
			}
			else {
				if(!Bukkit.getOfflinePlayer(args[0]).isOnline()) {
					player.sendMessage(Messager.getPrefix() + ChatColor.RED + "�������� �ʴ� �÷��̾��Դϴ�.");
					return false;
				}
				if(player.getName().equalsIgnoreCase(args[0])) {
					player.sendMessage(Messager.getPrefix() +  ChatColor.RED + "�ڱ� �ڽŰ� �ӼӸ��� �� �����ϴ�.");
					return false;
				}
				if(Target.containsKey(player.getUniqueId())) Target.remove(player.getUniqueId());
				Target.put(player.getUniqueId(), Bukkit.getPlayer(args[0]).getUniqueId());
				player.sendMessage(Messager.getPrefix() + ChatColor.WHITE + args[0] + ChatColor.AQUA + "�Կ��� �ӼӸ��� �����ϴ�.");
			}
		}
		return false;
	}
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		if(Target.containsKey(event.getPlayer().getUniqueId())) {
			event.getRecipients().clear();
			event.setMessage(event.getMessage().replaceAll("%", "%%"));
			if(Bukkit.getOfflinePlayer(Target.get(event.getPlayer().getUniqueId())).isOnline()) {
				event.setFormat("[" + ChatColor.GOLD + "WC" + ChatColor.WHITE + "] " + event.getPlayer().getName() + ": " + ChatColor.GOLD + event.getMessage());
				event.getRecipients().add(Bukkit.getPlayer(event.getPlayer().getUniqueId()));
				event.getRecipients().add(Bukkit.getPlayer(Target.get(event.getPlayer().getUniqueId())));
			}
			else event.getPlayer().sendMessage(Messager.getPrefix() + ChatColor.RED + "�ӼӸ��� ���� ��밡 ���������Դϴ�. \'/g\'�� �Է����ּ���.");
		}
	}
}