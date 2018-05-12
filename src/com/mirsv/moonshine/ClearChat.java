package com.mirsv.moonshine;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mirsv.MirPlugin;

public class ClearChat extends MirPlugin implements CommandExecutor{
	String prefix = ChatColor.GOLD + "[" + ChatColor.GREEN + "�̸�����" + ChatColor.GOLD + "] " + ChatColor.RESET;
	
	public ClearChat() {
		getCommand("clearchat", this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
		if (((sender instanceof Player)) && (getConfig().getBoolean("enable.ClearChat", true))) {
			Player p = (Player) sender;

			if (p.hasPermission("mirsv.admin") || p.isOp()) {
				for(int i = 0; i < 100; i++) for(Player p:Bukkit.getServer().getOnlinePlayers()) p.sendMessage("");
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &e" + p.getName() + "&f���� ä���� û���ϼ̽��ϴ�!"));
			}
		}
		return false;
	}
}