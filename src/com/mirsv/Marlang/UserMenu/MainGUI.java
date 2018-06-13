package com.mirsv.Marlang.UserMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.mirsv.MirPlugin;
import com.mirsv.Marlang.Adventure.AdventureGUI;
import com.mirsv.Marlang.CustomAdvancements.CustomAchievement;
import com.mirsv.Marlang.CustomAdvancements.GUI.CustomAdchievementGUI;
import com.mirsv.Marlang.CustomAdvancements.List.AchievementList;
import com.mirsv.Marlang.CustomAdvancements.Manager.RewardManager;
import com.mirsv.Marlang.Vote.VotePoint;
import com.mirsv.Marlang.Vote.Shop.VoteGUI;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class MainGUI extends MirPlugin implements Listener{

	Permission perm = null;
	public static Economy eco = null;
	RegisteredServiceProvider <Economy> rspe = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
	static MainGUI thisclass = new MainGUI();
	static HashMap<Player, Boolean> isOpeningGUI = new HashMap<Player, Boolean>();

	Inventory gui0 = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "유저 패널");
	Inventory gui1 = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "유저 패널");
	Inventory gui2 = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "유저 패널");
	Inventory gui3 = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "유저 패널");
	Inventory gui4 = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "유저 패널");
	Inventory gui5 = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "유저 패널");
	Inventory gui6 = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "유저 패널");
	Inventory gui7 = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "유저 패널");
	Inventory gui8 = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "유저 패널");
	Inventory gui9 = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "유저 패널");
	
	int randomNumber;
	Random r = new Random();
	
	public MainGUI() {
		setupPermission();
		
		getListener(this);
	}
	
	public boolean setupPermission() {
		RegisteredServiceProvider <Permission> rspp = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);
		if(rspp != null) perm = (Permission) rspp.getProvider();
		return perm != null;
	}
	
	public static void openGUI(Player p) {
		Inventory gui = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "유저 패널");
		
		eco = thisclass.rspe.getProvider();
		
		ItemStack profile = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
		SkullMeta profileMeta = (SkullMeta) profile.getItemMeta();
		profileMeta.setOwner(p.getName());
		profileMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&2&l" + p.getName()));
		ArrayList<String> profileLore = new ArrayList<String>();
		profileLore.add(ChatColor.translateAlternateColorCodes('&', "&7랭크: " +  thisclass.getRank(p)));
		profileMeta.setLore(profileLore);
		profile.setItemMeta(profileMeta);
		
		ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3);
		ItemMeta glassMeta = glass.getItemMeta();
		glassMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&r"));
		glass.setItemMeta(glassMeta);
		
		ItemStack advancements = new ItemStack(Material.COMPASS);
		ItemMeta advancementsMeta = advancements.getItemMeta();
		advancementsMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a모험"));
		ArrayList<String> advancementLore = new ArrayList<String>();
		advancementLore.add(ChatColor.translateAlternateColorCodes('&', "&7모험을 통해 새로운 것들을 발견하고 보상을 얻으세요!"));
		advancementsMeta.setLore(advancementLore);
		advancements.setItemMeta(advancementsMeta);
		
		ItemStack votepointshop = new ItemStack(Material.DIAMOND);
		ItemMeta votepointshopMeta = votepointshop.getItemMeta();
		votepointshopMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a추천상점"));
		ArrayList<String> votepointshopLore = new ArrayList<String>();
		votepointshopLore.add(ChatColor.translateAlternateColorCodes('&', "&7추천포인트를 상품으로 교환하세요!"));
		votepointshopMeta.setLore(votepointshopLore);
		votepointshop.setItemMeta(votepointshopMeta);
		
		gui.setItem(4, profile);
		
		gui.setItem(9, glass);
		gui.setItem(10, glass);
		gui.setItem(11, glass);
		gui.setItem(12, glass);
		gui.setItem(13, glass);
		gui.setItem(14, glass);
		gui.setItem(15, glass);
		gui.setItem(16, glass);
		gui.setItem(17, glass);
		
		gui.setItem(18, advancements);
		gui.setItem(19, votepointshop);
		
		int rn = thisclass.r.nextInt(9);
		thisclass.randomNumber = rn;
		isOpeningGUI.put(p, true);
		
		switch(rn) {
		case 0:
			thisclass.gui0 = gui;
			p.openInventory(thisclass.gui0);
			break;
		case 1:
			thisclass.gui1 = gui;
			p.openInventory(thisclass.gui1);
			break;
		case 2:
			thisclass.gui2 = gui;
			p.openInventory(thisclass.gui2);
			break;
		case 3:
			thisclass.gui3 = gui;
			p.openInventory(thisclass.gui3);
			break;
		case 4:
			thisclass.gui4 = gui;
			p.openInventory(thisclass.gui4);
			break;
		case 5:
			thisclass.gui5 = gui;
			p.openInventory(thisclass.gui5);
			break;
		case 6:
			thisclass.gui6 = gui;
			p.openInventory(thisclass.gui6);
			break;
		case 7:
			thisclass.gui7 = gui;
			p.openInventory(thisclass.gui7);
			break;
		case 8:
			thisclass.gui8 = gui;
			p.openInventory(thisclass.gui8);
			break;
		case 9:
			thisclass.gui9 = gui;
			p.openInventory(thisclass.gui9);
			break;
		}
	}
	
	public String getRank(Player p) {
		String[] group = perm.getPlayerGroups(p);
		String s = null;
		for(String g: group) {
			if(g.equalsIgnoreCase("newbie")) {
				s = ChatColor.translateAlternateColorCodes('&', "&6뉴비");
			}else if(g.equalsIgnoreCase("default")) {
				s = ChatColor.translateAlternateColorCodes('&', "&f기본");
			}else if(g.equalsIgnoreCase("vip")) {
				s = ChatColor.translateAlternateColorCodes('&', "&bVIP");
			}else if(g.equalsIgnoreCase("vipp")) {
				s = ChatColor.translateAlternateColorCodes('&', "&bVIP+");
			}else if(g.equalsIgnoreCase("vvip")) {
				s = ChatColor.translateAlternateColorCodes('&', "&bVVIP");
			}else if(g.equalsIgnoreCase("mvp")) {
				s = ChatColor.translateAlternateColorCodes('&', "&bMVP");
			}else if(g.equalsIgnoreCase("mvpp")) {
				s = ChatColor.translateAlternateColorCodes('&', "&bMVP+");
			}else if(g.equalsIgnoreCase("helper")) {
				s = ChatColor.translateAlternateColorCodes('&', "&5도우미");
			}else if(g.equalsIgnoreCase("admin")){
				s = ChatColor.translateAlternateColorCodes('&', "&c어드민");
			}else{
				s = g.toUpperCase();
			}
		}
		return s;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCloseInventory(InventoryCloseEvent e) {
		Player p = Bukkit.getPlayer(e.getPlayer().getName());
		Inventory inventory = e.getInventory();
		if(inventory.equals(gui0) || inventory.equals(gui1) || inventory.equals(gui2)
				|| inventory.equals(gui3) || inventory.equals(gui4) || inventory.equals(gui5)
				|| inventory.equals(gui6) || inventory.equals(gui7) || inventory.equals(gui8)
				|| inventory.equals(gui9)) {
			isOpeningGUI.put(p, false);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		ClickType click = e.getClick();
		Inventory inventory = e.getClickedInventory();
		ItemStack item = e.getCurrentItem();
		
		if(isOpeningGUI.get(p)) {
			e.setCancelled(true);
		}
		
		try {
			if(inventory.equals(gui0) || inventory.equals(gui1) || inventory.equals(gui2)
			|| inventory.equals(gui3) || inventory.equals(gui4) || inventory.equals(gui5)
			|| inventory.equals(gui6) || inventory.equals(gui7) || inventory.equals(gui8)
			|| inventory.equals(gui9)) {
				
				if(item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + p.getName())) {
					openGUI(p);
				}
				if(item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "모험")) {
					AdventureGUI.openInv(p);
				}
				if(item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "추천상점")) {
					VoteGUI.openMainShop(p);
				}
				e.setCancelled(true);
			}
		} catch (Exception ex) {}
		
	}
	
}
