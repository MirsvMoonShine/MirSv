package com.mirsv.function.list.daybreak.stock;

import com.mirsv.function.list.daybreak.item.ItemBuilder;
import com.mirsv.util.users.User;
import com.mirsv.util.users.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Collection;

public class StockViewGUI
		implements Listener, Stock.Observer {
	private static final ItemStack PREVIOUS_PAGE = new ItemBuilder()
			.type(Material.ARROW)
			.displayName(ChatColor.translateAlternateColorCodes('&', "&b이전 페이지"))
			.build();

	private static final ItemStack NEXT_PAGE = new ItemBuilder()
			.type(Material.ARROW)
			.displayName(ChatColor.translateAlternateColorCodes('&', "&b다음 페이지"))
			.build();

	private final OfflinePlayer player;
	private final User user;
	private final Player viewer;
	private int currentPage = 1;
	private Inventory gui;

	public StockViewGUI(OfflinePlayer player, Player viewer, Plugin plugin) {
		this.player = player;
		this.user = UserManager.getUser(player);
		this.viewer = viewer;
		Stock.attachObserver(this);
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	public void openGUI(int page) {
		Collection<Stock> stocks = Stock.getStocks();
		int maxPage = (stocks.size() - 1) / 36 + 1;
		if (maxPage < page) page = 1;
		if (page < 1) page = 1;
		this.gui = Bukkit.createInventory(null, 54, ChatColor.BLACK + this.player.getName() + "님의 주식 목록");
		this.currentPage = page;
		int count = 0;

		ItemBuilder builder = new ItemBuilder();

		for (Stock stock : stocks) {
			if (count / 36 == page - 1) {
				int amount = stock.getAmount(this.user), price = stock.getPrice();
				builder.type(amount > 0 ? Material.ENCHANTED_BOOK : Material.BOOK)
						.lore("",
								stock.isDelisted() ? ChatColor.RED + "상장 폐지됨." : "",
								"",
								ChatColor.GOLD + "▶ " + ChatColor.WHITE + "현재 주가: " + ChatColor.YELLOW + price + "원 " + ChatColor.DARK_GRAY + "(" + ChatColor.GRAY + "수수료" + ChatColor.WHITE + ": " + price / 50 + "원" + ChatColor.DARK_GRAY + ")", ChatColor.DARK_RED + "▶ " + ChatColor.WHITE + "보유중인 주식 수: " + ChatColor.RED + amount + "개");

				this.gui.setItem(count % 36, builder.displayName(ChatColor.AQUA + stock.getName()).build());
			}
			count++;
		}

		if (page > 1) this.gui.setItem(48, PREVIOUS_PAGE);
		if (page != maxPage) this.gui.setItem(50, NEXT_PAGE);
		this.gui.setItem(49, builder
				.type(Material.PAPER)
				.displayName(ChatColor.translateAlternateColorCodes('&', "&6페이지 &e" + page + " &6/ &e" + maxPage))
				.lore().build());

		this.viewer.openInventory(this.gui);
	}

	public void onUpdate() {
		openGUI(this.currentPage);
	}

	@EventHandler
	private void onInventoryClose(InventoryCloseEvent e) {
		if (e.getInventory().equals(this.gui)) {
			HandlerList.unregisterAll(this);
			Stock.detachObserver(this);
		}
	}

	@EventHandler
	private void onInventoryClick(InventoryClickEvent e) {
		if (e.getInventory().equals(this.gui)) {
			e.setCancelled(true);
			if ((e.getCurrentItem() != null) && (e.getCurrentItem().hasItemMeta()) && (e.getCurrentItem().getItemMeta().hasDisplayName())) {
				String displayName = e.getCurrentItem().getItemMeta().getDisplayName();
				if (displayName.equals(ChatColor.AQUA + "이전 페이지"))
					openGUI(this.currentPage - 1);
				else if (displayName.equals(ChatColor.AQUA + "다음 페이지"))
					openGUI(this.currentPage + 1);
			}
		}
	}

}