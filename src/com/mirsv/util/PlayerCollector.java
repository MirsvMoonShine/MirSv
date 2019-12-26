package com.mirsv.util;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerCollector {
	
	public static Collection<String> getOnlinePlayersName() {
		ArrayList<String> aa = new ArrayList<String>();
		for (Player p : Bukkit.getOnlinePlayers()) {
			aa.add(p.getName());
		}
		
		return aa;
	}

}
