package com.mirsv.catnote;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.mirsv.MirPlugin;

public class PlayTime extends MirPlugin implements Listener, CommandExecutor {
	final String prefix = ChatColor.GOLD + "[" + ChatColor.GREEN + "�̸�����" + ChatColor.GOLD + "] " + ChatColor.RESET;
	public PlayTime() {
		getCommand("playtime", this);
		getListener(this);
	}
	@SuppressWarnings({"deprecation"})
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) return false;
		Player player = (Player) sender;
		if(getConfig().getBoolean("enable.PlayTime", true)) {
			if(args.length == 0) {
				JSONParser parser = new JSONParser();
				try {
					Object obj = parser.parse(new FileReader("world/stats/" + player.getUniqueId() + ".json"));
					JSONObject jsonObject = (JSONObject) obj;
					int Ticks = Integer.parseInt(jsonObject.get("stat.playOneMinute") + "");
					player.sendMessage(prefix + ChatColor.AQUA + "����� " + TicktoString(Ticks) + " �÷��� �ϼ̽��ϴ�.");
				}
				catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				catch (ParseException e) {
					e.printStackTrace();
				}
			}
			else {
				if(args[0].equalsIgnoreCase("?") || args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("info")) {
					player.sendMessage(prefix + ChatColor.DARK_AQUA + "/playtime " + ChatColor.WHITE + "- �ڽ��� �÷��� �ð� Ȯ��");
					player.sendMessage(prefix + ChatColor.DARK_AQUA + "/playtime " + ChatColor.GRAY + "<�г���> " + ChatColor.WHITE + " - <�г���>�� �÷��� �ð� Ȯ��");
					player.sendMessage(prefix + ChatColor.DARK_AQUA + "/playtime " + ChatColor.GRAY + "top" + ChatColor.WHITE + " - �÷��� �ð� ���� Ȯ��");
				}
				else if(args[0].equalsIgnoreCase("top")) {
					Timer timer = new Timer();
					TimerTask task = new TimerTask() {
						@Override
						public void run() {
							getRank(player);
						}
					};
					timer.schedule(task, 100);
				}
				else {
					JSONParser parser = new JSONParser();
					try {
						if(!Bukkit.getOfflinePlayer(args[0]).hasPlayedBefore()) {
							player.sendMessage(prefix + ChatColor.YELLOW + "������ �����ߴ� �÷��̾ �ƴմϴ�.");
						}
						else {
							String uuid = Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString();
							Object obj = parser.parse(new FileReader("world/stats/" + uuid + ".json"));
							JSONObject jsonObject = (JSONObject) obj;
							int Ticks = Integer.parseInt(jsonObject.get("stat.playOneMinute") + "");
							player.sendMessage(prefix + ChatColor.WHITE + Bukkit.getOfflinePlayer(args[0]).getName() + ChatColor.AQUA + "���� " + TicktoString(Ticks) + " �÷��� �ϼ̽��ϴ�.");
						}
					}
					catch (FileNotFoundException e) {
						player.sendMessage(prefix + ChatColor.YELLOW + "������ �����ߴ� �÷��̾ �ƴմϴ�.");
						e.printStackTrace();
					}
					catch (IOException e) {
						player.sendMessage(prefix + ChatColor.YELLOW + "������ �����ߴ� �÷��̾ �ƴմϴ�.");
						e.printStackTrace();
					}
					catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return false;
	}
	public String TicktoString(int Ticks) {
		Ticks /= 20;
		int Seconds = Ticks % 60;
		int Minutes = (Ticks / 60) % 60;
		int Hours = (Ticks / 3600) % 24;
		int Days = Ticks / 86400;
		if(Days + Hours + Minutes == 0) return Seconds + "��";
		if(Days + Hours == 0) return Minutes + "�� " + Seconds + "��";
		if(Days == 0) return Hours + "�ð� " + Minutes + "�� " + Seconds + "��";
		return Days + "�� " + Hours + "�ð� " + Minutes + "�� " + Seconds + "��";
	}
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static List sortByValue(final Map map) {
        List <UUID> list = new ArrayList();
        list.addAll(map.keySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                Object v1 = map.get(o1);
                Object v2 = map.get(o2);
                return ((Comparable) v2).compareTo(v1);
            }
        });
        return list;
    }
	@SuppressWarnings("rawtypes")
	public void getRank(Player player) {
		HashMap <UUID, Integer> UserPlaytimeList = new HashMap <UUID, Integer>();
		JSONParser parser = new JSONParser();
		File forder = new File("world/stats");
		for(File file: forder.listFiles()) {
			String name = file.getName();
			int pos = name.lastIndexOf(".");
			name = name.substring(0, pos);
			UUID uuid = UUID.fromString(name);
			try {
				Object obj = parser.parse(new FileReader("world/stats/" + file.getName()));
				JSONObject jsonObject = (JSONObject) obj;
				int Ticks = Integer.parseInt(jsonObject.get("stat.playOneMinute") + "");
				if(Ticks > 34560000) UserPlaytimeList.put(uuid, Ticks);
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			catch (ParseException e) {
				e.printStackTrace();
			}
		}
		player.sendMessage(prefix + ChatColor.BLUE + "���� �÷��� �ð� ����");
		Iterator it = sortByValue(UserPlaytimeList).iterator();
		for(int i = 1; i <= 10; i++) {
			if(UserPlaytimeList.size() + 1 == i) break;
			UUID uuid = (UUID) it.next();
			player.sendMessage(i + ". " + Bukkit.getOfflinePlayer(uuid).getName() + ", " + TicktoString(UserPlaytimeList.get(uuid)));
		}
	}
}
