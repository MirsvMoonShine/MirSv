package com.mirsv.function.list.daybreak.achievements;

import com.google.gson.JsonObject;
import com.mirsv.function.list.daybreak.achievements.reward.Reward;
import com.mirsv.util.users.User;
import com.mirsv.util.users.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class Achievement {

	private final String id;
	private final String name;
	private final Type type;
	private final String[] description;
	private final Reward[] rewards;

	public Achievement(String id, String name, Type type, String[] description,  Reward ... rewards) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.description = description;
		this.rewards = rewards;
		AchievementManager.achievements.put(name, this);
	}

	public final String getId() {
		return id;
	}

	public final String getName() {
		return name;
	}

	public Type getType() {
		return type;
	}

	public String[] getDescription() {
		return description;
	}

	public Reward[] getRewards() {
		return rewards;
	}

	public final JsonObject getJson(User user) {
		JsonObject json = user.getConfig().getJson();
		if (!json.has("achievements")) json.add("achievements", new JsonObject());
		json = json.getAsJsonObject("achievements");
		if (!json.has(id)) json.add(id, new JsonObject());
		return json.getAsJsonObject(id);
	}

	public final JsonObject getJson(Player player) {
		return getJson(UserManager.getUser(player));
	}

	public final boolean isRewarded(JsonObject json) {
		if (!json.has("rewarded")) json.addProperty("rewarded", false);
		return json.get("rewarded").getAsBoolean();
	}

	public final void setRewarded(JsonObject json, boolean rewarded) {
		json.addProperty("rewarded", rewarded);
	}

	public final void reward(Player player) {
		JsonObject json = getJson(player);
		if (!isRewarded(json)) {
			setRewarded(json, true);
			for (Reward reward : rewards) {
				reward.reward(player);
			}
		}
	}

	public final boolean hasAchieved(JsonObject json) {
		if (!json.has("achieved")) json.addProperty("achieved", false);
		return json.get("achieved").getAsBoolean();
	}

	public final void setAchieved(JsonObject json, boolean achieved) {
		json.addProperty("achieved", achieved);
	}

	public final void achieve(Player player) {
		JsonObject json = getJson(player);
		if (!hasAchieved(json)) {
			setAchieved(json, true);
			type.noticer.notice(player, this);
		}
	}

	public enum Type {

		ADVANCE(new Noticer() {
			@Override
			public void notice(Player player, Achievement achievement) {
				Bukkit.broadcastMessage(player.getName() + "이(가) " + ChatColor.AQUA + "[" + achievement.getName() + "]" + ChatColor.WHITE + " 발전 과제를 달성했습니다");
			}
		}),
		CHALLENGE(new Noticer() {
			@Override
			public void notice(Player player, Achievement achievement) {
				Bukkit.broadcastMessage(player.getName() + "이(가) " + ChatColor.YELLOW + "[" + achievement.getName() + "]" + ChatColor.WHITE + " 도전 과제를 달성했습니다");
			}
		});

		private final Noticer noticer;

		Type(Noticer noticer) {
			this.noticer = noticer;
		}

	}

	public interface Noticer {

		void notice(Player player, Achievement achievement);

	}

}
