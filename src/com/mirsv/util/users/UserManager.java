package com.mirsv.util.users;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;

import com.google.common.cache.LoadingCache;
import com.mirsv.function.autosave.AutoSave;
import com.mirsv.function.autosave.AutoSaveManager;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class UserManager {

	private static final Map<UUID, User> users = new HashMap<>();
	
	public static List<String> getOnlinePlayersName() {
		return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
	}

	static {
		AutoSaveManager.registerAutoSave(new AutoSave() {
			@Override
			public void Save() {
				List<UUID> cleanUp = new LinkedList<>();
				for (Entry<UUID, User> entry : users.entrySet()) {
					User user = entry.getValue();
					user.getConfig().save();
					if (!user.getPlayer().isOnline()) {
						cleanUp.add(entry.getKey());
					}
				}
				for (UUID uuid : cleanUp) {
					users.remove(uuid);
				}
			}
		});
	}

	public static User getUser(OfflinePlayer player) {
		if (!users.containsKey(player.getUniqueId())) {
			try {
				users.put(player.getUniqueId(), new User(player));
			} catch (NotRegisteredException e) {
				throw new RuntimeException(e);
			}
		}
		return users.get(player.getUniqueId());
	}

}
