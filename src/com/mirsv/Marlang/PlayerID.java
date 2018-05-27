package com.mirsv.Marlang;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.mirsv.MirPlugin;
import com.mirsv.Marlang.Util.GenerateNumber;
import com.mirsv.Marlang.Util.IsInt;

public class PlayerID extends MirPlugin implements Listener, CommandExecutor{
	
	FileConfiguration PlayerID;
	List<String> IDs;
	File folder = new File("plugins/Mirsv/PlayerID");
	File IDsFile = new File("plugins/" + pm.getDescription().getName() + "/PlayerID/IDs.ids");
	File PlayerIDFile = new File("plugins/" + pm.getDescription().getName() + "/PlayerID/PlayerID.yml");
	
	public PlayerID() {
		getListener(this);
		getCommand("신원", this);

		PlayerID = YamlConfiguration.loadConfiguration(PlayerIDFile);
		IDs = new ArrayList<String>();
		
		if(!folder.exists()) {
			folder.mkdir();
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l✅&r &fPlayerID 폴더를 성공적으로 만들었습니다!"));
		}
		
		try {
			if(!PlayerIDFile.exists()) {
			PlayerID.save(PlayerIDFile);
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l✅&r &fPlayerID 파일을 성공적으로 만들었습니다!"));
			}
		} catch (Exception exception) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l✅&r &fPlayerID 파일을 만들지 못했습니다!"));
		}
		if(PlayerIDFile.exists()) {
			try {
				PlayerID.load(PlayerIDFile);
				
				Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l✅&r &fPlayerID 파일을 성공적으로 불러왔습니다!"));
			} catch (Exception exception) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l✅&r &fPlayerID 파일을 불러오지 못했습니다!"));
			}
		}
		
		if(!IDsFile.exists()) {
			try {
				IDsFile.createNewFile();
				
				Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l✅&r &fIDsFile 파일을 성공적으로 만들었습니다!"));
			} catch (Exception exception) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l✅&r &fIDsFile 파일을 만들지 못했습니다!"));
			}
		}
		
		try {
			BufferedReader b = new BufferedReader(new FileReader(IDsFile));
			String line = b.readLine();
			if (line != null) {String[] str = line.split("_"); for (String add : str) IDs.add(add); }
			b.close();
			
		} catch (Exception exception) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l✅&r &fIDsFile 파일을 읽어오는데 실패했습니다!"));
		}
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onJoin(PlayerJoinEvent e) {
		Random r = new Random();
		Player p = e.getPlayer();
		
		if ((int)(Math.log10(PlayerID.getInt("Players." + p.getName() + ".ID"))+1) == 9) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l✅&r &f유저 " + p.getName() + "이 접속했습니다!"));
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&fID : " + PlayerID.get("Players." + p.getName() + ".ID")));
		} else {
			int ID = GenerateNumber.generateNumber(9);
			for(String s : IDs) {
				if(ID == Integer.parseInt(s)) {
					ID = GenerateNumber.generateNumber(9);
				}
			}
			try {
				BufferedReader br = new BufferedReader(new FileReader(IDsFile));
				String line = br.readLine();
				BufferedWriter bw = new BufferedWriter(new FileWriter(IDsFile));
				if(line == null) {
					line = "";
				}
				bw.write(line + ID + "_");
				br.close();
				bw.close();
			} catch (Exception exception) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l✅&r &fIDsFile 파일을 읽어오는데 실패했습니다!"));
			}
			PlayerID.set("Players." + p.getName() + ".ID", ID);
			PlayerID.set("IDs." + ID + ".Name", p.getName());
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l✅&r &f유저 " + p.getName() + "의 신원을 만들었습니다!"));
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&fID : " + PlayerID.get("Players." + p.getName() + ".ID")));
			
			try {
				PlayerID.save(PlayerIDFile);
				PlayerID.load(PlayerIDFile);
				Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l✅&r &fPlayerID 파일을 성공적으로 저장했습니다!"));
			} catch (Exception exception) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l✅&r &fPlayerID 파일을 저장하는데 실패했습니다!"));
			}
		}
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c콘솔에서는 사용할 수 없는 명령어입니다!"));
			return true;
		}else if(args.length == 0) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c사용법 : /신원 조회"));
			return true;
		}
		
		Player p = (Player) sender;
		
		if(args[0].equalsIgnoreCase("조회")) {
			if(args.length < 2) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f당신의 ID는 " + PlayerID.get("Players." + p.getName() + ".ID") + "입니다!"));
				return true;
			} else if(!p.isOp()) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c권한이 부족합니다!"));
				return true;
			} else if(IsInt.isInt(args[1]) == true) {
				if(((int)(Math.log10(Integer.parseInt(args[1]))+1) != 9)) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c잘못된 ID 형식입니다!"));
					return true;
				}
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fID가 " + args[1] + "인 유저는 " + PlayerID.getString("IDs." + args[1] + ".Name") + "님 입니다!"));
				return true;
			}
			
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', args[1] + "님의 ID는 " + PlayerID.get("Players." + args[1] + ".ID") + "입니다!"));
			return true;
		}
		return true;
	}
	
}
