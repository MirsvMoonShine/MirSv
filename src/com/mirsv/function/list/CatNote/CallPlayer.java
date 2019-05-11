package com.mirsv.function.list.CatNote;

import org.bukkit.command.CommandExecutor;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mirsv.function.AbstractFunction;

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

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String prefix = ChatColor.GOLD + "[" + ChatColor.GREEN + "미르서버" + ChatColor.GOLD + "] " + ChatColor.RESET;
		Player player = null;
		if ((sender instanceof Player)) player = (Player) sender;
		if (args.length == 0) {
			if (!(sender instanceof Player)) Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "사용 방법  |  /cl [닉네임1] [닉네임2] ... : [닉네임]을 호출");
			else
				player.sendMessage(ChatColor.RED + "사용 방법  |  /cl [닉네임1] [닉네임2] ... : [닉네임]을 호출");
		} else {
			ArrayList < String > CallList = new ArrayList < String > ();
			ArrayList < String > AbsentList = new ArrayList < String > ();
			boolean CallMyself = false;
			for (String s: args) {
				boolean isOnline = false;
				boolean isOverlapped;
				for (Player p: Bukkit.getOnlinePlayers()) {
					if (s.equalsIgnoreCase(p.getName())) {
						isOnline = true;
						if (((sender instanceof Player)) && (player.getName().equalsIgnoreCase(s))) {
							if (!CallMyself) player.sendMessage(prefix + ChatColor.DARK_RED + "자기 자신은 호출할 수 없습니다!");
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
						p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 10.0F, 1.0F);
						if (!(sender instanceof Player)) {
							String Message = "Console이 당신을 호출하셨습니다!";
							String Command = "title " + p.getName() + " title {\"text\":\"" + Message + "\"}";
							Bukkit.getServer().dispatchCommand((CommandSender) Bukkit.getServer().getConsoleSender(), Command);
							p.sendMessage(prefix + "Console이 당신을 호출하셨습니다!");
						} else {
							String Message = player.getName() + "님이 당신을 호출하셨습니다!";
							String Command = "title " + p.getName() + " title {\"text\":\"" + Message + "\"}";
							Bukkit.getServer().dispatchCommand((CommandSender) Bukkit.getServer().getConsoleSender(), Command);
							p.sendMessage(prefix + player.getName() + "님이 당신을 호출하셨습니다!");
						}
						String Message = "누군가가 이 명령어로 도배를 하면 신고해주세요!";
						String Command = "title " + p.getName() + " subtitle {\"text\":\"" + Message + "\", \"color\":\"red\"}";
						Bukkit.getServer().dispatchCommand((CommandSender) Bukkit.getServer().getConsoleSender(), Command);
						p.sendMessage(prefix + ChatColor.RED + "누군가가 이 명령어로 도배를 하면 신고해주세요!");
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
				if (!(sender instanceof Player)) Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.GREEN + List + "님을 호출하였습니다!");
				else
					player.sendMessage(prefix + ChatColor.GREEN + List + "님을 호출하였습니다!");
			}
			if (AbsentList.size() > 0) {
				String List = "";
				for (int i = 0; i < AbsentList.size(); i++) {
					List = List + (String) AbsentList.get(i);
					if (i >= AbsentList.size() - 1) continue;
					List = List + ", ";
				}
				if (!(sender instanceof Player)) Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.RED + List + "님은 서버에 존재하지 않습니다!");
				else
					player.sendMessage(prefix + ChatColor.RED + List + "님은 서버에 존재하지 않습니다!");
			}
		}
		return false;
	}

}