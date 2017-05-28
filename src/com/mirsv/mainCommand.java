package com.mirsv;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

public class mainCommand
  implements CommandExecutor
{
  private final Mirsv plugin;
  PluginDescriptionFile profile;

  public mainCommand(Mirsv p)
  {
    this.plugin = p;
    this.profile = this.plugin.getDescription();
  }

  public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
    if ((sender instanceof Player)) {
      Player p = (Player)sender;
      if (p.hasPermission("mirsv.admin")) {
        if (args.length == 0) {
          String[] plu = (String[])this.plugin.plugins.toArray(new String[this.plugin.plugins.size()]);
          String plugin = "";
          if (plu.length > 0) {
            plugin = plu[0];
            for (int i = 1; i < plu.length; i++) {
              plugin += ChatColor.BLUE+", " + plu[i];
            }
          }
          p.sendMessage(ChatColor.BLUE + "�̸����� ���� �÷�����");
          p.sendMessage(ChatColor.BLUE + "����: " + this.profile.getVersion());
          p.sendMessage(ChatColor.BLUE + "�������� �÷�����: " + plugin);
          p.sendMessage(ChatColor.BLUE + "������: ___MoonShine___, CatNote");
          p.sendMessage(ChatColor.BLUE + "---��ɾ�---");
          p.sendMessage(ChatColor.BLUE + "/mirsv enable <plugin> - �÷������� Ȱ��ȭ�մϴ�.");
          p.sendMessage(ChatColor.BLUE + "/mirsv disable <plugin> - �÷������� ��Ȱ��ȭ�մϴ�.");
          p.sendMessage(ChatColor.BLUE + "<plugin>�� �� �������� �÷����� �̸��� ������ϸ� ��ҹ��ڸ� �����մϴ�.");
        }
        else if (args.length > 0) {
          if (args[0].equalsIgnoreCase("disable")) {
            if ((args.length == 2) && (this.plugin.plugins.contains(args[1])) && 
              (this.plugin.getConfig().getBoolean(args[1], true))) {
            	this.plugin.plugins.set(this.plugin.plugins.indexOf(ChatColor.GREEN+args[1]), ChatColor.RED+args[1]);
            	this.plugin.getConfig().set(args[1], false);
            	this.plugin.saveConfig();
            	p.sendMessage(ChatColor.BLUE + "[�̸�����] " + args[1] + " �÷������� ��Ȱ��ȭ�߽��ϴ�.");
            }
          }
          else if ((args[0].equalsIgnoreCase("enable")) && 
            (args.length == 2) && (this.plugin.plugins.contains(ChatColor.RED+args[1])) && 
            (!this.plugin.getConfig().getBoolean(args[1], false))) {
            this.plugin.plugins.set(this.plugin.plugins.indexOf(ChatColor.RED+args[1]), ChatColor.GREEN+args[1]);
            this.plugin.getConfig().set(args[1], Boolean.valueOf(true));
            this.plugin.saveConfig();
            p.sendMessage(ChatColor.BLUE + "[�̸�����] " + args[1] + " �÷������� Ȱ��ȭ�߽��ϴ�.");
          }
          else if (args[0].equalsIgnoreCase("update")){
        	  try {
      			HttpURLConnection con = (HttpURLConnection)new URL("http://www.mirsv.com/mirsvplugin/version.txt").openConnection();
      			String lastver = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
      			String version = plugin.getDescription().getVersion();
      			if (!lastver.equals(version)){
    				p.sendMessage(ChatColor.BLUE + "[�̸�����] ���� �÷����� �ֽŹ��� �߰� (������: "+version+",�ֽŹ���: "+lastver+")");
    				p.sendMessage(ChatColor.BLUE + "[�̸�����] �ٷ� �ٿ�ޱ�: http://www.mirsv.com/mirsvplugin/Mirsv.jar");
    			} else {
    				p.sendMessage(ChatColor.BLUE + "[�̸�����] ���� �ֽŹ����� �����ϰ� �ֽ��ϴ�.");
    			}
      		} catch (Exception e) {
      			p.sendMessage(ChatColor.BLUE + "[�̸�����] �ֽŹ��� üũ ����.");
      		}
          }
        }
      }

    }

    return false;
  }
}