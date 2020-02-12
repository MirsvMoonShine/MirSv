package com.mirsv.function.list.Cokes.CustomPrefix;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class PrefixGui implements Listener {
	private final Player target;
	private final Player opener;
	private final User user;
	private final ArrayList<String> values;
	private int playerPage;
	private Inventory GUI;
	private boolean admin;

	public PrefixGui(Player opener, Plugin Plugin) {
		this.target = opener;
		this.opener = opener;
		Bukkit.getPluginManager().registerEvents(this, Plugin);
		ArrayList<String> list = new ArrayList<>();
		this.user = UserManager.getUser(target);
		list.add(user.getGroupPrefix());
		for (JsonElement element : CustomPrefix.getPrefix(user)) {
			list.add(element.getAsString());
		}
		values = list;
		admin = opener.isOp();
	}

	public PrefixGui(Player target, Player opener, Plugin Plugin) {
		this.target = target;
		this.opener = opener;
		Bukkit.getPluginManager().registerEvents(this, Plugin);
		ArrayList<String> list = new ArrayList<>();
		this.user = UserManager.getUser(target);
		list.add(user.getGroupPrefix());
		for (JsonElement element : CustomPrefix.getPrefix(user)) {
			list.add(element.getAsString());
		}
		values = list;
		admin = opener.isOp();
	}

	public void openGUI(int page) {
		int MaxPage = (this.values.size() - 1) / 36 + 1;
		if (MaxPage < page)
			page = 1;
		if (page < 1)
			page = 1;
		this.GUI = Bukkit.createInventory(null, 54,
				ChatColor.translateAlternateColorCodes('&', "&e" + target.getName() + "§f의 칭호 목록"));
		this.playerPage = page;
		int Count = 0;

		for (String name : this.values) {
			ItemStack is = new ItemStack(Material.BOOK);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&r" + name));
			ArrayList<String> lore = new ArrayList<>();
			lore.add(ChatColor.translateAlternateColorCodes('&', "&2>> &f이 칭호를 적용하려면 클릭하세요."));
			if (playerPage == 1 && Count == 0) {
				lore.add("§2>> §f해당 칭호는 그룹 칭호입니다.");
			} else if (admin) {
				lore.add(ChatColor.translateAlternateColorCodes('&', "&2>> &c이 칭호를 목록에서 삭제하려면 우클릭하세요."));
			}
			im.setLore(lore);
			is.setItemMeta(im);

			if (Count / 36 == page - 1) {
				this.GUI.setItem(Count % 36, is);
			}
			Count++;
		}

		if (page > 1) {
			ItemStack previousPage = new ItemStack(Material.ARROW, 1);
			ItemMeta previousMeta = previousPage.getItemMeta();
			previousMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b이전 페이지"));
			previousPage.setItemMeta(previousMeta);
			this.GUI.setItem(48, previousPage);
		}

		if (page != MaxPage) {
			ItemStack nextPage = new ItemStack(Material.ARROW, 1);
			ItemMeta nextMeta = nextPage.getItemMeta();
			nextMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b다음 페이지"));
			nextPage.setItemMeta(nextMeta);
			this.GUI.setItem(50, nextPage);
		}

		ItemStack Page = new ItemStack(Material.PAPER, 1);
		ItemMeta PageMeta = Page.getItemMeta();
		PageMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6페이지 &e" + page + " &6/ &e" + MaxPage));
		Page.setItemMeta(PageMeta);
		this.GUI.setItem(49, Page);

		if (target.isOp()) {
			ItemStack None = new ItemStack(Material.BOOK, 1);
			ItemMeta NoneMeta = None.getItemMeta();
			NoneMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c칭호 제거하기"));
			ArrayList<String> lore = new ArrayList<>();
			lore.add(ChatColor.translateAlternateColorCodes('&', "&2>> &f이 칭호를 적용하려면 클릭하세요."));
			NoneMeta.setLore(lore);
			None.setItemMeta(NoneMeta);
			this.GUI.setItem(51, None);
		}

		this.opener.openInventory(this.GUI);
	}

	@EventHandler
	private void onInventoryClose(InventoryCloseEvent e) {
		if (e.getInventory().equals(this.GUI)) {
			HandlerList.unregisterAll(this);
		}
	}

	@EventHandler
	private void onInventoryClick(InventoryClickEvent e) {
		if (e.getInventory().equals(this.GUI)) {
			Player p = (Player) e.getWhoClicked();
			e.setCancelled(true);

			if (e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta()
					&& e.getCurrentItem().getItemMeta().hasDisplayName()) {
				if (e.getCurrentItem().getItemMeta().getDisplayName()
						.equals(ChatColor.translateAlternateColorCodes('&', "&b이전 페이지"))) {
					openGUI(this.playerPage - 1);
				} else if (e.getCurrentItem().getItemMeta().getDisplayName()
						.equals(ChatColor.translateAlternateColorCodes('&', "&b다음 페이지"))) {
					openGUI(this.playerPage + 1);
				}
			}

			if (e.getCurrentItem() != null && e.getCurrentItem().getType().equals(Material.BOOK)
					&& e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
				if (e.getRawSlot() == 51) {
					JsonObject json = user.getConfig().getJson();
					if (!json.has("prefix")) json.add("prefix", new JsonObject());
					json = json.get("prefix").getAsJsonObject();
					json.addProperty("index", -1);
					p.sendMessage("§f당신은 더이상 칭호를 사용하지 않습니다.");
					p.sendMessage("설정 완료!");
				} else {
					String prefix = e.getCurrentItem().getItemMeta().getDisplayName();

					if (admin) {
						if (e.getClick().equals(ClickType.RIGHT)) {
							if ((playerPage - 1) * 36 + e.getRawSlot() == 0) {
								p.sendMessage("해당 칭호는 삭제할 수 없습니다.");
							} else {
								JsonObject json = user.getConfig().getJson();
								if (!json.has("prefix")) json.add("prefix", new JsonObject());
								json = json.get("prefix").getAsJsonObject();
								JsonArray prefixes = CustomPrefix.getPrefix(user);
								prefixes.remove((playerPage - 1) * 36 + e.getRawSlot() - 1);
								json.add("list", prefixes);
								json.addProperty("index", -1);
								if (user.getPlayer().isOnline())
									user.getPlayer().getPlayer().sendMessage("§f당신의 칭호 " + prefix + "§f이(가) 삭제되었습니다.");
								p.sendMessage("처리 완료!");
							}
						} else {
							JsonObject json = user.getConfig().getJson();
							if (!json.has("prefix")) json.add("prefix", new JsonObject());
							json = json.get("prefix").getAsJsonObject();
							json.addProperty("index", (playerPage - 1) * 36 + e.getRawSlot());
							if (user.getPlayer().isOnline())
								user.getPlayer().getPlayer().sendMessage("§f당신의 칭호가 " + prefix + "§f으로 설정되었습니다.");
							p.sendMessage("설정 완료!");
						}
					} else {
						JsonObject json = user.getConfig().getJson();
						if (!json.has("prefix")) json.add("prefix", new JsonObject());
						json = json.get("prefix").getAsJsonObject();
						json.addProperty("index", (playerPage - 1) * 36 + e.getRawSlot());
						if (user.getPlayer().isOnline())
							user.getPlayer().getPlayer().sendMessage("§f당신의 칭호가 " + prefix + "§f으로 설정되었습니다.");
						p.sendMessage("설정 완료!");
					}
				}
			}

			p.closeInventory();
		}
	}
}
