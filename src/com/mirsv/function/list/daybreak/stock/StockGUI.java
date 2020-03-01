package com.mirsv.function.list.daybreak.stock;

import com.mirsv.Mirsv;
import com.mirsv.function.list.daybreak.item.ItemBuilder;
import com.mirsv.util.users.User;
import com.mirsv.util.users.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class StockGUI implements Listener, Stock.Observer {

	private static final ItemStack PREVIOUS_PAGE = new ItemBuilder()
			.type(Material.ARROW)
			.displayName(ChatColor.translateAlternateColorCodes('&', "&b이전 페이지"))
			.build();

	private static final ItemStack NEXT_PAGE = new ItemBuilder()
			.type(Material.ARROW)
			.displayName(ChatColor.translateAlternateColorCodes('&', "&b다음 페이지"))
			.build();

	private final Player player;
	private final User user;
	private double HANRIVER_TEMPERATURE = Double.NaN;

	public StockGUI(Player player, Plugin plugin) {
		this.player = player;
		this.user = UserManager.getUser(player);
		Stock.attachObserver(this);
		Bukkit.getPluginManager().registerEvents(this, plugin);
		CompletableFuture.runAsync(new Runnable() {
			@Override
			public void run() {
				try {
					HANRIVER_TEMPERATURE = Double.parseDouble(Jsoup.connect("https://www.wpws.kr/hangang/").get().getElementById("temp").text().replace("도", ""));
					new BukkitRunnable() {
						@Override
						public void run() {
							if (player.getOpenInventory() != null && gui.equals(player.getOpenInventory().getTopInventory())) {
								openGUI(currentPage);
							}
						}
					}.runTask(Mirsv.getPlugin());
				} catch (IOException ignored) {}
			}
		});
	}

	private int currentPage = 1;
	private Inventory gui;

	public void openGUI(int page) {
		Collection<Stock> stocks = Stock.getStocks();
		int maxPage = ((stocks.size() - 1) / 36) + 1;
		if (maxPage < page) page = 1;
		if (page < 1) page = 1;
		gui = Bukkit.createInventory(null, 54, ChatColor.BLACK + "주식 목록");
		currentPage = page;
		int count = 0;

		final ItemBuilder builder = new ItemBuilder();

		for (Stock stock : stocks) {
			if (count / 36 == page - 1) {
				int amount = stock.getAmount(user);
				int price = stock.getPrice();
				builder.type(amount > 0 ? Material.ENCHANTED_BOOK : Material.BOOK)
						.lore(
								"",
								stock.isDelisted() ? ChatColor.RED + "상장 폐지됨." : "",
								"",
								ChatColor.GOLD + "▶ " + ChatColor.WHITE + "현재 주가: " + ChatColor.YELLOW + price + "원 " + ChatColor.DARK_GRAY + "(" + ChatColor.GRAY + "수수료" + ChatColor.WHITE + ": " + (price / 50) + "원" + ChatColor.DARK_GRAY + ")",
								ChatColor.DARK_RED + "▶ " + ChatColor.WHITE + "보유중인 주식 수: " + ChatColor.RED + amount + "개",
								"",
								ChatColor.DARK_GREEN + "▶ " + ChatColor.WHITE + "1주 구매하려면 " + ChatColor.GREEN + "좌클릭" + ChatColor.WHITE + "하세요.",
								ChatColor.DARK_GREEN + "▶ " + ChatColor.WHITE + "올인하려면 " + ChatColor.GREEN + "SHIFT + 좌클릭" + ChatColor.WHITE + "하세요.",
								ChatColor.DARK_RED + "▶ " + ChatColor.WHITE + "1주 판매하려면 " + ChatColor.RED + "우클릭" + ChatColor.WHITE + "하세요.",
								ChatColor.DARK_RED + "▶ " + ChatColor.WHITE + "모두 판매하려면 " + ChatColor.RED + "SHIFT + 우클릭" + ChatColor.WHITE + "하세요."
						);
				gui.setItem(count % 36, builder.displayName(ChatColor.AQUA + stock.getName()).build());
			}
			count++;
		}

		gui.setItem(45, builder
				.type(Material.GHAST_TEAR)
				.displayName(ChatColor.translateAlternateColorCodes('&', "&f현재 한강 물 온도: &b" + HANRIVER_TEMPERATURE + "도"))
				.lore(
						"©WPWS"
				).build());
		if (page > 1) gui.setItem(48, PREVIOUS_PAGE);
		if (page != maxPage) gui.setItem(50, NEXT_PAGE);
		gui.setItem(49, builder
				.type(Material.PAPER)
				.displayName(ChatColor.translateAlternateColorCodes('&', "&6페이지 &e" + page + " &6/ &e" + maxPage))
				.lore().build());

		player.openInventory(gui);
	}

	@Override
	public void onUpdate() {
		openGUI(currentPage);
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
		if (e.getInventory().equals(gui)) {
			int clickedSlot = e.getSlot();
			e.setCancelled(true);
			if (e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
				final String displayName = e.getCurrentItem().getItemMeta().getDisplayName();
				if (clickedSlot <= 44) {
					Stock stock = Stock.getStock(ChatColor.stripColor(displayName));
					if (stock != null) {
						if (e.getClick() == ClickType.LEFT) {
							stock.buy(user, 1);
							openGUI(currentPage);
						} else if (e.getClick() == ClickType.SHIFT_LEFT) {
							stock.buyAll(user);
							openGUI(currentPage);
						} else if (e.getClick() == ClickType.RIGHT) {
							stock.sell(user, 1);
							openGUI(currentPage);
						} else if (e.getClick() == ClickType.SHIFT_RIGHT) {
							stock.sellAll(user);
							openGUI(currentPage);
						}
					}
				} else {
					if (displayName.equals(ChatColor.AQUA + "이전 페이지")) {
						openGUI(currentPage - 1);
					} else if (displayName.equals(ChatColor.AQUA + "다음 페이지")) {
						openGUI(currentPage + 1);
					}
				}
			}
		}
	}

}
