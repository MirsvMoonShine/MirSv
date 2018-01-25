package com.mirsv.moonshine;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.mirsv.MirPlugin;

public class DisableCommand extends MirPlugin implements Listener {
	String prefix = ChatColor.GOLD + "[" + ChatColor.GREEN + "미르서버" + ChatColor.GOLD + "] " + ChatColor.RESET;
	List<String> discommand;
	
	public DisableCommand() {
		getListener(this);
		
		discommand = getConfig().getStringList("DisableCommand.Commands");
		if (discommand.isEmpty()){
			discommand.add("mirsv");
			getConfig().set("DisableCommand.Commands", discommand);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCommand(PlayerCommandPreprocessEvent event){
		String[] Array = event.getMessage().substring(1).split(" ");
		Player player = event.getPlayer();
		if (!player.isOp()){
			for (int a = 0; a < discommand.size(); a++){
				String loaded = discommand.get(a);
				String[] command = loaded.split(" ");
				if (Array.length >= command.length){
					int control = command.length;
					for (int b=0;  b < command.length; b++){
						if (Array[b].equalsIgnoreCase(command[b])) control -= 1;
					}
					
					if (control == 0){
						event.setCancelled(true);
						player.sendMessage(prefix+"명령어를 사용할 수 없습니다.");
					}
				}
			}
		}
	}
}
