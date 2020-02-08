package com.mirsv.util;

import java.io.File;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.mirsv.util.data.FileUtil;
import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;

public class MirUser {
	Player p;
	YamlConfiguration config;
	File f;
	MirUser m;
	Resident r;
	
	public MirUser(Player p) {
		this.p = p;
		loadConfiguration();
	}
	
	public MirUser(UUID u) {
		this.p = Bukkit.getPlayer(u);
		loadConfiguration();
	}
	
	void loadConfiguration() {
		FileUtil.getFolder("UserData");
		f = FileUtil.getFile("UserData/"+p.getUniqueId()+".dat");
		config = YamlConfiguration.loadConfiguration(f);
		if (config.getString("nickname") != null) {
			config.set("nickname", p.getName());
		}
	}
	
	public Town getTown() {
		try {
			if (getResident().hasTown()) {
				return getResident().getTown();
			} else {
				return null;
			}
		} catch (NotRegisteredException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Nation getNation() {
		try {
			Town t = getTown();
			if (t.hasNation()) {
				return t.getNation();
			}
		} catch (NotRegisteredException e) {
			return null;
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public Resident getResident() {
		try {
			r = Towny.getPlugin().getTownyUniverse().getResident(getNickname());
			return r;
		} catch (NotRegisteredException e) {
			return null;
		}
	}
	
	public String getNickname() {
		return config.getString("nickname", p.getName());
	}
	
	public int getDonation() {
		return config.getInt("donation", 0);
	}
	
	public Player getPlayer() {
		return p;
	}
}
