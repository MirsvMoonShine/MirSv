package com.mirsv.util;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerCollector {
	static ArrayList<MirUser> users = new ArrayList<>();
	
	public static Collection<String> getOnlinePlayersName() {
		ArrayList<String> aa = new ArrayList<String>();
		for (Player p : Bukkit.getOnlinePlayers()) {
			aa.add(p.getName());
		}
		
		return aa;
	}
	
	public static void addMirUser(Player p) {
		if (!isMirUser(p)) {
			users.add(new MirUser(p));
		}
	}
	
	public static void removeMirUser(Player p) {
		if (isMirUser(p)) {
			users.remove(getMirUser(p));
		}
	}
	
	public static MirUser getMirUser(Player p) {
		for (MirUser m : users) {
			if (m.getPlayer().equals(p)) {
				return m;
			}
		}
		return null;
	}
	
	public static boolean isMirUser(Player p) {
		if (getMirUser(p) != null) return true;
		return false;
	}
}
