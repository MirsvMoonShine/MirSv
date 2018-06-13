package com.mirsv.Marlang.CustomAdvancements.Manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.mirsv.MirPlugin;

public class ConfigurationManager extends MirPlugin{
	
	public FileConfiguration Checker;
	File folder = new File("plugins/Mirsv/CustomAdvancements");
	File CheckerFile = new File("plugins/" + pm.getDescription().getName() + "/CustomAdvancements/Checker.yml");
	
	public ConfigurationManager() {
		Checker = YamlConfiguration.loadConfiguration(CheckerFile);
		SetupConfig();
	}
	
	public void SetupConfig() {
		
		//CustomAdvancements 폴더 만들기
		if(!folder.exists()) {
			folder.mkdir();
		}
		
		//Checker.yml 파일 만들기
		if(!CheckerFile.exists()) {
			try {
				CheckerFile.createNewFile();
				SaveConfig();
			} catch (IOException e) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l✅&r &fChecker.yml 파일을 만들지 못했습니다."));
				e.printStackTrace();
			}
		} else {
			SaveConfig();
		}
		
	}
	
	//Checker.yml 파일 불러오기
	public void LoadConfig() {
		try {
			Checker.load(CheckerFile);
		} catch (IOException | InvalidConfigurationException e) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l✅&r &fChecker.yml 파일을 불러오지 못했습니다."));
			e.printStackTrace();
		}
	}
	
	//Checker.yml 파일 저장하기
	public void SaveConfig() {
		try {
			Checker.options().copyDefaults(true);
			Checker.save(CheckerFile);
			LoadConfig();
		} catch (IOException e) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l✅&r &fChecker.yml 파일을 저장하지 못했습니다."));
			e.printStackTrace();
		}
	}
	
}
