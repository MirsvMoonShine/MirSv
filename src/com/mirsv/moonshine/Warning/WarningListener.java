package com.mirsv.moonshine.Warning;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class WarningListener
  implements Listener
{
  private final FileConfiguration plugin;
  Plugin pm = Bukkit.getPluginManager().getPlugin("Mirsv");

  public WarningListener(FileConfiguration fileConfiguration)
  {
    this.plugin = fileConfiguration;
  }
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent e) {
    if (this.plugin.getBoolean("enable.Warning", true)) {
      Player player = e.getPlayer();
      int warn = Warning.warning.getInt(player.getName());
      if (warn > 0)
        player.sendMessage(ChatColor.GREEN + "[미르서버] 당신의 경고 횟수: " + warn);
    	  if (Warning.loadboolean.getOrDefault(player.getName(),false)){
        	  warnCommand(player,warn);
        	  Warning.loadboolean.put(player.getName(), false);
          }
    }
  }
  
  public void warnCommand(Player player, int warn) {
	    List<String> command = Warning.getConfig().getStringList("Warning.warnCommand" + warn);
	    if (!command.isEmpty())
	    	for (String Command : command)
	    		if (Command != null){
	    			String result = Command.replace("[username]", player.getName());
	    			this.pm.getServer().getScheduler().runTask(pm, new Runnable()
	    		      {
						@Override
						public void run() {
							pm.getServer().dispatchCommand(pm.getServer().getConsoleSender(), result);
						}
	    		      });
	    		}
	  }
}