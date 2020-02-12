package com.mirsv.function.list.Cokes;

import com.mirsv.util.users.User.Channel;
import com.mirsv.util.users.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
	
	private boolean chatEnabled = true;

	public GlobalMute() {
		super("전체뮤트", "1.0.1", "mirsv.admin 권한 또는 OP 권한이 없는 유저 모두", "전체 채팅을 칠 수 없도록 설정합니다.");
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		if (!chatEnabled) {
			Player p = e.getPlayer();
			if (p.hasPermission("mirsv.admin") || p.isOp() || UserManager.getUser(p).getChatChannel() != Channel.GLOBAL_CHAT) return;
			p.sendMessage(ChatColor.RED + "지금 전체 채팅을 사용할 수 없습니다.");
			e.setCancelled(true);
		}
	}

	private static final String prefix = ChatColor.translateAlternateColorCodes('&', "&c공지 &f| ");

	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
		if (!(sender instanceof Player)) {
			this.chatEnabled = !chatEnabled;
			if (chatEnabled) {
				Bukkit.broadcastMessage(prefix + "지금부터 전체 채팅을 사용할 수 있습니다.");
			} else {
				Bukkit.broadcastMessage(prefix + "지금부터 전체 채팅을 사용할 수 없습니다.");
			}
		} else {
			Player p = (Player) sender;

			if (!p.hasPermission("mirsv.admin")) {
				p.sendMessage(Messager.getPrefix()+"§c권한이 부족합니다.");
			} else {
				this.chatEnabled = !chatEnabled;
				if (chatEnabled) {
					Bukkit.broadcastMessage(prefix + "지금부터 전체 채팅을 사용할 수 있습니다.");
				} else {
					Bukkit.broadcastMessage(prefix + "지금부터 전체 채팅을 사용할 수 없습니다.");
				}
			}
		}
		return true;
	}

}