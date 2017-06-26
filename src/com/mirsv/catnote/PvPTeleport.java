package com.mirsv.catnote;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.mirsv.MirPlugin;

public class PvPTeleport extends MirPlugin implements Listener, CommandExecutor {
	final String prefix = ChatColor.GOLD + "[" + ChatColor.GREEN + "�̸�����" + ChatColor.GOLD + "] " + ChatColor.RESET;
	public PvPTeleport() {
		getCommand("�ݷμ���", this);
		getCommand("ķ����", this);
		getCommand("õ����", this);
		getListener(this);
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) return false;
		Player player = (Player) sender;
		PotionEffect invincibility = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 40, 4);
		if(args.length == 0) {
			if(label.equalsIgnoreCase("�ݷμ���")) {
				final int CenterX = 535, Y = 73, CenterZ = 321, Range = 20;
				int X, Z;
				do {
					X = CenterX - Range + (int) (Math.random() * Range * 2);
					Z = CenterZ - Range + (int) (Math.random() * Range * 2);
				} while(new Location(Bukkit.getWorld("spawn"), X, Y, Z).getBlock().getType() != Material.AIR);
				player.teleport(new Location(Bukkit.getWorld("spawn"), X, Y, Z));
				player.addPotionEffect(invincibility);
				player.sendMessage(prefix + ChatColor.GOLD + "�ݷμ��� �����մϴ�.");
			}
			if(label.equalsIgnoreCase("õ����")) {
				final int CenterX = 797, Y = 69, CenterZ = 531, Range = 30;
				int X, Z;
				do {
					X = CenterX - Range + (int) (Math.random() * Range * 2);
					Z = CenterZ - Range + (int) (Math.random() * Range * 2);
				} while(new Location(Bukkit.getWorld("spawn"), X, Y - 2, Z).getBlock().getType() == Material.AIR || new Location(Bukkit.getWorld("spawn"), X, Y, Z).getBlock().getType() != Material.AIR);
				player.teleport(new Location(Bukkit.getWorld("spawn"), X, Y, Z));
				player.addPotionEffect(invincibility);
				player.sendMessage(prefix + ChatColor.GOLD + "õ�������� �����ϴ�.");
			}
			if(label.equalsIgnoreCase("ķ����")) {
				final int CenterX = 518, Y = 60, CenterZ = 535, Range = 20;
				int X, Z;
				do {
					X = CenterX - Range + (int) (Math.random() * Range * 2);
					Z = CenterZ - (int) (Range * 1.5) + (int) (Math.random() * Range * 3);
				} while(new Location(Bukkit.getWorld("spawn"), X, Y, Z).getBlock().getType() != Material.AIR);
				player.teleport(new Location(Bukkit.getWorld("spawn"), X, Y, Z));
				player.addPotionEffect(invincibility);
				player.sendMessage(prefix + ChatColor.GOLD + "ķ���忡 �����մϴ�.");
			}
		}
		return false;
	}
}
