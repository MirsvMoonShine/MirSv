package com.mirsv.catnote.WhistleChat;

import com.mirsv.MirPlugin;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class WhistleChat extends MirPlugin implements Listener, CommandExecutor
{
  public WhistleChat(String pluginname)
  {
    super(pluginname);
    getCommand("wc", this);
    getListener(this);
  }
  
  HashMap<String, String> Target = new HashMap<String, String>();
  String prefix = ChatColor.GOLD + "[" + ChatColor.GREEN + "미르서버" + ChatColor.GOLD + "] " + ChatColor.RESET;
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if ((getConfig().getBoolean("enable.WhistleChat")) && ((sender instanceof Player))) {
      Player player = (Player)sender;
      if (args.length == 0) {
        if (Target.containsKey(player.getName())) {
          Target.remove(player.getName());
          player.sendMessage(prefix + ChatColor.AQUA + "귓속말 채팅을 종료합니다.");
        } else {
          player.sendMessage(ChatColor.RED + "사용 방법  |  /wc [닉네임] : [닉네임]과 자동으로 귓속말 채팅  |  /wc : 귓속말 채팅 끄기");
        }
      } else {
        boolean isExist = false;
        for (Player p : org.bukkit.Bukkit.getOnlinePlayers()) {
          if (args[0].equalsIgnoreCase(p.getName())) {
            isExist = true;
            break;
          }
        }
        if (!isExist) {
          player.sendMessage(prefix + ChatColor.RED + "존재하지 않는 플레이어입니다!");
          return false;
        }
        if (Target.containsKey(player.getName())) Target.remove(player.getName());
        Target.put(player.getName(), args[0]);
        player.sendMessage(prefix + ChatColor.AQUA + args[0] + "님과 자동으로 귓속말 채팅을 합니다.");
      }
    }
    return false;
  }
  
  @EventHandler
  public void onChat(AsyncPlayerChatEvent event) { if (Target.containsKey(event.getPlayer().getName())) {
      event.setCancelled(true);
      Bukkit.getPlayer(Target.get(event.getPlayer().getName())).sendMessage(ChatColor.GOLD + "[" + ChatColor.WHITE + event.getPlayer().getName() + ChatColor.GOLD + " -> " + ChatColor.RED + "나" + ChatColor.GOLD + "]" + ChatColor.WHITE + " " + event.getMessage());
      event.getPlayer().sendMessage(ChatColor.GOLD + "[" + ChatColor.RED + "나" + ChatColor.GOLD + " -> " + ChatColor.WHITE + Target.get(event.getPlayer().getName()) + ChatColor.GOLD + "]" + ChatColor.WHITE + " " + event.getMessage());
  	}
  }
}
