package com.mirsv.catnote.controlmessage;

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
import org.bukkit.event.player.PlayerQuitEvent;

import com.mirsv.MirPlugin;

public class ControlMessage extends MirPlugin implements Listener, CommandExecutor {
	
	public ControlMessage() {
		getCommand("dm", this);
		getListener(this);
	}

	List < String > messagesOff = new ArrayList < String > ();
	String prefix = ChatColor.GOLD + "[" + ChatColor.GREEN + "미르서버" + ChatColor.GOLD + "] " + ChatColor.RESET;

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if ((getConfig().getBoolean("enable.ControlDeathMessage")) && ((sender instanceof Player))) {
			Player player = (Player) sender;
			String name = player.getName();
			if (this.messagesOff.contains(name)) {
				this.messagesOff.remove(name);
				player.sendMessage(this.prefix + ChatColor.AQUA + "이제 죽을 때 메시지가 보입니다.");
			} else {
				this.messagesOff.add(name);
				player.sendMessage(this.prefix + ChatColor.AQUA + "이제 죽을 때 메시지가 보이지 않습니다.");
			}
		}

		return false;
	}
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		String message = event.getDeathMessage();
		event.setDeathMessage(null);
		for (Player p: Bukkit.getOnlinePlayers()) {
			if (this.messagesOff.indexOf(p.getName()) != -1) continue;
			p.sendMessage(message);
		}
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		if (this.messagesOff.remove(event.getPlayer().getName())) this.messagesOff.remove(event.getPlayer().getName());
	}

}