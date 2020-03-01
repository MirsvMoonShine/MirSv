package com.mirsv.function.list.daybreak.stock;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mirsv.Mirsv;
import com.mirsv.util.math.MathUtil;
import com.mirsv.util.users.User;
import com.mirsv.util.users.UserManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Stock {

	public interface Observer {
		void onUpdate();
	}

	private static final String prefix = ChatColor.translateAlternateColorCodes('&', "&4[&c주식&4] &f");

	private static final Set<Observer> observers = new HashSet<>();

	public static void attachObserver(Observer observer) {
		observers.add(observer);
	}

	public static void detachObserver(Observer observer) {
		observers.remove(observer);
	}

	private static final Map<String, Stock> stocks = new HashMap<>();

	static {
		JsonObject database = Mirsv.database.getJson();
		if (!database.has("stocks")) database.add("stocks", new JsonArray());
		JsonArray stocksArray = database.getAsJsonArray("stocks");
		for (JsonElement element : stocksArray) {
			new Stock(element.getAsJsonObject());
		}
	}

	public static Collection<Stock> getStocks() {
		return stocks.values();
	}

	public static Stock getStock(String name) {
		return stocks.get(name);
	}

	private final Random random = new Random();
	private final String name;
	private final JsonObject stock;
	private final List<StockEvent> events;

	private Stock(JsonObject stock) {
		this.stock = stock;
		this.name = stock.get("name").getAsString();
		if (!stocks.containsKey(name)) {
			stocks.put(name, this);
		} else {
			throw new IllegalArgumentException("이미 존재하는 주식 이름입니다: " + name);
		}

		if (!stock.has("events")) stock.add("events", new JsonArray());
		JsonArray eventArray = stock.getAsJsonArray("events");
		this.events = new ArrayList<>(eventArray.size());
		for (JsonElement element : eventArray) events.add(new StockEvent(element.getAsJsonObject()));

		if (!stock.has("period")) stock.addProperty("period", 300);
		final int period = stock.get("period").getAsInt();
		if (!isDelisted()) new BukkitRunnable() {
			@Override
			public void run() {
				events.get(random.nextInt(events.size())).fire();
				for (Observer observer : observers) observer.onUpdate();
				if (isDelisted()) {
					this.cancel();
					Bukkit.broadcastMessage(prefix + ChatColor.YELLOW + name + ChatColor.WHITE + "(이)가 상장 폐지되었습니다.");
				}
			}
		}.runTaskTimer(Mirsv.getPlugin(), period * 20, period * 20);
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return stock.get("price").getAsInt();
	}

	public boolean isDelisted() {
		return getPrice() <= 0;
	}

	public int getAmount(User user) {
		JsonObject json = getJson(user);
		if (!json.has("amount")) json.addProperty("amount", 0);
		return json.get("amount").getAsInt();
	}

	public int getAmount(JsonObject user) {
		if (!user.has("amount")) user.addProperty("amount", 0);
		return user.get("amount").getAsInt();
	}

	public void buy(User user, int amount) {
		if (!isDelisted()) {
			JsonObject userJson = getJson(user);
			int stockAmount = getAmount(userJson);
			int totalPrice = (amount * getPrice()) + ((getPrice() / 50) * amount);
			Economy economy = Mirsv.getPlugin().getEconomy();
			if (economy.has(user.getPlayer(), totalPrice)) {
				economy.withdrawPlayer(user.getPlayer(), totalPrice);
				userJson.addProperty("amount", stockAmount + amount);
			} else {
				OfflinePlayer offlinePlayer = user.getPlayer();
				if (offlinePlayer.isOnline()) {
					Player player = offlinePlayer.getPlayer();
					player.sendMessage(ChatColor.RED + "돈이 부족합니다.");
					player.playSound(player.getLocation(), Sound.BLOCK_METAL_BREAK, 5, 1);
				}
			}
		} else {
			OfflinePlayer offlinePlayer = user.getPlayer();
			if (offlinePlayer.isOnline()) {
				Player player = offlinePlayer.getPlayer();
				player.sendMessage(ChatColor.RED + "상장 폐지됐습니다.");
				player.playSound(player.getLocation(), Sound.BLOCK_METAL_BREAK, 5, 1);
			}
		}
	}

	public void buyAll(User user) {
		if (!isDelisted()) {
			JsonObject userJson = getJson(user);
			Economy economy = Mirsv.getPlugin().getEconomy();
			int price = getPrice(), amount = (int) Math.floor(economy.getBalance(user.getPlayer()) / getPrice()), totalPrice = (amount * price) + ((getPrice() / 50) * amount);
			while (economy.getBalance(user.getPlayer()) < totalPrice && amount >= 0) {
				amount--;
				totalPrice = (amount * price) + ((getPrice() / 50) * amount);
			}
			if (amount > 0) {
				economy.withdrawPlayer(user.getPlayer(), totalPrice);
				userJson.addProperty("amount", getAmount(userJson) + amount);
			} else {
				OfflinePlayer offlinePlayer = user.getPlayer();
				if (offlinePlayer.isOnline()) {
					Player player = offlinePlayer.getPlayer();
					player.sendMessage(ChatColor.RED + "돈이 부족합니다.");
					player.playSound(player.getLocation(), Sound.BLOCK_METAL_BREAK, 5, 1);
				}
			}
		} else {
			OfflinePlayer offlinePlayer = user.getPlayer();
			if (offlinePlayer.isOnline()) {
				Player player = offlinePlayer.getPlayer();
				player.sendMessage(ChatColor.RED + "상장 폐지됐습니다.");
				player.playSound(player.getLocation(), Sound.BLOCK_METAL_BREAK, 5, 1);
			}
		}
	}

	public void sell(User user, int amount) {
		if (!isDelisted()) {
			JsonObject userJson = getJson(user);
			int stockAmount = getAmount(userJson);
			if (stockAmount >= amount) {
				Economy economy = Mirsv.getPlugin().getEconomy();
				economy.depositPlayer(user.getPlayer(), (amount * getPrice()) - ((getPrice() / 50.0) * amount));
				userJson.addProperty("amount", stockAmount - amount);
			} else if (stockAmount > 0) {
				sell(user, stockAmount);
			} else {
				OfflinePlayer offlinePlayer = user.getPlayer();
				if (offlinePlayer.isOnline()) {
					Player player = offlinePlayer.getPlayer();
					player.sendMessage(ChatColor.RED + "팔 수 있는 주식이 없습니다.");
					player.playSound(player.getLocation(), Sound.BLOCK_METAL_BREAK, 5, 1);
				}
			}
		} else {
			OfflinePlayer offlinePlayer = user.getPlayer();
			if (offlinePlayer.isOnline()) {
				Player player = offlinePlayer.getPlayer();
				player.sendMessage(ChatColor.RED + "상장 폐지됐습니다.");
				player.playSound(player.getLocation(), Sound.BLOCK_METAL_BREAK, 5, 1);
			}
		}
	}

	public void sellAll(User user) {
		if (!isDelisted()) {
			JsonObject userJson = getJson(user);
			int stockAmount = getAmount(userJson);
			if (stockAmount > 0) {
				Economy economy = Mirsv.getPlugin().getEconomy();
				economy.depositPlayer(user.getPlayer(), (stockAmount * getPrice()) - ((getPrice() / 50.0) * stockAmount));
				userJson.addProperty("amount", 0);
			} else {
				OfflinePlayer offlinePlayer = user.getPlayer();
				if (offlinePlayer.isOnline()) {
					Player player = offlinePlayer.getPlayer();
					player.sendMessage(ChatColor.RED + "팔 수 있는 주식이 없습니다.");
					player.playSound(player.getLocation(), Sound.BLOCK_METAL_BREAK, 5, 1);
				}
			}
		} else {
			OfflinePlayer offlinePlayer = user.getPlayer();
			if (offlinePlayer.isOnline()) {
				Player player = offlinePlayer.getPlayer();
				player.sendMessage(ChatColor.RED + "상장 폐지됐습니다.");
				player.playSound(player.getLocation(), Sound.BLOCK_METAL_BREAK, 5, 1);
			}
		}
	}

	public void broadcastMessage(String message) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			User user = UserManager.getUser(player);
			if (getAmount(user) > 0) {
				player.sendMessage(message);
			}
		}
	}

	public JsonObject getJson(User user) {
		JsonObject json = user.getConfig().getJson();
		if (!json.has("stocks")) json.add("stocks", new JsonObject());
		json = json.getAsJsonObject("stocks");
		if (!json.has(name)) json.add(name, new JsonObject());
		return json.getAsJsonObject(name);
	}

	public JsonObject getJson(Player player) {
		return getJson(UserManager.getUser(player));
	}

	public class StockEvent {

		private final String message;
		private final int min, max;

		private StockEvent(JsonObject eventObject) {
			this.message = ChatColor.translateAlternateColorCodes('&', eventObject.get("message").getAsString());
			this.min = eventObject.get("min").getAsInt();
			this.max = eventObject.get("max").getAsInt();
		}

		public void fire() {
			int price = getPrice(), changePercentage = MathUtil.randomBetween(min, max), change = (price / 100) * changePercentage;
			stock.addProperty("price", price + change);
			broadcastMessage(prefix + ChatColor.YELLOW + name + ChatColor.WHITE + ": " + message + " " + ChatColor.DARK_GRAY + "(" + ChatColor.GRAY + "변동량" + ChatColor.WHITE + ": " + (changePercentage > 0 ? "+" : "") + changePercentage + "% " + ChatColor.GRAY + "(" + ChatColor.WHITE + (change > 0 ? "+" : "") + change + ChatColor.GRAY + ")" + ChatColor.DARK_GRAY + ")");
		}

	}

}
