package com.mirsv.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerCollector {
	static Map<Player, MirUser> user = new HashMap<>();
	
	public static Collection<String> getOnlinePlayersName() {
		ArrayList<String> aa = new ArrayList<String>();
		for (Player p : Bukkit.getOnlinePlayers()) {
			aa.add(p.getName());
		}
		
		return aa;
	}
	
	public static void addMirUser(Player p) {
		if (!user.containsKey(p)) {
			user.put(p, new MirUser(p));
		}
	}
	
	public static void removeMirUser(Player p) {
		if (user.containsKey(p)) {
			user.remove(p);
		}
	}
	
	public static MirUser getMirUser(Player p) {
		if (isMirUser(p)) return user.get(p);
		return null;
	}
	
	public static boolean isMirUser(Player p) {
		if (user.containsKey(p)) return true;
		return false;
	}
}
