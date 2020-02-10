package com.mirsv.util;

import java.io.File;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.mirsv.Mirsv;
import com.mirsv.util.data.FileUtil;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyUniverse;

import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.context.ContextManager;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;
import net.md_5.bungee.api.ChatColor;

public class MirUser {
	private final Player p;
	private YamlConfiguration config;
	private final File f;
	
	public MirUser(Player p) {
		this.p = p;
		FileUtil.getFolder("UserData");
		f = FileUtil.getFile("UserData/"+p.getUniqueId()+".dat");
		loadConfiguration();
	}
	
	public MirUser(UUID u) {
		this.p = Bukkit.getPlayer(u);
		FileUtil.getFolder("UserData");
		f = FileUtil.getFile("UserData/"+p.getUniqueId()+".dat");
		loadConfiguration();
	}
	
	void loadConfiguration() {
		config = YamlConfiguration.loadConfiguration(f);
		if (config.getString("nickname") == null) {
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
			if (getTown() != null && getTown().hasNation()) {
				return getTown().getNation();
			}
		} catch (NotRegisteredException e) {
			return null;
		}
		return null;
	}
	
	public YamlConfiguration getConfig() {
		return config;
	}
	
	public Resident getResident() {
		try {
			return TownyUniverse.getDataSource().getResident(p.getName());
		} catch (NotRegisteredException e) {
			return null;
		}
	}
	
	public String getNickname() {
		return ChatColor.translateAlternateColorCodes('&', config.getString("nickname", p.getName()));
	}
	
	public void setNickname(String s) {
		config.set("nickname", s);
		reloadConfig();
	}
	
	public int getDonation() {
		return config.getInt("donation", 0);
	}
	
	public Player getPlayer() {
		return p;
	}
	
	public String getGroupPrefix() {
		User user = Mirsv.getPlugin().getPermAPI().getUserManager().getUser(p.getUniqueId());
		ContextManager cm = Mirsv.getPlugin().getPermAPI().getContextManager();
		QueryOptions queryOptions = cm.getQueryOptions(user).orElse(cm.getStaticQueryOptions());
		CachedMetaData metaData = user.getCachedData().getMetaData(queryOptions);
		return metaData.getPrefix();
	}
	
	public void reloadConfig() {
		try {
			config.save(f);
			config = YamlConfiguration.loadConfiguration(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
