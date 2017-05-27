package com.mirsv.moonshine.GlobalMute;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class MuteListener
  implements Listener
{
  private final FileConfiguration plugin;

  public MuteListener(FileConfiguration con){
    this.plugin = con;
  }
  @EventHandler
  public void onChat(AsyncPlayerChatEvent e) {
    if ((!MuteCommand.chat) && (plugin.getBoolean("enable.GlobalMute", true))) {
      if (e.getPlayer().hasPermission("mirsv.admin")) {
        return;
      }
      e.setCancelled(true);
    }
  }
}