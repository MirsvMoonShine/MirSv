package com.mirsv.function.list.Cokes.party;

import com.mirsv.function.AbstractFunction;
import com.mirsv.function.autosave.AutoSave;
import com.mirsv.function.autosave.AutoSaveManager;
import com.mirsv.function.list.Cokes.party.Party.Flag;
import com.mirsv.util.Messager;
import com.mirsv.util.data.FileUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringJoiner;
import java.util.UUID;

import static com.mirsv.function.list.Cokes.party.Party.getParties;
import static com.mirsv.function.list.Cokes.party.Party.getParty;
import static com.mirsv.function.list.Cokes.party.Party.hasParty;

public class PartyManager extends AbstractFunction implements CommandExecutor, Listener, AutoSave {

	public PartyManager() {
		super("파티", "1.1", "미르서버 파티 시스템입니다.");
	}

	private static final String prefix = ChatColor.DARK_PURPLE + "파티 " + ChatColor.WHITE + "| ";
	private File data = FileUtil.newFile("Party/Party.dat");

	@Override
	protected void onEnable() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(data));
			String str;
			while ((str = reader.readLine()) != null) {
				String[] split = str.split(" ");
				Party party = new Party(UUID.fromString(split[2]), split[0], Integer.parseInt(split[1]));
				for (int i = 3; i < split.length; i++) {
					party.addPlayer(UUID.fromString(split[i]));
				}
			}
			reader.close();
		} catch (IOException ex) {
			Messager.sendErrorMessage("파티 데이터를 불러오는 도중 오류가 발생하였습니다.");
			ex.printStackTrace();
		}

		registerCommand("party", this, new PartyTabCompleter());
		registerCommand("pc", this);
		registerListener(this);

		AutoSaveManager.registerAutoSave(this);
	}

	@Override
	protected void onDisable() {
	}

	@Override
	public void Save() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(data, false));
			for (Party party : Party.getParties()) {
				StringJoiner joiner = new StringJoiner(" ").add(party.getName()).add(String.valueOf(party.getFlag())).add(party.getOwner().toString());
				for (OfflinePlayer player : party.getPlayers())
					if (!party.getOwner().equals(player.getUniqueId())) joiner.add(player.getUniqueId().toString());
				writer.write(joiner.toString());
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			Messager.sendErrorMessage("파티 데이터를 저장하던 도중 오류가 발생하였습니다.");
			e.printStackTrace();
		}
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			Party damagerParty = getParty((Player) e.getDamager());
			Party victimParty = getParty((Player) e.getEntity());
			if (damagerParty != null && damagerParty.equals(victimParty) && !damagerParty.hasFlag(Flag.PVP)) {
				e.setCancelled(true);
				e.getDamager().sendMessage(ChatColor.RED + "파티원을 공격할 수 없습니다!");
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("create")) {
					if (args.length == 2) {
						if (hasParty(p))
							p.sendMessage(prefix + "이미 파티에 가입되어있습니다.");
						else {
							new Party(p, args[1], Flag.PVP);
							Bukkit.broadcastMessage(prefix + p.getName() + "님이 파티 '" + args[1] + "'을(를) 만드셨습니다.");
						}
					} else p.sendMessage(prefix + "사용법: /party create <이름>");
				} else if (args[0].equalsIgnoreCase("disband")) {
					if (!hasParty(p))
						p.sendMessage(prefix + "파티에 가입되어있지 않습니다.");
					else {
						Party party = getParty(p);
						if (party.getOwner().equals(p.getUniqueId())) {
							party.disband();
						} else p.sendMessage(prefix + "귀하는 파티를 해산시킬 권한이 없습니다.");
					}
				} else if (args[0].equalsIgnoreCase("add")) {
					if (args.length == 2) {
						Party party = Party.getParty(p);
						if (party.getOwner().equals(p.getUniqueId())) {
							Player exact = Bukkit.getPlayerExact(args[1]);
							if (exact != null) {
								if (hasParty(exact)) {
									if (getParty(exact).equals(party))
										p.sendMessage(prefix + "이미 귀하의 파티원입니다.");
									else
										p.sendMessage(prefix + "이미 다른 파티에 소속되어 있는 플레이어입니다.");
									return false;
								}
								for (OfflinePlayer player : party.getPlayers())
									if (player.isOnline())
										player.getPlayer().sendMessage(prefix + ChatColor.YELLOW + exact.getName() + ChatColor.WHITE + "님이 파티의 일원이 되셨습니다.");
								party.addPlayer(exact.getUniqueId());
								exact.sendMessage(prefix + p.getName() + "님이 귀하를 " + party.getName() + " 파티의 일원으로 추가했습니다.");
								return false;
							} else p.sendMessage(prefix + "현재 접속중인 플레이어만 추가할 수 있습니다.");
						} else p.sendMessage(prefix + "귀하는 파티에 플레이어를 초대할 수 있는 권한이 없습니다.");
					} else p.sendMessage(prefix + "사용법: /party add <대상>");
				} else if (args[0].equalsIgnoreCase("join")) {
					if (args.length == 2) {
						if (hasParty(p)) {
							p.sendMessage(prefix + "귀하는 이미 파티에 소속되어 있습니다.");
							return false;
						}
						if (hasParty(args[1])) {
							Party party = getParty(args[1]);
							if (!party.hasFlag(Flag.OPEN)) {
								p.sendMessage(prefix + "해당 파티는 개방된 파티가 아닙니다.");
								return false;
							} else {
								party.addPlayer(p.getUniqueId());
								for (OfflinePlayer player : party.getPlayers())
									if (player.isOnline())
										player.getPlayer().sendMessage(prefix + ChatColor.YELLOW + p.getName() + ChatColor.WHITE + "님이 파티의 일원이 되셨습니다.");
								return false;
							}
						} else p.sendMessage(prefix + "해당 파티는 존재하지 않습니다.");
						return false;
					}
					p.sendMessage(prefix + "사용법: /party join <파티>");
				} else if (args[0].equalsIgnoreCase("kick")) {
					if (args.length == 2) {
						if (!hasParty(p)) {
							p.sendMessage(prefix + "파티에 가입되어있지 않습니다.");
							return false;
						}
						if (!getParty(p).getOwner().equals(p.getUniqueId())) {
							p.sendMessage(prefix + "귀하는 플레이어를 추방할 권한이 없습니다.");
							return false;
						}
						Party party = getParty(p);
						OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
						if (!target.hasPlayedBefore() || !party.hasPlayer(target)) {
							p.sendMessage(prefix + "해당 플레이어는 파티의 일원이 아닙니다.");
							return false;
						}

						if (p.getUniqueId().equals(target.getUniqueId())) {
							p.sendMessage(prefix + "자기 자신은 추방할 수 없습니다.");
							return false;
						}
						party.removePlayer(target.getUniqueId());
						if (target.isOnline())
							target.getPlayer().sendMessage(prefix + p.getName() + "님이 귀하를 " + party.getName() + " 파티에서 추방하셨습니다.");
						for (OfflinePlayer player : party.getPlayers())
							if (player.isOnline())
								player.getPlayer().sendMessage(prefix + ChatColor.YELLOW + target.getName() + ChatColor.WHITE + "님이 파티에서 추방되셨습니다.");
					} else p.sendMessage(prefix + "사용법: /party kick <대상>");
				} else if (args[0].equalsIgnoreCase("leave")) {
					if (hasParty(p)) {
						Party party = getParty(p);
						if (party.getOwner().equals(p.getUniqueId())) p.sendMessage(prefix + "파티장은 파티를 나갈 수 없습니다.");
						else {
							party.removePlayer(p.getUniqueId());
							if (p.isOnline()) p.getPlayer().sendMessage(prefix + party.getName() + " 파티를 떠나셨습니다.");
							for (OfflinePlayer player : party.getPlayers())
								if (player.isOnline())
									player.getPlayer().sendMessage(prefix + ChatColor.YELLOW + p.getName() + ChatColor.WHITE + "님이 파티를 떠나셨습니다.");
						}
					} else p.sendMessage(prefix + "파티에 가입되어있지 않습니다.");
				} else if (args[0].equalsIgnoreCase("toggle")) {
					if (!hasParty(p)) {
						p.sendMessage(prefix + "귀하는 파티에 소속되지 않았습니다.");
						return false;
					}
					if (!getParty(p).getOwner().equals(p.getUniqueId())) {
						p.sendMessage(prefix + "귀하는 파티의 설정을 변경할 권한이 없습니다.");
						return false;
					}
					if (args.length == 1) {
						p.sendMessage(ChatColor.BLUE + "/party toggle pvp: " + ChatColor.WHITE + "파티원 간의 pvp 허용 여부을 변경합니다.");
						p.sendMessage(ChatColor.BLUE + "/party toggle open: " + ChatColor.WHITE + "초대 없이 파티에 가입 가능 여부를 변경합니다.");
					} else if (args[1].equalsIgnoreCase("pvp")) {
						Party party = getParty(p);
						party.toggleFlag(Flag.PVP);
						String msg = prefix + "파티원 간 PvP: " + (party.hasFlag(Flag.PVP) ? "§a활성화" : "§c비활성화");
						for (OfflinePlayer player : party.getPlayers())
							if (player.isOnline())
								player.getPlayer().sendMessage(msg);
					} else if (args[1].equalsIgnoreCase("open")) {
						Party party = getParty(p);
						party.toggleFlag(Flag.OPEN);
						String msg = prefix + "파티 개방: " + (party.hasFlag(Flag.OPEN) ? "§a활성화" : "§c비활성화");
						for (OfflinePlayer player : party.getPlayers())
							if (player.isOnline())
								player.getPlayer().sendMessage(msg);
					} else {
						p.sendMessage(ChatColor.BLUE + "/party toggle pvp: " + ChatColor.WHITE + "파티원 간의 pvp 허용 여부을 변경합니다.");
						p.sendMessage(ChatColor.BLUE + "/party toggle open: " + ChatColor.WHITE + "초대 없이 파티에 가입 가능 여부를 변경합니다.");
					}
				} else if (args[0].equalsIgnoreCase("info")) {
					if (hasParty(p)) {
						Party party = getParty(p);
						p.sendMessage(prefix + party.getName() + ChatColor.YELLOW + " 파티 정보 - 파티장: " + ChatColor.WHITE + Bukkit.getOfflinePlayer(party.getOwner()).getName());
						p.sendMessage(ChatColor.AQUA + " PvP: " + (party.hasFlag(Flag.PVP) ? ChatColor.GREEN + "켜짐" : ChatColor.RED + "꺼짐") + " " + ChatColor.AQUA + "개방: " + (party.hasFlag(Flag.OPEN) ? ChatColor.GREEN + "켜짐" : ChatColor.RED + "꺼짐"));
						for (OfflinePlayer player : party.getPlayers())
							if (player.isOnline()) {
								p.sendMessage(ChatColor.WHITE + " ◇ " + player.getName() + ChatColor.YELLOW + " - 체력: " + ((int) (player.getPlayer().getHealth())) + " / 20, 배고픔: " + player.getPlayer().getFoodLevel() + " / 20 " + ChatColor.GREEN + "(온라인)");
							} else {
								p.sendMessage(ChatColor.WHITE + " ◇ " + player.getName() + ChatColor.YELLOW + " - " + ChatColor.RED + "(오프라인)");
							}
					} else p.sendMessage(prefix + "귀하는 파티에 소속되지 않았습니다.");
				} else if (args[0].equalsIgnoreCase("list")) {
					if (getParties().size() == 0)
						p.sendMessage(prefix + "생성된 파티가 없습니다.");
					else {
						p.sendMessage(Messager.getPrefix() + ChatColor.DARK_GRAY + "============ " + ChatColor.YELLOW + "파티 목록" + ChatColor.DARK_GRAY + " ============");
						for (Party party : getParties())
							p.sendMessage(ChatColor.WHITE + "◇ " + ChatColor.GOLD + ChatColor.BOLD + party.getName() + ChatColor.YELLOW + " - 파티장: " + ChatColor.WHITE + Bukkit.getOfflinePlayer(party.getOwner()).getName() + ChatColor.YELLOW + ", 파티원 " + ChatColor.WHITE + party.getPlayers().size() + ChatColor.AQUA + (party.hasFlag(Flag.OPEN) ? " (개방)" : ""));
					}
				} else if (args[0].equalsIgnoreCase("?")) {
					p.sendMessage("----- " + ChatColor.AQUA + "미르서버 파티 플러그인" + ChatColor.WHITE + " -----");
					p.sendMessage(ChatColor.BLUE + "/party create <이름>: " + ChatColor.WHITE + "파티를 만듭니다.");
					p.sendMessage(ChatColor.BLUE + "/party disband: " + ChatColor.WHITE + "파티를 해체합니다.");
					p.sendMessage(ChatColor.BLUE + "/party add <닉네임>: " + ChatColor.WHITE + "파티원을 추가합니다.");
					p.sendMessage(ChatColor.BLUE + "/party kick <닉네임>: " + ChatColor.WHITE + "파티원을 추방합니다.");
					p.sendMessage(ChatColor.BLUE + "/party join <파티이름>: " + ChatColor.WHITE + "개방된 파티에 가입합니다.");
					p.sendMessage(ChatColor.BLUE + "/party leave: " + ChatColor.WHITE + "소속된 파티에서 떠납니다.");
					p.sendMessage(ChatColor.BLUE + "/party toggle: " + ChatColor.WHITE + "파티의 설정을 변경합니다.");
					p.sendMessage(ChatColor.BLUE + "/party info: " + ChatColor.WHITE + "파티 정보를 불러옵니다.");
					p.sendMessage(ChatColor.BLUE + "/party list: " + ChatColor.WHITE + "존재하는 파티의 목록을 확인합니다.");
				} else p.sendMessage(prefix + "사용법: /party ?");
			} else p.sendMessage(prefix + "사용법: /party ?");
		}
		return false;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		if (hasParty(p)) {
			getParty(p).sendMessage(prefix + "파티원 " + ChatColor.YELLOW + p.getName() + ChatColor.WHITE + "님이 서버를 퇴장하셨습니다.");
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		if (hasParty(p)) {
			getParty(p).sendMessage(prefix + "파티원 " + ChatColor.YELLOW + p.getName() + ChatColor.WHITE + "님이 서버에 입장하셨습니다.");
		}
	}

}