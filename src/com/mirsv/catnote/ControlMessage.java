package com.mirsv.catnote;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.mirsv.MirPlugin;

public class ControlMessage extends MirPlugin implements Listener, CommandExecutor {
	
	public ControlMessage() {
		getCommand("dm", this);
		getListener(this);
	}

	List < String > messagesOff = new ArrayList < String > ();
	String prefix = ChatColor.GOLD + "[" + ChatColor.GREEN + "�̸�����" + ChatColor.GOLD + "] " + ChatColor.RESET;

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if ((getConfig().getBoolean("enable.ControlDeathMessage")) && ((sender instanceof Player))) {
			Player player = (Player) sender;
			String name = player.getName();
			if (this.messagesOff.contains(name)) {
				this.messagesOff.remove(name);
				player.sendMessage(this.prefix + ChatColor.AQUA + "���� ���� �� �޽����� ���Դϴ�.");
			} else {
				this.messagesOff.add(name);
				player.sendMessage(this.prefix + ChatColor.AQUA + "���� ���� �� �޽����� ������ �ʽ��ϴ�.");
			}
		}

		return false;
	}
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		if(getConfig().getBoolean("enable.ControlDeathMessage", true)){
			String message = event.getDeathMessage();
			event.setDeathMessage(null);
			for (Player p: Bukkit.getOnlinePlayers()) {
				if (messagesOff.contains(p.getName())) continue;
				p.sendMessage(message);
			}
		}
	}
}