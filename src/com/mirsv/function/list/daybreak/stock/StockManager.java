package com.mirsv.function.list.daybreak.stock;

import com.mirsv.Mirsv;
import com.mirsv.function.AbstractFunction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StockManager extends AbstractFunction implements CommandExecutor {

	public StockManager() {
		super("주식", "1.0.0");
	}

	@Override
	protected void onEnable() {
		try {
			Class.forName("com.mirsv.function.list.daybreak.stock.Stock");
		} catch (ClassNotFoundException ignored) {}
		registerCommand("주식", this);
	}

	@Override
	protected void onDisable() {}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
		if (sender instanceof Player) {
			if (args.length == 0) {
				new StockGUI((Player) sender, Mirsv.getPlugin()).openGUI(1);
			} else {
				OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
				if (target.hasPlayedBefore()) {
					new StockViewGUI(target, (Player) sender, Mirsv.getPlugin()).openGUI(1);
				} else {
					sender.sendMessage(ChatColor.RED + args[0] + "은(는) 존재하지 않는 플레이어입니다.");
				}
			}
		} else {
			sender.sendMessage(ChatColor.RED + "콘솔에서 사용할 수 없는 명령어입니다.");
		}
		return true;
	}

}
