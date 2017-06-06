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

import com.mirsv.MirPlugin;

public class PartyMain extends MirPlugin implements CommandExecutor, Listener{
	String prefix = ChatColor.GOLD + "[" + ChatColor.GREEN + "�̸�����" + ChatColor.GOLD + "] " + ChatColor.RESET;
	List<Party> partys = new ArrayList<>();
	HashMap<Player, Boolean> chat = new HashMap<>();
	
	public PartyMain(){
		getCommand("party", this);
		getListener(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
		if (sender instanceof Player){
			Player p =(Player) sender;
			if (args.length > 0){
				if (args[0].equalsIgnoreCase("create")){
					if (args.length == 2){
						Party party = new Party(p,args[1]);
						partys.add(party);
						p.sendMessage(prefix+ChatColor.YELLOW+"��Ƽ \'"+args[1]+"\'��(��) ��������ϴ�.");
					}
				} else if (args[0].equalsIgnoreCase("add")){
					if (args.length == 2){
						for (Party party : partys){
							if (party.getOwner() == p){
								Player target = Bukkit.getPlayer(args[1]);
								for (Party p2 : partys){
									if (p2.isPlayerJoin(target)){
										break;
									}
								}
								party.getPlayers().add(target);
								target.sendMessage(prefix+ChatColor.YELLOW+p.getName()+"���� "+party.getPartyName()+" ��Ƽ�� ���Խ��׽��ϴ�.");
								p.sendMessage(prefix+ChatColor.YELLOW+target.getName()+"���� "+party.getPartyName()+" ��Ƽ�� ���Խ��׽��ϴ�.");
							}
						}
					}
				} else if (args[0].equalsIgnoreCase("chat")){
					for (Party party : partys){
						if (party.isPlayerJoin(p)){
							if (chat.getOrDefault(p, false) == false){
								chat.put(p, true);
								p.sendMessage(prefix+ChatColor.YELLOW+"��Ƽ ä���� �����Ͽ����ϴ�.");
							} else {
								chat.put(p, false);
								p.sendMessage(prefix+ChatColor.YELLOW+"��Ƽ ä���� ���½��ϴ�.");
							}
						}
					}
				} else if (args[0].equalsIgnoreCase("info")){
					for (Party party : partys){
						if (party.isPlayerJoin(p)){
							p.sendMessage(prefix+ChatColor.YELLOW+party.getPartyName()+" ��Ƽ ����");
							p.sendMessage(prefix+ChatColor.YELLOW+"��Ƽ��: "+party.getOwner().getName());
							p.sendMessage(prefix+ChatColor.YELLOW+"��Ƽ���� ����");
							for (Player t : party.getPlayers()){
								p.sendMessage(prefix+ChatColor.YELLOW+t.getName()+" ä��: "+t.getHealth()+" �����: "+t.getFoodLevel());
							}
						}
					}
				} else if (args[0].equalsIgnoreCase("?")){
					p.sendMessage(prefix+ChatColor.YELLOW+"/party create <�̸�>: ��Ƽ�� ����ϴ�.");
					p.sendMessage(prefix+ChatColor.YELLOW+"/party add <�̸�>: ��Ƽ���� �߰��մϴ�.");
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
			event.getRecipients().add(p);
			String prefix = ChatColor.YELLOW + "[Party] ";
			event.setFormat(prefix + p.getName()+": "+ event.getMessage());
			for (Party party : partys){
				if (party.isPlayerJoin(p)){
					for (Player t : party.player){
						event.getRecipients().add(t);
					}
				}
			}
		}
	}
}
