package com.mirsv.function.list.daybreak;

import com.mirsv.Mirsv;
import com.mirsv.function.AbstractFunction;
import com.mirsv.util.BungeeUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Iterator;

public class BungeeManager extends AbstractFunction implements CommandExecutor {

	public BungeeManager() {
		super("번지 매니저", "1.0.0");
	}

	@Override
	protected void onEnable() {
		registerCommand("bungeejump", this);
	}

	@Override
	protected void onDisable() {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.isOp()) {
			if (args.length >= 2) {
				if (args[0].equalsIgnoreCase("@a")) {
					Iterator<? extends Player> iterator = new ArrayList<>(Bukkit.getOnlinePlayers()).iterator();
					new BukkitRunnable() {
						@Override
						public void run() {
							if (iterator.hasNext()) {
								Player player = iterator.next();
								if (player.isOnline()) {
									BungeeUtil.connect(player, args[1]);
								}
							} else {
								cancel();
							}
						}
					}.runTaskTimer(Mirsv.getPlugin(), 0, 20L);
					sender.sendMessage(ChatColor.BLUE + "모든 플레이어" + ChatColor.WHITE + "를 " + ChatColor.GREEN + args[1] + ChatColor.WHITE + " 서버로 보냅니다. (약 " + Bukkit.getOnlinePlayers().size() + "초 소요됨)");
				} else {
					Player target = Bukkit.getPlayerExact(args[0]);
					if (target != null) {
						BungeeUtil.connect(target, args[1]);
						sender.sendMessage(ChatColor.BLUE + target.getName() + ChatColor.WHITE + "님을 " + ChatColor.GREEN + args[1] + ChatColor.WHITE + " 서버로 보냅니다.");
					} else {
						sender.sendMessage(ChatColor.RED + "존재하지 않는 플레이어: " + ChatColor.WHITE + args[0]);
					}
				}
			} else {
				sender.sendMessage(ChatColor.RED + "사용법 " + ChatColor.WHITE + "| /bungeejump <대상/@a> <서버>");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "권한이 부족합니다.");
		}
		return true;
	}
}
