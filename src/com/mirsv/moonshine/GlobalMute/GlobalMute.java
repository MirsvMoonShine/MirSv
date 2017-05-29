package com.mirsv.moonshine.GlobalMute;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.mirsv.MirPlugin;

public class GlobalMute extends MirPlugin implements Listener{

	public GlobalMute(String pluginname) {
		super(pluginname);

		getCommand("gmute", new MuteCommand(getConfig()));
		getListener(this);
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		if ((!MuteCommand.chat) && (getConfig().getBoolean("enable.GlobalMute", true))) {
			if (e.getPlayer().hasPermission("mirsv.admin")) {
				return;
			}
			e.setCancelled(true);
		}
	}
}