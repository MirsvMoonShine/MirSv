package com.mirsv.function.list.CatNote;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mirsv.function.AbstractFunction;
import com.mirsv.util.Messager;

public class CallPlayer extends AbstractFunction implements CommandExecutor {

	public CallPlayer() {
		super("ȣ��", "1.0", "�÷��̾ ȣ���մϴ�.");
	}

	@Override
	protected void onEnable() {
		registerCommand("cl", this);
	}

	@Override
	protected void onDisable() {}

	private HashMap<String, Instant> coolMap = new HashMap<String, Instant>();
	
	private Instant getCool(CommandSender sender) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			return coolMap.get(p.getUniqueId().toString());
		} else {
			return Instant.EPOCH;
		}
	}
	
	private void setCool(CommandSender sender, Instant now) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			coolMap.put(p.getUniqueId().toString(), now);
		}
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			if (!(sender instanceof Player)) Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "��� ���  |  /cl [�г���1] [�г���2] ... : [�г���]�� ȣ��");
			else
				sender.sendMessage(ChatColor.RED + "��� ���  |  /cl [�г���1] [�г���2] ... : [�г���]�� ȣ��");
		} else {
			Instant cool = getCool(sender);
			Instant now = Instant.now();
			if(cool == null || Duration.between(cool, now).toMillis() >= 60000) {
				setCool(sender, now);
				ArrayList<String> CallList = new ArrayList<String>();
				ArrayList<String> AbsentList = new ArrayList<String>();
				boolean CallMyself = false;
				for (String s: args) {
					boolean isOnline = false;
					boolean isOverlapped;
					for (Player p: Bukkit.getOnlinePlayers()) {
						if (s.equalsIgnoreCase(p.getName())) {
							isOnline = true;
							if (sender.getName().equalsIgnoreCase(s)) {
								if (!CallMyself) sender.sendMessage(Messager.getPrefix() + ChatColor.DARK_RED + "�ڱ� �ڽ��� ȣ���� �� �����ϴ�!");
								CallMyself = true;
								break;
							}
							isOverlapped = false;
							for (String string: CallList) {
								if (string.equalsIgnoreCase(s)) {
									isOverlapped = true;
									break;
								}
							}
							if (isOverlapped) {
								continue;
							}
							CallList.add(s);
							p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 10, 1);
							
							p.sendTitle(
									ChatColor.translateAlternateColorCodes('&', "&e" + sender.getName() + "&f���� ����� ȣ���ϼ̽��ϴ�."),
									ChatColor.translateAlternateColorCodes('&', "&c������ �ʴ� ȣ���� ��ӵ� ��� �Ű����ּ���."),
									10, 65, 10);
							p.sendMessage(Messager.getPrefix() + ChatColor.translateAlternateColorCodes('&', "&e" + sender.getName() + "&f���� ����� ȣ���ϼ̽��ϴ�."));
							p.sendMessage(Messager.getPrefix() + ChatColor.RED + "������ �ʴ� ȣ���� ��ӵ� ��� �Ű����ּ���.");
							break;
						}
					}
					if (!isOnline) {
						boolean isOverlapped1 = false;
						for (String string: AbsentList) {
							if (string.equalsIgnoreCase(s)) {
								isOverlapped1 = true;
								break;
							}
						}
						if (isOverlapped1) continue;
						AbsentList.add(s);
					}
				}
				if (CallList.size() > 0) {
					String List = "";
					for (int i = 0; i < CallList.size(); i++) {
						List = List + (String) CallList.get(i);
						if (i >= CallList.size() - 1) continue;
						List = List + ", ";
					}
					sender.sendMessage(Messager.getPrefix() + ChatColor.GREEN + List + "���� ȣ���Ͽ����ϴ�!");
				}
				if (AbsentList.size() > 0) {
					String List = "";
					for (int i = 0; i < AbsentList.size(); i++) {
						List = List + (String) AbsentList.get(i);
						if (i >= AbsentList.size() - 1) continue;
						List = List + ", ";
					}
					sender.sendMessage(Messager.getPrefix() + ChatColor.RED + List + "���� ������ �������� �ʽ��ϴ�!");
				}
			} else {
				sender.sendMessage(Messager.getPrefix() + ChatColor.RED + "��� �Ŀ� �õ����ּ���.");
			}
		}
		return false;
	}

}