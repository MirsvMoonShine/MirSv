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
  String prefix = ChatColor.GOLD + "[" + ChatColor.GREEN + "�̸�����" + ChatColor.GOLD + "] " + ChatColor.RESET;
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if ((getConfig().getBoolean("enable.WhistleChat")) && ((sender instanceof Player))) {
      Player player = (Player)sender;
      if (args.length == 0) {
        if (Target.containsKey(player.getName())) {
          Target.remove(player.getName());
          player.sendMessage(prefix + ChatColor.AQUA + "�ӼӸ� ä���� �����մϴ�.");
        } else {
          player.sendMessage(ChatColor.RED + "��� ���  |  /wc [�г���] : [�г���]�� �ڵ����� �ӼӸ� ä��  |  /wc : �ӼӸ� ä�� ����");
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
          player.sendMessage(prefix + ChatColor.RED + "�������� �ʴ� �÷��̾��Դϴ�!");
          return false;
        }
        if (Target.containsKey(player.getName())) Target.remove(player.getName());
        Target.put(player.getName(), args[0]);
        player.sendMessage(prefix + ChatColor.AQUA + args[0] + "�԰� �ڵ����� �ӼӸ� ä���� �մϴ�.");
      }
    }
    return false;
  }
  
  @EventHandler
  public void onChat(AsyncPlayerChatEvent event) { if (Target.containsKey(event.getPlayer().getName())) {
      event.setCancelled(true);
      Bukkit.getPlayer(Target.get(event.getPlayer().getName())).sendMessage(ChatColor.GOLD + "[" + ChatColor.WHITE + event.getPlayer().getName() + ChatColor.GOLD + " -> " + ChatColor.RED + "��" + ChatColor.GOLD + "]" + ChatColor.WHITE + " " + event.getMessage());
      event.getPlayer().sendMessage(ChatColor.GOLD + "[" + ChatColor.RED + "��" + ChatColor.GOLD + " -> " + ChatColor.WHITE + Target.get(event.getPlayer().getName()) + ChatColor.GOLD + "]" + ChatColor.WHITE + " " + event.getMessage());
  	}
  }
}
