package com.mirsv.catnote;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.mirsv.MirPlugin;

import net.milkbowl.vault.permission.Permission;

public class Guide extends MirPlugin implements Listener, CommandExecutor {
	Permission per = null;
	HashMap < String, String > Guides = new HashMap < String, String >();
	final String Prefix = ChatColor.GRAY + "[" + ChatColor.GOLD + ChatColor.BOLD + "!" + ChatColor.GRAY + "] " + ChatColor.RESET;
	public Guide() {
		setupPermission();
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
		getCommand("guide", this);
		getCommand("추천", this);
		getListener(this);
	}
	public boolean setupPermission() {
		RegisteredServiceProvider < Permission > chatProvider = pm.getServer().getServicesManager().getRegistration(Permission.class);
		if(chatProvider != null) per = (Permission) chatProvider.getProvider();
		return per != null;
	}
	@EventHandler
	private void onPlayerChat(AsyncPlayerChatEvent e) {
		if(getConfig().getBoolean("enable.Guide", true)) {
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
					e.getPlayer().sendMessage(Prefix + ChatColor.WHITE + ChatColor.BOLD + e.getPlayer().getName() + "님, " + ChatColor.GOLD + Guides.get(key));
					e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_EGG_THROW, 1.0F, 10.0F);
					break;
				}
			}
		}
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) return false;
		if(getConfig().getBoolean("enable.Guide", true)) {
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
				Bukkit.getConsoleSender().sendMessage("리로드 완료");
			}
			if(label.equalsIgnoreCase("추천")) {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "broadcast https://minelist.kr/servers/mirsv.com");
				for(Player p: Bukkit.getOnlinePlayers()) p.playSound(p.getLocation(), Sound.ENTITY_EGG_THROW, 10.0F, 1.0F);
			}
		}
		return false;
	}
}
