package com.mirsv.catnote;

import java.sql.Date;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.mirsv.MirPlugin;

public class PvPTeleport extends MirPlugin implements Listener, CommandExecutor {
	final String prefix = ChatColor.GOLD + "[" + ChatColor.GREEN + "미르서버" + ChatColor.GOLD + "] " + ChatColor.RESET;
	HashMap <UUID, Location> Teleports = new HashMap <UUID, Location>();
	HashMap <UUID, Long> Times = new HashMap <UUID, Long>();
	public PvPTeleport() {
		getCommand("콜로세움", this);
		getCommand("캠핑장", this);
		getCommand("천공섬", this);
		getListener(this);
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		if(player.isOp()) return;
		String[] args = event.getMessage().split(" ");
		final int ColoCX = 535, ColoCZ = 321, ColoRange = 50;
		if(Math.sqrt(Math.pow(player.getLocation().getX() - ColoCX, 2) + Math.pow(player.getLocation().getZ() - ColoCZ, 2)) < ColoRange) {
			if(!args[0].equalsIgnoreCase("/spawn") && !args[0].equalsIgnoreCase("/home") && !args[0].equalsIgnoreCase("/ts") && !(args.length > 1 && args[0].equalsIgnoreCase("/t") && args[1].equalsIgnoreCase("spawn"))) return;
			event.setCancelled(true);
			if(args[0].equalsIgnoreCase("/ts") || (args.length > 1 && args[0].equalsIgnoreCase("/t") && args[1].equalsIgnoreCase("spawn"))) {
				Teleports.put(player.getUniqueId(), player.getLocation());
				Times.put(player.getUniqueId(), new Date(System.currentTimeMillis()).getTime());
			}
			player.sendMessage(prefix + ChatColor.RED + "콜로세움에서는 이 명령어를 사용할 수 없습니다.");
		}
		final int SkyICX = 797, SkyICZ = 531, SkyIRange = 70;
		if(Math.sqrt(Math.pow(player.getLocation().getX() - SkyICX, 2) + Math.pow(player.getLocation().getZ() - SkyICZ, 2)) < SkyIRange) {
			if(!args[0].equalsIgnoreCase("/spawn") && !args[0].equalsIgnoreCase("/home") && !args[0].equalsIgnoreCase("/ts") && !(args.length > 1 && args[0].equalsIgnoreCase("/t") && args[1].equalsIgnoreCase("spawn"))) return;
			event.setCancelled(true);
			if(args[0].equalsIgnoreCase("/ts") || (args.length > 1 && args[0].equalsIgnoreCase("/t") && args[1].equalsIgnoreCase("spawn"))) {
				Teleports.put(player.getUniqueId(), player.getLocation());
				Times.put(player.getUniqueId(), new Date(System.currentTimeMillis()).getTime());
			}
			player.sendMessage(prefix + ChatColor.RED + "천공섬에서는 이 명령어를 사용할 수 없습니다.");
		}
		final int CampCX = 535, CampCZ = 510, CampRange = 70;
		if(Math.sqrt(Math.pow(player.getLocation().getX() - CampCX, 2) + Math.pow(player.getLocation().getZ() - CampCZ, 2)) < CampRange) {
			if(!args[0].equalsIgnoreCase("/spawn") && !args[0].equalsIgnoreCase("/home") && !args[0].equalsIgnoreCase("/ts") && !(args.length > 1 && args[0].equalsIgnoreCase("/t") && args[1].equalsIgnoreCase("spawn"))) return;
			event.setCancelled(true);
			if(args[0].equalsIgnoreCase("/ts") || (args.length > 1 && args[0].equalsIgnoreCase("/t") && args[1].equalsIgnoreCase("spawn"))) {
				Teleports.put(player.getUniqueId(), player.getLocation());
				Times.put(player.getUniqueId(), new Date(System.currentTimeMillis()).getTime());
			}
			player.sendMessage(prefix + ChatColor.RED + "캠핑장에서는 이 명령어를 사용할 수 없습니다.");
		}
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		if(!Teleports.isEmpty()) {
			for(UUID uuid: Teleports.keySet()) {
				long Now = new Date(System.currentTimeMillis()).getTime();
				if(Bukkit.getOfflinePlayer(uuid).isOnline() && Now - Times.get(uuid) > 1000) {
					Bukkit.getPlayer(uuid).teleport(Teleports.get(uuid));
					Teleports.remove(uuid);
				}
			}
		}
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) return false;
		Player player = (Player) sender;
		PotionEffect invincibility = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 40, 4);
		if(args.length == 0) {
			if(label.equalsIgnoreCase("콜로세움")) {
				final int CenterX = 535, Y = 72, CenterZ = 321, Range = 20;
				int X, Z;
				do {
					X = CenterX - Range + (int) (Math.random() * Range * 2);
					Z = CenterZ - Range + (int) (Math.random() * Range * 2);
				} while(new Location(Bukkit.getWorld("spawn"), X, Y, Z).getBlock().getType() != Material.AIR);
				player.teleport(new Location(Bukkit.getWorld("spawn"), X, Y, Z));
				player.addPotionEffect(invincibility);
				player.sendMessage(prefix + ChatColor.GOLD + "콜로세움에 도착합니다.");
			}
			if(label.equalsIgnoreCase("천공섬")) {
				final int CenterX = 797, Y = 69, CenterZ = 531, Range = 30;
				int X, Z;
				do {
					X = CenterX - Range + (int) (Math.random() * Range * 2);
					Z = CenterZ - Range + (int) (Math.random() * Range * 2);
				} while(new Location(Bukkit.getWorld("spawn"), X, Y - 2, Z).getBlock().getType() == Material.AIR || new Location(Bukkit.getWorld("spawn"), X, Y, Z).getBlock().getType() != Material.AIR);
				player.teleport(new Location(Bukkit.getWorld("spawn"), X, Y, Z));
				player.addPotionEffect(invincibility);
				player.sendMessage(prefix + ChatColor.GOLD + "천공섬으로 떠납니다.");
			}
			if(label.equalsIgnoreCase("캠핑장")) {
				final int CenterX = 518, Y = 60, CenterZ = 535, Range = 20;
				int X, Z;
				do {
					X = CenterX - Range + (int) (Math.random() * Range * 2);
					Z = CenterZ - (int) (Range * 1.5) + (int) (Math.random() * Range * 3);
				} while(new Location(Bukkit.getWorld("spawn"), X, Y, Z).getBlock().getType() != Material.AIR);
				player.teleport(new Location(Bukkit.getWorld("spawn"), X, Y, Z));
				player.addPotionEffect(invincibility);
				player.sendMessage(prefix + ChatColor.GOLD + "캠핑장에 진입합니다.");
			}
		}
		return false;
	}
}
