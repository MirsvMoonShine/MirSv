package com.mirsv.moonshine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.mirsv.MirPlugin;
import com.mirsv.moonshine.Warning.WarningCommand;

public class ForbiddenWord extends MirPlugin implements Listener,CommandExecutor{
	List<String> forbidden;
	File f = new File("plugins/Mirsv/ForbiddenWorld/ForbiddenWorld.forbidden");
	File folder = new File("plugins/Mirsv/ForbiddenWorld");
	
	public ForbiddenWord(){
		forbidden = new ArrayList<String>();
		if (!f.exists()) try {folder.mkdirs();f.createNewFile();} catch (IOException e) {}
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String s = br.readLine();
			if (s != null) {String[] str = s.split("_");
			for (String add : str) forbidden.add(add); }
			br.close();
		} catch (IOException e) {}
		getCommand("forbidden", this);
		getListener(this);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChat(AsyncPlayerChatEvent e){
		String message = e.getMessage();
		for (String forb : forbidden){
			if (message.contains(forb)){
				e.setCancelled(true);
				e.getPlayer().sendMessage(prefix+"부적절한 언어를 사용할 수 없습니다.");
				break;
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		
		if ((getConfig().getBoolean("enable.forbiddenword", true)) && ((player.isOp()) || (player.hasPermission("mirsv.admin")))) {
			if (args.length > 0) {
				if ((args[0].equalsIgnoreCase("add")) || (args[0].equalsIgnoreCase("추가"))) {
					if (args.length == 2){
						if (!forbidden.contains(args[1])){
							forbidden.add(args[1]);
							try {
								BufferedReader br = new BufferedReader(new FileReader(f));
								String s = br.readLine();
								BufferedWriter bw = new BufferedWriter(new FileWriter(f));
								bw.write(s+args[1]+"_");
								br.close();
								bw.close();
							} catch (IOException e) {}
							player.sendMessage(prefix+"금지 단어 "+args[1]+ "이(가) 추가되었습니다.");
						}
					}
				} else if ((args[0].equalsIgnoreCase("delete")) || (args[0].equalsIgnoreCase("제거"))) {
					if (args.length == 2){
						if (forbidden.contains(args[1])){
							forbidden.remove(args[1]);
							f.delete();
							try {
								f.createNewFile();
								BufferedWriter br = new BufferedWriter(new FileWriter(f));
								for (String s : forbidden) br.write(s + "_");
								br.close();
							} catch (IOException e) {}
							player.sendMessage(prefix+ args[1]+ "은(는) 더이상 금지단어가 아닙니다.");
						}
					}
				} else if ((args[0].equalsIgnoreCase("list")) || (args[0].equalsIgnoreCase("목록"))) {
					if (args.length == 1){
						if (forbidden.size() != 0){
							String s = forbidden.get(0);
							for (int a = 1;a<forbidden.size();a++) s = s+", "+forbidden.get(a);
							player.sendMessage(prefix+ "금지단어 목록");
							player.sendMessage(prefix+ s);
						}
					}
				}
			}
		}
		return false;
	}
}
