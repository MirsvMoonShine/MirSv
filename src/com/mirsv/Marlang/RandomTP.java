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

public class RandomTP extends MirPlugin implements CommandExecutor{
	
	public RandomTP() {
		getCommand("야생", this);
		getCommand("채집", this);
		getCommand("네더", this);
		getCommand("지옥", this);
		getCommand("엔드", this);
		getCommand("엔더", this);
	}
	
	public HashMap<String, Long> mineCooldown = new HashMap<String, Long>();
	public HashMap<String, Long> netherCooldown = new HashMap<String, Long>();
	public HashMap<String, Long> endCooldown = new HashMap<String, Long>();
	final int cooldownTime = 35;
	
	Random r = new Random();
	int X;
	int Z;
	int Y;
	final int maxCoord = 4000;
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
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l야생&f&l월드에 다시 랜덤으로 TP할 수 있는 시간까지 &e&l" + secondsLeft + "초 &f&l남았습니다!"));
					return true;
				}
			}
			World world = Bukkit.getServer().getWorld("mineworld");
			genCoord(world, p);
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l야생&f&l월드에 랜덤으로 TP되었습니다! &8( X : " + this.X + ", Y : " + this.Y + ", Z : " + this.Z + " )"));
			p.teleport(l);
			mineCooldown.put(sender.getName(), System.currentTimeMillis());
			return true;
		}
		
		if(label.equalsIgnoreCase("네더") || label.equalsIgnoreCase("지옥")) {
			if(netherCooldown.containsKey(sender.getName())) {
				long secondsLeft = ((netherCooldown.get(p.getName())/1000)+cooldownTime) - (System.currentTimeMillis()/1000);
				if(secondsLeft>0) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l네더&f&l월드에 다시 랜덤으로 TP할 수 있는 시간까지 &e&l" + secondsLeft + "초 &f&l남았습니다!"));
					return true;
				}
			}
			World world = Bukkit.getServer().getWorld("world_nether");
			genCoord(world, p);
			this.Y = world.getHighestBlockYAt(X, Z);
			this.l = new Location(world, this.X + 0.5D, this.Y, this.Z + 0.5D);
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l네더&f&l월드에 랜덤으로 TP되었습니다! &8( X : " + this.X + ", Y : " + this.Y + ", Z : " + this.Z + " )"));
			p.teleport(l);
			netherCooldown.put(sender.getName(), System.currentTimeMillis());
			return true;
		}
		
		if(label.equalsIgnoreCase("엔드") || label.equalsIgnoreCase("엔더")) {
			if(endCooldown.containsKey(sender.getName())) {
				long secondsLeft = ((endCooldown.get(p.getName())/1000)+cooldownTime) - (System.currentTimeMillis()/1000);
				if(secondsLeft>0) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&l엔더&f&l월드에 다시 TP할 수 있는 시간까지 &e&l" + secondsLeft + "초 &f&l남았습니다!"));
					return true;
				}
			}
			World world = Bukkit.getServer().getWorld("world_the_end");
			this.X = 0;
			this.Z = 0;
			this.Y = world.getHighestBlockYAt(X, Z);
			this.l = new Location(world, this.X + 0.5D, this.Y, this.Z + 0.5D);
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&l엔더&f&l월드에 TP되었습니다! &8( X : " + this.X + ", Y : " + this.Y + ", Z : " + this.Z + " )"));
			p.teleport(l);
			endCooldown.put(sender.getName(), System.currentTimeMillis());
			return true;
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
