package com.mirsv;

import com.google.gson.GsonBuilder;
import com.mirsv.function.AbstractFunction;
import com.mirsv.function.Functions;
import com.mirsv.function.autosave.AutoSave;
import com.mirsv.function.autosave.AutoSaveManager;
import com.mirsv.util.Messager;
import com.mirsv.util.database.FileUtil;
import com.mirsv.util.database.JsonDatabase;
import com.mirsv.util.thread.ThreadUtil;
import net.luckperms.api.LuckPerms;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * 미르서버 종합 플러그인
 *
 * @author Cokes_86, Daybreak, CatNote
 * @renewal Daybreak
 */
public class Mirsv extends JavaPlugin {

	public static JsonDatabase database = new JsonDatabase(FileUtil.newFile("database.json"), new GsonBuilder().setPrettyPrinting().create());
	private static Mirsv plugin;

	private LuckPerms luckPerms;
	private Economy economy;

	public static Mirsv getPlugin() {
		return plugin;
	}

	@Override
	public void onEnable() {
		plugin = this;

		getConfig().options().copyDefaults(true);

		getCommand("Mirsv").setExecutor(new MainCommand());

		if (Bukkit.getPluginManager().getPlugin("LuckPerms") != null) this.luckPerms = getProvider(LuckPerms.class);
		if (Bukkit.getPluginManager().getPlugin("Vault") != null)this.economy = getProvider(Economy.class);

		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

		StringJoiner joiner = new StringJoiner(ChatColor.translateAlternateColorCodes('&', ", "));
		for (AbstractFunction abstractFunction : initFunctions()) joiner.add(abstractFunction.getName());

		Messager.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9┌─┬─┐ &3┬ &b┌──┐ "));
		Messager.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9│ │ │ &3│ &b│──┘ &9M&3i&br&fServer v" + this.getDescription().getVersion()));
		Messager.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9│   │ &3│ &b│↘  &7플러그인이 최신 버전입니다."));
		Messager.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9│   │ &3┴ &b│ ↘ "));

		Messager.sendMessage("활성화된 기능: " + joiner.toString());
		ThreadUtil.onEnable();

		Messager.sendMessage("플러그인이 활성화되었습니다.");
		Bukkit.broadcastMessage(Messager.getPrefix() + "미르서버 플러그인이 활성화되었습니다.");

		AutoSaveManager.registerAutoSave(new AutoSave() {
			@Override
			public void Save() {
				database.save();
			}
		});
	}

	@Override
	public void onDisable() {
		Bukkit.broadcastMessage(Messager.getPrefix() + "미르서버 플러그인이 비활성화되었습니다.");
		Bukkit.broadcastMessage(Messager.getPrefix() + "채팅 채널이 해제되고 사용중이던 인벤토리, GUI가 닫힐 수 있습니다.");
		for (Player player : Bukkit.getOnlinePlayers()) player.closeInventory();
		ThreadUtil.onDisable();
	}

	private List<AbstractFunction> initFunctions() {
		List<AbstractFunction> list = new ArrayList<>();

		for (Functions functions : Functions.values()) {
			if (getConfig().getBoolean("enable." + functions.toString(), true) && functions.getCondition()) {
				getConfig().set("enable." + functions.toString(), true);
				AbstractFunction function = functions.getFunction();
				function.Enable();
				list.add(function);
			} else {
				getConfig().set("enable." + functions.toString(), false);
			}
		}

		return list;
	}

	public LuckPerms getPermAPI() {
		return luckPerms;
	}

	public Economy getEconomy() {
		return economy;
	}

	private static <T> T getProvider(Class<T> clazz) {
		if (Bukkit.getServicesManager().isProvidedFor(clazz)) {
			return Bukkit.getServicesManager().getRegistration(clazz).getProvider();
		}
		return null;
	}

}