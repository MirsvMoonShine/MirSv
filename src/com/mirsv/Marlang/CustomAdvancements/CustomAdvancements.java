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
import com.mirsv.Marlang.PlayerID.PlayerID;

public class CustomAdvancements extends MirPlugin implements Listener, CommandExecutor{

	static CustomAdvancementsConfigManager cfgm = new CustomAdvancementsConfigManager();
	static PlayerID pid = new PlayerID();
	
	public CustomAdvancements() {
		getListener(this);
		getCommand("customadvancements", this);
		cfgm.SetupConfig();
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		setChecker(p);
	}
	
	public void setChecker(Player p) {
		for(AdvancementsList s : AdvancementsList.values()) {
			cfgm.Checker.set("Players." + p.getUniqueId() + ".Nickname" , p.getName());
			cfgm.Checker.addDefault("Players." + p.getUniqueId() + "." + s.getAdvid() + ".Clear" , false);
			cfgm.Checker.addDefault("Players." + p.getUniqueId() + "." + s.getAdvid() + ".Reward" , false);
			cfgm.SaveConfig();
		}
	}
	
	public static boolean getCleared(Player p, AdvancementsList s) {
		cfgm.LoadConfig();
		boolean b = cfgm.Checker.getBoolean("Players." + p.getUniqueId() + "." + s.getAdvid() + ".Clear");
		return b;
	}
	
	public static boolean getRewarded(Player p, AdvancementsList s) {
		cfgm.LoadConfig();
		boolean b = cfgm.Checker.getBoolean("Players." + p.getUniqueId() + "." + s.getAdvid() + ".Reward");
		return b;
	}
	 
	public static void setCleared(Player p, AdvancementsList s, boolean b) {
		cfgm.Checker.set("Players." + p.getUniqueId() + "." + s.getAdvid() + ".Clear", b);
		cfgm.SaveConfig();
	}
	
	public static void setRewarded(Player p, AdvancementsList s, boolean b) {
		cfgm.Checker.set("Players." + p.getUniqueId() + "." + s.getAdvid() + ".Reward", b);
		cfgm.SaveConfig();
	}
	
	public static void clearCustomAdvancements(Player p, AdvancementsList s) {
		cfgm.LoadConfig();
		if(!getCleared(p, s)) {
			setCleared(p, s, true);
			CustomAdvancementClearEvent event = new CustomAdvancementClearEvent(p, s);
			Bukkit.getServer().getPluginManager().callEvent(event);
		}
	}
	
	public static void showClear(Player p, AdvancementsList s) {
		if(s.getAdvType().equals(AdvancementTypes.발전)) {
			for(int i = 0; i > 4; i++) {
				Firework fw = p.getWorld().spawn(p.getLocation(), Firework.class);
				FireworkMeta fwm = fw.getFireworkMeta();
				Builder builder = FireworkEffect.builder();
				
				fwm.addEffect(builder.flicker(true).withColor(Color.YELLOW).build());
				fwm.addEffect(builder.with(org.bukkit.FireworkEffect.Type.STAR).build());
				fwm.addEffect(builder.trail(true).build());
				fwm.setPower(1);
				fw.setFireworkMeta(fwm);
			}
			
			Bukkit.broadcastMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', "&c>&b>&a> &e " + p.getName() + "&f님이 &b" + s.getAdvname() + "&f " + s.getAdvType().getClearMessage() + " &a<&b<&c<"));
		} else if(s.getAdvType().equals(AdvancementTypes.도전)) {
			for(int i = 0; i > 4; i++) {
				Firework fw = p.getWorld().spawn(p.getLocation(), Firework.class);
				FireworkMeta fwm = fw.getFireworkMeta();
				Builder builder = FireworkEffect.builder();
				
				fwm.addEffect(builder.flicker(true).withColor(Color.YELLOW).build());
				fwm.addEffect(builder.with(org.bukkit.FireworkEffect.Type.STAR).build());
				fwm.addEffect(builder.trail(true).build());
				fwm.setPower(1);
				fw.setFireworkMeta(fwm);
			}
			
			Bukkit.broadcastMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', "&c>&b>&a> &e " + p.getName() + "&f님이 &b" + s.getAdvname() + "&f " + s.getAdvType().getClearMessage() + " &a<&b<&c<"));
			
		}
			
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(label.equalsIgnoreCase("customadvancements")) {
			if(args.length != 0 && args[0].equalsIgnoreCase("reload")) {
				if(!sender.isOp()) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c이 명령어를 사용하려면 Admin 또는 그 이상이어야 합니다!"));
					return true;
				} else {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[&aCustomAdvancements&b]&f 리로드 완료!"));
					cfgm.LoadConfig();
					return true;
				}
			}
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[&aCustomAdvancements&b]&f 커스텀 발전과제 플러그인"));
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[&aCustomAdvancements&b]&f 개발자 : _Marlang"));
			if(sender instanceof Player) {
				Player p = (Player) sender;
				//CustomAdvancements.clearCustomAdvancements(p, AdvancementsList.StartOfJourney);
			}
		}
		return true;
	}
	
}
