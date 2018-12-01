package com.mirsv.Marlang;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mirsv.MirPlugin;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Coord;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import com.palmergames.bukkit.towny.object.TownyWorld;

public class RandomTP extends MirPlugin implements CommandExecutor{
	
	public RandomTP() {
		getCommand("야생", this);
		getCommand("채집", this);
		getCommand("건축", this);
	}

	public HashMap<String, Long> mineCooldown = new HashMap<String, Long>();
	public HashMap<String, Long> buildCooldown = new HashMap<String, Long>();
	final int cooldownTime = 5;
	
	Random r = new Random();
	int X;
	int Z;
	int Y;
	final int maxCoord = 5000;
	Location l;
	Location fl;
	Location tl;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c콘솔에서는 사용할 수 없는 명령어입니다!"));
			return true;
		}
		
		Player p = (Player) sender;
					
		if(label.equalsIgnoreCase("야생") || label.equalsIgnoreCase("채집")) {
			if(mineCooldown.containsKey(sender.getName())) {
				long secondsLeft = ((mineCooldown.get(p.getName())/1000)+cooldownTime) - (System.currentTimeMillis()/1000);
				if(secondsLeft>0) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l채집&f&l월드에 다시 랜덤으로 TP할 수 있는 시간까지 &e&l" + secondsLeft + "초 &f&l남았습니다!"));
					return true;
				}
			}
			World world = Bukkit.getServer().getWorld("mineworld");
			genCoord(world, p);
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l채집&f&l월드에 랜덤으로 TP되었습니다! &8( X : " + this.X + ", Y : " + this.Y + ", Z : " + this.Z + " )"));
			p.teleport(l);
			mineCooldown.put(sender.getName(), System.currentTimeMillis());
			return true;
		} else if(label.equalsIgnoreCase("건축")) {
			if(buildCooldown.containsKey(sender.getName())) {
				long secondsLeft = ((buildCooldown.get(p.getName())/1000)+cooldownTime) - (System.currentTimeMillis()/1000);
				if(secondsLeft>0) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l건축&f&l월드에 다시 랜덤으로 TP할 수 있는 시간까지 &e&l" + secondsLeft + "초 &f&l남았습니다!"));
					return true;
				}
			}
			World world = Bukkit.getServer().getWorld("world");
			genCoord(world, p);
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l건축&f&l월드에 랜덤으로 TP되었습니다! &8( X : " + this.X + ", Y : " + this.Y + ", Z : " + this.Z + " )"));
			p.teleport(l);
			buildCooldown.put(sender.getName(), System.currentTimeMillis());
		}
		
		return true;
	}
	
	public void genCoord(World w, Player p) {
		this.X = r.nextInt(maxCoord);
		this.Z = r.nextInt(maxCoord);
		this.Y = w.getHighestBlockYAt(X, Z);
	
		this.l = new Location(w, this.X + 0.5D, this.Y, this.Z + 0.5D);
		this.fl = new Location(w, this.X + 0.5D, this.Y - 1, this.Z + 0.5D);
		this.tl = new Location(w, this.X + 0.5D, this.Y + 1, this.Z + 0.5D);
		
		if(l.getBlock().getType().equals(Material.WATER) || l.getBlock().getType().equals(Material.STATIONARY_WATER)
				|| fl.getBlock().getType().equals(Material.WATER) || fl.getBlock().getType().equals(Material.STATIONARY_WATER) 
				|| l.getBlock().getType().equals(Material.LAVA) || l.getBlock().getType().equals(Material.STATIONARY_LAVA)
				|| fl.getBlock().getType().equals(Material.LAVA) || fl.getBlock().getType().equals(Material.STATIONARY_LAVA)
				|| l.getBlock().getType().equals(Material.BEDROCK) || fl.getBlock().getType().equals(Material.BEDROCK)
				|| !tl.getBlock().getType().equals(Material.AIR) || fl.getBlock().getType().equals(Material.MAGMA)
				|| fl.getBlock().getType().equals(Material.SOUL_SAND)) {
			genCoord(w, p);
		}
	}

}
