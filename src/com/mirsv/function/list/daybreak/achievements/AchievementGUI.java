package com.mirsv.function.list.daybreak.achievements;

import com.google.gson.JsonObject;
import com.mirsv.function.list.daybreak.achievements.list.base.BaseAchievements;
import com.mirsv.function.list.daybreak.achievements.list.event.EventAchievements;
import com.mirsv.function.list.daybreak.achievements.reward.Reward;
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
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class AchievementGUI implements Listener {

	private static final ItemStack PREVIOUS_PAGE = new ItemBuilder()
			.type(Material.ARROW)
			.displayName(ChatColor.translateAlternateColorCodes('&', "&b이전 페이지"))
			.build();

	private static final ItemStack NEXT_PAGE = new ItemBuilder()
			.type(Material.ARROW)
			.displayName(ChatColor.translateAlternateColorCodes('&', "&b다음 페이지"))
			.build();

	private static final ItemStack QUIT = new ItemBuilder()
			.type(Material.SPRUCE_DOOR)
			.displayName(ChatColor.translateAlternateColorCodes('&', "&b돌아가기"))
			.build();

	private static final ItemStack BASE_ACHIEVEMENTS = new ItemBuilder()
			.type(Material.DIAMOND_SWORD)
			.displayName(ChatColor.translateAlternateColorCodes('&', "&b일반"))
			.build();

	private static final ItemStack EVENT_ACHIEVEMENTS = new ItemBuilder()
			.type(Material.DIAMOND)
			.displayName(ChatColor.translateAlternateColorCodes('&', "&d이벤트"))
			.build();

	private enum Type { MAIN_MENU, BASE, EVENT }
	private enum SortType { NAME }

	private final Player player;
	private final User user;
	private Type type = Type.MAIN_MENU;
	private SortType sortType = SortType.NAME;
	private Achievement information = null;

	public AchievementGUI(Player player, Plugin plugin) {
		this.player = player;
		this.user = UserManager.getUser(player);
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	private int currentPage = 1;
	private Inventory gui;

	public void openGUI(int page) {
		if (information == null) {
			switch (type) {
				case MAIN_MENU:
				{
					gui = Bukkit.createInventory(null, InventoryType.HOPPER, ChatColor.BLACK + "과제");
					gui.setItem(2, BASE_ACHIEVEMENTS);
					gui.setItem(1, EVENT_ACHIEVEMENTS);
					player.openInventory(gui);
					break;
				}
				case BASE:
				{
					final List<Achievement> achievements;
					switch (sortType) {
						case NAME:
							achievements = BaseAchievements.nameBased;
							break;
						default:
							achievements = BaseAchievements.nameBased;

					}
					int maxPage = ((achievements.size() - 1) / 36) + 1;
					if (maxPage < page) page = 1;
					if (page < 1) page = 1;
					gui = Bukkit.createInventory(null, 54, ChatColor.BLACK + "과제 목록");
					currentPage = page;
					int count = 0;

					final ItemBuilder builder = new ItemBuilder();

					for (Achievement achievement : achievements) {
						if (count / 36 == page - 1) {
							JsonObject json = achievement.getJson(user);
							if (achievement.hasAchieved(json)) {
								if (achievement.isRewarded(json)) {
									builder.type(Material.BOOK).lore(
											"",
											ChatColor.AQUA + "▶ " + ChatColor.WHITE + "과제 정보를 확인하려면 우클릭하세요."
									);
								} else {
									builder.type(Material.ENCHANTED_BOOK).lore(
											"",
											ChatColor.DARK_GREEN + "▶ " + ChatColor.GREEN + "보상을 수령하려면 좌클릭하세요.",
											ChatColor.AQUA + "▶ " + ChatColor.WHITE + "과제 정보를 확인하려면 우클릭하세요."
									);
								}
							} else {
								builder.type(Material.WRITABLE_BOOK).lore(
										"",
										ChatColor.DARK_RED + "▶ " + ChatColor.RED + "아직 달성하지 않았습니다.",
										ChatColor.AQUA + "▶ " + ChatColor.WHITE + "과제 정보를 확인하려면 우클릭하세요."
								);
							}
							gui.setItem(count % 36, builder.displayName(ChatColor.AQUA + achievement.getName()).build());
						}
						count++;
					}

					if (page > 1) {
						gui.setItem(48, PREVIOUS_PAGE);
					}

					if (page != maxPage) {
						gui.setItem(50, NEXT_PAGE);
					}

					gui.setItem(52, QUIT);

					gui.setItem(49, builder
							.type(Material.PAPER)
							.displayName(ChatColor.translateAlternateColorCodes('&', "&6페이지 &e" + page + " &6/ &e" + maxPage))
							.lore().build());

					player.openInventory(gui);
					break;
				}
				case EVENT:
				{
					final List<Achievement> achievements;
					switch (sortType) {
						case NAME:
							achievements = EventAchievements.nameBased;
							break;
						default:
							achievements = EventAchievements.nameBased;

					}
					int maxPage = ((achievements.size() - 1) / 36) + 1;
					if (maxPage < page) page = 1;
					if (page < 1) page = 1;
					gui = Bukkit.createInventory(null, 54, ChatColor.BLACK + "과제 목록");
					currentPage = page;
					int count = 0;

					final ItemBuilder builder = new ItemBuilder();

					for (Achievement achievement : achievements) {
						if (count / 36 == page - 1) {
							JsonObject json = achievement.getJson(user);
							if (achievement.hasAchieved(json)) {
								if (achievement.isRewarded(json)) {
									builder.type(Material.BOOK).lore(
											"",
											ChatColor.AQUA + "▶ " + ChatColor.WHITE + "과제 정보를 확인하려면 우클릭하세요."
									);
								} else {
									builder.type(Material.ENCHANTED_BOOK).lore(
											"",
											ChatColor.DARK_GREEN + "▶ " + ChatColor.GREEN + "보상을 수령하려면 좌클릭하세요.",
											ChatColor.AQUA + "▶ " + ChatColor.WHITE + "과제 정보를 확인하려면 우클릭하세요."
									);
								}
							} else {
								builder.type(Material.WRITABLE_BOOK).lore(
										"",
										ChatColor.DARK_RED + "▶ " + ChatColor.RED + "아직 달성하지 않았습니다.",
										ChatColor.AQUA + "▶ " + ChatColor.WHITE + "과제 정보를 확인하려면 우클릭하세요."
								);
							}
							gui.setItem(count % 36, builder.displayName(ChatColor.AQUA + achievement.getName()).build());
						}
						count++;
					}

					if (page > 1) {
						gui.setItem(48, PREVIOUS_PAGE);
					}

					if (page != maxPage) {
						gui.setItem(50, NEXT_PAGE);
					}

					gui.setItem(52, QUIT);

					gui.setItem(49, builder
							.type(Material.PAPER)
							.displayName(ChatColor.translateAlternateColorCodes('&', "&6페이지 &e" + page + " &6/ &e" + maxPage))
							.lore().build());

					player.openInventory(gui);
					break;
				}
			}
		} else {
			gui = Bukkit.createInventory(null, InventoryType.HOPPER, ChatColor.BLACK + information.getName());
			final ItemBuilder builder = new ItemBuilder();
			gui.setItem(0, builder.type(Material.BOOK).displayName(ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + " 이름 " + ChatColor.DARK_AQUA + "]").lore(ChatColor.WHITE + information.getName()).build());
			List<String> lore = new ArrayList<>();
			for (String description : information.getDescription()) {
				lore.add(ChatColor.WHITE + description);
			}
			gui.setItem(1, builder.type(Material.EMERALD).displayName(ChatColor.DARK_GREEN + "[" + ChatColor.GREEN + " 설명 " + ChatColor.DARK_GREEN + "]").lore(lore).build());
			lore = new ArrayList<>();
			for (Reward reward : information.getRewards()) {
				lore.add(reward.toString());
			}
			gui.setItem(2, builder.type(Material.SUNFLOWER).displayName(ChatColor.GOLD + "[" + ChatColor.YELLOW + " 보상 " + ChatColor.GOLD + "]").lore(lore).build());
			gui.setItem(4, QUIT);
			player.openInventory(gui);
		}
	}

	@EventHandler
	private void onInventoryClose(InventoryCloseEvent e) {
		if (e.getInventory().equals(this.gui)) {
			HandlerList.unregisterAll(this);
		}
	}

	@EventHandler
	private void onInventoryClick(InventoryClickEvent e) {
		if (e.getInventory().equals(gui)) {
			int clickedSlot = e.getSlot();
			e.setCancelled(true);
			if (e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
				final String displayName = e.getCurrentItem().getItemMeta().getDisplayName();
				final Material type = e.getCurrentItem().getType();
				if (this.type == Type.MAIN_MENU) {
					switch (clickedSlot) {
						case 2:
							this.type = Type.BASE;
							openGUI(1);
							break;
						case 1:
							this.type = Type.EVENT;
							openGUI(1);
							break;
					}
				} else if (information == null) {
					if (clickedSlot <= 44) {
						Achievement achievement = AchievementManager.getAchievement(ChatColor.stripColor(displayName));
						if (achievement != null) {
							if (e.getClick() == ClickType.LEFT) {
								if (type == Material.ENCHANTED_BOOK) {
									achievement.reward(player);
									openGUI(currentPage);
								}
							} else if (e.getClick() == ClickType.RIGHT) {
								this.information = achievement;
								openGUI(currentPage);
							}
						}
					} else {
						if (displayName.equals(ChatColor.AQUA + "이전 페이지")) {
							openGUI(currentPage - 1);
						} else if (displayName.equals(ChatColor.AQUA + "다음 페이지")) {
							openGUI(currentPage + 1);
						} else if (displayName.equals(ChatColor.AQUA + "돌아가기")) {
							this.type = Type.MAIN_MENU;
							openGUI(1);
						}
					}
				} else {
					if (clickedSlot == 4) {
						this.information = null;
						openGUI(currentPage);
					}
				}
			}
		}
	}

}
