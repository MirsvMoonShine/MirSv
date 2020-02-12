package com.mirsv.function.list.daybreak.achievements;

import com.mirsv.Mirsv;
import com.mirsv.function.AbstractFunction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;

public class AchievementManager extends AbstractFunction implements CommandExecutor {

	static final Map<String, Achievement> achievements = new HashMap<>();

	static {
		try {
			Class.forName("com.mirsv.function.list.daybreak.achievements.list.base.BaseAchievements");
		} catch (ClassNotFoundException ignored) {}
	}

	public static Achievement getAchievement(String name) {
		return achievements.get(name);
	}

	public AchievementManager() {
		super("Achievements", "1.0.0");
	}

	@Override
	protected void onEnable() {
		registerCommand("과제", this);
		for (Achievement achievement : achievements.values()) {
			if (achievement instanceof Listener) {
				Bukkit.getPluginManager().registerEvents((Listener) achievement, Mirsv.getPlugin());
			}
		}
	}

	@Override
	protected void onDisable() {
		for (Achievement achievement : achievements.values()) {
			if (achievement instanceof Listener) {
				HandlerList.unregisterAll((Listener) achievement);
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		switch (label) {
			case "과제":
				if (sender instanceof Player) {
					new AchievementGUI((Player) sender, Mirsv.getPlugin()).openGUI(1);
				} else {
					sender.sendMessage(ChatColor.RED + "콘솔에서 사용할 수 없는 명령어입니다.");
				}
				break;
		}
		return true;
	}

}
