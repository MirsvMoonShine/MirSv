package com.mirsv.function.list.Cokes;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mirsv.function.AbstractFunction;
import com.mirsv.util.MirUser;
import com.mirsv.util.PlayerCollector;

import net.md_5.bungee.api.ChatColor;

public class NickName extends AbstractFunction implements CommandExecutor {

	public NickName() {
		super("닉네임관리", "1.0", "자신을 표현하는 닉네임을 바꿀 수 있다.");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length == 2) {
				if (Bukkit.getPlayer(args[0]) != null) {
					MirUser user = PlayerCollector.getMirUser(Bukkit.getPlayer(args[0]));
					if (args[1].equalsIgnoreCase("reset")) {
						user.setNickname(args[0]);
						player.sendMessage(args[0]+"님의 닉네임을 원래대로 돌려놓았습니다.");
						Bukkit.getPlayer(args[0]).sendMessage("당신의 닉네임이 원래대로 돌아왔습니다.");
					} else {
						user.setNickname(args[1]);
						player.sendMessage(args[0]+"님의 닉네임을 "+ChatColor.translateAlternateColorCodes('&', args[1])+"§f으로 설정하였습니다.");
						Bukkit.getPlayer(args[0]).sendMessage("당신의 닉네임이 "+ChatColor.translateAlternateColorCodes('&', args[1])+"§f가 되었습니다.");
					}
				}
			} else {
				player.sendMessage("§c사용법: /nickname [player] [reset|nickname]");
			}
		}
		return false;
	}

	@Override
	protected void onEnable() {
		registerCommand("nickname", this);
	}

	@Override
	protected void onDisable() {	
	}

}
