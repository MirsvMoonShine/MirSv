package com.mirsv.function.list.Cokes.party;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Party {

	private static final String prefix = ChatColor.DARK_PURPLE + "파티 " + ChatColor.WHITE + "| ";
	public static Map<UUID, Party> partyMap = new HashMap<UUID, Party>() {
		@Override
		public Party put(UUID k, Party v) {
			if (k == null) {
				Bukkit.broadcastMessage("null 들어옴");
			}
			return super.put(k, v);
		}
	};
	public static Map<String, Party> parties = new HashMap<>();

	public static boolean hasParty(String name) {
		return parties.containsKey(name);
	}

	public static Party getParty(String name) {
		return parties.get(name);
	}

	public static Party getParty(OfflinePlayer player) {
		return partyMap.get(player.getUniqueId());
	}

	public static boolean hasParty(OfflinePlayer player) {
		return partyMap.containsKey(player.getUniqueId());
	}

	public static Collection<Party> getParties() {
		return parties.values();
	}

	private final UUID owner;
	private final String name;
	private final Set<UUID> players;
	private int flag;

	public Party(OfflinePlayer owner, String name, int initialFlag) {
		this.owner = owner.getUniqueId();
		this.name = name;
		this.players = new HashSet<>();
		addPlayer(owner.getUniqueId());
		this.flag = initialFlag;
		parties.put(name, this);
	}

	public Party(UUID owner, String name, int flag) {
		this.owner = owner;
		this.name = name;
		this.players = new HashSet<>();
		addPlayer(owner);
		this.flag = flag;
		parties.put(name, this);
	}

	public boolean hasPlayer(OfflinePlayer player) {
		return players.contains(player.getUniqueId());
	}

	public List<OfflinePlayer> getPlayers() {
		List<OfflinePlayer> list = new LinkedList<>();
		for (UUID uuid : players) list.add(Bukkit.getOfflinePlayer(uuid));
		return list;
	}

	public boolean addPlayer(UUID uuid) {
		partyMap.put(uuid, this);
		return players.add(uuid);
	}

	public boolean removePlayer(UUID uuid) {
		partyMap.remove(uuid);
		return players.remove(uuid);
	}

	public String getName() {
		return name;
	}

	public UUID getOwner() {
		return owner;
	}

	public void disband() {
		for (UUID uuid : players) {
			OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
			partyMap.remove(player.getUniqueId());
			if (player.isOnline()) player.getPlayer().sendMessage(prefix + "귀하가 소속된 파티가 해체되었습니다.");
		}
		parties.remove(this);
	}

	public void sendMessage(String msg) {
		for (UUID uuid : players) {
			OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
			if (player.isOnline()) player.getPlayer().sendMessage(msg);
		}
	}

	public void toggleFlag(int flag) {
		this.flag ^= flag;
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

	public int getFlag() {
		return flag;
	}

	public static class Flag {

		public static final int PVP = 0x1;
		public static final int OPEN = 0x2;

	}

}