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
		getCommand("�߻�", this);
		getCommand("ä��", this);
		getCommand("�״�", this);
		getCommand("����", this);
		getCommand("����", this);
		getCommand("����", this);
	}
	
	public HashMap<String, Long> mineCooldown = new HashMap<String, Long>();
	public HashMap<String, Long> netherCooldown = new HashMap<String, Long>();
	public HashMap<String, Long> endCooldown = new HashMap<String, Long>();
	int cooldownTime = 35;
	
	Random r = new Random();
	int X;
	int Z;
	int Y;
	Location l;
	Location fl;
	Location tl;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c�ֿܼ����� ����� �� ���� ���ɾ��Դϴ�!"));
			return true;
		}
		
		Player p = (Player) sender;
					
		if(label.equalsIgnoreCase("�߻�") || label.equalsIgnoreCase("ä��")) {
			if(mineCooldown.containsKey(sender.getName())) {
				long secondsLeft = ((mineCooldown.get(p.getName())/1000)+cooldownTime) - (System.currentTimeMillis()/1000);
				if(secondsLeft>0) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l�߻�&f&l���忡 �ٽ� �������� TP�� �� �ִ� �ð����� &e&l" + secondsLeft + "�� &f&l���ҽ��ϴ�!"));
					return true;
				}
			}
			World world = Bukkit.getServer().getWorld("mineworld");
			genCoord(world, p);
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l�߻�&f&l���忡 �������� TP�Ǿ����ϴ�! &8( X : " + this.X + ", Y : " + this.Y + ", Z : " + this.Z + " )"));
			p.teleport(l);
			mineCooldown.put(sender.getName(), System.currentTimeMillis());
			return true;
		}
		
		if(label.equalsIgnoreCase("�״�") || label.equalsIgnoreCase("����")) {
			if(netherCooldown.containsKey(sender.getName())) {
				long secondsLeft = ((netherCooldown.get(p.getName())/1000)+cooldownTime) - (System.currentTimeMillis()/1000);
				if(secondsLeft>0) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l�״�&f&l���忡 �ٽ� �������� TP�� �� �ִ� �ð����� &e&l" + secondsLeft + "�� &f&l���ҽ��ϴ�!"));
					return true;
				}
			}
			World world = Bukkit.getServer().getWorld("world_nether");
			genCoord(world, p);
			this.Y = world.getHighestBlockYAt(X, Z);
			this.l = new Location(world, this.X + 0.5D, this.Y, this.Z + 0.5D);
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l�״�&f&l���忡 �������� TP�Ǿ����ϴ�! &8( X : " + this.X + ", Y : " + this.Y + ", Z : " + this.Z + " )"));
			p.teleport(l);
			netherCooldown.put(sender.getName(), System.currentTimeMillis());
			return true;
		}
		
		if(label.equalsIgnoreCase("����") || label.equalsIgnoreCase("����")) {
			if(endCooldown.containsKey(sender.getName())) {
				long secondsLeft = ((endCooldown.get(p.getName())/1000)+cooldownTime) - (System.currentTimeMillis()/1000);
				if(secondsLeft>0) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&l����&f&l���忡 �ٽ� TP�� �� �ִ� �ð����� &e&l" + secondsLeft + "�� &f&l���ҽ��ϴ�!"));
					return true;
				}
			}
			World world = Bukkit.getServer().getWorld("world_the_end");
			this.X = 0;
			this.Z = 0;
			this.Y = world.getHighestBlockYAt(X, Z);
			this.l = new Location(world, this.X + 0.5D, this.Y, this.Z + 0.5D);
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&l����&f&l���忡 TP�Ǿ����ϴ�! &8( X : " + this.X + ", Y : " + this.Y + ", Z : " + this.Z + " )"));
			p.teleport(l);
			endCooldown.put(sender.getName(), System.currentTimeMillis());
			return true;
		}
		return true;
	}
	
	public void genCoord(World w, Player p) {
		this.X = r.nextInt(10000);
		this.Z = r.nextInt(10000);
		this.Y = w.getHighestBlockYAt(X, Z);
	
		this.l = new Location(w, this.X + 0.5D, this.Y, this.Z + 0.5D);
		this.fl = new Location(w, this.X + 0.5D, this.Y - 1, this.Z + 0.5D);
		this.tl = new Location(w, this.X + 0.5D, this.Y + 1, this.Z + 0.5D);
		
		if(l.getBlock().getType().equals(Material.WATER) || l.getBlock().getType().equals(Material.STATIONARY_WATER)
				|| fl.getBlock().getType().equals(Material.WATER) || fl.getBlock().getType().equals(Material.STATIONARY_WATER) 
				|| l.getBlock().getType().equals(Material.LAVA) || l.getBlock().getType().equals(Material.STATIONARY_LAVA)
				|| fl.getBlock().getType().equals(Material.LAVA) || fl.getBlock().getType().equals(Material.STATIONARY_LAVA)
				|| l.getBlock().getType().equals(Material.BEDROCK) || fl.getBlock().getType().equals(Material.BEDROCK)
				|| !tl.getBlock().getType().equals(Material.AIR) || fl.getBlock().getType().equals(Material.MAGMA)) {
			genCoord(w, p);
		}
	}

}