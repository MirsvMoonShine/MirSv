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
		super("��ü��Ʈ", "1.0", "mirsv.admin ���� �Ǵ� OP ������ ���� ���� ���", "ä���� ĥ �� ������ �����մϴ�.");
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
				Bukkit.broadcastMessage(Messager.getPrefix()+"��a��ü ��Ʈ.");
			} else {
				chat = true;
				Bukkit.broadcastMessage(Messager.getPrefix()+"��a��ü ��Ʈ ����.");
			}
		} else {
			Player p = (Player) sender;

			if (!p.hasPermission("mirsv.admin")) {
				p.sendMessage(Messager.getPrefix()+"��c������ �����մϴ�.");
			} else {
				if (chat) {
					chat = false;
					Bukkit.broadcastMessage(Messager.getPrefix()+"��a��ü ��Ʈ.");
				} else {
					chat = true;
					Bukkit.broadcastMessage(Messager.getPrefix()+"��a��ü ��Ʈ ����.");
				}
			}
		}
		return true;
	}

}