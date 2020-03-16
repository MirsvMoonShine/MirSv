package com.mirsv.function.list.CatNote;

import com.mirsv.function.AbstractFunction;
import com.mirsv.util.Messager;
import com.mirsv.util.users.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WhisperChat extends AbstractFunction implements Listener, CommandExecutor, TabCompleter {

	@Override
	protected void onEnable() {
		registerCommand("wc", this, this);
		registerListener(this);
	}

	@Override
	protected void onDisable() {
	}

	public WhisperChat() {
		super("귓속말채팅", "1.1", "귓속말 대상을 고정시켜 채팅을 치는 것 만으로", "귓속말을 보낼 수 있습니다.");
	}

	Map<OfflinePlayer, OfflinePlayer> targets = new HashMap<>();

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		if (!targets.containsKey(event.getPlayer())) return;
		String[] split = event.getMessage().split(" ");
		if (split[0].equalsIgnoreCase("/tc") || split[0].equalsIgnoreCase("/nc") || split[0].equalsIgnoreCase("/lc") || split[0].equalsIgnoreCase("/pc") || split[0].equalsIgnoreCase("/g") || split[0].equalsIgnoreCase("/admin") || split[0].equalsIgnoreCase("/mod") || split[0].equalsIgnoreCase("/a") || split[0].equalsIgnoreCase("/m") || split[0].equalsIgnoreCase("/l")) {
			event.getPlayer().sendMessage(Messager.getPrefix() + ChatColor.WHITE + targets.get(event.getPlayer()).getName() + ChatColor.AQUA + "님에게 귓속말을 보내지 않습니다.");
			targets.remove(event.getPlayer());
		}
		if (split.length > 1 && split[0].equalsIgnoreCase("/party") && split[1].equalsIgnoreCase("chat")) {
			event.getPlayer().sendMessage(Messager.getPrefix() + ChatColor.WHITE + targets.get(event.getPlayer()).getName() + ChatColor.AQUA + "님에게 귓속말을 보내지 않습니다.");
			targets.remove(event.getPlayer());
		}
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length == 0) {
				if (targets.containsKey(player)) {
					player.sendMessage(Messager.getPrefix() + ChatColor.WHITE + targets.get(player).getName() + ChatColor.AQUA + "님에게 귓속말을 보내지 않습니다.");
					targets.remove(player);
				} else {
					player.sendMessage(Messager.getPrefix() + ChatColor.AQUA + "사용 방법: /wc <닉네임>");
				}
			} else {
				if (!Bukkit.getOfflinePlayer(args[0]).isOnline()) {
					player.sendMessage(Messager.getPrefix() + ChatColor.RED + "존재하지 않는 플레이어입니다.");
					return false;
				}
				if (player.getName().equalsIgnoreCase(args[0])) {
					player.sendMessage(Messager.getPrefix() + ChatColor.RED + "자기 자신과 귓속말할 수 없습니다.");
					return false;
				}
				targets.remove(player);
				targets.put(player, Bukkit.getPlayer(args[0]));
				player.sendMessage(Messager.getPrefix() + ChatColor.WHITE + args[0] + ChatColor.AQUA + "님에게 귓속말을 보냅니다.");
			}
		}
		return false;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onChat(AsyncPlayerChatEvent event) {
		if (targets.containsKey(event.getPlayer())) {
			Player player = event.getPlayer();
			event.setMessage(event.getMessage().replaceAll("%", "%%"));
			event.setCancelled(true);
			OfflinePlayer target = targets.get(player);
			if (target.isOnline()) {
				event.setCancelled(true);
				player.sendMessage(ChatColor.GOLD + "귓속말" + ChatColor.WHITE + " | " + ChatColor.RED + "보냄" + ChatColor.WHITE + " | " + player.getName() + ": " + ChatColor.GOLD + event.getMessage());
				target.getPlayer().sendMessage(ChatColor.GOLD + "귓속말" + ChatColor.WHITE + " | " + ChatColor.GREEN + "받음" + ChatColor.WHITE + " | " + player.getName() + ": " + ChatColor.GOLD + event.getMessage());
				Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.GREEN + " -> " + ChatColor.YELLOW + target.getName() + ChatColor.WHITE + " | " + player.getName() + ": " + ChatColor.GOLD + event.getMessage());
			} else {
				player.sendMessage(Messager.getPrefix() + ChatColor.RED + "귓속말을 보낸 상대가 오프라인입니다. 귓속말 채팅이 종료됩니다.");
				targets.remove(player);
			}
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("wc")) {
			if (args.length == 1) {
				List<String> player = UserManager.getOnlinePlayersName();
				if (!args[0].isEmpty()) player.removeIf(cc -> !cc.toLowerCase().startsWith(args[0].toLowerCase()));
				if (sender instanceof Player) player.remove(sender.getName());
				return player;
			}
		}
		return null;
	}
}