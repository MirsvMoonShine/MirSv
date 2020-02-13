package com.mirsv.function.list.Cokes;

import com.google.common.base.Enums;
import com.google.common.base.Optional;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mirsv.Mirsv;
import com.mirsv.function.AbstractFunction;
import com.mirsv.function.list.Cokes.party.Party;
import com.mirsv.util.Messager;
import com.mirsv.util.users.User;
import com.mirsv.util.users.User.Channel;
import com.mirsv.util.users.UserManager;
import com.palmergames.bukkit.towny.object.Resident;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.*;

public class AdvancedChat extends AbstractFunction implements CommandExecutor, Listener {

	private static final String prefix = ChatColor.translateAlternateColorCodes('&', "&2[&aChat&2] &f");

	public AdvancedChat() {
		super("채팅", "1.0.1", "미르서버 전용 채팅 시스템입니다.");
	}

	@Override
	protected void onEnable() {
		registerListener(this);
		registerCommand("g", this);
		registerCommand("tc", this);
		registerCommand("nc", this);
		registerCommand("lc", this);
		registerCommand("pc", this);
		registerCommand("mod", this);
		registerCommand("a", this);
		registerCommand("bc", this);
		registerCommand("spychat", this);
		registerCommand("ac", this);
	}

	@Override
	protected void onDisable() {
	}

	private final Set<OfflinePlayer> spying = new HashSet<>();

	@EventHandler(priority = EventPriority.NORMAL)
	public void onChat(AsyncPlayerChatEvent e) {
		Player player = e.getPlayer();
		User user = UserManager.getUser(player);

		String message = e.getMessage().replace("%", "%%");

		Set<Player> recipients = e.getRecipients();
		switch (user.getChatChannel()) {
			case TOWN_CHAT:
				if (!user.hasTown()) {
					user.setChatChannel(Channel.GLOBAL_CHAT);
					player.sendMessage(prefix + "속해있는 마을이 존재하지 않아 자동으로 전체 채팅에 참여하셨습니다.");
					e.setCancelled(true);
					return;
				}
				recipients.clear();
				for (Resident resident : user.getTown().getResidents()) {
					Player recipient = Bukkit.getPlayerExact(resident.getName());
					if (!resident.isNPC() && recipient != null) recipients.add(recipient);
				}
				Mirsv.getPlugin().getDynmapAPI().setDisableChatToWebProcessing(true);
				e.setFormat(ChatColor.translateAlternateColorCodes('&', "&b마을 &f| " + getPrefix(user) + user.getNickname() + "&f: &b") + message);
				break;
			case NATION_CHAT:
				if (!user.hasNation()) {
					user.setChatChannel(Channel.GLOBAL_CHAT);
					player.sendMessage(prefix + "속해있는 국가가 존재하지 않아 자동으로 전체 채팅에 참여하셨습니다.");
					e.setCancelled(true);
					return;
				}
				recipients.clear();
				for (Resident resident : user.getNation().getResidents()) {
					Player recipient = Bukkit.getPlayerExact(resident.getName());
					if (!resident.isNPC() && recipient != null) recipients.add(recipient);
				}
				Mirsv.getPlugin().getDynmapAPI().setDisableChatToWebProcessing(true);
				e.setFormat(ChatColor.translateAlternateColorCodes('&', "&6국가 &f| " + getPrefix(user) + user.getNickname() + "&f: &6") + message);
				break;
			case LOCAL_CHAT:
				recipients.clear();
				for (Entity recipient : player.getNearbyEntities(30, 30, 30)) {
					if (recipient instanceof Player) {
						recipients.add((Player) recipient);
					}
				}
				recipients.add(player);
				Mirsv.getPlugin().getDynmapAPI().setDisableChatToWebProcessing(true);
				e.setFormat(ChatColor.translateAlternateColorCodes('&', "&a지역 &f| " + getPrefix(user) + user.getNickname() + "&f: &a") + message);
				break;
			case MODERATOR_CHAT:
				recipients.clear();
				for (Player recipient : Bukkit.getOnlinePlayers())
					if (recipient.hasPermission("mirsv.chat.moderator")) recipients.add(recipient);
				recipients.add(player);
				Mirsv.getPlugin().getDynmapAPI().setDisableChatToWebProcessing(true);
				e.setFormat(ChatColor.translateAlternateColorCodes('&', "&5MOD &f| " + getPrefix(user) + user.getNickname() + "&f: &d" + message));
				break;
			case ADMIN_CHAT:
				recipients.clear();
				for (Player recipient : Bukkit.getOnlinePlayers()) if (recipient.isOp()) recipients.add(recipient);
				recipients.add(player);
				Mirsv.getPlugin().getDynmapAPI().setDisableChatToWebProcessing(true);
				e.setFormat(ChatColor.translateAlternateColorCodes('&', "&4관리자 &f| " + getPrefix(user) + user.getNickname() + "&f: &c" + message));
				break;
			case PARTY_CHAT:
				recipients.clear();
				for (OfflinePlayer recipient : Party.getParty(player).getPlayers()) {
					if (recipient.isOnline()) recipients.add(recipient.getPlayer());
				}
				recipients.add(player);
				Mirsv.getPlugin().getDynmapAPI().setDisableChatToWebProcessing(true);
				e.setFormat(ChatColor.translateAlternateColorCodes('&', "&5파티 &f| " + getPrefix(user) + user.getNickname() + "&f: &d") + message);
				break;
			case NOTICE_CHAT:
				for (Player target : Bukkit.getOnlinePlayers())
					target.playSound(target.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5F, 0.5F);
				Mirsv.getPlugin().getDynmapAPI().setDisableChatToWebProcessing(true);
				e.setFormat(ChatColor.translateAlternateColorCodes('&', "&c공지 &f| " + getPrefix(user) + user.getNickname() + "&f: " + message));
				break;
			default:
				Mirsv.getPlugin().getDynmapAPI().setDisableChatToWebProcessing(false);
				e.setFormat(ChatColor.translateAlternateColorCodes('&', (user.hasTown() ? (user.hasNation() ? "&f[&6" + user.getNation().getName() + "&f|&b" + user.getTown().getName() + "&f]" : "&f[&b" + user.getTown().getName() + "&f]") : "") + getPrefix(user) + user.getNickname() + "&f: " + message));
				break;
		}

		for (OfflinePlayer spy : spying) {
			if (spy.isOnline()) {
				recipients.add(spy.getPlayer());
			}
		}

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			User user = UserManager.getUser(player);
			switch (label.toLowerCase()) {
				case "g":
					if (args.length == 0) {
						user.setChatChannel(Channel.GLOBAL_CHAT);
						player.sendMessage(prefix + "모드: 전체 채팅");
					} else {
					StringJoiner message = new StringJoiner(" ");
					for (String arg : args) {
						message.add(arg);
					}

					Channel channel = user.getChatChannel();
					user.setChatChannel(Channel.GLOBAL_CHAT);
					player.chat(message.toString());
					user.setChatChannel(channel);
				}
					break;
				case "tc":
					if (args.length == 0) {
						if (user.getChatChannel() != Channel.TOWN_CHAT) {
							if (user.hasTown()) {
								user.setChatChannel(Channel.TOWN_CHAT);
								player.sendMessage(prefix + "모드: 마을 채팅");
							} else {
								user.setChatChannel(Channel.GLOBAL_CHAT);
								player.sendMessage(prefix + "귀하는 마을에 소속되지 않았습니다.");
							}
						} else {
							user.setChatChannel(Channel.GLOBAL_CHAT);
							player.sendMessage(prefix + "모드: 전체 채팅");
						}
					} else {
						if (user.hasTown()) {
							StringJoiner message = new StringJoiner(" ");
							for (String arg : args) {
								message.add(arg);
							}

							Channel channel = user.getChatChannel();
							user.setChatChannel(Channel.TOWN_CHAT);
							player.chat(message.toString());
							user.setChatChannel(channel);
						} else {
							player.sendMessage(prefix + "귀하는 마을에 소속되지 않았습니다.");
						}
					}
					break;
				case "nc":
					if (args.length == 0) {
						if (user.getChatChannel() != Channel.NATION_CHAT) {
							if (user.hasNation()) {
								user.setChatChannel(Channel.NATION_CHAT);
								player.sendMessage(prefix + "모드: 국가 채팅");
							} else {
								user.setChatChannel(Channel.GLOBAL_CHAT);
								player.sendMessage(prefix + "귀하는 국가에 소속되지 않았습니다.");
							}
						} else {
							user.setChatChannel(Channel.GLOBAL_CHAT);
							player.sendMessage(prefix + "모드: 전체 채팅");
						}
					} else {
						if (user.hasNation()) {
							StringJoiner message = new StringJoiner(" ");
							for (String arg : args) {
								message.add(arg);
							}

							Channel channel = user.getChatChannel();
							user.setChatChannel(Channel.NATION_CHAT);
							player.chat(message.toString());
							user.setChatChannel(channel);
						} else {
							player.sendMessage(prefix + "귀하는 국가에 소속되지 않았습니다.");
						}
					}
					break;
				case "lc":
				case "l":
					if (args.length == 0) {
						if (user.getChatChannel() != Channel.LOCAL_CHAT) {
							user.setChatChannel(Channel.LOCAL_CHAT);
							player.sendMessage(prefix + "모드: 지역 채팅");
						} else {
							user.setChatChannel(Channel.GLOBAL_CHAT);
							player.sendMessage(prefix + "모드: 전체 채팅");
						}
					} else {
						StringJoiner message = new StringJoiner(" ");
						for (String arg : args) {
							message.add(arg);
						}

						Channel channel = user.getChatChannel();
						user.setChatChannel(Channel.LOCAL_CHAT);
						player.chat(message.toString());
						user.setChatChannel(channel);
					}
					break;
				case "pc":
					if (args.length == 0) {
						if (user.getChatChannel() != Channel.PARTY_CHAT) {
							if (Party.hasParty(player)) {
								user.setChatChannel(Channel.PARTY_CHAT);
								player.sendMessage(prefix + "모드: 파티 채팅");
							} else {
								user.setChatChannel(Channel.GLOBAL_CHAT);
								player.sendMessage(prefix + "귀하는 파티에 소속되지 않았습니다.");
							}
						} else {
							user.setChatChannel(Channel.GLOBAL_CHAT);
							player.sendMessage(prefix + "모드: 전체 채팅");
						}
					} else {
						if (Party.hasParty(player)) {
							StringJoiner message = new StringJoiner(" ");
							for (String arg : args) {
								message.add(arg);
							}

							Channel channel = user.getChatChannel();
							user.setChatChannel(Channel.PARTY_CHAT);
							player.chat(message.toString());
							user.setChatChannel(channel);
						} else {
							player.sendMessage(prefix + "귀하는 파티에 소속되지 않았습니다.");
						}
					}
					break;
				case "mod":
				case "m":
					if (args.length == 0) {
						if (user.getChatChannel() != Channel.MODERATOR_CHAT) {
							if (player.hasPermission("mirsv.chat.moderator")) {
								user.setChatChannel(Channel.MODERATOR_CHAT);
								player.sendMessage(prefix + "모드: 모더레이터 채팅");
							} else {
								player.sendMessage(ChatColor.RED + "권한이 부족합니다.");
							}
						} else {
							user.setChatChannel(Channel.GLOBAL_CHAT);
							player.sendMessage(prefix + "모드: 전체 채팅");
						}
					} else {
						if (player.hasPermission("mirsv.chat.moderator")) {
							StringJoiner message = new StringJoiner(" ");
							for (String arg : args) {
								message.add(arg);
							}

							Channel channel = user.getChatChannel();
							user.setChatChannel(Channel.MODERATOR_CHAT);
							player.chat(message.toString());
							user.setChatChannel(channel);
						} else {
							player.sendMessage(ChatColor.RED + "권한이 부족합니다.");
						}
					}
					break;
				case "admin":
				case "a":
					if (args.length == 0) {
						if (user.getChatChannel() != Channel.ADMIN_CHAT) {
							if (player.isOp()) {
								user.setChatChannel(Channel.ADMIN_CHAT);
								player.sendMessage(prefix + "모드: 관리자 채팅");
							} else {
								player.sendMessage(ChatColor.RED + "권한이 부족합니다.");
							}
						} else {
							user.setChatChannel(Channel.GLOBAL_CHAT);
							player.sendMessage(prefix + "모드: 전체 채팅");
						}
					} else {
						if (player.isOp()) {
							StringJoiner message = new StringJoiner(" ");
							for (String arg : args) {
								message.add(arg);
							}

							Channel channel = user.getChatChannel();
							user.setChatChannel(Channel.ADMIN_CHAT);
							player.chat(message.toString());
							user.setChatChannel(channel);
						} else {
							player.sendMessage(ChatColor.RED + "권한이 부족합니다.");
						}
					}
					break;
				case "bc":
					if (user.getChatChannel() != Channel.NOTICE_CHAT) {
						if (player.isOp()) {
							user.setChatChannel(Channel.NOTICE_CHAT);
							player.sendMessage(prefix + "모드: 공지 채팅");
						} else {
							player.sendMessage(ChatColor.RED + "권한이 부족합니다.");
						}
					} else {
						user.setChatChannel(Channel.GLOBAL_CHAT);
						player.sendMessage(prefix + "모드: 전체 채팅");
					}
					break;
				case "spychat":
					if (spying.add(player)) {
						player.sendMessage(prefix + "지금부터 모든 채팅을 엿듣습니다.");
					} else {
						spying.remove(player);
						player.sendMessage(prefix + "이제 채팅을 엿듣지 않습니다.");
					}
					break;
				case "ac":
					int arg = args.length;
					if (arg == 0) {
						player.sendMessage(Messager.formatTitle(ChatColor.AQUA, ChatColor.WHITE, "채팅 명령어"));
						player.sendMessage(prefix + Messager.formatCommand("ac", "set <대상> <채널>", "대상 플레이어의 채팅 채널을 강제로 변경합니다.", true));
						player.sendMessage(prefix + "채널 : GLOBAL, LOCAL, TOWN, NATION, PARTY, MODERATOR, ADMIN");
					} else {
						if (arg == 3) {
							if (args[0].equalsIgnoreCase("set")) {
								Player target = Bukkit.getPlayerExact(args[1]);
								if (target != null) {
									User tuser = UserManager.getUser(target);
									String mode = args[2].toUpperCase();
									Optional<Channel> optional = Enums.getIfPresent(Channel.class, mode.toUpperCase().concat("_CHAT"));
									if (optional.isPresent()) {
										Channel channel = optional.get();
										tuser.setChatChannel(channel);
										target.sendMessage(prefix + "귀하의 채팅 채널이 " + channel.name() + "(으)로 변경되었습니다.");
										player.sendMessage(prefix + args[1] + "님의 채팅 채널을 변경했습니다.");
									} else {
										player.sendMessage(prefix + "존재하지 않는 채널입니다.");
									}
								} else {
									player.sendMessage(prefix + args[1] + "은(는) 존재하지 않는 플레이어입니다.");
								}
							}
						}
					}
					break;
			}
		}
		return false;
	}

	public String getPrefix(User user) {
		if (Mirsv.getPlugin().getConfig().getBoolean("enable.prefix", true)) {
			JsonObject json = user.getConfig().getJson();
			if (!json.has("prefix")) json.add("prefix", new JsonObject());
			json = json.get("prefix").getAsJsonObject();
			if (!json.has("index")) json.addProperty("index", 0);
			int index = json.get("index").getAsInt();
			if (index == 0) {
				return user.getGroupPrefix();
			} else if (index == -1) {
				if (user.getPlayer().isOp()) {
					return "";
				} else {
					json.addProperty("index", 0);
					return getPrefix(user);
				}
			} else {
				if (!json.has("list")) json.add("list", new JsonArray());
				JsonArray prefix = json.get("list").getAsJsonArray();
				if ((index - 1) >= prefix.size()) return user.getGroupPrefix();
				else return prefix.get(index - 1).getAsString();
			}
		}
		return user.getGroupPrefix();
	}
}