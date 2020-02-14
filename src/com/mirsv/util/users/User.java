package com.mirsv.util.users;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mirsv.Mirsv;
import com.mirsv.util.database.FileUtil;
import com.mirsv.util.database.JsonConfiguration;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import net.luckperms.api.context.ContextManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.OfflinePlayer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class User {

	private static final Logger logger = Logger.getLogger(User.class.getName());

	private final OfflinePlayer player;
	private final Resident townyResident;
	private final File configFile;
	private final JsonConfiguration config;
	private final JsonObject configJson;
	private int flag = 0x0;
	private Channel chatChannel = Channel.GLOBAL_CHAT;
	private final Map<String, Cooldown> cooldownMap = new HashMap<>();

	public User(OfflinePlayer player) throws NotRegisteredException {
		this.player = player;
		this.townyResident = TownyUniverse.getDataSource().getResident(player.getName());
		this.configFile = FileUtil.newFile(FileUtil.newDirectory("userdata"), player.getUniqueId() + ".json");
		this.config = new JsonConfiguration(configFile);
		this.configJson = config.getJson();
		if (!configJson.has("nickname")) configJson.addProperty("nickname", player.getName());
		if (!configJson.has("warnings")) configJson.add("warnings", new JsonArray());
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

	public JsonConfiguration getConfig() {
		return config;
	}

	public String getNickname() {
		if (!configJson.has("nickname")) configJson.addProperty("nickname", player.getName());
		return ChatColor.translateAlternateColorCodes('&', configJson.get("nickname").getAsString());
	}

	public void setNickname(String nickname) {
		configJson.addProperty("nickname", nickname);
	}

	public OfflinePlayer getPlayer() {
		return player;
	}

	public String getGroupPrefix() {
		net.luckperms.api.model.user.User user = Mirsv.getPlugin().getPermAPI().getUserManager().getUser(player.getUniqueId());
		ContextManager contextManager = Mirsv.getPlugin().getPermAPI().getContextManager();
		return user.getCachedData().getMetaData(contextManager.getQueryOptions(user).orElse(contextManager.getStaticQueryOptions())).getPrefix();
	}

	public void addFlag(int flag) {
		this.flag |= flag;
	}

	public void removeFlag(int flag) {
		this.flag &= ~flag;
	}

	public void toggleFlag(int flag) {
		this.flag ^= flag;
	}

	public boolean hasFlag(int flag) {
		return (this.flag & flag) == flag;
	}

	public static class Flag {

		public static final int SUGAR_CANE_MODE = 0x1;

	}

	public Channel getChatChannel() {
		return chatChannel;
	}

	public void setChatChannel(Channel chatChannel) {
		this.chatChannel = Preconditions.checkNotNull(chatChannel);
	}

	public enum Channel {
		GLOBAL_CHAT, TOWN_CHAT, NATION_CHAT, LOCAL_CHAT, PARTY_CHAT, MODERATOR_CHAT, ADMIN_CHAT, NOTICE_CHAT;
	}

	public final Cooldown CALL_PLAYER = new Cooldown(60);

	public static class Cooldown {

		private final int cooldown;
		protected long lastAction = 0L;

		public Cooldown(int cooldown) {
			this.cooldown = cooldown;
		}

		public boolean action() {
			if (!isCooldown()) {
				this.lastAction = System.currentTimeMillis();
				return true;
			}
			return false;
		}

		public boolean isCooldown() {
			return TimeUnit.SECONDS.convert(System.currentTimeMillis() - lastAction, TimeUnit.MILLISECONDS) < cooldown;
		}

		public long getLeftCooldown() {
			return cooldown - TimeUnit.SECONDS.convert(System.currentTimeMillis() - lastAction, TimeUnit.MILLISECONDS);
		}

	}

}
