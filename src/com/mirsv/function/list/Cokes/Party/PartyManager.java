package com.mirsv.function.list.Cokes.Party;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.mirsv.function.AbstractFunction;
import com.mirsv.function.autosave.AutoSave;
import com.mirsv.function.autosave.AutoSaveManager;
import com.mirsv.util.Messager;
import com.mirsv.util.data.FileUtil;

public class PartyManager extends AbstractFunction implements CommandExecutor, Listener, AutoSave {

	public PartyManager() {
		super("파티", "1.0", "미르서버 파티 시스템입니다.");
	}

	private File data = FileUtil.getFile("Party/Party.dat");
	private static List<Party> Parties = new ArrayList<Party>();

	public static Party getParty(UUID uuid) {
		for(Party party: Parties) for(UUID u: party.getPlayers()) if(u.equals(uuid)) return party;
		return null;
	}
	
	@Override
	protected void onEnable() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(data));
			String s;
			while((s = br.readLine()) != null) {
				String[] Array = s.split(" ");
				boolean pvp = (Integer.parseInt("0" + Array[1]) == 1) ? true : false;
				boolean open = (Integer.parseInt("0" + Array[2]) == 1) ? true : false;
				Party party = new Party(UUID.fromString(Array[3]), Array[0], pvp, open);
				for(int i = 4; i < Array.length; i++) party.getPlayers().add(UUID.fromString(Array[i]));
				
				Parties.add(party);
			}
			br.close();
		} catch(IOException ex) {
			Messager.sendErrorMessage("파티 데이터를 저장하던 도중 오류가 발생하였습니다.");
			ex.printStackTrace();
		}
		
		registerCommand("party", this);
		registerCommand("pc", this);
		registerListener(this);
		
		AutoSaveManager.registerAutoSave(this);
	}
	
	@Override
	protected void onDisable() {}

	@Override
	public void Save() {
		try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(data, false));
            for(Party party: Parties) {
            	int PVP = party.isPVP() ? 1 : 0;
            	int OPEN = party.isOPEN() ? 1 : 0;
            	String s = party.getPartyName() + " " + PVP + " " + OPEN + " " + party.getOwner() + " ";
            	for(UUID u: party.getPlayers()) if(!party.getOwner().equals(u)) s += u + " ";
            	bw.write(s);
            	bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch(IOException e) {
			Messager.sendErrorMessage("파티 데이터를 저장하던 도중 오류가 발생하였습니다.");
            e.printStackTrace();
		}
	}
	
	private List<UUID> chat = new ArrayList<UUID>();

	@EventHandler(priority = EventPriority.HIGH)
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if(chat.contains(p.getUniqueId())) {
			e.getRecipients().clear();
			if(getParty(p.getUniqueId()) != null) {
				e.setMessage(e.getMessage().replaceAll("%", "%%"));
				e.setFormat(ChatColor.translateAlternateColorCodes('&', "&f[&d파티&f][&5" + getParty(p.getUniqueId()).getPartyName() + "&f] " + p.getName() + ": &d" + e.getMessage()));
				for(UUID u: getParty(p.getUniqueId()).getPlayers()) if(Bukkit.getOfflinePlayer(u).isOnline()) e.getRecipients().add(Bukkit.getPlayer(u));
			} else {
				Messager.sendMessage(p, ChatColor.translateAlternateColorCodes('&', Messager.getPrefix() + "&e당신은 파티에 소속되어있지 않습니다. /g 명령어를 통해 전체 채팅으로 변경해주세요!"));
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCommand(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		if(chat.contains(p.getUniqueId())) {
			String s = e.getMessage().split(" ")[0];
			if(s.equalsIgnoreCase("/tc") || s.equalsIgnoreCase("/nc") || s.equalsIgnoreCase("/lc") || s.equalsIgnoreCase("/wc") || s.equalsIgnoreCase("/g") || s.equalsIgnoreCase("/admin") || s.equalsIgnoreCase("/mod") || s.equalsIgnoreCase("/a") || s.equalsIgnoreCase("/l") || s.equalsIgnoreCase("/m")) {
				chat.remove(p.getUniqueId());
				Messager.sendMessage(p, ChatColor.translateAlternateColorCodes('&', Messager.getPrefix() + "파티 채팅을 종료합니다."));
			}
		}
	}
	
	@EventHandler
	public void onAttack(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			Player player1 = (Player) e.getDamager();
			Player player2 = (Player) e.getEntity();
			Party p1 = PartyManager.getParty(player1.getUniqueId());
			Party p2 = PartyManager.getParty(player2.getUniqueId());
			if(p1 == p2 && p1 != null){
				if(p1.isPVP()) return;
				e.setCancelled(true);
				Messager.sendMessage(player1, ChatColor.translateAlternateColorCodes('&', Messager.getPrefix() + "&e파티원간 PVP가 비활성화되어있습니다."));
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(string.equalsIgnoreCase("pc")) {
				if(args.length > 0) {
					if(getParty(p.getUniqueId()) != null) {
						String Message = "";
						for(int i = 0; i < args.length; i++) Message += args[i] + " ";
						
						chat.add(p.getUniqueId());
						p.chat(Message);
						chat.remove(p.getUniqueId());
					} else p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "당신은 파티에 소속되어있지 않습니다.");
				} else {
					if(getParty(p.getUniqueId()) != null) {
						if(!chat.contains(p.getUniqueId())) {
							chat.add(p.getUniqueId());
							p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "파티 채팅을 시작합니다.");
						}
						else {
							chat.remove(p.getUniqueId());
							p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "파티 채팅을 종료합니다.");
						}
					}
					else p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "당신은 파티에 소속되어있지 않습니다.");
				}
				return false;
			}
			if(args.length > 0) {
				if(args[0].equalsIgnoreCase("create")) {
					if(args.length == 2) {
						if(getParty(p.getUniqueId()) != null)
							p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "이미 파티에 가입되어 있습니다.");
						else {
							Party party = new Party(p.getUniqueId(), args[1], true, false);
							Parties.add(party);
							Bukkit.broadcastMessage(Messager.getPrefix() + ChatColor.YELLOW + p.getName() + "님이 파티 \'" + args[1] + "\'을(를) 만들었습니다.");
						}
					}
					else p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "사용법: /party create <닉네임>");
				}
				else if(args[0].equalsIgnoreCase("disband")) {
					if(getParty(p.getUniqueId()) == null) p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "당신은 권한이 없습니다.");
					else {
						if(getParty(p.getUniqueId()).getOwner().equals(p.getUniqueId())) {
							for(UUID pl: getParty(p.getUniqueId()).getPlayers()) {
								chat.remove(pl);
								if(Bukkit.getOfflinePlayer(pl).isOnline()) Bukkit.getPlayer(pl).sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "파티가 해체되었습니다.");
							}
							Parties.remove(getParty(p.getUniqueId()));
						}
						else p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "당신은 권한이 없습니다.");
					}
				}
				else if(args[0].equalsIgnoreCase("add")) {
					if(args.length == 2) {
						for(Party party: Parties) {
							if(party.getOwner().equals(p.getUniqueId())) {
								boolean isExist = false;
								for(Player pl: Bukkit.getOnlinePlayers()) {
									if(pl.getName().equalsIgnoreCase(args[1])) {
										isExist = true;
										break;
									}
								}
								if(!isExist) p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "현재 접속중인 플레이어만 추가할 수 있습니다.");
								else {
									Player target = Bukkit.getPlayer(args[1]);
									for(Party p2: Parties) {
										if(p2.isPlayerJoin(target.getUniqueId())) {
											if(party.equals(p2)) p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "이미 당신의 파티원입니다.");
											else p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "다른 파티에 소속되어 있는 플레이어입니다.");
											return false;
										}
									}
									for(UUID u: party.getPlayers()) if(Bukkit.getOfflinePlayer(u).isOnline()) Bukkit.getPlayer(u).sendMessage(Messager.getPrefix() + ChatColor.YELLOW + target.getName() + "님을 파티에 추가했습니다.");
									party.getPlayers().add(target.getUniqueId());
									target.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + p.getName() + "님이 당신을 " + party.getPartyName() + " 파티에 추가했습니다.");
									return false;
								}
							}
						}
						p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "당신은 권한이 없습니다.");
					}
					else p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "사용법: /party add <파티이름>");
				}
				else if(args[0].equalsIgnoreCase("join")) {
					if(args.length == 2) {
						if(getParty(p.getUniqueId()) != null) {
							p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "당신은 이미 파티에 소속되어 있습니다.");
							return false;
						}
						for(Party party : Parties) {
							if(party.getPartyName().equalsIgnoreCase(args[1])) {
								if(!party.isOPEN()) {
									p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "해당 파티는 개방되어있지 않습니다.");
									return false;
								}
								party.getPlayers().add(p.getUniqueId());
								for(UUID uuid: party.getPlayers()) if(Bukkit.getOfflinePlayer(uuid).isOnline()) Bukkit.getPlayer(uuid).sendMessage(Messager.getPrefix() + ChatColor.YELLOW + p.getName() + "님이 파티에 가입했습니다.");
								return false;
							}
						}
						p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "해당 파티는 존재하지 않습니다.");
						return false;
					}
					p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "사용법: /party join <파티이름>");
				}
				else if(args[0].equalsIgnoreCase("kick")) {
					if(args.length == 2) {
						if(!Bukkit.getOfflinePlayer(args[1]).hasPlayedBefore()) {
							p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "파티에 존재하지 않는 플레이어입니다.");
							return false;
						}
						if(getParty(p.getUniqueId()) != null) {
							if(getParty(p.getUniqueId()).getOwner().equals(p.getUniqueId())) {
								if(p.getName().equalsIgnoreCase(args[1])) {
									p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "자기 자신은 추방할 수 없습니다.");
									return false;
								}
								Party party = getParty(p.getUniqueId());
								for(UUID u: party.getPlayers()) {
									if(u.equals(Bukkit.getOfflinePlayer(args[1]).getUniqueId())) {
										party.getPlayers().remove(u);
										if(Bukkit.getOfflinePlayer(u).isOnline()) Bukkit.getPlayer(u).sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "파티로부터 추방당하셨습니다.");
										for(UUID um: party.getPlayers()) if(Bukkit.getOfflinePlayer(um).isOnline()) Bukkit.getPlayer(um).sendMessage(Messager.getPrefix() + ChatColor.YELLOW + Bukkit.getOfflinePlayer(args[1]).getName() + "님을 파티에서 추방시켰습니다.");
										return false;
									}
								}
								p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "파티에 존재하지 않는 플레이어입니다.");
							}
							else p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "당신은 권한이 없습니다.");
						}
						else p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "당신은 권한이 없습니다.");
					}
					else p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "사용법: /party kick <닉네임>");
				}
				else if(args[0].equalsIgnoreCase("leave")) {
					if(getParty(p.getUniqueId()) != null) {
						Party party = getParty(p.getUniqueId());
						if(getParty(p.getUniqueId()).getOwner().equals(p.getUniqueId())) p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "파티장은 파티를 나갈 수 없습니다.");
						else {
							for(UUID u: party.getPlayers()) {
								if(u.equals(p.getUniqueId())) {
									party.getPlayers().remove(u);
									for(UUID um: party.getPlayers()) if(Bukkit.getOfflinePlayer(um).isOnline()) Bukkit.getPlayer(um).sendMessage(Messager.getPrefix() + ChatColor.YELLOW + p.getName() + "님이 파티를 떠났습니다.");
									p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "파티를 떠났습니다.");
									break;
								}
							}
						}
					}
					else {
						p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "당신은 파티에 소속되어있지 않습니다.");
					}
				}
				else if(args[0].equalsIgnoreCase("toggle")) {
					if(getParty(p.getUniqueId()) == null) {
						p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "당신은 파티에 소속되어있지 않습니다.");
						return false;
					}
					if(!getParty(p.getUniqueId()).getOwner().equals(p.getUniqueId())) {
						p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "당신은 권한이 없습니다.");
	                    return false;
	                }
					if(args.length == 1) {
						p.sendMessage(ChatColor.BLUE + "/party toggle pvp: " + ChatColor.WHITE + "파티원 간의 pvp 허용 여부을 변경합니다.");
	                    p.sendMessage(ChatColor.BLUE + "/party toggle open: " + ChatColor.WHITE + "초대 없이 파티에 가입 가능 여부를 변경합니다.");
	                }
					else if(args[1].equalsIgnoreCase("pvp")) {
						if(getParty(p.getUniqueId()).TogglePvP()) {
							for(UUID uuid : getParty(p.getUniqueId()).getPlayers()) {
								if(Bukkit.getOfflinePlayer(uuid).isOnline()) {
									Bukkit.getPlayer(uuid).sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "이제 파티원 간 PvP가 허용됩니다.");
								}
							}
	                    }
						else {
							for(UUID uuid : getParty(p.getUniqueId()).getPlayers()) {
								if(Bukkit.getOfflinePlayer(uuid).isOnline()) {
									Bukkit.getPlayer(uuid).sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "이제 파티원 간 PvP가 금지됩니다.");
								}
							}
						}
					}
					else if(args[1].equalsIgnoreCase("open")) {
						if(getParty(p.getUniqueId()).ToggleOpen()) {
							for(UUID uuid : getParty(p.getUniqueId()).getPlayers()) {
								if(Bukkit.getOfflinePlayer(uuid).isOnline()) {
									Bukkit.getPlayer(uuid).sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "이제 파티장의 초대 없이 플레이어가 가입할 수 있습니다.");
								}
							}
	                    }
						else {
							for(UUID uuid : getParty(p.getUniqueId()).getPlayers()) {
								if(Bukkit.getOfflinePlayer(uuid).isOnline()) {
									Bukkit.getPlayer(uuid).sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "이제 파티장의 초대 없이 플레이어가 가입할 수 없습니다.");
								}
							}
	                    }
					}
					else {
						p.sendMessage(ChatColor.BLUE + "/party toggle pvp: " + ChatColor.WHITE + "파티원 간의 pvp 허용 여부을 변경합니다.");
	                    p.sendMessage(ChatColor.BLUE + "/party toggle open: " + ChatColor.WHITE + "초대 없이 파티에 가입 가능 여부를 변경합니다.");
	                }
				}
				else if(args[0].equalsIgnoreCase("chat")) {
					if(args.length > 1) {
						String Message = "";
						for(int i = 1; i < args.length; i++) Message += args[i] + " ";
						Message.replaceAll("%", "%%");
						if(getParty(p.getUniqueId()) != null) {
							for(UUID u: getParty(p.getUniqueId()).getPlayers()) if(Bukkit.getOfflinePlayer(u).isOnline()) Bukkit.getPlayer(u).sendMessage("[" + ChatColor.LIGHT_PURPLE + "PC" + ChatColor.WHITE + "][" + ChatColor.DARK_PURPLE + getParty(p.getUniqueId()).getPartyName() + ChatColor.WHITE + "] " + Messager.getPrefix() + p.getName() + ": " + ChatColor.LIGHT_PURPLE + Message);
							Bukkit.getConsoleSender().sendMessage("[" + ChatColor.LIGHT_PURPLE + "PC" + ChatColor.WHITE + "][" + ChatColor.DARK_PURPLE + getParty(p.getUniqueId()).getPartyName() + ChatColor.WHITE + "] " + Messager.getPrefix() + p.getName() + ": " + ChatColor.LIGHT_PURPLE + Message);
						}
						else p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "당신은 파티에 소속되어있지 않습니다.");
					}
					else {
						if(getParty(p.getUniqueId()) != null) {
							if(!chat.contains(p.getUniqueId())) {
								chat.add(p.getUniqueId());
								p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "파티 채팅을 시작합니다.");
							}
							else {
								chat.remove(p.getUniqueId());
								p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "파티 채팅을 종료합니다.");
							}
						}
						else p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "당신은 파티에 소속되어있지 않습니다.");
					}
				}
				else if(args[0].equalsIgnoreCase("info")) {
					if(getParty(p.getUniqueId()) != null) {
						Party party = getParty(p.getUniqueId());
						p.sendMessage(Messager.getPrefix() + ChatColor.GOLD + party.getPartyName() + ChatColor.YELLOW + " 파티 정보 - 파티장: " + ChatColor.WHITE + Bukkit.getOfflinePlayer(party.getOwner()).getName());
						String PvPString = "";
	                    if(party.isPVP()) PvPString = ChatColor.GREEN + "켜짐";
	                    else PvPString = ChatColor.RED + "꺼짐";
	                    String OpenString = "";
	                    if(party.isOPEN()) OpenString = ChatColor.GREEN + "켜짐";
	                    else OpenString = ChatColor.RED + "꺼짐";
	                    p.sendMessage(ChatColor.AQUA + " PvP: " + PvPString + " " + ChatColor.AQUA + "개방: " + OpenString);
						for(UUID u: party.getPlayers()) if(Bukkit.getOfflinePlayer(u).isOnline()) p.sendMessage(ChatColor.WHITE + " ◇ " + Bukkit.getOfflinePlayer(u).getName() + ChatColor.YELLOW + " - 체력: " + ((int)(Bukkit.getPlayer(u).getHealth())) + "/20, 배고픔: " + ((int)(Bukkit.getPlayer(u).getFoodLevel())) + "/20 " + ChatColor.GREEN + "(온라인)");
						for(UUID u: party.getPlayers()) if(!Bukkit.getOfflinePlayer(u).isOnline()) p.sendMessage(ChatColor.WHITE + " ◇ " + Bukkit.getOfflinePlayer(u).getName() + ChatColor.YELLOW + " - " + ChatColor.RED + "(오프라인)");
					}
					else p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "당신은 파티에 소속되어있지 않습니다.");
				}
				else if(args[0].equalsIgnoreCase("list")) {
					if(Parties.size() == 0) p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "현재 생성된 파티가 없습니다.");
					else {
						p.sendMessage(Messager.getPrefix() + ChatColor.DARK_GRAY + "============ " + ChatColor.YELLOW + "파티 목록" + ChatColor.DARK_GRAY + " ============");
						for(Party party: Parties) p.sendMessage(ChatColor.WHITE + "◇ " + ChatColor.GOLD + ChatColor.BOLD + party.getPartyName() + ChatColor.YELLOW + " - 파티장: " + ChatColor.WHITE + Bukkit.getOfflinePlayer(party.getOwner()).getName() + ChatColor.YELLOW + ", 파티원 " + ChatColor.WHITE + party.getPlayers().size() + ChatColor.AQUA + (party.isOPEN() ? " (개방)" : ""));
					}
				}
				else if(args[0].equalsIgnoreCase("?")) {
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
					p.sendMessage(ChatColor.BLUE + "/party chat: " + ChatColor.WHITE + "파티채팅을 시작합니다. (/pc)");
				}
				else p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "사용법: /party ?");
			}
			else p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "사용법: /party ?");
		}
		return false;
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		if(getParty(p.getUniqueId()) != null) for(UUID u: getParty(p.getUniqueId()).getPlayers()) if(Bukkit.getOfflinePlayer(u).isOnline() && !u.equals(p.getName())) Bukkit.getPlayer(u).sendMessage(ChatColor.YELLOW + "파티원 " + p.getName() + "님이 서버에서 퇴장하셨습니다.");
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		if(getParty(p.getUniqueId()) != null) for(UUID u: getParty(p.getUniqueId()).getPlayers()) if(Bukkit.getOfflinePlayer(u).isOnline() && !u.equals(p.getUniqueId())) Bukkit.getPlayer(u).sendMessage(ChatColor.YELLOW + "파티원 " + p.getName() + "님이 서버에 입장하셨습니다.");
	}
	
}