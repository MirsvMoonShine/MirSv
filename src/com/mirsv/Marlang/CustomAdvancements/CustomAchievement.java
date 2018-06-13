package com.mirsv.Marlang.CustomAdvancements;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import com.mirsv.MirPlugin;
import com.mirsv.Marlang.CustomAdvancements.List.AchievementType;
import com.mirsv.Marlang.CustomAdvancements.List.AchievementList;
import com.mirsv.Marlang.CustomAdvancements.Manager.ConfigurationManager;
import com.mirsv.Marlang.PlayerID.PlayerID;

public class CustomAchievement extends MirPlugin implements Listener, CommandExecutor{

	static ConfigurationManager cfgm = new ConfigurationManager();
	
	public CustomAchievement() {
		getListener(this);
		getCommand("customachievement", this);
		cfgm.SetupConfig();
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		setChecker(p);
	}
	
	public void setChecker(Player p) {
		cfgm.Checker.set("Players." + p.getUniqueId() + ".Nickname" , p.getName());
		for(AchievementList l : AchievementList.values()) {
			cfgm.Checker.addDefault("Players." + p.getUniqueId() + "." + l.getID() + ".Clear" , false);
			cfgm.Checker.addDefault("Players." + p.getUniqueId() + "." + l.getID() + ".Reward" , false);
			cfgm.SaveConfig();
		}
	}
	
	public static boolean getCleared(Player p, AchievementList l) {
		cfgm.LoadConfig();
		boolean b = cfgm.Checker.getBoolean("Players." + p.getUniqueId() + "." + l.getID() + ".Clear");
		return b;
	}
	
	public static boolean getRewarded(Player p, AchievementList l) {
		cfgm.LoadConfig();
		boolean b = cfgm.Checker.getBoolean("Players." + p.getUniqueId() + "." + l.getID() + ".Reward");
		return b;
	}
	 
	public static void setCleared(Player p, AchievementList l, boolean b) {
		cfgm.Checker.set("Players." + p.getUniqueId() + "." + l.getID() + ".Clear", b);
		cfgm.SaveConfig();
	}
	
	public static void setRewarded(Player p, AchievementList l, boolean b) {
		cfgm.Checker.set("Players." + p.getUniqueId() + "." + l.getID() + ".Reward", b);
		cfgm.SaveConfig();
	}
	
	public static void clearCustomAdvancements(Player p, AchievementList s) {
		cfgm.LoadConfig();
		if(!getCleared(p, s)) {
			setCleared(p, s, true);
			CustomAchievementClearEvent event = new CustomAchievementClearEvent(p, s);
			Bukkit.getServer().getPluginManager().callEvent(event);
			CustomAchievement.showClear(p, s);
		}
	}
	
	public static void showClear(Player p, AchievementList l) {
		Bukkit.broadcastMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', "&a>&e>&6>&c> &e " + p.getName() + "&f님이 &b" + l.getTitle() + "&f " + l.getType().getClearMessage() + " &c<&6<&e<&a<"));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(label.equalsIgnoreCase("customachievement")) {
			if(args.length != 0 && args[0].equalsIgnoreCase("reload")) {
				if(!sender.isOp()) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c이 명령어를 사용하려면 Admin 또는 그 이상이어야 합니다!"));
					return true;
				} else {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b《&acustomachievement&b》&f 리로드 완료!"));
					cfgm.LoadConfig();
					return true;
				}
			}
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b《&acustomachievement&b》&f 커스텀 발전과제 플러그인"));
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b《&acustomachievement&b》&f 개발자 : _Marlang"));
			if(sender instanceof Player) {
				Player p = (Player) sender;
				CustomAchievement.clearCustomAdvancements(p, AchievementList.StartOfJourney);
			}
		}
		return true;
	}
	
}
