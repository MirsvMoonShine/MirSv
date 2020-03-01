package com.mirsv.function.list.daybreak;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mirsv.function.AbstractFunction;
import com.mirsv.util.JsonItemStack;
import com.mirsv.util.users.UserManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeliveryService extends AbstractFunction implements CommandExecutor {

	public DeliveryService() {
		super("배송 서비스", "1.0.0");
	}

	@Override
	protected void onEnable() {
		registerCommand("보관함", this);
	}

	@Override
	protected void onDisable() {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			JsonObject user = UserManager.getUser(player).getConfig().getJson();
			JsonItemStack.fromJson(getStorage(user));
		}
		return true;
	}

	public static JsonArray getStorage(JsonObject user) {
		if (!user.has("storage")) user.add("storage", new JsonArray());
		return user.getAsJsonArray("storage");
	}

}
