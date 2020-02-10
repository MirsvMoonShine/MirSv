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
		super("호출", "1.0", "플레이어를 호출합니다.");
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
			if (!(sender instanceof Player)) Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "사용 방법  |  /cl [닉네임1] [닉네임2] ... : [닉네임]을 호출");
			else
				sender.sendMessage(ChatColor.RED + "사용 방법  |  /cl [닉네임1] [닉네임2] ... : [닉네임]을 호출");
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
								if (!CallMyself) sender.sendMessage(Messager.getPrefix() + ChatColor.DARK_RED + "자기 자신은 호출할 수 없습니다!");
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
							p.playSound(p.getLocation(), Sound.BLOCK_NOTE_HARP, 10, 1);
							
							p.sendTitle(
									ChatColor.translateAlternateColorCodes('&', "&e" + sender.getName() + "&f님이 당신을 호출하셨습니다."),
									ChatColor.translateAlternateColorCodes('&', "&c원하지 않는 호출이 계속될 경우 신고해주세요."),
									10, 65, 10);
							p.sendMessage(Messager.getPrefix() + ChatColor.translateAlternateColorCodes('&', "&e" + sender.getName() + "&f님이 당신을 호출하셨습니다."));
							p.sendMessage(Messager.getPrefix() + ChatColor.RED + "원하지 않는 호출이 계속될 경우 신고해주세요.");
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
					sender.sendMessage(Messager.getPrefix() + ChatColor.GREEN + List + "님을 호출하였습니다!");
				}
				if (AbsentList.size() > 0) {
					String List = "";
					for (int i = 0; i < AbsentList.size(); i++) {
						List = List + (String) AbsentList.get(i);
						if (i >= AbsentList.size() - 1) continue;
						List = List + ", ";
					}
					sender.sendMessage(Messager.getPrefix() + ChatColor.RED + List + "님은 서버에 존재하지 않습니다!");
				}
			} else {
				sender.sendMessage(Messager.getPrefix() + ChatColor.RED + "잠시 후에 시도해주세요.");
			}
		}
		return false;
	}
}