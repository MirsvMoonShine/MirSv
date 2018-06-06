package com.mirsv.Marlang.Vote;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.mirsv.MirPlugin;

public class VoteConfigManager extends MirPlugin{
	
	FileConfiguration VotePoint;
	File folder = new File("plugins/Mirsv/VotePoint");
	File VotePointFile = new File("plugins/" + pm.getDescription().getName() + "/VotePoint/VotePoint.yml");
	
	public VoteConfigManager() {
		VotePoint = YamlConfiguration.loadConfiguration(VotePointFile);
		SetupConfig();
	}
	
	public void SetupConfig() {
		
		//CustomAdvancements 폴더 만들기
		if(!folder.exists()) {
			folder.mkdir();
		}
		
		//Checker.yml 파일 만들기
		if(!VotePointFile.exists()) {
			try {
				VotePointFile.createNewFile();
				SaveConfig();
			} catch (IOException e) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l✅&r &fVotePoint.yml 파일을 만들지 못했습니다."));
				e.printStackTrace();
			}
		} else {
			SaveConfig();
		}
		
	}
	
	//Checker.yml 파일 불러오기
	public void LoadConfig() {
		try {
			VotePoint.load(VotePointFile);
		} catch (IOException | InvalidConfigurationException e) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l✅&r &fVotePoint.yml 파일을 불러오지 못했습니다."));
			e.printStackTrace();
		}
	}
	
	//Checker.yml 파일 저장하기
	public void SaveConfig() {
		try {
			VotePoint.options().copyDefaults(true);
			VotePoint.save(VotePointFile);
			LoadConfig();
		} catch (IOException e) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l✅&r &fVotePoint.yml 파일을 저장하지 못했습니다."));
			e.printStackTrace();
		}
	}
	
}
