package com.mirsv.moonshine.Party;

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
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.mirsv.MirPlugin;

public class PartyMain extends MirPlugin implements CommandExecutor, Listener{
	String prefix = ChatColor.GOLD+"["+ChatColor.GREEN+"�̸�����"+ChatColor.GOLD+"] "+ChatColor.RESET;
	ArrayList<UUID> chat = new ArrayList<UUID>();
	static List<Party> partys = com.mirsv.Mirsv.getPartys();
	public PartyMain(){
		getCommand("party", this);
		getListener(this);
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCommand(PlayerCommandPreprocessEvent event){
		if (getConfig().getBoolean("enable.Party", true) && chat.contains(event.getPlayer().getUniqueId())){
			String s = event.getMessage().split(" ")[0];
			if (s.equalsIgnoreCase("/tc") || s.equalsIgnoreCase("/nc") || s.equalsIgnoreCase("/lc") || s.equalsIgnoreCase("/wc") || s.equalsIgnoreCase("/g") || s.equalsIgnoreCase("/admin") || s.equalsIgnoreCase("/mod") || s.equalsIgnoreCase("/a") || s.equalsIgnoreCase("/l") || s.equalsIgnoreCase("/m")) {
				chat.remove(event.getPlayer().getUniqueId());
				event.getPlayer().sendMessage(prefix + ChatColor.YELLOW + "��Ƽ ä���� �����մϴ�.");
			}
		}
	}
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args){
		if (getConfig().getBoolean("enable.Party", true) && sender instanceof Player){
			Player p = (Player) sender;
			if (args.length > 0){
				if (args[0].equalsIgnoreCase("create")){
					if (args.length == 2){
						if (getParty(p.getUniqueId()) != null)
							p.sendMessage(prefix+ChatColor.YELLOW+"�̹� ��Ƽ�� ���ԵǾ� �ֽ��ϴ�.");
						else {
							Party party = new Party(p.getUniqueId(),args[1]);
							partys.add(party);
							Bukkit.broadcastMessage(prefix+ChatColor.YELLOW+p.getName()+"���� ��Ƽ \'"+args[1]+"\'��(��) ��������ϴ�.");
						}
					}  else {
						p.sendMessage(prefix+ChatColor.YELLOW+"����: /party create <�г���>");
					}
				} else if (args[0].equalsIgnoreCase("disband")){
					if (getParty(p.getUniqueId()) == null) {
						p.sendMessage(prefix+ChatColor.YELLOW+"����� ������ �����ϴ�.");
						return false;
					}
					if (getParty(p.getUniqueId()).getOwner().equals(p.getUniqueId())){
						for (UUID pl : getParty(p.getUniqueId()).getPlayers()){
							chat.remove(pl);
							if (Bukkit.getOfflinePlayer(pl).isOnline()) {
								Bukkit.getPlayer(pl).sendMessage(prefix+ChatColor.YELLOW+"��Ƽ�� ��ü�Ǿ����ϴ�.");
							}
						}
						partys.remove(getParty(p.getUniqueId()));
					}
					else {
						p.sendMessage(prefix+ChatColor.YELLOW+"����� ������ �����ϴ�.");
					}
				} else if (args[0].equalsIgnoreCase("add")){
					if (args.length == 2){
						for (Party party : partys){
							if (party.getOwner().equals(p.getUniqueId())){
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
									if (p2.isPlayerJoin(target.getUniqueId())){
										if (party.equals(p2)){
											p.sendMessage(prefix+ChatColor.YELLOW+"�̹� ����� ��Ƽ���Դϴ�.");
										} else {
											p.sendMessage(prefix+ChatColor.YELLOW+"�ٸ� ��Ƽ�� �ҼӵǾ� �ִ� �÷��̾��Դϴ�.");
										}
										return false;
									}
								}
								party.getPlayers().add(target.getUniqueId());
								target.sendMessage(prefix+ChatColor.YELLOW+p.getName()+"���� ����� "+party.getPartyName()+" ��Ƽ�� �߰��߽��ϴ�.");
								for (UUID u : party.getPlayers()){
									if (Bukkit.getOfflinePlayer(u).isOnline()){
										Bukkit.getPlayer(u).sendMessage(prefix+ChatColor.YELLOW+target.getName()+"���� ��Ƽ�� �߰��߽��ϴ�.");
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
						if(!Bukkit.getOfflinePlayer(args[1]).hasPlayedBefore()) {
							p.sendMessage(prefix+ChatColor.YELLOW+"��Ƽ�� �������� �ʴ� �÷��̾��Դϴ�.");
							return false;
						}
						if (getParty(p.getUniqueId()) != null){
							if (getParty(p.getUniqueId()).getOwner().equals(p.getUniqueId())){
								if (p.getName().equalsIgnoreCase(args[1])) {
									p.sendMessage(prefix+ChatColor.YELLOW+"�ڱ� �ڽ��� �߹��� �� �����ϴ�.");
									return false;
								}
								Party party = getParty(p.getUniqueId());
								for (UUID u : party.getPlayers()){
									if (u.equals(Bukkit.getOfflinePlayer(args[1]).getUniqueId())){
										party.getPlayers().remove(u);
										if (Bukkit.getOfflinePlayer(u).isOnline()) {
											Bukkit.getPlayer(u).sendMessage(prefix+ChatColor.YELLOW+"��Ƽ�κ��� �߹���ϼ̽��ϴ�.");
										}
										for (UUID um : party.getPlayers()){
											if (Bukkit.getOfflinePlayer(um).isOnline()) {
												Bukkit.getPlayer(um).sendMessage(prefix+ChatColor.YELLOW+Bukkit.getOfflinePlayer(args[1]).getName()+"���� ��Ƽ���� �߹���׽��ϴ�.");
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
					if (getParty(p.getUniqueId()) != null) {
						Party party = getParty(p.getUniqueId());
						if (getParty(p.getUniqueId()).getOwner().equals(p.getUniqueId())) {
							p.sendMessage(prefix+ChatColor.YELLOW+"��Ƽ���� ��Ƽ�� ���� �� �����ϴ�.");
						}
						else {
							for (UUID u : party.getPlayers()) {
								if (u.equals(p.getUniqueId())) {
									party.getPlayers().remove(u);
									for (UUID um : party.getPlayers()) {
										if (Bukkit.getOfflinePlayer(um).isOnline()) {
											Bukkit.getPlayer(um).sendMessage(prefix+ChatColor.YELLOW+p.getName()+"���� ��Ƽ�� �������ϴ�.");
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
					if (getParty(p.getUniqueId()) != null){
						if (!chat.contains(p.getUniqueId())){
							chat.add(p.getUniqueId());
							p.sendMessage(prefix + ChatColor.YELLOW + "��Ƽ ä���� �����մϴ�.");
						} else {
							chat.remove(p.getUniqueId());
							p.sendMessage(prefix + ChatColor.YELLOW + "��Ƽ ä���� �����մϴ�.");
						}
					}
					else {
						p.sendMessage(prefix+ChatColor.YELLOW+"����� ��Ƽ�� �ҼӵǾ����� �ʽ��ϴ�.");
					}
				} else if (args[0].equalsIgnoreCase("info")){
					if (getParty(p.getUniqueId()) != null){
						Party party = getParty(p.getUniqueId());
						p.sendMessage(prefix+ChatColor.GOLD+party.getPartyName()+ChatColor.YELLOW+" ��Ƽ ���� - ��Ƽ��: "+ChatColor.WHITE+Bukkit.getOfflinePlayer(party.getOwner()).getName());
						for (UUID u : party.getPlayers()){
							if (Bukkit.getOfflinePlayer(u).isOnline()) {
								p.sendMessage(ChatColor.WHITE+" �� "+Bukkit.getOfflinePlayer(u).getName()+ChatColor.YELLOW+" - ü��: "+((int)(Bukkit.getPlayer(u).getHealth()))+"/20, �����: "+((int)(Bukkit.getPlayer(u).getFoodLevel()))+"/20 "+ChatColor.GREEN+"(�¶���)");
							}
						}
						for (UUID u : party.getPlayers()){
							if (!Bukkit.getOfflinePlayer(u).isOnline()) {
								p.sendMessage(ChatColor.WHITE+" �� "+Bukkit.getOfflinePlayer(u).getName()+ChatColor.YELLOW+" - "+ChatColor.RED+"(��������)");
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
						p.sendMessage(prefix+ChatColor.DARK_GRAY+"============ "+ChatColor.YELLOW+"��Ƽ ���"+ChatColor.DARK_GRAY+" ============");
						for (Party party : partys) {
							p.sendMessage(ChatColor.WHITE+"�� "+ChatColor.GOLD+ChatColor.BOLD+party.getPartyName()+ChatColor.YELLOW+" - ��Ƽ��: "+ChatColor.WHITE+Bukkit.getOfflinePlayer(party.getOwner()).getName()+ChatColor.YELLOW+", ��Ƽ�� "+ChatColor.WHITE+party.getPlayers().size());
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
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(AsyncPlayerChatEvent event){
		Player p = event.getPlayer();
		if (getConfig().getBoolean("enable.Party", true) && chat.contains(p.getUniqueId())){
			event.getRecipients().clear();
			event.setMessage(event.getMessage().replaceAll("%", "%%"));
			event.setFormat("["+ChatColor.LIGHT_PURPLE+"PC"+ChatColor.WHITE+"] "+event.getPlayer().getName()+": "+ChatColor.LIGHT_PURPLE+event.getMessage());
			if (getParty(p.getUniqueId()) != null){
				for (UUID u : getParty(p.getUniqueId()).getPlayers()){
					if (Bukkit.getOfflinePlayer(u).isOnline()) {
						event.getRecipients().add(Bukkit.getPlayer(u));
					}
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		if (getConfig().getBoolean("enable.Party", true) && getParty(p.getUniqueId()) != null) {
			for (UUID u: getParty(p.getUniqueId()).getPlayers()) {
				if (Bukkit.getOfflinePlayer(u).isOnline() && !u.equals(p.getName())) {
					Bukkit.getPlayer(u).sendMessage(ChatColor.YELLOW+"��Ƽ�� "+p.getName()+"���� �������� �����ϼ̽��ϴ�.");
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		if (getConfig().getBoolean("enable.Party", true) && getParty(p.getUniqueId()) != null) {
			for (UUID u : getParty(p.getUniqueId()).getPlayers()) {
				if (Bukkit.getOfflinePlayer(u).isOnline() && !u.equals(p.getUniqueId())) {
					Bukkit.getPlayer(u).sendMessage(ChatColor.YELLOW+"��Ƽ�� "+p.getName()+"���� ������ �����ϼ̽��ϴ�.");
				}
			}
		}
	}
	public Party getParty(UUID uuid){
		Party result = null;
		for (Party party : partys){
			for (UUID u : party.getPlayers()) {
				if (u.equals(uuid)) {
					result = party;
				}
			}
		}
		return result;
	}
	public static List<Party> getPartys() {
		return partys;
	}
}