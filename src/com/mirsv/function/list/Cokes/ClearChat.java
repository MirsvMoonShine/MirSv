package com.mirsv.function.list.Cokes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mirsv.function.AbstractFunction;
import com.mirsv.util.Messager;

public class ClearChat extends AbstractFunction implements CommandExecutor{

	@Override
	protected void onEnable() {
		registerCommand("clearchat", this);
	}

	@Override
	protected void onDisable() {}
	
	public ClearChat() {
		super("ä��û��", "1.0", "ä���� �����ϰ� û���մϴ�.");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
		if (sender instanceof Player) {
			
			if (sender.hasPermission("mirsv.admin") || sender.isOp()) {
				for(int i = 0; i < 100; i++) for(Player p:Bukkit.getServer().getOnlinePlayers()) p.sendMessage("");
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Messager.getPrefix() + " &e" + sender.getName() + "&f���� ä���� û���ϼ̽��ϴ�!"));
			} else {
				for(int i = 0; i < 100; i++) sender.sendMessage("");
			}
		}
		return true;
	}

}