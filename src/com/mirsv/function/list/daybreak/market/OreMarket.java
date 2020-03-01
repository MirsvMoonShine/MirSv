package com.mirsv.function.list.daybreak.market;

import com.mirsv.Mirsv;
import com.mirsv.function.list.daybreak.item.ItemBuilder;
import com.mirsv.function.list.daybreak.market.Product.TransactionType;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class OreMarket implements Listener {

	private static final Product[] products = {
			new Product("다이아몬드", Material.DIAMOND, 35),
			new Product("금괴", Material.GOLD_INGOT, 15),
			new Product("철괴", Material.IRON_INGOT, 7)
	};

	private static final Set<OreMarket> markets = new HashSet<>();

	private final Player player;

	public OreMarket(Player player, Plugin plugin) {
		this.player = player;
		Bukkit.getPluginManager().registerEvents(this, plugin);
		markets.add(this);
	}

	private Inventory gui;

	public void openGUI() {
		this.gui = Bukkit.createInventory(null, 54, ChatColor.BLACK + "광물 상점");
		ItemBuilder builder = new ItemBuilder();
		for (int i = 0; i < products.length; i++) {
			Product product = products[i];
			gui.setItem(i, builder.type(product.getType()).displayName(ChatColor.AQUA + product.getName()).lore(
					"",
					ChatColor.translateAlternateColorCodes('&', "&f가격: &e" + product.getPrice(TransactionType.NORMAL)),
					ChatColor.translateAlternateColorCodes('&', "&f구매가: &e" + product.getPrice(TransactionType.BUYING)),
					ChatColor.translateAlternateColorCodes('&', "&f판매가: &e" + product.getPrice(TransactionType.SELLING)),
					"",
					ChatColor.translateAlternateColorCodes('&', "&f1개 &a구매&f하려면 &a좌클릭"),
					ChatColor.translateAlternateColorCodes('&', "&f10개 &a구매&f하려면 &aSHIFT + 좌클릭"),
					ChatColor.translateAlternateColorCodes('&', "&f1개 &c판매&f하려면 &c우클릭"),
					ChatColor.translateAlternateColorCodes('&', "&f10개 &c판매&f하려면 &cSHIFT + 우클릭"),
					ChatColor.translateAlternateColorCodes('&', "&f모두 &c판매&f하려면 &c휠클릭")).build()
			);
		}
		player.openInventory(gui);
	}

	@EventHandler
	private void onInventoryClose(InventoryCloseEvent e) {
		if (e.getInventory().equals(this.gui)) {
			HandlerList.unregisterAll(this);
			markets.remove(this);
		}
	}

	@EventHandler
	private void onInventoryClick(InventoryClickEvent e) {
		if (e.getInventory().equals(gui)) {
			e.setCancelled(true);
			if (e.getCurrentItem() != null && e.getSlot() <= 44) {
				Product product = Product.getProduct(e.getCurrentItem().getType());
				if (product != null) {
					Economy economy = Mirsv.getPlugin().getEconomy();
					switch (e.getClick()) {
						case LEFT:
							if (economy.has(player, product.getPrice(TransactionType.BUYING))) {
								if (addItem(player.getInventory(), product.getType(), 1)) {
									economy.withdrawPlayer(player, Math.min(economy.getBalance(player), product.getPrice(TransactionType.BUYING)));
									product.addDemand(1);
									for (OreMarket market : markets) {
										market.openGUI();
									}
								} else player.sendMessage(ChatColor.RED + "인벤토리에 칸이 부족합니다.");
							} else player.sendMessage(ChatColor.RED + "돈이 부족합니다.");
							break;
						case SHIFT_LEFT:
							if (economy.has(player, product.getPrice(TransactionType.BUYING) * 10)) {
								if (addItem(player.getInventory(), product.getType(), 10)) {
									economy.withdrawPlayer(player, Math.min(economy.getBalance(player), product.getPrice(TransactionType.BUYING) * 10));
									product.addDemand(10);
									for (OreMarket market : markets) {
										market.openGUI();
									}
								} else player.sendMessage(ChatColor.RED + "인벤토리에 칸이 부족합니다.");
							} else player.sendMessage(ChatColor.RED + "돈이 부족합니다.");
							break;
						case RIGHT:
							if (removeItem(player.getInventory(), product.getType(), 1)) {
								economy.depositPlayer(player, product.getPrice(TransactionType.SELLING));
								product.addSupply(1);
								for (OreMarket market : markets) {
									market.openGUI();
								}
							} else player.sendMessage(ChatColor.RED + "팔 아이템이 부족합니다.");
							break;
						case SHIFT_RIGHT:
							if (removeItem(player.getInventory(), product.getType(), 10)) {
								economy.depositPlayer(player, product.getPrice(TransactionType.SELLING) * 10);
								product.addSupply(10);
								for (OreMarket market : markets) {
									market.openGUI();
								}
							} else player.sendMessage(ChatColor.RED + "팔 아이템이 부족합니다.");
							break;
						case MIDDLE:
							int sold = removeItem(player.getInventory(), product.getType());
							if (sold > 0) {
								economy.depositPlayer(player, product.getPrice(TransactionType.SELLING) * sold);
								product.addSupply(sold);
								for (OreMarket market : markets) {
									market.openGUI();
								}
							} else player.sendMessage(ChatColor.RED + "팔 아이템이 부족합니다.");
							break;
					}
				}
			}
		}
	}

	private static final ItemStack AIR = new ItemStack(Material.AIR);

	public static boolean addItem(Inventory inventory, Material type, int amount) {
		final Map<Integer, ItemStack> updates = new HashMap<>();
		for (int i = 0; i < inventory.getContents().length; i++) {
			ItemStack stack = inventory.getItem(i);
			if (stack == null || stack.getType() == Material.AIR) {
				if (amount <= 64) {
					updates.put(i, new ItemStack(type, amount));
					amount = 0;
					break;
				} else {
					updates.put(i, new ItemStack(type, 64));
					amount -= 64;
				}
			} else {
				if (stack.getType() != type || stack.hasItemMeta()) continue;
				int left = 64 - stack.getAmount();
				if (left >= amount) {
					updates.put(i, new ItemStack(type, stack.getAmount() + amount));
					amount = 0;
					break;
				} else {
					updates.put(i, new ItemStack(type, 64));
					amount -= left;
				}
			}
		}
		if (amount <= 0) {
			for (Entry<Integer, ItemStack> entry : updates.entrySet()) {
				inventory.setItem(entry.getKey(), entry.getValue());
			}
			return true;
		} else return false;
	}

	public static boolean removeItem(Inventory inventory, Material type, int amount) {
		final Map<Integer, ItemStack> updates = new HashMap<>();
		for (int i = 0; i < inventory.getContents().length; i++) {
			ItemStack stack = inventory.getItem(i);
			if (stack == null || stack.getType() != type) continue;
			if (stack.getAmount() >= amount) {
				updates.put(i, new ItemStack(type, stack.getAmount() - amount));
				amount = 0;
				break;
			} else {
				updates.put(i, AIR);
				amount -= stack.getAmount();
			}
		}
		if (amount <= 0) {
			for (Entry<Integer, ItemStack> entry : updates.entrySet()) {
				inventory.setItem(entry.getKey(), entry.getValue());
			}
			return true;
		} else return false;
	}

	public static int removeItem(Inventory inventory, Material type) {
		int amount = 0;
		for (int i = 0; i < inventory.getContents().length; i++) {
			ItemStack stack = inventory.getItem(i);
			if (stack == null || stack.getType() != type) continue;
			amount += stack.getAmount();
			inventory.setItem(i, AIR);
		}
		return amount;
	}

}
