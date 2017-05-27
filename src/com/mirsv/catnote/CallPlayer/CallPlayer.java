package com.mirsv.catnote.CallPlayer;

import org.bukkit.command.CommandExecutor;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mirsv.MirPlugin;

public class CallPlayer extends MirPlugin implements CommandExecutor{
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	  {
	    if (getConfig().getBoolean("enable.CallPlayer")) {
	      Player player = null;
	      if ((sender instanceof Player)) player = (Player)sender;
	      if (args.length == 0) {
	        if (!(sender instanceof Player)) Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "��� ��� : /cl [�г���1] [�г���2] ..."); else
	          player.sendMessage(ChatColor.RED + "��� ��� : /cl [�г���1] [�г���2] ...");
	      }
	      else {
	        ArrayList<String> CallList = new ArrayList<String>();
	        ArrayList<String> AbsentList = new ArrayList<String>();
	        boolean CallMyself = false;
	        for (String s : args) {
	          boolean isOnline = false;
	          boolean isOverlapped;
	          for (Player p : Bukkit.getOnlinePlayers()) {
	            if (s.equalsIgnoreCase(p.getName())) {
	              isOnline = true;
	              if (((sender instanceof Player)) && (player.getName().equalsIgnoreCase(s))) {
	                if (!CallMyself) player.sendMessage(ChatColor.DARK_RED  + "�ڱ� �ڽ��� ȣ���� �� �����ϴ�!");
	                CallMyself = true;
	                break;
	              }
	              isOverlapped = false;
	              for (String string : CallList) {
	                if (string.equalsIgnoreCase(s)) {
	                  isOverlapped = true;
	                  break;
	                }
	              }
	              if (!isOverlapped) CallList.add(s);
	              p.playSound(p.getLocation(), Sound.BLOCK_NOTE_HARP, 10.0F, 1.0F);
	              if (!(sender instanceof Player)) { p.sendMessage(ChatColor.GRAY +  "Console" + ChatColor.RESET + "�� ����� ȣ���ϼ̽��ϴ�!"); break;
	              }
	              p.sendMessage(ChatColor.GOLD +  player.getName() + ChatColor.RESET + "���� ����� ȣ���ϼ̽��ϴ�!");
	              p.sendMessage(ChatColor.DARK_RED +  "�������� �� ��ɾ�� ���踦 �ϸ� �Ű����ּ���!");

	              break;
	            }
	          }
	          if (!isOnline) {
	            boolean isOverlapped1 = false;
	            for (String string : AbsentList) {
	              if (string.equalsIgnoreCase(s)) {
	                isOverlapped1 = true;
	                break;
	              }
	            }
	            if (isOverlapped1) continue; AbsentList.add(s);
	          }
	        }
	        if (CallList.size() > 0) {
	          String List = "";
	          for (int i = 0; i < CallList.size(); i++) {
	            List = List + (String)CallList.get(i);
	            if (i >= CallList.size() - 1) continue; List = List + ", ";
	          }
	          if (!(sender instanceof Player)) Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN +  List + "���� ȣ���Ͽ����ϴ�!"); else
	            player.sendMessage(ChatColor.GREEN +  List + "���� ȣ���Ͽ����ϴ�!");
	        }
	        if (AbsentList.size() > 0) {
	          String List = "";
	          for (int i = 0; i < AbsentList.size(); i++) {
	            List = List + (String)AbsentList.get(i);
	            if (i >= AbsentList.size() - 1) continue; List = List + ", ";
	          }
	          if (!(sender instanceof Player)) Bukkit.getConsoleSender().sendMessage(ChatColor.RED +  List + "���� ������ �������� �ʽ��ϴ�!"); else
	            player.sendMessage(ChatColor.RED +  List + "���� ������ �������� �ʽ��ϴ�!");
	        }
	      }
	    }
	    return false;
	  }

	public CallPlayer(String pluginname) {
		super(pluginname);
		getCommand("ȣ��", this);
	}

}
