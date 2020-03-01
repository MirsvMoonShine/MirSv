package com.mirsv.function.list.daybreak.market;

import com.mirsv.Mirsv;
import com.mirsv.function.AbstractFunction;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MarketManager extends AbstractFunction implements CommandExecutor {

	public MarketManager() {
		super("시세 상점", "1.0.0");
	}

	@Override
	protected void onEnable() {
		registerCommand("시세상점", this);
	}

	@Override
	protected void onDisable() {}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			new OreMarket((Player) sender, Mirsv.getPlugin()).openGUI();
		}
		return true;
	}

}
