package com.mirsv.function.list.Cokes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import com.mirsv.Mirsv;
import com.mirsv.function.AbstractFunction;
import com.mirsv.function.list.Cokes.Party.*;
import com.mirsv.util.MirUser;
import com.mirsv.util.PlayerCollector;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Town;

import net.md_5.bungee.api.ChatColor;

public class AdvanceChat extends AbstractFunction implements CommandExecutor, Listener {
	Map<MirUser, Integer> info = new HashMap<>();
	ArrayList<Player> spy = new ArrayList<>();
	/*
	 * info값에 따른 채팅 정보
	 * 0 전체 / 1 마을 / 2 국가 / 3 파티 / 4 어드민 / 5 공지
	 */
	
	String[] prefix = new String[] {"", "[Towny] ", "[Nation] ", "[Party] ", "[Admin] ", "§4[§a공지§4] "};
	String[] color = new String[] {"§f", "§b", "§6", "§a", "§c", "§a"};
	
	public AdvanceChat() {
		super("더나은채팅", "1.0", "더 나은 타우니 채팅을 줍니다.");
	}

	@Override
	protected void onEnable() {
		registerListener(this);
		registerCommand("g", this);
		registerCommand("tc", this);
		registerCommand("nc", this);
		registerCommand("pc", this);
		registerCommand("a", this);
		registerCommand("bc", this);
		registerCommand("spychat", this);
	}

	@Override
	protected void onDisable() {
		
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (!PlayerCollector.isMirUser(p)) {
			PlayerCollector.addMirUser(p);
		}
		MirUser m = PlayerCollector.getMirUser(p);
		int gets = info.getOrDefault(m, 0);
		
		Town town = m.getTown();
		Nation nation = m.getNation();
		Party party = PartyManager.getParty(p.getUniqueId());
		
		if ((town == null && gets == 1) || (nation == null && gets == 2) || (party == null && gets == 3)) {
			p.sendMessage("[AdvanceChat] 해당 소속이 아닙니다. 전체채팅으로 변경합니다.");
			gets = 0;
			info.put(m, 0);
		}
		
		String message = e.getMessage().replace("%", "%%");
		String hash = prefix[gets];
		
		String group = getPrefix(m);
		if (group != null) group = ChatColor.translateAlternateColorCodes('&', group);
		else group = "";
		
		if (gets == 0) {
			if (town != null) {
				if (nation != null) {
					hash = "§f[§6"+nation.getName()+"§f|§b"+town.getName()+"§f] ";
				} else {
					hash = "§f[§b"+town.getName()+"§f] ";
				}
			}
			
			e.setFormat(color[gets]+hash+"§r"+group+"§r"+m.getNickname()+"§f: "+color[gets]+message);
		}
		else if (gets == 1) {
			e.getRecipients().clear();
			for (Player player : Bukkit.getOnlinePlayers()) {
				MirUser user = PlayerCollector.getMirUser(player);
				if (town.getResidents().contains(user.getResident()) && user.getPlayer().isOnline()) {
					e.getRecipients().add(user.getPlayer());
				}
			}
			e.setFormat(color[gets]+hash+"§f"+m.getNickname()+"§f: "+color[gets]+message);
		} else if (gets == 2) {
			e.getRecipients().clear();
			for (Player player : Bukkit.getOnlinePlayers()) {
				MirUser user = PlayerCollector.getMirUser(player);
				if (nation.getResidents().contains(user.getResident()) && user.getPlayer().isOnline()) {
					e.getRecipients().add(user.getPlayer());
				}
			}
			e.setFormat(color[gets]+hash+"§f[§b"+m.getTown().getName()+"§f] "+m.getNickname()+"§f: "+color[gets]+message);
		} else if (gets == 3) {
			e.getRecipients().clear();
			e.getRecipients().add(p);
			for (UUID r : PartyManager.getParty(p.getUniqueId()).getPlayers()) {
				e.getRecipients().add(Bukkit.getPlayer(r));
			}
			e.setFormat(color[gets]+hash+"§f"+m.getNickname()+"§f: "+color[gets]+message);
		} else if (gets == 4) {
			e.getRecipients().clear();
			e.getRecipients().add(p);
			for (Player t : Bukkit.getOnlinePlayers()) {
				if (t.isOp()) e.getRecipients().add(t);
			}
			e.setFormat(color[gets]+hash+"§f"+m.getNickname()+"§f: "+color[gets]+message);
		} else if (gets == 5) {
			for (Player t : Bukkit.getOnlinePlayers()) {
				t.playSound(t.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5F, 0.5F);
			}
			e.setFormat(color[gets]+hash+"§f"+group+"§f"+m.getNickname()+"§f: "+color[gets]+message);
		}
		
		for (Player spy: spy) {
			e.getRecipients().add(spy);
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (!PlayerCollector.isMirUser(e.getPlayer())) {
			PlayerCollector.addMirUser(e.getPlayer());
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (!PlayerCollector.isMirUser(p)) {
				PlayerCollector.addMirUser(p);
			}
			MirUser m = PlayerCollector.getMirUser(p);
			int gets = info.getOrDefault(m, 0);
			if (args.length == 0) {
				if (label.equals("g") && gets != 0) {
					info.put(m, 0);
					p.sendMessage("[AdvanceChat] 전체채팅으로 전환되었습니다.");
				} else if (label.equals("tc") &&  m.getTown() != null) {
					if (gets != 1) {
						info.put(m, 1);
						p.sendMessage("[AdvanceChat] 마을채팅으로 전환되었습니다.");
					} else {
						info.put(m, 0);
						p.sendMessage("[AdvanceChat] 전체채팅으로 전환되었습니다.");
					}
				} else if (label.equals("nc") && m.getNation() != null) {
					if (gets != 2) {
						info.put(m, 2);
						p.sendMessage("[AdvanceChat] 국가채팅으로 전환되었습니다.");
					} else {
						info.put(m, 0);
						p.sendMessage("[AdvanceChat] 전체채팅으로 전환되었습니다.");
					}
				} else if (label.equals("pc") && PartyManager.getParty(p.getUniqueId()) != null) {
					if (gets != 3) {
						info.put(m, 3);
						p.sendMessage("[AdvanceChat] 파티채팅으로 전환되었습니다.");
					} else {
						info.put(m, 0);
						p.sendMessage("[AdvanceChat] 전체채팅으로 전환되었습니다.");
					}
				} else if (label.equals("a") &&  p.isOp()) {
					if (gets != 4) {
						info.put(m, 4);
						p.sendMessage("[AdvanceChat] 어드민채팅으로 전환되었습니다.");
					} else {
						info.put(m, 0);
						p.sendMessage("[AdvanceChat] 전체채팅으로 전환되었습니다.");
					}
				} else if (label.equals("bc") &  p.isOp()) {
					if (gets != 5) {
						info.put(m, 5);
						p.sendMessage("[AdvanceChat] 공지채팅으로 전환되었습니다.");
					} else {
						info.put(m, 0);
						p.sendMessage("[AdvanceChat] 전체채팅으로 전환되었습니다.");
					}
				} else if (label.equals("spychat")) {
					if (spy.contains(p)) {
						spy.remove(p);
						p.sendMessage("[AdvanceChat] 스파이쳇을 종료합니다.");
					} else {
						spy.add(p);
						p.sendMessage("[AdvanceChat] 스파이쳇을 시작합니다.");
					}
				} else {
					p.sendMessage("[AdvanceChat] 이미 그 채팅이거나, 마을이 없거나 국가가 없거나 파티에 가입되어있지 않는 상태입니다.");
				}
			} else {
				String Message = args[0];
				for (int a = 1 ; a < args.length ; a++) {
					Message = Message + " "+ args[a];
				}
				
				if (label.equals("g") && gets != 0) {
					info.put(m, 0);
				} else if (label.equals("tc") && gets != 1 && m.getTown() != null) {
					info.put(m, 1);
				} else if (label.equals("nc") && gets != 2 && m.getNation() != null) {
					info.put(m, 2);
				} else if (label.equals("pc") && gets != 3 && PartyManager.getParty(p.getUniqueId()) != null) {
					info.put(m, 3);
				} else if (label.equals("a") && gets != 4 &&  p.isOp()) {
					info.put(m, 4);
				} else if (label.equals("bc") && gets != 4 &&  p.isOp()) {
					info.put(m, 5);
				}
				
				p.chat(Message);
				info.put(m, gets);
			}
		}
		return false;
	}
	
	public String getPrefix(MirUser user) {
		if (Mirsv.getPlugin().getConfig().getBoolean("enable.CustomPrefix", true)) {
			if (user.getConfig().getInt("CustomPrefix.Index", 0) == 0) {
				return user.getGroupPrefix();
			} else if (user.getConfig().getInt("CustomPrefix.Index",0) == -1) {
				if (user.getPlayer().isOp()) {
					return "";
				} else {
					user.getConfig().set("CustomPrefix.Index", 0);
					user.reloadConfig();
					return getPrefix(user);
				}
			} else {
				List<String> prefix = user.getConfig().getStringList("CustomPrefix.List");
				int index = user.getConfig().getInt("CustomPrefix.Index", 0)-1;
				if (index >= prefix.size()) return user.getGroupPrefix();
				else return prefix.get(index);
			}
		}
		return user.getGroupPrefix();
	}
}
