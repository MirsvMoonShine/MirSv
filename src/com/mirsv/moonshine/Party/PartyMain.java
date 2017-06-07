package com.mirsv.moonshine.Party;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.bukkit.event.player.PlayerQuitEvent;

import com.mirsv.MirPlugin;

public class PartyMain extends MirPlugin implements CommandExecutor, Listener{
	String prefix = ChatColor.GOLD+"["+ChatColor.GREEN+"�̸�����"+ChatColor.GOLD+"] "+ChatColor.RESET;
	List<Party> partys = new ArrayList<>();
	HashMap<Player, Boolean> chat = new HashMap<>();
	
	public PartyMain(){
		getCommand("party", this);
		getListener(this);
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		String s = event.getMessage().split(" ")[0].substring(1);
		if(s.equalsIgnoreCase("tc") || s.equalsIgnoreCase("nc") || s.equalsIgnoreCase("lc") || s.equalsIgnoreCase("wc") || s.equalsIgnoreCase("g")) chat.put(event.getPlayer(), false);
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
		if (sender instanceof Player){
			Player p =(Player) sender;
			if (args.length > 0){
				if (args[0].equalsIgnoreCase("create")){
					if (args.length == 2){
						if (getParty(p) != null)
							p.sendMessage(prefix+ChatColor.YELLOW+"�̹� ��Ƽ�� ���ԵǾ� �ֽ��ϴ�.");
						else {
							Party party = new Party(p,args[1]);
							partys.add(party);
							Bukkit.broadcastMessage(prefix+ChatColor.YELLOW+p.getName()+"���� ��Ƽ \'"+args[1]+"\'��(��) ��������ϴ�.");
						}
					}
				} else if(args[0].equalsIgnoreCase("disband")) {
					if(getParty(p).getOwner().equals(p)) {
						for(Player pl : getParty(p).getPlayers()) {
							pl.sendMessage(prefix+ChatColor.YELLOW+"��Ƽ�� ��ü�Ǿ����ϴ�.");
						}
						partys.remove(getParty(p));
					}
				} else if (args[0].equalsIgnoreCase("add")){
					if (args.length == 2){
						for (Party party : partys){
							if (party.getOwner() == p){
								boolean isExist = false;
								for(Player pl : Bukkit.getOnlinePlayers()) {
									if(pl.getName().equalsIgnoreCase(args[1])) {
										isExist = true;
										break;
									}
								}
								if(!isExist) {
									p.sendMessage(prefix+ChatColor.YELLOW+"���� �������� �÷��̾ �߰��� �� �ֽ��ϴ�.");
									break;
								}
								Player target = Bukkit.getPlayer(args[1]);
								boolean isPlayerJoined = false;
								for (Party p2 : partys){
									if (p2.isPlayerJoin(target)){
										isPlayerJoined = true;
										if(party.equals(p2)) {
											p.sendMessage(prefix+ChatColor.YELLOW+"�̹� ����� ��Ƽ���Դϴ�.");
										} else {
											p.sendMessage(prefix+ChatColor.YELLOW+"�ٸ� ��Ƽ�� �ҼӵǾ� �ִ� �÷��̾��Դϴ�.");
										}
										break;
									}
								}
								if(isPlayerJoined) break;
								party.getPlayers().add(target);
								target.sendMessage(prefix+ChatColor.YELLOW+p.getName()+"���� ����� "+party.getPartyName()+" ��Ƽ�� �߰��߽��ϴ�.");
								for(Player pl : party.getPlayers()) {
									if(pl.isOnline()){
										pl.sendMessage(prefix+ChatColor.YELLOW+target.getName()+"���� ��Ƽ�� �߰��߽��ϴ�.");
									}
								}
							}
						}
					}
				} else if(args[0].equalsIgnoreCase("kick")) {
					if (args.length == 2) {
						if (getParty(p) != null) {
							if (getParty(p).getOwner() == p) {
								Party party = getParty(p);
								boolean isPlayerJoined = false;
								for(Player pl : party.getPlayers()) {
									if(pl.getName().equalsIgnoreCase(args[1])) {
										isPlayerJoined = true;
										party.getPlayers().remove(pl);
										p.sendMessage(prefix+ChatColor.YELLOW+pl.getName()+"���� ��Ƽ���� �߹���׽��ϴ�.");
									}
								}
								if(!isPlayerJoined) {
									p.sendMessage(prefix+ChatColor.YELLOW+"��Ƽ�� �������� �ʴ� �÷��̾��Դϴ�.");
								}
							}
						}
					}
				} else if (args[0].equalsIgnoreCase("chat")){
					if (getParty(p) != null){
						if (chat.getOrDefault(p, false) == false){
							chat.put(p, true);
							p.sendMessage(ChatColor.GOLD+"[Towny] "+ChatColor.DARK_GREEN+"��� ����: party");
							p.sendMessage(ChatColor.GOLD+"[Towny] "+ChatColor.DARK_GREEN+"[TownyChat] You are now talking in "+ChatColor.WHITE+"party");

						} else {
							chat.put(p, false);
							p.sendMessage(ChatColor.GOLD+"[Towny] "+ChatColor.DARK_GREEN+"��� ����: global");
							p.sendMessage(ChatColor.GOLD+"[Towny] "+ChatColor.DARK_GREEN+"[TownyChat] You are now talking in "+ChatColor.WHITE+"global");
						}
					}
				} else if (args[0].equalsIgnoreCase("info")){
					if (getParty(p) != null){
						Party party = getParty(p);
						p.sendMessage(prefix+ChatColor.YELLOW+party.getPartyName()+" ��Ƽ ����");
						p.sendMessage(prefix+ChatColor.YELLOW+"��Ƽ��: "+party.getOwner().getName());
						p.sendMessage(prefix+ChatColor.YELLOW+"��Ƽ���� ����");
						for (Player t : party.getPlayers()){
							p.sendMessage(prefix+ChatColor.YELLOW+t.getName()+" ü��: "+t.getHealth()+" �����: "+t.getFoodLevel());
						}
					} else {
						p.sendMessage(prefix+ChatColor.YELLOW+"����� ��Ƽ�� �ҼӵǾ����� �ʽ��ϴ�.");
					}
				} else if (args[0].equalsIgnoreCase("?")){
					p.sendMessage(prefix+ChatColor.YELLOW+"/party create <�̸�>: ��Ƽ�� ����ϴ�.");
					p.sendMessage(prefix+ChatColor.YELLOW+"/party disband: ��Ƽ�� ��ü�մϴ�.");
					p.sendMessage(prefix+ChatColor.YELLOW+"/party add <�г���>: ��Ƽ���� �߰��մϴ�.");
					p.sendMessage(prefix+ChatColor.YELLOW+"/party kick <�г���>: ��Ƽ���� �߹��մϴ�.");
					p.sendMessage(prefix+ChatColor.YELLOW+"/party info: ��Ƽ ������ �ҷ��ɴϴ�.");
					p.sendMessage(prefix+ChatColor.YELLOW+"/party chat: ��Ƽä���� �����մϴ�.");
				}
			} else {
				p.sendMessage(prefix+ChatColor.YELLOW+"����: /party ?");
			}
		}
		return false;
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(AsyncPlayerChatEvent event) {
		Player p = event.getPlayer();
		if (chat.getOrDefault(p, false) == true){
			event.getRecipients().clear();
			event.setFormat("[" + ChatColor.DARK_AQUA + "PC" + ChatColor.WHITE + "] " + event.getPlayer().getName() + ": " + ChatColor.GOLD + event.getMessage());
			if (getParty(p) != null){
				for (Player t : getParty(p).player){
					event.getRecipients().add(t);
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerQuit(PlayerQuitEvent event) {
		if(getParty(event.getPlayer()) != null) {
			getParty(event.getPlayer()).player.remove(event.getPlayer());
			for(Player pl: getParty(event.getPlayer()).player) {
				pl.sendMessage(prefix+ChatColor.YELLOW+event.getPlayer().getName()+" ���� ��Ƽ�� �������ϴ�.");
			}
		}
	}
	public Party getParty(Player p){
		Party result = null;
		for (Party party : partys){
			if (party.isPlayerJoin(p)){
				result = party;
				break;
			}
		}
		return result;
	}
}
