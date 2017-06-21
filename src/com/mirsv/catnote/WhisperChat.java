package com.mirsv.catnote;

import com.mirsv.MirPlugin;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class WhisperChat extends MirPlugin implements Listener, CommandExecutor {
	public WhisperChat() {
		getCommand("wc", this);
		getListener(this);
	}
	HashMap < UUID, UUID > Target = new HashMap < UUID, UUID > ();
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		if(getConfig().getBoolean("enable.BroadCast", true)) {
			String string = event.getMessage().substring(1);
			String[] s = string.split(" ");
			if(s[0].equalsIgnoreCase("tc") || s[0].equalsIgnoreCase("nc") || s[0].equalsIgnoreCase("lc") || s[0].equalsIgnoreCase("pc") || s[0].equalsIgnoreCase("g")) Target.remove(event.getPlayer().getUniqueId());
			if(s.length > 1 && s[0].equalsIgnoreCase("party") && s[1].equalsIgnoreCase("chat")) Target.remove(event.getPlayer().getUniqueId());
		}
	}
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if((getConfig().getBoolean("enable.WhisperChat", true)) && ((sender instanceof Player))) {
			Player player = (Player) sender;
			if(args.length == 0) {
				if(Target.containsKey(player.getUniqueId())) {
					Target.remove(player.getUniqueId());
					player.sendMessage(ChatColor.GOLD + "[Towny] " + ChatColor.DARK_GREEN + "모드 설정: general");
					player.sendMessage(ChatColor.GOLD + "[Towny] " + ChatColor.DARK_GREEN + "[TownyChat] You are now talking in " + ChatColor.WHITE + "general");
				}
				else {
					player.sendMessage(ChatColor.GOLD + "[Towny] " + ChatColor.AQUA + "/wc [닉네임] : [닉네임]과 자동으로 귓속말 채팅");
				}
			}
			else {
				if(!Bukkit.getOfflinePlayer(args[0]).isOnline()) {
					player.sendMessage(ChatColor.GOLD + "[Towny] " + ChatColor.RED + "존재하지 않는 플레이어입니다.");
					return false;
				}
				if(player.getName().equalsIgnoreCase(args[0])) {
					player.sendMessage(ChatColor.GOLD + "[Towny] " +  ChatColor.RED + "자기 자신과 귓속말할 수 없습니다.");
					return false;
				}
				if(Target.containsKey(player.getUniqueId())) Target.remove(player.getUniqueId());
				Target.put(player.getUniqueId(), Bukkit.getPlayer(args[0]).getUniqueId());
				player.sendMessage(ChatColor.GOLD + "[Towny] " + ChatColor.DARK_GREEN + "모드 설정: whisper");
				player.sendMessage(ChatColor.GOLD + "[Towny] " + ChatColor.DARK_GREEN + "[TownyChat] You are now talking in " + ChatColor.WHITE + "whisper" + ChatColor.DARK_GREEN + " with " + ChatColor.WHITE + args[0]);
			}
		}
		return false;
	}
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		if(getConfig().getBoolean("enable.WhisperChat", true) && Target.containsKey(event.getPlayer().getUniqueId())) {
			event.getRecipients().clear();
			event.setMessage(event.getMessage().replaceAll("%", "%%"));
			if(Bukkit.getOfflinePlayer(Target.get(event.getPlayer().getUniqueId())).isOnline()) {
				event.setFormat("[" + ChatColor.BLUE + "WC" + ChatColor.WHITE + "] " + event.getPlayer().getName() + ": " + ChatColor.BLUE + event.getMessage());
				event.getRecipients().add(Bukkit.getPlayer(event.getPlayer().getUniqueId()));
				event.getRecipients().add(Bukkit.getPlayer(Target.get(event.getPlayer().getUniqueId())));
			}
			else event.getPlayer().sendMessage(ChatColor.GOLD + "[Towny] " + ChatColor.RED + "귓속말을 보낸 상대가 오프라인입니다. \'/g\'를 입력해주세요.");
		}
	}
}