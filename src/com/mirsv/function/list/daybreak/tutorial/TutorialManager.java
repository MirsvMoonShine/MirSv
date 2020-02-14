package com.mirsv.function.list.daybreak.tutorial;

import com.mirsv.function.AbstractFunction;
import com.mirsv.function.list.daybreak.tutorial.list.Tutorial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TutorialManager extends AbstractFunction implements CommandExecutor {

	public static final Location SPAWN = new Location(Bukkit.getWorld("spawn"), 0.5, 154, 0.5, -90, 0);

	public TutorialManager() {
		super("튜토리얼", "1.0.0", "튜토리얼 매니저");
	}



	@Override
	protected void onEnable() {
		registerCommand("튜토리얼", this);
	}

	@Override
	protected void onDisable() {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
		if (sender instanceof Player) {
			Tutorial.toggle((Player) sender);
		} else {
			sender.sendMessage(ChatColor.RED + "콘솔에서 사용할 수 없는 명령어입니다.");
		}
		return true;
	}
}
