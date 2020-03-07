package com.mirsv.function.list.daybreak.minigames;

import com.mirsv.function.list.daybreak.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class MinigamesGUI implements Listener {

	private static final ItemStack QUIT = new ItemBuilder()
			.type(Material.SPRUCE_DOOR)
			.displayName(ChatColor.translateAlternateColorCodes('&', "&b돌아가기"))
			.build();

	private enum Type {MAIN_MENU, RUSSIAN_ROULETTE}

	private Type type = Type.MAIN_MENU;
	private final Player player;

	public MinigamesGUI(Player player, Plugin plugin) {
		this.player = player;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	private int currentPage = 1;
	private Inventory gui;

	public void openGUI() {
		switch (type) {
			case MAIN_MENU:
				gui = Bukkit.createInventory(player, 27, ChatColor.translateAlternateColorCodes('&', "&5미니게임"));
				break;
		}

		player.openInventory(gui);
	}

}
