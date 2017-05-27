package com.mirsv.moonshine.Prefix;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PrefixCommand
  implements CommandExecutor
{
	Plugin pm = Bukkit.getPluginManager().getPlugin("Mirsv");
	private final FileConfiguration p;
    public static FileConfiguration prefixList;
    public String cmdPrefix = Prefix.cmdPrefix;
    File prefixListFile = new File("plugins/" + this.pm.getDescription().getName() + "/Prefix/prefixList.yml");

  public PrefixCommand(FileConfiguration fileConfiguration) {
	  this.p = fileConfiguration;
  }

  public void sendMessage(Player player, String message) {
    player.sendMessage(cmdPrefix + message);
  }
  public void errorMessage(Player player, String message) {
    player.sendMessage(Prefix.errorPrefix + message);
  }

  @SuppressWarnings("deprecation")
public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
  {
	  File prefixListFile = new File("plugins/" + this.pm.getDescription().getName() + "/Prefix/prefixList.yml");
    Player player = (Player)sender;

    if (this.p.getBoolean("enable.Prefix", true)) {
      if (args.length > 0) {
        if (args[0].equalsIgnoreCase("help")) {
          sendMessage(player, "Prefix ��ɾ�");
          sendMessage(player, "/prefix set [index] - " + ChatColor.YELLOW + "[index]��ȣ�� Īȣ�� ����˴ϴ�.");
          sendMessage(player, "/prefix show - " + ChatColor.YELLOW + "�ڽ��� Īȣ ����� �����ݴϴ�.");
          if ((player.isOp()) || (player.hasPermission("easyprefix.help.other"))) {
            sendMessage(player, "/prefix set [playername] [index] - " + ChatColor.YELLOW + "[playernam]�� Īȣ�� [index]��ȣ�� ������ ����˴ϴ�.");
            sendMessage(player, "/prefix add [prefix] - " + ChatColor.YELLOW + "�������� ��� ������ Īȣ [prefix]�� �߰��մϴ�.");
            sendMessage(player, "/prefix add <playername> [prefix] - " + ChatColor.YELLOW + "[playername]�� Īȣ [prefix]�� �߰��մϴ�.");
            sendMessage(player, "/prefix show [playername] - " + ChatColor.YELLOW + "[playername]�� Īȣ ����� �����ݴϴ�.");
            sendMessage(player, "/prefix remove [playername] [index] - " + ChatColor.YELLOW + "[playername]�� Īȣ [index]�� ������ŵ�ϴ�.");
            sendMessage(player, "/prefix fix [playername] [index]  [prefix] - " + ChatColor.YELLOW + "[playername]�� [index]��ȣ Īȣ [prefix]�� �����մϴ�.");
            sendMessage(player, "/prefix reload - " + ChatColor.YELLOW + "prefixList.yml ������ �ٽ� �ҷ��ɴϴ�.");
            sendMessage(player, ChatColor.YELLOW + "add Ŀ�ǵ��� <playername> �� 'all' �� �Է½� �������� ��� �÷��̾�� ����˴ϴ�.");
          }
        }
        else if (args[0].equalsIgnoreCase("set")) {
          if (1 < args.length) {
            if ((player.isOp()) || (player.hasPermission("mirsv.admin"))) {
              if (!isNumber(args[1])) {
                if ((Bukkit.getPlayer(args[1]) != null) && 
                  (2 < args.length)) {
                  if (isNumber(args[2])) {
                    String target = Bukkit.getPlayer(args[1]).getName();
                    int prefix = Integer.parseInt(args[2]);
                    setPrefix(player.getName(), target, prefix);
                  }
                  else if (args[1].equalsIgnoreCase("-1")) {
                    String target = Bukkit.getPlayer(args[1]).getName();
                    int prefix = Integer.parseInt(args[2]);
                    setPrefix(player.getName(), target, prefix);
                  }
                }
              }
              else
              {
                int prefix = Integer.parseInt(args[1]);
                if (prefix >= 0) {
                  if (prefixList.getString(player.getName() + "." + prefix) != null);
                  setPrefix(player.getName(), prefix);
                }
                else if (args[1].equalsIgnoreCase("-1")) {
                  setPrefix(player.getName(), prefix);
                }
              }

            }
            else if (isNumber(args[1])) {
              int prefix = Integer.parseInt(args[1]);
              setPrefix(player.getName(), prefix);
            }
            else if (args[1].equalsIgnoreCase("-1")) {
              int prefix = Integer.parseInt(args[1]);
              setPrefix(player.getName(), prefix);
            }
          }
          else
          {
            errorMessage(player, "���ڰ��� �����մϴ�. /prefix help");
          }
        } else if (args[0].equalsIgnoreCase("show")) {
          if (1 < args.length) {
            if (Bukkit.getPlayer(args[1]) != null)
              if ((player.isOp()) || (player.hasPermission("mirsv.admin"))) {
                Player target = Bukkit.getPlayer(args[1]);
                player.sendMessage(Prefix.cmdPrefix + target.getName() + "���� �������� Īȣ");
                player.sendMessage(Prefix.cmdPrefix + ChatColor.RESET + "-1: Īȣ �����");
                List<?> prefix = prefixList.getStringList(target.getName());
                String[] Prefix = (String[])prefix.toArray(new String[prefix.size()]);
                for (int i = 0; i < prefix.size(); i++) {
                  Prefix[i] = Prefix[i].replaceAll("&", "��");
                  player.sendMessage(cmdPrefix + ChatColor.RESET + i + ": " + Prefix[i]);
                }
              }
              else {
                errorMessage(player, "����� �ٸ� �÷��̾��� Īȣ����� Ȯ���ϽǼ� �����ϴ�.");
              }
          }
          else
          {
            player.sendMessage(Prefix.cmdPrefix + "�ڽ��� �������� Īȣ");
            player.sendMessage(Prefix.cmdPrefix + ChatColor.RESET + "-1: Īȣ �����");
            List<?> prefix = prefixList.getStringList(player.getName());
            String[] Prefix = (String[])prefix.toArray(new String[prefix.size()]);
            for (int i = 0; i < prefix.size(); i++) {
            	Prefix[i] = Prefix[i].replaceAll("&", "��");
              player.sendMessage(cmdPrefix + ChatColor.RESET + i + ": " + Prefix[i]);
            }
          }
        }
        else if ((args[0].equalsIgnoreCase("add")) && ((player.isOp()) || (player.hasPermission("mirsv.admin")))) {
          if (1 < args.length)
          {
            if (2 < args.length) {
              if (Bukkit.getPlayer(args[1]) != null) {
                Player target = Bukkit.getPlayer(args[1]);
                List<String> result = prefixList.getStringList(target.getName());
                if (args[2] != null) {
                  String prefix = args[2];
                  result.add(prefix);
                  prefix = prefix.replaceAll("&", "��");
                  prefixList.set(target.getName(), null);
                  prefixList.set(target.getName(), result);
                  try {
                    prefixList.save(prefixListFile); } catch (IOException localIOException) {
                  }
                  player.sendMessage(Prefix.cmdPrefix + target.getName() + "���� Īȣ�� �߰��Ͽ����ϴ� : " + ChatColor.RESET + prefix);
                  target.sendMessage(Prefix.cmdPrefix + "����� Īȣ�� �߰��Ͽ����ϴ� : " + ChatColor.RESET + prefix);
                }
              }

            }
            else if (args[1] != null) {
              String prefix = args[1];
              for (Player online : Bukkit.getOnlinePlayers()) {
                List<String> result = prefixList.getStringList(online.getName());
                result.add(prefix);
                prefix = prefix.replaceAll("&", "��");
                prefixList.set(online.getName(), null);
                prefixList.set(online.getName(), result);
                try {
                  prefixList.save(prefixListFile); } catch (IOException localIOException1) {
                }
                online.sendMessage(Prefix.cmdPrefix + "����� Īȣ�� �߰��Ͽ����ϴ� : " + ChatColor.RESET + prefix);
              }
              player.sendMessage(Prefix.cmdPrefix + "�������� ��� ������ Īȣ�� �߰��Ͽ����ϴ� : " + ChatColor.RESET + prefix);
            }
          }

        }
        else if ((args[0].equalsIgnoreCase("remove")) && ((player.isOp()) || (player.hasPermission("mirsv.admin")))) {
          if ((2 < args.length) && 
            (Bukkit.getPlayer(args[1]) != null) && 
            (isNumber(args[2]))) {
            List<?> result = prefixList.getStringList(player.getName());
            String[] result2 = (String[])result.toArray(new String[result.size()]);

            int index = Integer.parseInt(args[2]);
            String prefix = result2[index];
            for (World world : Bukkit.getWorlds()) {
              if (Prefix.chat.getPlayerPrefix(world, Bukkit.getPlayer(args[1]).getName()) == prefix) {
            	  Prefix.chat.setPlayerPrefix(world, Bukkit.getPlayer(args[1]).getName(), "");
              }
            }
            prefixList.set(Bukkit.getPlayer(args[1]).getName(), null);
            result.remove(prefix);
            prefixList.set(Bukkit.getPlayer(args[1]).getName(), result);
            try {
              prefixList.save(prefixListFile); } catch (IOException localIOException2) {
            }
            sendMessage(player, Bukkit.getPlayer(args[1]).getName() + "���� Īȣ " + ChatColor.RESET + prefix.replaceAll("&", "��") + ChatColor.GREEN + " �� �����Ǿ����ϴ�.");
          }

        }
        else if ((args[0].equalsIgnoreCase("fix")) && ((player.isOp()) || (player.hasPermission("mirsv.admin")))) {
          if ((3 < args.length) && 
            (Bukkit.getPlayer(args[1]) != null) && 
            (isNumber(args[2]))) {
            Player target = Bukkit.getPlayer(args[1]);
            int index = Integer.parseInt(args[2]);
            List<?> result = prefixList.getStringList(player.getName());
            String[] result2 = (String[])result.toArray(new String[result.size()]);
            String Prefix = result2[index];
            if (args[3] != null) {
              fixPrefix(args[1], index, args[3]);
              sendMessage(player, target.getName() + "���� Īȣ " + Prefix.replaceAll("&", "��") + ChatColor.GREEN + " �� " + args[3].replaceAll("&", "��") + ChatColor.GREEN + " ���� ����Ǿ����ϴ�.");
              sendMessage(target, "����� Īȣ " + Prefix.replaceAll("&", "��") + ChatColor.GREEN + " �� " + args[3].replaceAll("&", "��") + ChatColor.GREEN + " ���� ����Ǿ����ϴ�.");
            }

          }

        }
        else if ((args[0].equalsIgnoreCase("reload")) && ((player.isOp()) || (player.hasPermission("mirsv.admin")))) {
          try {
            prefixList.save(prefixListFile); } catch (Exception localException) {
          }
          prefixList = YamlConfiguration.loadConfiguration(prefixListFile);
          sendMessage(player, "Īȣ �ε� ����.");
        }
      }
      else {
        errorMessage(player, "���ڰ��� �����ϴ�. /prefix help");
      }
    }
    return true;
  }

  private boolean isNumber(String str) {
    boolean result = true;
    if (str.equals("")) {
      result = false;
    }
    for (int i = 0; i < str.length(); i++) {
      int c = str.charAt(i);
      if (((c < 48) || (c > 57)) && (c != 45)) {
        result = false;
        break;
      }
    }
    return result;
  }

  public void setPrefix(String sender, String target, int prefix) {
    Player Sender = Bukkit.getServer().getPlayer(sender);
    Player player = Bukkit.getServer().getPlayer(target);
    String pPrefix = "";
    List<?> result = prefixList.getStringList(player.getName());
    String[] result2 = (String[]) result.toArray(new String[result.size()]);

    if (prefix < result.size()) {
      if (prefix == -1) {
        pPrefix = "";
      }
      else {
        pPrefix = result2[prefix];
      }
    } else {return;}

    if (Prefix.getConfig().getBoolean("permission_enable")) {
    	Prefix.per.getUser(player).setPrefix(pPrefix, null);
    }
    else {
    	Prefix.chat.setPlayerPrefix(player, pPrefix);
    }

    target = player.getName();

    if (prefix >= 0) {
      String message = Prefix.cmdPrefix + target + "���� Īȣ�� �����Ǿ����ϴ�. : " + ChatColor.RESET + pPrefix;
      message = message.replaceAll("&", "��");

      Bukkit.getPlayer(target).sendMessage(Prefix.cmdPrefix + "����� Īȣ�� �����Ǿ����ϴ�. : " + ChatColor.RESET + pPrefix.replaceAll("&", "��"));
      if (sender != target) {
        Sender.sendMessage(message);
      }
    }
    else if (prefix == -1) {
      String message = Prefix.cmdPrefix + target + "���� Īȣ�� �������ϴ�.";

      Bukkit.getPlayer(target).sendMessage(Prefix.cmdPrefix + "����� Īȣ�� �������ϴ�.");
      if (sender != target)
        player.sendMessage(message);
    }
  }

  public void setPrefix(String sender, int prefix)
  {
    setPrefix(sender, sender, prefix);
  }

  public void fixPrefix(String target, int index, String Fix) {
    Player player = Bukkit.getServer().getPlayer(target);
    List<String> result = prefixList.getStringList(player.getName());
    String[] result2 = (String[])result.toArray(new String[result.size()]);
    String loadPrefix = null;
    if (index < result.size()) {
      loadPrefix = result2[index];
    }
    String hasPrefix = Prefix.chat.getPlayerPrefix(player);
    if (hasPrefix == loadPrefix) {
      if (Prefix.getConfig().getBoolean("Prefix.permission_enable")) {
    	  Prefix.per.getUser(player).setPrefix(Fix, null);
      }
      else {
    	  Prefix.chat.setPlayerPrefix(player, Fix);
      }
    }

    result.add(index, Fix);
    result.remove(loadPrefix);
    prefixList.set(Bukkit.getPlayer(target).getName(), null);
    prefixList.set(Bukkit.getPlayer(target).getName(), result);
    try {
      prefixList.save(this.prefixListFile);
    }
    catch (IOException localIOException)
    {
    }
  }
}