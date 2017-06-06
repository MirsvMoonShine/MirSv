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
	String prefix = ChatColor.GOLD + "[" + ChatColor.GREEN + "미르서버" + ChatColor.GOLD + "] " + ChatColor.RESET;
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
						p.sendMessage(prefix+ChatColor.YELLOW+"파티 \'"+args[1]+"\'을(를) 만들었습니다.");
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
								target.sendMessage(prefix+ChatColor.YELLOW+p.getName()+"님이 "+party.getPartyName()+" 파티에 가입시켰습니다.");
								p.sendMessage(prefix+ChatColor.YELLOW+target.getName()+"님을 "+party.getPartyName()+" 파티에 가입시켰습니다.");
							}
						}
					}
				} else if (args[0].equalsIgnoreCase("chat")){
					for (Party party : partys){
						if (party.isPlayerJoin(p)){
							if (chat.getOrDefault(p, false) == false){
								chat.put(p, true);
								p.sendMessage(prefix+ChatColor.YELLOW+"파티 채팅을 시작하였습니다.");
							} else {
								chat.put(p, false);
								p.sendMessage(prefix+ChatColor.YELLOW+"파티 채팅을 끝냈습니다.");
							}
						}
					}
				} else if (args[0].equalsIgnoreCase("info")){
					for (Party party : partys){
						if (party.isPlayerJoin(p)){
							p.sendMessage(prefix+ChatColor.YELLOW+party.getPartyName()+" 파티 정보");
							p.sendMessage(prefix+ChatColor.YELLOW+"파티장: "+party.getOwner().getName());
							p.sendMessage(prefix+ChatColor.YELLOW+"파티원들 정보");
							for (Player t : party.getPlayers()){
								p.sendMessage(prefix+ChatColor.YELLOW+t.getName()+" 채력: "+t.getHealth()+" 배고픔: "+t.getFoodLevel());
							}
						}
					}
				} else if (args[0].equalsIgnoreCase("?")){
					p.sendMessage(prefix+ChatColor.YELLOW+"/party create <이름>: 파티를 만듭니다.");
					p.sendMessage(prefix+ChatColor.YELLOW+"/party add <이름>: 파티원을 추가합니다.");
					p.sendMessage(prefix+ChatColor.YELLOW+"/party info: 파티 정보를 불러옵니다.");
					p.sendMessage(prefix+ChatColor.YELLOW+"/party chat: 파티채팅을 시작합니다.");
				}
			} else {
				p.sendMessage(prefix+ChatColor.YELLOW+"사용법: /party ?");
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
