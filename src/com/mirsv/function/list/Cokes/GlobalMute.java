package com.mirsv.function.list.Cokes;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.mirsv.function.AbstractFunction;
import com.mirsv.util.Messager;

public class GlobalMute extends AbstractFunction implements Listener, CommandExecutor {

	@Override
	protected void onEnable() {
		registerCommand("globalmute", this);
		registerListener(this);
	}

	@Override
	protected void onDisable() {}
	
	boolean chat = true;

	public GlobalMute() {
		super("전체뮤트", "1.0", "mirsv.admin 권한 또는 OP 권한이 없는 유저 모두", "채팅을 칠 수 없도록 설정합니다.");
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		if (!chat) {
			if (e.getPlayer().hasPermission("mirsv.admin") || e.getPlayer().isOp()) {
				return;
			}
			e.setCancelled(true);
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
		if (!(sender instanceof Player)) {
			if (chat) {
				chat = false;
				Bukkit.broadcastMessage(Messager.getPrefix()+"§a전체 뮤트.");
			} else {
				chat = true;
				Bukkit.broadcastMessage(Messager.getPrefix()+"§a전체 뮤트 해제.");
			}
		} else {
			Player p = (Player) sender;

			if (!p.hasPermission("mirsv.admin")) {
				p.sendMessage(Messager.getPrefix()+"§c권한이 부족합니다.");
			} else {
				if (chat) {
					chat = false;
					Bukkit.broadcastMessage(Messager.getPrefix()+"§a전체 뮤트.");
				} else {
					chat = true;
					Bukkit.broadcastMessage(Messager.getPrefix()+"§a전체 뮤트 해제.");
				}
			}
		}
		return true;
	}

}