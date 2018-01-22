package com.mirsv.catnote;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.mirsv.MirPlugin;
import com.mirsv.moonshine.Warning.Warning;
import com.mirsv.moonshine.Warning.WarningCommand;

import net.milkbowl.vault.permission.Permission;

public class Guide extends MirPlugin implements Listener, CommandExecutor {
	Permission per = null;
	ArrayList < Material > Resources = new ArrayList < Material >(Arrays.asList(Material.COAL_ORE, Material.IRON_ORE, Material.GOLD_ORE, Material.REDSTONE_ORE, Material.LAPIS_ORE, Material.DIAMOND_ORE, Material.EMERALD_ORE, Material.LOG, Material.LOG_2, Material.SAND, Material.CLAY, Material.GRAVEL));
	HashMap < String, String > Guides = new HashMap < String, String >();
	HashMap < UUID, Burrow > Burrow = new HashMap < UUID, Burrow >();
	HashMap < UUID, Tower > Tower = new HashMap < UUID, Tower >();
	ArrayList < Chat > ChatList = new ArrayList < Chat >();
	final String Prefix = ChatColor.GRAY + "[" + ChatColor.GOLD + ChatColor.BOLD + "!" + ChatColor.GRAY + "] " + ChatColor.RESET;
	File f = new File("plugins/Mirsv/Guide/Guide.dat");
	public Guide() {
		setupPermission();
		try {
			if (!f.exists()) f.createNewFile();
			BufferedReader in = new BufferedReader(new FileReader(f));
			String s;
			while((s = in.readLine()) != null) {
				String[] Array = s.split(" ");
				String desc = "";
				for(int i = 1; i < Array.length; i++) desc += Array[i] + " ";
				Guides.put(Array[0], desc);
			}
			in.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		getCommand("guide", this);
		getCommand("��õ", this);
		getListener(this);
	}
	public boolean setupPermission() {
		RegisteredServiceProvider < Permission > chatProvider = pm.getServer().getServicesManager().getRegistration(Permission.class);
		if(chatProvider != null) per = (Permission) chatProvider.getProvider();
		return per != null;
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	private void onPlayerChat(AsyncPlayerChatEvent e) {
		if(getConfig().getBoolean("enable.Guide", true)) {
			if(e.getMessage().length() < 6) {
				boolean isExist = false;
				for(Chat c: ChatList) {
					if(c.uuid.equals(e.getPlayer().getUniqueId())) {
						if(c.Stack(System.currentTimeMillis())) {
							Warning warning = new Warning();
							WarningCommand wCommand = new WarningCommand(warning);
							wCommand.AddWarning(e.getPlayer());
							e.getPlayer().sendMessage(ChatColor.RED + "" +  ChatColor.BOLD + "��Ÿ ���� ���� �������� ��� 1ȸ�Դϴ�.");
						}
						if(c.Number > 3) {
							e.getPlayer().sendMessage(ChatColor.RED + "" + ChatColor.BOLD + c.Number + "ȸ° ��Ÿ �Է����Դϴ�. 6ȸ �Է� �� ��� 1ȸ�Դϴ�.");
							e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_EGG_THROW, 1.0F, 10.0F);
						}
						isExist = true;
						break;
					}
				}
				if(!isExist) ChatList.add(new Chat(e.getPlayer().getUniqueId(), System.currentTimeMillis()));
			}
			String[] group = per.getPlayerGroups(e.getPlayer());
			Boolean isNewbie = false;
			for(String g: group) {
				if(g.equalsIgnoreCase("newbie")) {
					isNewbie = true;
					break;
				}
			}
			if(!isNewbie) return;
			for(String key: Guides.keySet()) {
				if(e.getMessage().contains(key)) {
					e.getPlayer().sendMessage(Prefix + ChatColor.WHITE + ChatColor.BOLD + e.getPlayer().getName() + "��, " + ChatColor.GOLD + Guides.get(key));
					e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_EGG_THROW, 1.0F, 10.0F);
					break;
				}
			}
		}
	}
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if(getConfig().getBoolean("enable.Guide", true)) {
			String[] group = per.getPlayerGroups(event.getPlayer());
			Boolean isNewbie = false;
			for(String g: group) {
				if(g.equalsIgnoreCase("newbie")) {
					isNewbie = true;
					break;
				}
			}
			if(!isNewbie) return;
			if(Burrow.containsKey(event.getPlayer().getUniqueId())) {
				if(Burrow.get(event.getPlayer().getUniqueId()).isBurrow(event.getBlock().getLocation())) {
					event.getPlayer().sendMessage(Prefix + ChatColor.WHITE + ChatColor.BOLD + event.getPlayer().getName() + "��, " + ChatColor.GOLD + "������� �������� �����Ǿ����ϴ�. ���� ������ �ƴ϶�� �� ������ ���ּ���!");
					event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_EGG_THROW, 1.0F, 10.0F);
				}
			}
			else Burrow.put(event.getPlayer().getUniqueId(), new Burrow(event.getBlock().getLocation()));
			if(event.getBlock().getWorld().getName().equalsIgnoreCase("world") && Resources.contains(event.getBlock().getType())) {
				event.getPlayer().sendMessage(Prefix + ChatColor.WHITE + ChatColor.BOLD + event.getPlayer().getName() + "��, " + ChatColor.GOLD + "�ڿ� ä�� ������ ���� / ���� �� �ı��� ��� ���� �� �ֽ��ϴ�. �ڿ� ä�� �������� ���� ĳ�� �ִٸ� ������ ���ּ���!");
				event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_EGG_THROW, 1.0F, 10.0F);
			}
		}
	}
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if(getConfig().getBoolean("enable.Guide", true)) {
			String[] group = per.getPlayerGroups(event.getPlayer());
			Boolean isNewbie = false;
			for(String g: group) {
				if(g.equalsIgnoreCase("newbie")) {
					isNewbie = true;
					break;
				}
			}
			if(!isNewbie) return;
			if(Tower.containsKey(event.getPlayer().getUniqueId())) {
				if(Tower.get(event.getPlayer().getUniqueId()).isTower(event.getBlock().getLocation())) {
					event.getPlayer().sendMessage(Prefix + ChatColor.WHITE + ChatColor.BOLD + event.getPlayer().getName() + "��, " + ChatColor.GOLD + "������� ����ž�� �����Ǿ����ϴ�. ����� �ϰ� ���� �� ö�Ÿ� ���ּ���!");
					event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_EGG_THROW, 1.0F, 10.0F);
				}
			}
			else Tower.put(event.getPlayer().getUniqueId(), new Tower(event.getBlock().getLocation()));
		}
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(getConfig().getBoolean("enable.Guide", true)) {
			if(label.equalsIgnoreCase("��õ")) {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "broadcast https://minelist.kr/servers/mirsv.com");
				for(Player p: Bukkit.getOnlinePlayers()) p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 10.0F);
			}
			if(sender instanceof Player) return false;
			if(label.equalsIgnoreCase("guide") && args[0].equalsIgnoreCase("reload")) {
				try {
					BufferedReader in = new BufferedReader(new FileReader("plugins/Mirsv/Guide/Guide.dat"));
					String s;
					while((s = in.readLine()) != null) {
						String[] Array = s.split(" ");
						String desc = "";
						for(int i = 1; i < Array.length; i++) desc += Array[i] + " ";
						Guides.put(Array[0], desc);
					}
					in.close();
				}
				catch(IOException e) {
					e.printStackTrace();
				}
				Bukkit.getConsoleSender().sendMessage("���ε� �Ϸ�");
			}
		}
		return false;
	}
}


class Burrow {
	Location loc;
	int num = 1;
	public Burrow(Location loc) {
		this.loc = loc;
	}
	public boolean isBurrow(Location loc) {
		if(this.loc.getBlockX() == loc.getBlockX() && this.loc.getBlockZ() == loc.getBlockZ() && this.loc.getBlockY() - 1 == loc.getBlockY() && loc.getWorld().getName().equalsIgnoreCase("world") && isNearFilled(loc)) num++;
		else num = 1;
		this.loc = loc;
		return num > 4;
	}
	public boolean isNearFilled(Location loc) {
		int R[][] = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
		for(int i = 0; i < 4; i++) if(!new Location(Bukkit.getWorld("World"), loc.getBlockX() + R[i][0], loc.getBlockY(), loc.getBlockZ() + R[i][1]).getBlock().getType().equals(Material.AIR)) return true;
		return false;
	}
}

class Tower {
	Location loc;
	int num = 1;
	public Tower(Location loc) {
		this.loc = loc;
	}
	public boolean isTower(Location loc) {
		if(this.loc.getBlockX() == loc.getBlockX() && this.loc.getBlockZ() == loc.getBlockZ() && this.loc.getBlockY() + 1 == loc.getBlockY() && loc.getWorld().getName().equalsIgnoreCase("world") && isNearEmpty(loc)) num++;
		else num = 1;
		this.loc = loc;
		return num > 9;
	}
	public boolean isNearEmpty(Location loc) {
		int R[][] = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
		for(int i = 0; i < 4; i++) if(new Location(Bukkit.getWorld("World"), loc.getBlockX() + R[i][0], loc.getBlockY(), loc.getBlockZ() + R[i][1]).getBlock().getType().equals(Material.AIR)) return true;
		return false;
	}
}

class Chat {
	UUID uuid;
	int Number;
	long Time;
	public Chat(UUID uuid, long Time) {
		this.uuid = uuid;
		this.Time = Time;
		this.Number = 1;
	}
	boolean Stack(long Now) {
		if(Now - Time < 3000) {
			Number++;
			Time = Now;
			return Number > 5;
		}
		Time = Now;
		Number = 1;
		return false;
	}
}