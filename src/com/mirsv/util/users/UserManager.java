package com.mirsv.util.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.mirsv.util.users.User;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class UserManager {

	private static final Map<Player, User> user = new HashMap<>();
	
	public static List<String> getOnlinePlayersName() {
		return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
	}

	/*
	public static void removeUser(Player p) {
		user.remove(p);
	}*/
	
	public static User getUser(Player p) {
		if (!isInitialized(p)) {
			try {
				user.put(p, new User(p));
			} catch (NotRegisteredException e) {
				throw new RuntimeException(e);
			}
		}
		return user.get(p);
	}
	
	public static boolean isInitialized(Player p) {
		return user.containsKey(p);
	}
	
	public static Map<Player, User> getUsers() {
		return user;
	}
}
