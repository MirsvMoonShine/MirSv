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
	String prefix = ChatColor.GOLD+"["+ChatColor.GREEN+"�̸�����"+ChatColor.GOLD+"] "+ChatColor.RESET;
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
							p.sendMessage(prefix+ChatColor.YELLOW+"�̹� ��Ƽ�� ���ԵǾ� �ֽ��ϴ�.");
						else {
							Party party = new Party(p.getName(),args[1]);
							partys.add(party);
							Bukkit.broadcastMessage(prefix+ChatColor.YELLOW+p.getName()+"���� ��Ƽ \'"+args[1]+"\'��(��) ��������ϴ�.");
						}
					}  else {
						p.sendMessage(prefix+ChatColor.YELLOW+"����: /party create <�г���>");
					}
				} else if (args[0].equalsIgnoreCase("disband")){
					if (getParty(p.getName()) == null) {
						p.sendMessage(prefix+ChatColor.YELLOW+"����� ������ �����ϴ�.");
						return false;
					}
					if (getParty(p.getName()).getOwner().equalsIgnoreCase(p.getName())){
						for (String pl : getParty(p.getName()).getPlayers()){
							chat.remove(pl);
							if (Bukkit.getOfflinePlayer(pl).isOnline()) {
								Bukkit.getPlayer(pl).sendMessage(prefix+ChatColor.YELLOW+"��Ƽ�� ��ü�Ǿ����ϴ�.");
							}
						}
						partys.remove(getParty(p.getName()));
					}
					else {
						p.sendMessage(prefix+ChatColor.YELLOW+"����� ������ �����ϴ�.");
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
									p.sendMessage(prefix+ChatColor.YELLOW+"���� �������� �÷��̾ �߰��� �� �ֽ��ϴ�.");
									break;
								}
								Player target = Bukkit.getPlayer(args[1]);
								for (Party p2 : partys){
									if (p2.isPlayerJoin(target.getName())){
										if (party.equals(p2)){
											p.sendMessage(prefix+ChatColor.YELLOW+"�̹� ����� ��Ƽ���Դϴ�.");
										} else {
											p.sendMessage(prefix+ChatColor.YELLOW+"�ٸ� ��Ƽ�� �ҼӵǾ� �ִ� �÷��̾��Դϴ�.");
										}
										return false;
									}
								}
								party.getPlayers().add(target.getName());
								target.sendMessage(prefix+ChatColor.YELLOW+p.getName()+"���� ����� "+party.getPartyName()+" ��Ƽ�� �߰��߽��ϴ�.");
								for (String pl : party.getPlayers()){
									if (Bukkit.getOfflinePlayer(pl).isOnline()){
										Bukkit.getPlayer(pl).sendMessage(prefix+ChatColor.YELLOW+target.getName()+"���� ��Ƽ�� �߰��߽��ϴ�.");
									}
								}
								return false;
							}
						}
						p.sendMessage(prefix+ChatColor.YELLOW+"����� ������ �����ϴ�.");
					} else {
						p.sendMessage(prefix+ChatColor.YELLOW+"����: /party add <�г���>");
					}
				} else if (args[0].equalsIgnoreCase("kick")){
					if (args.length == 2){
						if (getParty(p.getName()) != null){
							if (getParty(p.getName()).getOwner().equalsIgnoreCase(p.getName())){
								if (p.getName().equalsIgnoreCase(args[1])) {
									p.sendMessage(prefix+ChatColor.YELLOW+"�ڱ� �ڽ��� �߹��� �� �����ϴ�.");
									return false;
								}
								Party party = getParty(p.getName());
								for (String pl : party.getPlayers()){
									if (pl.equalsIgnoreCase(args[1])){
										party.getPlayers().remove(pl);
										if (Bukkit.getOfflinePlayer(pl).isOnline()) {
											Bukkit.getPlayer(pl).sendMessage(prefix+ChatColor.YELLOW+"��Ƽ�κ��� �߹���ϼ̽��ϴ�.");
										}
										for (String pm : party.getPlayers()){
											if (Bukkit.getOfflinePlayer(pm).isOnline()) {
												Bukkit.getPlayer(pm).sendMessage(prefix+ChatColor.YELLOW+pl+"���� ��Ƽ���� �߹���׽��ϴ�.");
											}
										}
										return false;
									}
								}
								p.sendMessage(prefix+ChatColor.YELLOW+"��Ƽ�� �������� �ʴ� �÷��̾��Դϴ�.");
							}
							else {
								p.sendMessage(prefix+ChatColor.YELLOW+"����� ������ �����ϴ�.");
							}
						}
						else {
							p.sendMessage(prefix+ChatColor.YELLOW+"����� ������ �����ϴ�.");
						}
					} else {
						p.sendMessage(prefix+ChatColor.YELLOW+"����: /party kick <�г���>");
					}
				} else if (args[0].equalsIgnoreCase("leave")) {
					if (getParty(p.getName()) != null) {
						Party party = getParty(p.getName());
						if (getParty(p.getName()).getOwner().equalsIgnoreCase(p.getName())) {
							p.sendMessage(prefix+ChatColor.YELLOW+"��Ƽ���� ��Ƽ�� ���� �� �����ϴ�.");
						}
						else {
							for (String pl : party.getPlayers()) {
								if (pl.equalsIgnoreCase(p.getName())) {
									party.getPlayers().remove(pl);
									for (String pm : party.getPlayers()) {
										if (Bukkit.getOfflinePlayer(pm).isOnline()) {
											Bukkit.getPlayer(pm).sendMessage(prefix+ChatColor.YELLOW+p.getName()+"���� ��Ƽ�� �������ϴ�.");
										}
									}
									break;
								}
							}
						}
					}
					else {
						p.sendMessage(prefix+ChatColor.YELLOW+"����� ��Ƽ�� �ҼӵǾ����� �ʽ��ϴ�.");
					}
				} else if (args[0].equalsIgnoreCase("chat")){
					if (getParty(p.getName()) != null){
						if (!chat.contains(p.getName())){
							chat.add(p.getName());
							p.sendMessage(ChatColor.GOLD+"[Towny] "+ChatColor.DARK_GREEN+"��� ����: party");
							p.sendMessage(ChatColor.GOLD+"[Towny] "+ChatColor.DARK_GREEN+"[TownyChat] You are now talking in "+ChatColor.WHITE+"party");

						} else {
							chat.remove(p.getName());
							p.sendMessage(ChatColor.GOLD+"[Towny] "+ChatColor.DARK_GREEN+"��� ����: general");
							p.sendMessage(ChatColor.GOLD+"[Towny] "+ChatColor.DARK_GREEN+"[TownyChat] You are now talking in "+ChatColor.WHITE+"general");
						}
					}
					else {
						p.sendMessage(prefix+ChatColor.YELLOW+"����� ��Ƽ�� �ҼӵǾ����� �ʽ��ϴ�.");
					}
				} else if (args[0].equalsIgnoreCase("info")){
					if (getParty(p.getName()) != null){
						Party party = getParty(p.getName());
						p.sendMessage(prefix+ChatColor.GOLD+party.getPartyName()+ChatColor.YELLOW+" ��Ƽ ���� - ��Ƽ��: "+ChatColor.WHITE+party.getOwner());
						for (String t : party.getPlayers()){
							if (Bukkit.getOfflinePlayer(t).isOnline()) {
								p.sendMessage(prefix+ChatColor.WHITE+t+ChatColor.YELLOW+" - ü��: "+((int)(Bukkit.getPlayer(t).getHealth()))+"/20, �����: "+((int)(Bukkit.getPlayer(t).getFoodLevel()))+"/20 "+ChatColor.GREEN+"(�¶���)");
							}
						}
						for (String t : party.getPlayers()){
							if (!Bukkit.getOfflinePlayer(t).isOnline()) {
								p.sendMessage(prefix+ChatColor.WHITE+t+ChatColor.YELLOW+" - "+ChatColor.RED+"(��������)");
							}
						}
					} else {
						p.sendMessage(prefix+ChatColor.YELLOW+"����� ��Ƽ�� �ҼӵǾ����� �ʽ��ϴ�.");
					}
				} else if (args[0].equalsIgnoreCase("list")) {
					if (partys.size() == 0) {
						p.sendMessage(prefix+ChatColor.YELLOW+"���� ������ ��Ƽ�� �����ϴ�.");
					}
					else {
						p.sendMessage(prefix+ChatColor.YELLOW+"========== ��Ƽ ��� ==========");
						for (Party party : partys) {
							p.sendMessage(prefix+ChatColor.YELLOW+party.getPartyName()+" - ��Ƽ��: "+party.getOwner()+", ��Ƽ�� "+party.getPlayers().size());
						}
					}
				} else if (args[0].equalsIgnoreCase("?")){
					p.sendMessage(prefix+ChatColor.YELLOW+"/party create <�̸�>: ��Ƽ�� ����ϴ�.");
					p.sendMessage(prefix+ChatColor.YELLOW+"/party disband: ��Ƽ�� ��ü�մϴ�.");
					p.sendMessage(prefix+ChatColor.YELLOW+"/party add <�г���>: ��Ƽ���� �߰��մϴ�.");
					p.sendMessage(prefix+ChatColor.YELLOW+"/party kick <�г���>: ��Ƽ���� �߹��մϴ�.");
					p.sendMessage(prefix+ChatColor.YELLOW+"/party leave: �Ҽӵ� ��Ƽ���� �����ϴ�.");
					p.sendMessage(prefix+ChatColor.YELLOW+"/party info: ��Ƽ ������ �ҷ��ɴϴ�.");
					p.sendMessage(prefix+ChatColor.YELLOW+"/party list: �����ϴ� ��Ƽ�� ����� Ȯ���մϴ�.");
					p.sendMessage(prefix+ChatColor.YELLOW+"/party chat: ��Ƽä���� �����մϴ�. (/pc)");
				} else {
					p.sendMessage(prefix+ChatColor.YELLOW+"����: /party ?");
				}
			} else {
				p.sendMessage(prefix+ChatColor.YELLOW+"����: /party ?");
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
					Bukkit.getPlayer(pl).sendMessage(ChatColor.YELLOW+"��Ƽ�� "+p.getName()+"���� �������� �����ϼ̽��ϴ�.");
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
					Bukkit.getPlayer(pl).sendMessage(ChatColor.YELLOW+"��Ƽ�� "+p.getName()+"���� ������ �����ϼ̽��ϴ�.");
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
