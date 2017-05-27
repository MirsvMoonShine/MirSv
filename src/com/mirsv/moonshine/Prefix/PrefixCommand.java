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
          sendMessage(player, "Prefix 명령어");
          sendMessage(player, "/prefix set [index] - " + ChatColor.YELLOW + "[index]번호의 칭호로 변경됩니다.");
          sendMessage(player, "/prefix show - " + ChatColor.YELLOW + "자신의 칭호 목록을 보여줍니다.");
          if ((player.isOp()) || (player.hasPermission("easyprefix.help.other"))) {
            sendMessage(player, "/prefix set [playername] [index] - " + ChatColor.YELLOW + "[playernam]의 칭호를 [index]번호로 강제로 변경됩니다.");
            sendMessage(player, "/prefix add [prefix] - " + ChatColor.YELLOW + "접속중인 모든 유저의 칭호 [prefix]를 추가합니다.");
            sendMessage(player, "/prefix add <playername> [prefix] - " + ChatColor.YELLOW + "[playername]의 칭호 [prefix]를 추가합니다.");
            sendMessage(player, "/prefix show [playername] - " + ChatColor.YELLOW + "[playername]의 칭호 목록을 보여줍니다.");
            sendMessage(player, "/prefix remove [playername] [index] - " + ChatColor.YELLOW + "[playername]의 칭호 [index]를 삭제시킵니다.");
            sendMessage(player, "/prefix fix [playername] [index]  [prefix] - " + ChatColor.YELLOW + "[playername]의 [index]번호 칭호 [prefix]로 변경합니다.");
            sendMessage(player, "/prefix reload - " + ChatColor.YELLOW + "prefixList.yml 파일을 다시 불러옵니다.");
            sendMessage(player, ChatColor.YELLOW + "add 커맨드의 <playername> 에 'all' 를 입력시 접속중인 모든 플레이어에게 적용됩니다.");
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
            errorMessage(player, "인자값이 부족합니다. /prefix help");
          }
        } else if (args[0].equalsIgnoreCase("show")) {
          if (1 < args.length) {
            if (Bukkit.getPlayer(args[1]) != null)
              if ((player.isOp()) || (player.hasPermission("mirsv.admin"))) {
                Player target = Bukkit.getPlayer(args[1]);
                player.sendMessage(Prefix.cmdPrefix + target.getName() + "님이 보유중인 칭호");
                player.sendMessage(Prefix.cmdPrefix + ChatColor.RESET + "-1: 칭호 지우기");
                List<?> prefix = prefixList.getStringList(target.getName());
                String[] Prefix = (String[])prefix.toArray(new String[prefix.size()]);
                for (int i = 0; i < prefix.size(); i++) {
                  Prefix[i] = Prefix[i].replaceAll("&", "§");
                  player.sendMessage(cmdPrefix + ChatColor.RESET + i + ": " + Prefix[i]);
                }
              }
              else {
                errorMessage(player, "당신은 다른 플레이어의 칭호목록을 확인하실수 없습니다.");
              }
          }
          else
          {
            player.sendMessage(Prefix.cmdPrefix + "자신이 보유중인 칭호");
            player.sendMessage(Prefix.cmdPrefix + ChatColor.RESET + "-1: 칭호 지우기");
            List<?> prefix = prefixList.getStringList(player.getName());
            String[] Prefix = (String[])prefix.toArray(new String[prefix.size()]);
            for (int i = 0; i < prefix.size(); i++) {
            	Prefix[i] = Prefix[i].replaceAll("&", "§");
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
                  prefix = prefix.replaceAll("&", "§");
                  prefixList.set(target.getName(), null);
                  prefixList.set(target.getName(), result);
                  try {
                    prefixList.save(prefixListFile); } catch (IOException localIOException) {
                  }
                  player.sendMessage(Prefix.cmdPrefix + target.getName() + "님의 칭호를 추가하였습니다 : " + ChatColor.RESET + prefix);
                  target.sendMessage(Prefix.cmdPrefix + "당신의 칭호를 추가하였습니다 : " + ChatColor.RESET + prefix);
                }
              }

            }
            else if (args[1] != null) {
              String prefix = args[1];
              for (Player online : Bukkit.getOnlinePlayers()) {
                List<String> result = prefixList.getStringList(online.getName());
                result.add(prefix);
                prefix = prefix.replaceAll("&", "§");
                prefixList.set(online.getName(), null);
                prefixList.set(online.getName(), result);
                try {
                  prefixList.save(prefixListFile); } catch (IOException localIOException1) {
                }
                online.sendMessage(Prefix.cmdPrefix + "당신의 칭호를 추가하였습니다 : " + ChatColor.RESET + prefix);
              }
              player.sendMessage(Prefix.cmdPrefix + "접속중인 모든 유저의 칭호를 추가하였습니다 : " + ChatColor.RESET + prefix);
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
            sendMessage(player, Bukkit.getPlayer(args[1]).getName() + "님의 칭호 " + ChatColor.RESET + prefix.replaceAll("&", "§") + ChatColor.GREEN + " 가 삭제되었습니다.");
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
              sendMessage(player, target.getName() + "님의 칭호 " + Prefix.replaceAll("&", "§") + ChatColor.GREEN + " 가 " + args[3].replaceAll("&", "§") + ChatColor.GREEN + " 으로 변경되었습니다.");
              sendMessage(target, "당신의 칭호 " + Prefix.replaceAll("&", "§") + ChatColor.GREEN + " 가 " + args[3].replaceAll("&", "§") + ChatColor.GREEN + " 으로 변경되었습니다.");
            }

          }

        }
        else if ((args[0].equalsIgnoreCase("reload")) && ((player.isOp()) || (player.hasPermission("mirsv.admin")))) {
          try {
            prefixList.save(prefixListFile); } catch (Exception localException) {
          }
          prefixList = YamlConfiguration.loadConfiguration(prefixListFile);
          sendMessage(player, "칭호 로딩 성공.");
        }
      }
      else {
        errorMessage(player, "인자값이 없습니다. /prefix help");
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
      String message = Prefix.cmdPrefix + target + "님의 칭호가 설정되었습니다. : " + ChatColor.RESET + pPrefix;
      message = message.replaceAll("&", "§");

      Bukkit.getPlayer(target).sendMessage(Prefix.cmdPrefix + "당신의 칭호가 설정되었습니다. : " + ChatColor.RESET + pPrefix.replaceAll("&", "§"));
      if (sender != target) {
        Sender.sendMessage(message);
      }
    }
    else if (prefix == -1) {
      String message = Prefix.cmdPrefix + target + "님의 칭호를 지웠습니다.";

      Bukkit.getPlayer(target).sendMessage(Prefix.cmdPrefix + "당신의 칭호를 지웠습니다.");
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