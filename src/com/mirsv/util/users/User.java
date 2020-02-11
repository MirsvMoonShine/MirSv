package com.mirsv.util.users;

import com.google.common.base.Preconditions;
import com.mirsv.Mirsv;
import com.mirsv.util.data.FileUtil;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import net.luckperms.api.context.ContextManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class User {

	private static final Logger logger = Logger.getLogger(User.class.getName());

	private final OfflinePlayer player;
	private final Resident townyResident;
	private final File configFile;
	private final YamlConfiguration config;
	private int flag = 0x0;
	private Channel chatChannel = Channel.GLOBAL_CHAT;

	public User(OfflinePlayer player) throws NotRegisteredException {
		this.player = player;
		this.townyResident = TownyUniverse.getDataSource().getResident(player.getName());
		this.configFile = FileUtil.newFile(FileUtil.newDirectory("userdata"), player.getUniqueId() + ".yml");
		this.config = YamlConfiguration.loadConfiguration(configFile);
		if (!config.contains("nickname")) config.set("nickname", player.getName());
	}

	public Resident getResident() {
		return townyResident;
	}

	public boolean hasTown() {
		return townyResident.hasTown();
	}

	public Town getTown() {
		try {
			if (hasTown()) {
				return townyResident.getTown();
			}
		} catch (NotRegisteredException ignored) {}
		return null;
	}

	public boolean hasNation() {
		return hasTown() && getTown().hasNation();
	}

	public Nation getNation() {
		try {
			if (hasNation()) {
				return getTown().getNation();
			}
		} catch (NotRegisteredException ignored) {}
		return null;
	}

	public YamlConfiguration getConfig() {
		return config;
	}

	public String getNickname() {
		return ChatColor.translateAlternateColorCodes('&', config.getString("nickname", player.getName()));
	}

	public void setNickname(String nickname) {
		config.set("nickname", nickname);
		reloadConfig();
	}

	public int getDonation() {
		return config.getInt("donation", 0);
	}

	public OfflinePlayer getPlayer() {
		return player;
	}

	public String getGroupPrefix() {
		net.luckperms.api.model.user.User user = Mirsv.getPlugin().getPermAPI().getUserManager().getUser(player.getUniqueId());
		ContextManager contextManager = Mirsv.getPlugin().getPermAPI().getContextManager();
		return user.getCachedData().getMetaData(contextManager.getQueryOptions(user).orElse(contextManager.getStaticQueryOptions())).getPrefix();
	}

	public void reloadConfig() {
		try {
			config.save(configFile);
			config.load(configFile);
		} catch (IOException | InvalidConfigurationException e) {
			logger.log(Level.SEVERE, player.getName() + "님의 유저 데이터를 다시 저장하는 도중 오류가 발생하였습니다.", e);
		}
	}

	public void addFlag(int flag) {
		this.flag |= flag;
	}

	public void removeFlag(int flag) {
		this.flag &= ~flag;
	}

	public boolean hasFlag(int flag) {
		return (this.flag & flag) == flag;
	}

	public static class Flag {

	}

	public Channel getChatChannel() {
		return chatChannel;
	}

	public void setChatChannel(Channel chatChannel) {
		this.chatChannel = Preconditions.checkNotNull(chatChannel);
	}

	public enum Channel {
		GLOBAL_CHAT, TOWN_CHAT, NATION_CHAT, LOCAL_CHAT, PARTY_CHAT, MODERATOR_CHAT, ADMIN_CHAT, NOTICE_CHAT
	}

}
