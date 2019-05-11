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
import com.mirsv.function.AutoSave.AutoSave;
import com.mirsv.function.AutoSave.AutoSaveManager;
import com.mirsv.util.Messager;
import com.mirsv.util.data.FileUtil;

public class PartyManager extends AbstractFunction implements CommandExecutor, Listener, AutoSave {

	public PartyManager() {
		super("��Ƽ", "1.0", "�̸����� ��Ƽ �ý����Դϴ�.");
	}

	private File folder = FileUtil.getFolder("Party");
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
			Messager.sendErrorMessage("��Ƽ �����͸� �����ϴ� ���� ������ �߻��Ͽ����ϴ�.");
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
			Messager.sendErrorMessage("��Ƽ �����͸� �����ϴ� ���� ������ �߻��Ͽ����ϴ�.");
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
				e.setFormat(ChatColor.translateAlternateColorCodes('&', "&f[&d��Ƽ&f][&5" + getParty(p.getUniqueId()).getPartyName() + "&f] " + p.getName() + ": &d" + e.getMessage()));
				for(UUID u: getParty(p.getUniqueId()).getPlayers()) if(Bukkit.getOfflinePlayer(u).isOnline()) e.getRecipients().add(Bukkit.getPlayer(u));
			} else {
				Messager.sendMessage(p, ChatColor.translateAlternateColorCodes('&', Messager.getPrefix() + "&e����� ��Ƽ�� �ҼӵǾ����� �ʽ��ϴ�. /g ��ɾ ���� ��ü ä������ �������ּ���!"));
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
				Messager.sendMessage(p, ChatColor.translateAlternateColorCodes('&', Messager.getPrefix() + "��Ƽ ä���� �����մϴ�."));
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
				Messager.sendMessage(player1, ChatColor.translateAlternateColorCodes('&', Messager.getPrefix() + "&e��Ƽ���� PVP�� ��Ȱ��ȭ�Ǿ��ֽ��ϴ�."));
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
					} else p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "����� ��Ƽ�� �ҼӵǾ����� �ʽ��ϴ�.");
				} else {
					if(getParty(p.getUniqueId()) != null) {
						if(!chat.contains(p.getUniqueId())) {
							chat.add(p.getUniqueId());
							p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "��Ƽ ä���� �����մϴ�.");
						}
						else {
							chat.remove(p.getUniqueId());
							p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "��Ƽ ä���� �����մϴ�.");
						}
					}
					else p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "����� ��Ƽ�� �ҼӵǾ����� �ʽ��ϴ�.");
				}
				return false;
			}
			if(args.length > 0) {
				if(args[0].equalsIgnoreCase("create")) {
					if(args.length == 2) {
						if(getParty(p.getUniqueId()) != null)
							p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "�̹� ��Ƽ�� ���ԵǾ� �ֽ��ϴ�.");
						else {
							Party party = new Party(p.getUniqueId(), args[1], true, false);
							Parties.add(party);
							Bukkit.broadcastMessage(Messager.getPrefix() + ChatColor.YELLOW + p.getName() + "���� ��Ƽ \'" + args[1] + "\'��(��) ��������ϴ�.");
						}
					}
					else p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "����: /party create <�г���>");
				}
				else if(args[0].equalsIgnoreCase("disband")) {
					if(getParty(p.getUniqueId()) == null) p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "����� ������ �����ϴ�.");
					else {
						if(getParty(p.getUniqueId()).getOwner().equals(p.getUniqueId())) {
							for(UUID pl: getParty(p.getUniqueId()).getPlayers()) {
								chat.remove(pl);
								if(Bukkit.getOfflinePlayer(pl).isOnline()) Bukkit.getPlayer(pl).sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "��Ƽ�� ��ü�Ǿ����ϴ�.");
							}
							Parties.remove(getParty(p.getUniqueId()));
						}
						else p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "����� ������ �����ϴ�.");
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
								if(!isExist) p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "���� �������� �÷��̾ �߰��� �� �ֽ��ϴ�.");
								else {
									Player target = Bukkit.getPlayer(args[1]);
									for(Party p2: Parties) {
										if(p2.isPlayerJoin(target.getUniqueId())) {
											if(party.equals(p2)) p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "�̹� ����� ��Ƽ���Դϴ�.");
											else p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "�ٸ� ��Ƽ�� �ҼӵǾ� �ִ� �÷��̾��Դϴ�.");
											return false;
										}
									}
									for(UUID u: party.getPlayers()) if(Bukkit.getOfflinePlayer(u).isOnline()) Bukkit.getPlayer(u).sendMessage(Messager.getPrefix() + ChatColor.YELLOW + target.getName() + "���� ��Ƽ�� �߰��߽��ϴ�.");
									party.getPlayers().add(target.getUniqueId());
									target.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + p.getName() + "���� ����� " + party.getPartyName() + " ��Ƽ�� �߰��߽��ϴ�.");
									return false;
								}
							}
						}
						p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "����� ������ �����ϴ�.");
					}
					else p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "����: /party add <��Ƽ�̸�>");
				}
				else if(args[0].equalsIgnoreCase("join")) {
					if(args.length == 2) {
						if(getParty(p.getUniqueId()) != null) {
							p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "����� �̹� ��Ƽ�� �ҼӵǾ� �ֽ��ϴ�.");
							return false;
						}
						for(Party party : Parties) {
							if(party.getPartyName().equalsIgnoreCase(args[1])) {
								if(!party.isOPEN()) {
									p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "�ش� ��Ƽ�� ����Ǿ����� �ʽ��ϴ�.");
									return false;
								}
								party.getPlayers().add(p.getUniqueId());
								for(UUID uuid: party.getPlayers()) if(Bukkit.getOfflinePlayer(uuid).isOnline()) Bukkit.getPlayer(uuid).sendMessage(Messager.getPrefix() + ChatColor.YELLOW + p.getName() + "���� ��Ƽ�� �����߽��ϴ�.");
								return false;
							}
						}
						p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "�ش� ��Ƽ�� �������� �ʽ��ϴ�.");
						return false;
					}
					p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "����: /party join <��Ƽ�̸�>");
				}
				else if(args[0].equalsIgnoreCase("kick")) {
					if(args.length == 2) {
						if(!Bukkit.getOfflinePlayer(args[1]).hasPlayedBefore()) {
							p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "��Ƽ�� �������� �ʴ� �÷��̾��Դϴ�.");
							return false;
						}
						if(getParty(p.getUniqueId()) != null) {
							if(getParty(p.getUniqueId()).getOwner().equals(p.getUniqueId())) {
								if(p.getName().equalsIgnoreCase(args[1])) {
									p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "�ڱ� �ڽ��� �߹��� �� �����ϴ�.");
									return false;
								}
								Party party = getParty(p.getUniqueId());
								for(UUID u: party.getPlayers()) {
									if(u.equals(Bukkit.getOfflinePlayer(args[1]).getUniqueId())) {
										party.getPlayers().remove(u);
										if(Bukkit.getOfflinePlayer(u).isOnline()) Bukkit.getPlayer(u).sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "��Ƽ�κ��� �߹���ϼ̽��ϴ�.");
										for(UUID um: party.getPlayers()) if(Bukkit.getOfflinePlayer(um).isOnline()) Bukkit.getPlayer(um).sendMessage(Messager.getPrefix() + ChatColor.YELLOW + Bukkit.getOfflinePlayer(args[1]).getName() + "���� ��Ƽ���� �߹���׽��ϴ�.");
										return false;
									}
								}
								p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "��Ƽ�� �������� �ʴ� �÷��̾��Դϴ�.");
							}
							else p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "����� ������ �����ϴ�.");
						}
						else p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "����� ������ �����ϴ�.");
					}
					else p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "����: /party kick <�г���>");
				}
				else if(args[0].equalsIgnoreCase("leave")) {
					if(getParty(p.getUniqueId()) != null) {
						Party party = getParty(p.getUniqueId());
						if(getParty(p.getUniqueId()).getOwner().equals(p.getUniqueId())) p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "��Ƽ���� ��Ƽ�� ���� �� �����ϴ�.");
						else {
							for(UUID u: party.getPlayers()) {
								if(u.equals(p.getUniqueId())) {
									party.getPlayers().remove(u);
									for(UUID um: party.getPlayers()) if(Bukkit.getOfflinePlayer(um).isOnline()) Bukkit.getPlayer(um).sendMessage(Messager.getPrefix() + ChatColor.YELLOW + p.getName() + "���� ��Ƽ�� �������ϴ�.");
									p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "��Ƽ�� �������ϴ�.");
									break;
								}
							}
						}
					}
					else {
						p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "����� ��Ƽ�� �ҼӵǾ����� �ʽ��ϴ�.");
					}
				}
				else if(args[0].equalsIgnoreCase("toggle")) {
					if(getParty(p.getUniqueId()) == null) {
						p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "����� ��Ƽ�� �ҼӵǾ����� �ʽ��ϴ�.");
						return false;
					}
					if(!getParty(p.getUniqueId()).getOwner().equals(p.getUniqueId())) {
						p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "����� ������ �����ϴ�.");
	                    return false;
	                }
					if(args.length == 1) {
						p.sendMessage(ChatColor.BLUE + "/party toggle pvp: " + ChatColor.WHITE + "��Ƽ�� ���� pvp ��� ������ �����մϴ�.");
	                    p.sendMessage(ChatColor.BLUE + "/party toggle open: " + ChatColor.WHITE + "�ʴ� ���� ��Ƽ�� ���� ���� ���θ� �����մϴ�.");
	                }
					else if(args[1].equalsIgnoreCase("pvp")) {
						if(getParty(p.getUniqueId()).TogglePvP()) {
							for(UUID uuid : getParty(p.getUniqueId()).getPlayers()) {
								if(Bukkit.getOfflinePlayer(uuid).isOnline()) {
									Bukkit.getPlayer(uuid).sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "���� ��Ƽ�� �� PvP�� ���˴ϴ�.");
								}
							}
	                    }
						else {
							for(UUID uuid : getParty(p.getUniqueId()).getPlayers()) {
								if(Bukkit.getOfflinePlayer(uuid).isOnline()) {
									Bukkit.getPlayer(uuid).sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "���� ��Ƽ�� �� PvP�� �����˴ϴ�.");
								}
							}
						}
					}
					else if(args[1].equalsIgnoreCase("open")) {
						if(getParty(p.getUniqueId()).ToggleOpen()) {
							for(UUID uuid : getParty(p.getUniqueId()).getPlayers()) {
								if(Bukkit.getOfflinePlayer(uuid).isOnline()) {
									Bukkit.getPlayer(uuid).sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "���� ��Ƽ���� �ʴ� ���� �÷��̾ ������ �� �ֽ��ϴ�.");
								}
							}
	                    }
						else {
							for(UUID uuid : getParty(p.getUniqueId()).getPlayers()) {
								if(Bukkit.getOfflinePlayer(uuid).isOnline()) {
									Bukkit.getPlayer(uuid).sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "���� ��Ƽ���� �ʴ� ���� �÷��̾ ������ �� �����ϴ�.");
								}
							}
	                    }
					}
					else {
						p.sendMessage(ChatColor.BLUE + "/party toggle pvp: " + ChatColor.WHITE + "��Ƽ�� ���� pvp ��� ������ �����մϴ�.");
	                    p.sendMessage(ChatColor.BLUE + "/party toggle open: " + ChatColor.WHITE + "�ʴ� ���� ��Ƽ�� ���� ���� ���θ� �����մϴ�.");
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
						else p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "����� ��Ƽ�� �ҼӵǾ����� �ʽ��ϴ�.");
					}
					else {
						if(getParty(p.getUniqueId()) != null) {
							if(!chat.contains(p.getUniqueId())) {
								chat.add(p.getUniqueId());
								p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "��Ƽ ä���� �����մϴ�.");
							}
							else {
								chat.remove(p.getUniqueId());
								p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "��Ƽ ä���� �����մϴ�.");
							}
						}
						else p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "����� ��Ƽ�� �ҼӵǾ����� �ʽ��ϴ�.");
					}
				}
				else if(args[0].equalsIgnoreCase("info")) {
					if(getParty(p.getUniqueId()) != null) {
						Party party = getParty(p.getUniqueId());
						p.sendMessage(Messager.getPrefix() + ChatColor.GOLD + party.getPartyName() + ChatColor.YELLOW + " ��Ƽ ���� - ��Ƽ��: " + ChatColor.WHITE + Bukkit.getOfflinePlayer(party.getOwner()).getName());
						String PvPString = "";
	                    if(party.isPVP()) PvPString = ChatColor.GREEN + "����";
	                    else PvPString = ChatColor.RED + "����";
	                    String OpenString = "";
	                    if(party.isOPEN()) OpenString = ChatColor.GREEN + "����";
	                    else OpenString = ChatColor.RED + "����";
	                    p.sendMessage(ChatColor.AQUA + " PvP: " + PvPString + " " + ChatColor.AQUA + "����: " + OpenString);
						for(UUID u: party.getPlayers()) if(Bukkit.getOfflinePlayer(u).isOnline()) p.sendMessage(ChatColor.WHITE + " �� " + Bukkit.getOfflinePlayer(u).getName() + ChatColor.YELLOW + " - ü��: " + ((int)(Bukkit.getPlayer(u).getHealth())) + "/20, �����: " + ((int)(Bukkit.getPlayer(u).getFoodLevel())) + "/20 " + ChatColor.GREEN + "(�¶���)");
						for(UUID u: party.getPlayers()) if(!Bukkit.getOfflinePlayer(u).isOnline()) p.sendMessage(ChatColor.WHITE + " �� " + Bukkit.getOfflinePlayer(u).getName() + ChatColor.YELLOW + " - " + ChatColor.RED + "(��������)");
					}
					else p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "����� ��Ƽ�� �ҼӵǾ����� �ʽ��ϴ�.");
				}
				else if(args[0].equalsIgnoreCase("list")) {
					if(Parties.size() == 0) p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "���� ������ ��Ƽ�� �����ϴ�.");
					else {
						p.sendMessage(Messager.getPrefix() + ChatColor.DARK_GRAY + "============ " + ChatColor.YELLOW + "��Ƽ ���" + ChatColor.DARK_GRAY + " ============");
						for(Party party: Parties) p.sendMessage(ChatColor.WHITE + "�� " + ChatColor.GOLD + ChatColor.BOLD + party.getPartyName() + ChatColor.YELLOW + " - ��Ƽ��: " + ChatColor.WHITE + Bukkit.getOfflinePlayer(party.getOwner()).getName() + ChatColor.YELLOW + ", ��Ƽ�� " + ChatColor.WHITE + party.getPlayers().size() + ChatColor.AQUA + (party.isOPEN() ? " (����)" : ""));
					}
				}
				else if(args[0].equalsIgnoreCase("?")) {
					p.sendMessage("----- " + ChatColor.AQUA + "�̸����� ��Ƽ �÷�����" + ChatColor.WHITE + " -----");
					p.sendMessage(ChatColor.BLUE + "/party create <�̸�>: " + ChatColor.WHITE + "��Ƽ�� ����ϴ�.");
					p.sendMessage(ChatColor.BLUE + "/party disband: " + ChatColor.WHITE + "��Ƽ�� ��ü�մϴ�.");
					p.sendMessage(ChatColor.BLUE + "/party add <�г���>: " + ChatColor.WHITE + "��Ƽ���� �߰��մϴ�.");
					p.sendMessage(ChatColor.BLUE + "/party kick <�г���>: " + ChatColor.WHITE + "��Ƽ���� �߹��մϴ�.");
					p.sendMessage(ChatColor.BLUE + "/party join <��Ƽ�̸�>: " + ChatColor.WHITE + "����� ��Ƽ�� �����մϴ�.");
	                p.sendMessage(ChatColor.BLUE + "/party leave: " + ChatColor.WHITE + "�Ҽӵ� ��Ƽ���� �����ϴ�.");
	                p.sendMessage(ChatColor.BLUE + "/party toggle: " + ChatColor.WHITE + "��Ƽ�� ������ �����մϴ�."); 
					p.sendMessage(ChatColor.BLUE + "/party info: " + ChatColor.WHITE + "��Ƽ ������ �ҷ��ɴϴ�.");
					p.sendMessage(ChatColor.BLUE + "/party list: " + ChatColor.WHITE + "�����ϴ� ��Ƽ�� ����� Ȯ���մϴ�.");
					p.sendMessage(ChatColor.BLUE + "/party chat: " + ChatColor.WHITE + "��Ƽä���� �����մϴ�. (/pc)");
				}
				else p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "����: /party ?");
			}
			else p.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "����: /party ?");
		}
		return false;
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		if(getParty(p.getUniqueId()) != null) for(UUID u: getParty(p.getUniqueId()).getPlayers()) if(Bukkit.getOfflinePlayer(u).isOnline() && !u.equals(p.getName())) Bukkit.getPlayer(u).sendMessage(ChatColor.YELLOW + "��Ƽ�� " + p.getName() + "���� �������� �����ϼ̽��ϴ�.");
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		if(getParty(p.getUniqueId()) != null) for(UUID u: getParty(p.getUniqueId()).getPlayers()) if(Bukkit.getOfflinePlayer(u).isOnline() && !u.equals(p.getUniqueId())) Bukkit.getPlayer(u).sendMessage(ChatColor.YELLOW + "��Ƽ�� " + p.getName() + "���� ������ �����ϼ̽��ϴ�.");
	}
	
}