package com.mirsv.moonshine.Party;

import java.util.ArrayList;
import java.util.List;

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
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.mirsv.MirPlugin;

public class PartyMain extends MirPlugin implements CommandExecutor, Listener{
	String prefix = ChatColor.GOLD+"["+ChatColor.GREEN+"미르서버"+ChatColor.GOLD+"] "+ChatColor.RESET;
	List<Party> partys = new ArrayList<>();
	ArrayList<String> chat = new ArrayList<String>();
	
	public PartyMain(){
		getCommand("party", this);
		getListener(this);
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCommand(PlayerCommandPreprocessEvent event){
		String s = event.getMessage().split(" ")[0].substring(1);
		if (s.equalsIgnoreCase("tc") || s.equalsIgnoreCase("nc") || s.equalsIgnoreCase("lc") || s.equalsIgnoreCase("wc") || s.equalsIgnoreCase("g")) chat.remove(event.getPlayer().getName());
	}
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (args.length > 0){
				if (args[0].equalsIgnoreCase("create")){
					if (args.length == 2){
						if (getParty(p.getName()) != null)
							p.sendMessage(prefix+ChatColor.YELLOW+"이미 파티에 가입되어 있습니다.");
						else {
							Party party = new Party(p.getName(),args[1]);
							partys.add(party);
							Bukkit.broadcastMessage(prefix+ChatColor.YELLOW+p.getName()+"님이 파티 \'"+args[1]+"\'을(를) 만들었습니다.");
						}
					}  else {
						p.sendMessage(prefix+ChatColor.YELLOW+"사용법: /party create <닉네임>");
					}
				} else if (args[0].equalsIgnoreCase("disband")){
					if (getParty(p.getName()) == null) {
						p.sendMessage(prefix+ChatColor.YELLOW+"당신은 권한이 없습니다.");
						return false;
					}
					if (getParty(p.getName()).getOwner().equalsIgnoreCase(p.getName())){
						for (String pl : getParty(p.getName()).getPlayers()){
							chat.remove(pl);
							if (Bukkit.getOfflinePlayer(pl).isOnline()) {
								Bukkit.getPlayer(pl).sendMessage(prefix+ChatColor.YELLOW+"파티가 해체되었습니다.");
							}
						}
						partys.remove(getParty(p.getName()));
					}
					else {
						p.sendMessage(prefix+ChatColor.YELLOW+"당신은 권한이 없습니다.");
					}
				} else if (args[0].equalsIgnoreCase("add")){
					if (args.length == 2){
						for (Party party : partys){
							if (party.getOwner().equalsIgnoreCase(p.getName())){
								boolean isExist = false;
								for (Player pl : Bukkit.getOnlinePlayers()){
									if (pl.getName().equalsIgnoreCase(args[1])){
										isExist = true;
										break;
									}
								}
								if (!isExist){
									p.sendMessage(prefix+ChatColor.YELLOW+"현재 접속중인 플레이어만 추가할 수 있습니다.");
									break;
								}
								Player target = Bukkit.getPlayer(args[1]);
								for (Party p2 : partys){
									if (p2.isPlayerJoin(target.getName())){
										if (party.equals(p2)){
											p.sendMessage(prefix+ChatColor.YELLOW+"이미 당신의 파티원입니다.");
										} else {
											p.sendMessage(prefix+ChatColor.YELLOW+"다른 파티에 소속되어 있는 플레이어입니다.");
										}
										return false;
									}
								}
								party.getPlayers().add(target.getName());
								target.sendMessage(prefix+ChatColor.YELLOW+p.getName()+"님이 당신을 "+party.getPartyName()+" 파티에 추가했습니다.");
								for (String pl : party.getPlayers()){
									if (Bukkit.getOfflinePlayer(pl).isOnline()){
										Bukkit.getPlayer(pl).sendMessage(prefix+ChatColor.YELLOW+target.getName()+"님을 파티에 추가했습니다.");
									}
								}
								return false;
							}
						}
						p.sendMessage(prefix+ChatColor.YELLOW+"당신은 권한이 없습니다.");
					} else {
						p.sendMessage(prefix+ChatColor.YELLOW+"사용법: /party add <닉네임>");
					}
				} else if (args[0].equalsIgnoreCase("kick")){
					if (args.length == 2){
						if (getParty(p.getName()) != null){
							if (getParty(p.getName()).getOwner().equalsIgnoreCase(p.getName())){
								if (p.getName().equalsIgnoreCase(args[1])) {
									p.sendMessage(prefix+ChatColor.YELLOW+"자기 자신은 추방할 수 없습니다.");
									return false;
								}
								Party party = getParty(p.getName());
								for (String pl : party.getPlayers()){
									if (pl.equalsIgnoreCase(args[1])){
										party.getPlayers().remove(pl);
										if (Bukkit.getOfflinePlayer(pl).isOnline()) {
											Bukkit.getPlayer(pl).sendMessage(prefix+ChatColor.YELLOW+"파티로부터 추방당하셨습니다.");
										}
										for (String pm : party.getPlayers()){
											if (Bukkit.getOfflinePlayer(pm).isOnline()) {
												Bukkit.getPlayer(pm).sendMessage(prefix+ChatColor.YELLOW+pl+"님을 파티에서 추방시켰습니다.");
											}
										}
										return false;
									}
								}
								p.sendMessage(prefix+ChatColor.YELLOW+"파티에 존재하지 않는 플레이어입니다.");
							}
							else {
								p.sendMessage(prefix+ChatColor.YELLOW+"당신은 권한이 없습니다.");
							}
						}
						else {
							p.sendMessage(prefix+ChatColor.YELLOW+"당신은 권한이 없습니다.");
						}
					} else {
						p.sendMessage(prefix+ChatColor.YELLOW+"사용법: /party kick <닉네임>");
					}
				} else if (args[0].equalsIgnoreCase("leave")) {
					if (getParty(p.getName()) != null) {
						Party party = getParty(p.getName());
						if (getParty(p.getName()).getOwner().equalsIgnoreCase(p.getName())) {
							p.sendMessage(prefix+ChatColor.YELLOW+"파티장은 파티를 나갈 수 없습니다.");
						}
						else {
							for (String pl : party.getPlayers()) {
								if (pl.equalsIgnoreCase(p.getName())) {
									party.getPlayers().remove(pl);
									for (String pm : party.getPlayers()) {
										if (Bukkit.getOfflinePlayer(pm).isOnline()) {
											Bukkit.getPlayer(pm).sendMessage(prefix+ChatColor.YELLOW+p.getName()+"님이 파티를 떠났습니다.");
										}
									}
									break;
								}
							}
						}
					}
					else {
						p.sendMessage(prefix+ChatColor.YELLOW+"당신은 파티에 소속되어있지 않습니다.");
					}
				} else if (args[0].equalsIgnoreCase("chat")){
					if (getParty(p.getName()) != null){
						if (!chat.contains(p.getName())){
							chat.add(p.getName());
							p.sendMessage(ChatColor.GOLD+"[Towny] "+ChatColor.DARK_GREEN+"모드 설정: party");
							p.sendMessage(ChatColor.GOLD+"[Towny] "+ChatColor.DARK_GREEN+"[TownyChat] You are now talking in "+ChatColor.WHITE+"party");

						} else {
							chat.remove(p.getName());
							p.sendMessage(ChatColor.GOLD+"[Towny] "+ChatColor.DARK_GREEN+"모드 설정: general");
							p.sendMessage(ChatColor.GOLD+"[Towny] "+ChatColor.DARK_GREEN+"[TownyChat] You are now talking in "+ChatColor.WHITE+"general");
						}
					}
					else {
						p.sendMessage(prefix+ChatColor.YELLOW+"당신은 파티에 소속되어있지 않습니다.");
					}
				} else if (args[0].equalsIgnoreCase("info")){
					if (getParty(p.getName()) != null){
						Party party = getParty(p.getName());
						p.sendMessage(prefix+ChatColor.GOLD+party.getPartyName()+ChatColor.YELLOW+" 파티 정보 - 파티장: "+ChatColor.WHITE+party.getOwner());
						for (String t : party.getPlayers()){
							if (Bukkit.getOfflinePlayer(t).isOnline()) {
								p.sendMessage(prefix+ChatColor.WHITE+t+ChatColor.YELLOW+" - 체력: "+((int)(Bukkit.getPlayer(t).getHealth()))+"/20, 배고픔: "+((int)(Bukkit.getPlayer(t).getFoodLevel()))+"/20 "+ChatColor.GREEN+"(온라인)");
							}
						}
						for (String t : party.getPlayers()){
							if (!Bukkit.getOfflinePlayer(t).isOnline()) {
								p.sendMessage(prefix+ChatColor.WHITE+t+ChatColor.YELLOW+" - "+ChatColor.RED+"(오프라인)");
							}
						}
					} else {
						p.sendMessage(prefix+ChatColor.YELLOW+"당신은 파티에 소속되어있지 않습니다.");
					}
				} else if (args[0].equalsIgnoreCase("list")) {
					if (partys.size() == 0) {
						p.sendMessage(prefix+ChatColor.YELLOW+"현재 생성된 파티가 없습니다.");
					}
					else {
						p.sendMessage(prefix+ChatColor.YELLOW+"========== 파티 목록 ==========");
						for (Party party : partys) {
							p.sendMessage(prefix+ChatColor.YELLOW+party.getPartyName()+" - 파티장: "+party.getOwner()+", 파티원 "+party.getPlayers().size());
						}
					}
				} else if (args[0].equalsIgnoreCase("?")){
					p.sendMessage(prefix+ChatColor.YELLOW+"/party create <이름>: 파티를 만듭니다.");
					p.sendMessage(prefix+ChatColor.YELLOW+"/party disband: 파티를 해체합니다.");
					p.sendMessage(prefix+ChatColor.YELLOW+"/party add <닉네임>: 파티원을 추가합니다.");
					p.sendMessage(prefix+ChatColor.YELLOW+"/party kick <닉네임>: 파티원을 추방합니다.");
					p.sendMessage(prefix+ChatColor.YELLOW+"/party leave: 소속된 파티에서 떠납니다.");
					p.sendMessage(prefix+ChatColor.YELLOW+"/party info: 파티 정보를 불러옵니다.");
					p.sendMessage(prefix+ChatColor.YELLOW+"/party list: 존재하는 파티의 목록을 확인합니다.");
					p.sendMessage(prefix+ChatColor.YELLOW+"/party chat: 파티채팅을 시작합니다. (/pc)");
				} else {
					p.sendMessage(prefix+ChatColor.YELLOW+"사용법: /party ?");
				}
			} else {
				p.sendMessage(prefix+ChatColor.YELLOW+"사용법: /party ?");
			}
		}
		return false;
	}
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(AsyncPlayerChatEvent event){
		Player p = event.getPlayer();
		if (chat.contains(p.getName())){
			event.getRecipients().clear();
			event.setFormat("["+ChatColor.DARK_AQUA+"PC"+ChatColor.WHITE+"] "+event.getPlayer().getName()+": "+ChatColor.LIGHT_PURPLE+event.getMessage());
			if (getParty(p.getName()) != null){
				for (String t : getParty(p.getName()).getPlayers()){
					if (Bukkit.getOfflinePlayer(t).isOnline()) {
						event.getRecipients().add(Bukkit.getPlayer(t));
					}
				}
			}
		}
	}
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		if (getParty(p.getName()) != null) {
			for (String pl : getParty(p.getName()).getPlayers()) {
				if (Bukkit.getOfflinePlayer(pl).isOnline() && !pl.equalsIgnoreCase(p.getName())) {
					Bukkit.getPlayer(pl).sendMessage(ChatColor.YELLOW+"파티원 "+p.getName()+"님이 서버에서 퇴장하셨습니다.");
				}
			}
		}
	}
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		if (getParty(p.getName()) != null) {
			for (String pl : getParty(p.getName()).getPlayers()) {
				if (Bukkit.getOfflinePlayer(pl).isOnline() && !pl.equalsIgnoreCase(p.getName())) {
					Bukkit.getPlayer(pl).sendMessage(ChatColor.YELLOW+"파티원 "+p.getName()+"님이 서버에 입장하셨습니다.");
				}
			}
		}
	}
	public Party getParty(String s){
		Party result = null;
		for (Party party : partys){
			for (String p : party.getPlayers()) {
				if (p.equalsIgnoreCase(s)) {
					result = party;
				}
			}
		}
		return result;
	}
}
