package com.mirsv.Marlang.Vote;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.mirsv.MirPlugin;
import com.mirsv.Marlang.CustomAdvancements.CustomAdvancementsConfigManager;
import com.mirsv.Marlang.Util.IsInt;

public class VotePoint extends MirPlugin implements CommandExecutor, Listener{
	
	static VotePoint thisclass = new VotePoint();
	static VoteConfigManager cfgm = new VoteConfigManager();
	
	public VotePoint() {
		getListener(this);
		getCommand("추천포인트", this);
	}
	
	final String votepoint = "&9[&b추천포인트&9]&r ";
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(!p.isOp()) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', votepoint + "권한이 부족합니다!"));
				return true;
			}
		}
		
		
		
		
		if(label.equalsIgnoreCase("추천포인트")) {
			if(args.length == 0) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', votepoint + "/추천포인트 조회 [대상]"));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', votepoint + "/추천포인트 더하기 <대상> [#]"));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', votepoint + "/추천포인트 빼기 <대상> [#]"));
				return true;
			}
			
			if(args[0].equalsIgnoreCase("조회")) {
				if(args.length < 2) {
					if(sender instanceof Player) {
						Player p = (Player) sender;
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', votepoint + "당신의 추천포인트는 &b[" + cfgm.VotePoint.getInt("Players." + p.getName() + ".Point") + "]&f점입니다"));
						return true;
					} else {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', votepoint + "콘솔에서 사용할 수 없는 명령어입니다!"));
					}
				} else if(args.length >= 2) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', votepoint + args[1] + "님의 추천포인트는 &b[" + cfgm.VotePoint.getInt("Players." + args[1] + ".Point") + "]&f점입니다"));
					return true;
				}
			}
			if(args[0].equalsIgnoreCase("더하기")) {
				if(args.length < 2) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', votepoint + "/추천포인트 더하기 <대상> [#]"));
					return true;
				} else if(args.length == 2) {
					int ogpoint = cfgm.VotePoint.getInt("Players." + args[1] + ".Point");
					cfgm.VotePoint.set("Players." + args[1] + ".Point", ogpoint + 1);
					cfgm.SaveConfig();
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', votepoint + args[1] + "님의 추천포인트에 1점을 더해서 추천포인트 &b[" + cfgm.VotePoint.getInt("Players." + args[1] + ".Point") + "]&f점이 되었습니다"));
					if(Bukkit.getPlayer(args[1]) != null) {
						Player gp = Bukkit.getPlayer(args[1]);
						gp.sendMessage(ChatColor.translateAlternateColorCodes('&', votepoint + "당신의 추천포인트 점수에 1점이 더해져 &b[" + cfgm.VotePoint.getInt("Players." + args[1] + ".Point") + "]&f점이 되었습니다"));
					}
					return true;
				} else if(args.length >= 3) {
					if(IsInt.isInt(args[2]) == false) {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', votepoint + "잘못된 인수입니다! [#]은 Integer로 입력되어야 합니다!"));
						return true;
					} else if(Integer.parseInt(args[2]) < 1) {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', votepoint + "잘못된 인수입니다! [#]은 0보다 큰 수로 입력되어야 합니다!"));
						return true;
					}
					int ogpoint = cfgm.VotePoint.getInt("Players." + args[1] + ".Point");
					cfgm.VotePoint.set("Players." + args[1] + ".Point", ogpoint + Integer.parseInt(args[2]));
					cfgm.SaveConfig();
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', votepoint + args[1] + "님에게 추천포인트 " + Integer.parseInt(args[2]) + "점을 더해서 추천포인트 &b[" + cfgm.VotePoint.getInt("Players." + args[1] + ".Point") + "]&f점이 되었습니다"));
					if(Bukkit.getPlayer(args[1]) != null) {
						Player gp = Bukkit.getPlayer(args[1]);
						gp.sendMessage(ChatColor.translateAlternateColorCodes('&', votepoint + "당신의 추천포인트 점수에 " + Integer.parseInt(args[2]) + "점이 더해져 &b[" + cfgm.VotePoint.getInt("Players." + args[1] + ".Point") + "]&f점이 되었습니다"));
					}
					return true;
				}
			}
			if(args[0].equalsIgnoreCase("빼기")) {
				if(args.length < 2) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', votepoint + "/추천포인트 빼기 <대상> [#]"));
					return true;
				} else if(args.length == 2) {
					int ogpoint = cfgm.VotePoint.getInt("Players." + args[1] + ".Point");
					cfgm.VotePoint.set("Players." + args[1] + ".Point", ogpoint - 1);
					cfgm.SaveConfig();
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', votepoint + args[1] + "님의 추천포인트 점수에 1을 빼서 추천포인트 &b[" + cfgm.VotePoint.getInt("Players." + args[1] + ".Point") + "]&f점이 되었습니다"));
					if(Bukkit.getPlayer(args[1]) != null) {
						Player gp = Bukkit.getPlayer(args[1]);
						gp.sendMessage(ChatColor.translateAlternateColorCodes('&', votepoint + "당신의 추천포인트 점수에 1점이 빠져 &b[" + cfgm.VotePoint.getInt("Players." + args[1] + ".Point") + "]&f점이 되었습니다"));
					}
					return true;
				} else if(args.length >= 3) {
					if(IsInt.isInt(args[2]) == false) {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', votepoint + "잘못된 인수입니다! [#]은 Integer로 입력되어야 합니다!"));
						return true;
					} else if(Integer.parseInt(args[2]) < 1) {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', votepoint + "잘못된 인수입니다! [#]은 0보다 큰 수로 입력되어야 합니다!"));
						return true;
					}
					int ogpoint = cfgm.VotePoint.getInt("Players." + args[1] + ".Point");
					cfgm.VotePoint.set("Players." + args[1] + ".Point", ogpoint - Integer.parseInt(args[2]));
					cfgm.SaveConfig();
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', votepoint + args[1] + "님에게 추천포인트 " + Integer.parseInt(args[2]) + "점을 빼서 추천포인트 &b[" + cfgm.VotePoint.getInt("Players." + args[1] + ".Point") + "]&f점이 되었습니다"));
					if(Bukkit.getPlayer(args[1]) != null) {
						Player gp = Bukkit.getPlayer(args[1]);
						gp.sendMessage(ChatColor.translateAlternateColorCodes('&', votepoint + "당신의 추천포인트 점수에 " + Integer.parseInt(args[2]) + "점이 빠져 &b[" + cfgm.VotePoint.getInt("Players." + args[1] + ".Point") + "]&f점이 되었습니다"));
					}
					return true;
				}
			}
		}
		
		return true;
	}
	
	public static boolean hasPoint(Player p, int i) {
		if(VotePoint.getVotePoint(p) < i) {
			return false;
		} else {
			return true;
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		if(cfgm.VotePoint.getInt("Players." + p.getName() + ".Point") >= 0) {
		} else {
			cfgm.VotePoint.addDefault("Players." + p.getName() + ".Point", 0);
			cfgm.SaveConfig();
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l✅&r &f유저 " + p.getName() + "의 추천포인트 기본 설정이 완료되었습니다!"));
		}
	}
	
	public static int getVotePoint(Player p) {
		cfgm.LoadConfig();
		return cfgm.VotePoint.getInt("Players." + p.getName() + ".Point");
	}
	
	public static void minusVotePoint(Player p, int i) {
		cfgm.LoadConfig();
		int ogpoint = cfgm.VotePoint.getInt("Players." + p.getName() + ".Point");
		cfgm.VotePoint.set("Players." + p.getName() + ".Point", ogpoint - i);
		cfgm.SaveConfig();
	}
	
}
