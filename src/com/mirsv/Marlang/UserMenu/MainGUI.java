package com.mirsv.Marlang.UserMenu;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
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
import com.mirsv.Marlang.CustomAdvancements.AdvancementsList;
import com.mirsv.Marlang.CustomAdvancements.CustomAdvancements;
import com.mirsv.Marlang.CustomAdvancements.CustomAdvancementsGUI;
import com.mirsv.Marlang.Vote.VoteGUI;
import com.mirsv.Marlang.Vote.VotePoint;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class MainGUI extends MirPlugin implements Listener{

	Permission perm = null;
	public static Economy eco = null;
	RegisteredServiceProvider <Economy> rspe = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
	static MainGUI thisclass = new MainGUI();

	Inventory gui0 = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "���� �г�");
	Inventory gui1 = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "���� �г�");
	Inventory gui2 = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "���� �г�");
	Inventory gui3 = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "���� �г�");
	Inventory gui4 = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "���� �г�");
	Inventory gui5 = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "���� �г�");
	Inventory gui6 = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "���� �г�");
	Inventory gui7 = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "���� �г�");
	Inventory gui8 = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "���� �г�");
	Inventory gui9 = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "���� �г�");
	
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
	
	public void openGUI(Player p) {
		Inventory gui = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "���� �г�");
		
		eco = thisclass.rspe.getProvider();
		
		ItemStack profile = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
		SkullMeta profileMeta = (SkullMeta) profile.getItemMeta();
		profileMeta.setOwner(p.getName());
		profileMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&2&l" + p.getName()));
		ArrayList<String> profileLore = new ArrayList<String>();
		profileLore.add(ChatColor.translateAlternateColorCodes('&', "&7��ũ: " +  thisclass.getRank(p)));
		profileMeta.setLore(profileLore);
		profile.setItemMeta(profileMeta);
		
		ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3);
		ItemMeta glassMeta = glass.getItemMeta();
		glassMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&r"));
		glass.setItemMeta(glassMeta);
		
		ItemStack advancements = new ItemStack(Material.COMPASS);
		ItemMeta advancementsMeta = advancements.getItemMeta();
		advancementsMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a����"));
		ArrayList<String> advancementLore = new ArrayList<String>();
		advancementLore.add(ChatColor.translateAlternateColorCodes('&', "&7������ ���� ������ ȹ���ϼ���!"));
		advancementsMeta.setLore(advancementLore);
		advancements.setItemMeta(advancementsMeta);
		
		ItemStack votepointshop = new ItemStack(Material.DIAMOND);
		ItemMeta votepointshopMeta = votepointshop.getItemMeta();
		votepointshopMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a��õ����"));
		ArrayList<String> votepointshopLore = new ArrayList<String>();
		votepointshopLore.add(ChatColor.translateAlternateColorCodes('&', "&7��õ����Ʈ�� ��ǰ���� ��ȯ�ϼ���!"));
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
		
		int rn = r.nextInt(9);
		this.randomNumber = rn;
		
		switch(rn) {
		case 0:
			this.gui0 = gui;
			p.openInventory(gui0);
			break;
		case 1:
			this.gui1 = gui;
			p.openInventory(gui1);
			break;
		case 2:
			this.gui2 = gui;
			p.openInventory(gui2);
			break;
		case 3:
			this.gui3 = gui;
			p.openInventory(gui3);
			break;
		case 4:
			this.gui4 = gui;
			p.openInventory(gui4);
			break;
		case 5:
			this.gui5 = gui;
			p.openInventory(gui5);
			break;
		case 6:
			this.gui6 = gui;
			p.openInventory(gui6);
			break;
		case 7:
			this.gui7 = gui;
			p.openInventory(gui7);
			break;
		case 8:
			this.gui8 = gui;
			p.openInventory(gui8);
			break;
		case 9:
			this.gui9 = gui;
			p.openInventory(gui9);
			break;
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		try {
			Player p = (Player) e.getPlayer();
			ItemStack[] stContents = p.getInventory().getStorageContents();
			ItemStack[] hotContents = p.getInventory().getContents();
			
			ItemStack profile = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
			SkullMeta profileMeta = (SkullMeta) profile.getItemMeta();
			profileMeta.setOwner(p.getName());
			profileMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a" + p.getName()));
			ArrayList<String> profileLore = new ArrayList<String>();
			profileLore.add(ChatColor.translateAlternateColorCodes('&', "&7��ũ: " +  thisclass.getRank(p)));
			profileLore.add(ChatColor.translateAlternateColorCodes('&', "&7�ܰ�: &e$" +  eco.getBalance(p)));
			profileLore.add(ChatColor.translateAlternateColorCodes('&', "&7��õ����Ʈ: &9" + VotePoint.getVotePoint(p)));
			profileMeta.setLore(profileLore);
			profile.setItemMeta(profileMeta);
			
			ItemStack blank = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
			ItemMeta blankMeta = blank.getItemMeta();
			blankMeta.setDisplayName(" ");
			blank.setItemMeta(blankMeta);
			
			ItemStack advancements = new ItemStack(Material.COMPASS);
			ItemMeta advancementsMeta = advancements.getItemMeta();
			advancementsMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a��������"));
			ArrayList<String> advancementLore = new ArrayList<String>();
			advancementLore.add(ChatColor.translateAlternateColorCodes('&', "&7���������� �޼��ؼ� Ư���� ������ ��������!"));
			advancementsMeta.setLore(advancementLore);
			advancements.setItemMeta(advancementsMeta);
			
			ItemStack votepointshop = new ItemStack(Material.DIAMOND);
			ItemMeta votepointshopMeta = votepointshop.getItemMeta();
			votepointshopMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a��õ����"));
			ArrayList<String> votepointshopLore = new ArrayList<String>();
			votepointshopLore.add(ChatColor.translateAlternateColorCodes('&', "&7��õ����Ʈ�� ��ǰ���� ��ȯ�ϼ���!"));
			votepointshopMeta.setLore(votepointshopLore);
			votepointshop.setItemMeta(votepointshopMeta);
			
			ItemStack back = new ItemStack(Material.ARROW);
			ItemMeta backMeta = back.getItemMeta();
			backMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a�ڷ� ����"));
			ArrayList<String> backLore = new ArrayList<String>();
			backLore.add(ChatColor.translateAlternateColorCodes('&', "&7��õ�������� ���ư���"));
			backMeta.setLore(backLore);
			back.setItemMeta(backMeta);
			
			ItemStack back2 = new ItemStack(Material.ARROW);
			ItemMeta backMeta2 = back2.getItemMeta();
			backMeta2.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a�ڷ� ����"));
			ArrayList<String> backLore2 = new ArrayList<String>();
			backLore2.add(ChatColor.translateAlternateColorCodes('&', "&7���� �гη� ���ư���"));
			backMeta2.setLore(backLore2);
			back2.setItemMeta(backMeta2);
			
			ItemStack reload = new ItemStack(Material.FEATHER);
			ItemMeta reloadMeta = reload.getItemMeta();
			reloadMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a���� ��ħ"));
			ArrayList<String> reloadLore = new ArrayList<String>();
			reloadLore.add(ChatColor.translateAlternateColorCodes('&', "&7GUI ���� ��ġ��"));
			reloadMeta.setLore(reloadLore);
			reload.setItemMeta(reloadMeta);
			
			ItemStack info = new ItemStack(Material.DIAMOND);
			ItemMeta infoMeta = info.getItemMeta();
			infoMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a��õ����Ʈ"));
			ArrayList<String> infoLore = new ArrayList<String>();
			infoLore.add(ChatColor.translateAlternateColorCodes('&', "&7���� : &b" + VotePoint.getVotePoint(p) + "��"));
			infoMeta.setLore(infoLore);
			info.setItemMeta(infoMeta);
			
			ItemStack money1 = new ItemStack(Material.CONCRETE, 1, (short) 14);
			ItemMeta money1Meta = money1.getItemMeta();
			money1Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e300��"));
			ArrayList<String> money1Lore = new ArrayList<String>();
			money1Lore.add(ChatColor.translateAlternateColorCodes('&', "&7���� : &b��õ����Ʈ 1��"));
			money1Lore.add(" ");
			money1Lore.add(ChatColor.translateAlternateColorCodes('&', "&a�� &7�����Ϸ��� Ŭ���ϼ���!"));
			money1Meta.setLore(money1Lore);
			money1.setItemMeta(money1Meta);
			
			ItemStack money2 = new ItemStack(Material.CONCRETE, 1, (short) 1);
			ItemMeta money2Meta = money2.getItemMeta();
			money2Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e1000��"));
			ArrayList<String> money2Lore = new ArrayList<String>();
			money2Lore.add(ChatColor.translateAlternateColorCodes('&', "&7���� : &b��õ����Ʈ 3��"));
			money2Lore.add(" ");
			money2Lore.add(ChatColor.translateAlternateColorCodes('&', "&a�� &7�����Ϸ��� Ŭ���ϼ���!"));
			money2Meta.setLore(money2Lore);
			money2.setItemMeta(money2Meta);
			
			ItemStack money3 = new ItemStack(Material.CONCRETE, 1, (short) 4);
			ItemMeta money3Meta = money3.getItemMeta();
			money3Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e3300��"));
			ArrayList<String> money3Lore = new ArrayList<String>();
			money3Lore.add(ChatColor.translateAlternateColorCodes('&', "&7���� : &b��õ����Ʈ 10��"));
			money3Lore.add(" ");
			money3Lore.add(ChatColor.translateAlternateColorCodes('&', "&a�� &7�����Ϸ��� Ŭ���ϼ���!"));
			money3Meta.setLore(money3Lore);
			money3.setItemMeta(money3Meta);
			
			ItemStack moneyShop = new ItemStack(Material.EMERALD);
			ItemMeta moneyShopMeta = moneyShop.getItemMeta();
			moneyShopMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e��"));
			ArrayList<String> moneyLore = new ArrayList<String>();
			moneyLore.add(ChatColor.translateAlternateColorCodes('&', "&7��õ����Ʈ�� ������ ��ȯ�ϼ���"));
			moneyLore.add(" ");
			moneyLore.add(ChatColor.translateAlternateColorCodes('&', "&a�� &7���� GUI�� ������ Ŭ���ϼ���"));
			moneyShopMeta.setLore(moneyLore);
			moneyShop.setItemMeta(moneyShopMeta);
			
			ItemStack scrollShop = new ItemStack(Material.ENCHANTED_BOOK);
			ItemMeta scrollShopMeta = scrollShop.getItemMeta();
			scrollShopMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&5��ũ��"));
			ArrayList<String> scrollLore = new ArrayList<String>();
			scrollLore.add(ChatColor.translateAlternateColorCodes('&', "&7��õ����Ʈ�� ��ũ�ѷ� ��ȯ�ϼ���"));
			scrollLore.add(" ");
			scrollLore.add(ChatColor.translateAlternateColorCodes('&', "&a�� &7���� GUI�� ������ Ŭ���ϼ���"));
			scrollShopMeta.setLore(scrollLore);
			scrollShop.setItemMeta(scrollShopMeta);
			
			for(ItemStack is : hotContents) {
				if(is.getItemMeta().getDisplayName().equals(profile.getItemMeta().getDisplayName())) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c����� �κ��丮���� GUI���� ����� �������� �����Ǿ� �����߽��ϴ�!"));
					p.getInventory().remove(profile);
				}
				if(is.getItemMeta().getDisplayName().equals(blank.getItemMeta().getDisplayName())) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c����� �κ��丮���� GUI���� ����� �������� �����Ǿ� �����߽��ϴ�!"));
					p.getInventory().remove(blank);
				}
				if(is.getItemMeta().getDisplayName().equals(advancements.getItemMeta().getDisplayName())) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c����� �κ��丮���� GUI���� ����� �������� �����Ǿ� �����߽��ϴ�!"));
					p.getInventory().remove(advancements);
				}
				if(is.getItemMeta().getDisplayName().equals(votepointshop.getItemMeta().getDisplayName())) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c����� �κ��丮���� GUI���� ����� �������� �����Ǿ� �����߽��ϴ�!"));
					p.getInventory().remove(votepointshop);
				}
				if(is.getItemMeta().getDisplayName().equals(back.getItemMeta().getDisplayName())) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c����� �κ��丮���� GUI���� ����� �������� �����Ǿ� �����߽��ϴ�!"));
					p.getInventory().remove(back);
				}
				if(is.getItemMeta().getDisplayName().equals(back2.getItemMeta().getDisplayName())) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c����� �κ��丮���� GUI���� ����� �������� �����Ǿ� �����߽��ϴ�!"));
					p.getInventory().remove(back2);
				}
				if(is.getItemMeta().getDisplayName().equals(reload.getItemMeta().getDisplayName())) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c����� �κ��丮���� GUI���� ����� �������� �����Ǿ� �����߽��ϴ�!"));
					p.getInventory().remove(reload);
				}
				if(is.getItemMeta().getDisplayName().equals(info.getItemMeta().getDisplayName())) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c����� �κ��丮���� GUI���� ����� �������� �����Ǿ� �����߽��ϴ�!"));
					p.getInventory().remove(info);
				}
				if(is.getItemMeta().getDisplayName().equals(money1.getItemMeta().getDisplayName())) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c����� �κ��丮���� GUI���� ����� �������� �����Ǿ� �����߽��ϴ�!"));
					p.getInventory().remove(money1);
				}
				if(is.getItemMeta().getDisplayName().equals(money2.getItemMeta().getDisplayName())) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c����� �κ��丮���� GUI���� ����� �������� �����Ǿ� �����߽��ϴ�!"));
					p.getInventory().remove(money2);
				}
				if(is.getItemMeta().getDisplayName().equals(money3.getItemMeta().getDisplayName())) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c����� �κ��丮���� GUI���� ����� �������� �����Ǿ� �����߽��ϴ�!"));
					p.getInventory().remove(money3);
				}
				if(is.getItemMeta().getDisplayName().equals(moneyShop.getItemMeta().getDisplayName())) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c����� �κ��丮���� GUI���� ����� �������� �����Ǿ� �����߽��ϴ�!"));
					p.getInventory().remove(moneyShop);
				}
				if(is.getItemMeta().getDisplayName().equals(scrollShop.getItemMeta().getDisplayName())) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c����� �κ��丮���� GUI���� ����� �������� �����Ǿ� �����߽��ϴ�!"));
					p.getInventory().remove(scrollShop);
				}
			}
			
			for(ItemStack is : stContents) {
				if(is.getItemMeta().getDisplayName().equals(profile.getItemMeta().getDisplayName())) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c����� �κ��丮���� GUI���� ����� �������� �����Ǿ� �����߽��ϴ�!"));
					p.getInventory().remove(profile);
				}
				if(is.getItemMeta().getDisplayName().equals(blank.getItemMeta().getDisplayName())) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c����� �κ��丮���� GUI���� ����� �������� �����Ǿ� �����߽��ϴ�!"));
					p.getInventory().remove(blank);
				}
				if(is.getItemMeta().getDisplayName().equals(advancements.getItemMeta().getDisplayName())) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c����� �κ��丮���� GUI���� ����� �������� �����Ǿ� �����߽��ϴ�!"));
					p.getInventory().remove(advancements);
				}
				if(is.getItemMeta().getDisplayName().equals(votepointshop.getItemMeta().getDisplayName())) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c����� �κ��丮���� GUI���� ����� �������� �����Ǿ� �����߽��ϴ�!"));
					p.getInventory().remove(votepointshop);
				}
				if(is.getItemMeta().getDisplayName().equals(back.getItemMeta().getDisplayName())) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c����� �κ��丮���� GUI���� ����� �������� �����Ǿ� �����߽��ϴ�!"));
					p.getInventory().remove(back);
				}
				if(is.getItemMeta().getDisplayName().equals(back2.getItemMeta().getDisplayName())) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c����� �κ��丮���� GUI���� ����� �������� �����Ǿ� �����߽��ϴ�!"));
					p.getInventory().remove(back2);
				}
				if(is.getItemMeta().getDisplayName().equals(reload.getItemMeta().getDisplayName())) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c����� �κ��丮���� GUI���� ����� �������� �����Ǿ� �����߽��ϴ�!"));
					p.getInventory().remove(reload);
				}
				if(is.getItemMeta().getDisplayName().equals(info.getItemMeta().getDisplayName())) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c����� �κ��丮���� GUI���� ����� �������� �����Ǿ� �����߽��ϴ�!"));
					p.getInventory().remove(info);
				}
				if(is.getItemMeta().getDisplayName().equals(money1.getItemMeta().getDisplayName())) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c����� �κ��丮���� GUI���� ����� �������� �����Ǿ� �����߽��ϴ�!"));
					p.getInventory().remove(money1);
				}
				if(is.getItemMeta().getDisplayName().equals(money2.getItemMeta().getDisplayName())) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c����� �κ��丮���� GUI���� ����� �������� �����Ǿ� �����߽��ϴ�!"));
					p.getInventory().remove(money2);
				}
				if(is.getItemMeta().getDisplayName().equals(money3.getItemMeta().getDisplayName())) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c����� �κ��丮���� GUI���� ����� �������� �����Ǿ� �����߽��ϴ�!"));
					p.getInventory().remove(money3);
				}
				if(is.getItemMeta().getDisplayName().equals(moneyShop.getItemMeta().getDisplayName())) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c����� �κ��丮���� GUI���� ����� �������� �����Ǿ� �����߽��ϴ�!"));
					p.getInventory().remove(moneyShop);
				}
				if(is.getItemMeta().getDisplayName().equals(scrollShop.getItemMeta().getDisplayName())) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c����� �κ��丮���� GUI���� ����� �������� �����Ǿ� �����߽��ϴ�!"));
					p.getInventory().remove(scrollShop);
				}
			}
		} catch (Exception ex) {}
	}
	
	public String getRank(Player p) {
		String[] group = perm.getPlayerGroups(p);
		String s = null;
		for(String g: group) {
			if(g.equalsIgnoreCase("newbie")) {
				s = ChatColor.translateAlternateColorCodes('&', "&6����");
			}else if(g.equalsIgnoreCase("default")) {
				s = ChatColor.translateAlternateColorCodes('&', "&f�⺻");
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
				s = ChatColor.translateAlternateColorCodes('&', "&5�����");
			}else if(g.equalsIgnoreCase("admin")){
				s = ChatColor.translateAlternateColorCodes('&', "&c����");
			}else{
				s = g.toUpperCase();
			}
		}
		return s;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		ClickType click = e.getClick();
		Inventory inventory = e.getClickedInventory();
		ItemStack item = e.getCurrentItem();
		try {
			if(inventory.equals(gui0) || inventory.equals(gui1) || inventory.equals(gui2)
			|| inventory.equals(gui3) || inventory.equals(gui4) || inventory.equals(gui5)
			|| inventory.equals(gui6) || inventory.equals(gui7) || inventory.equals(gui8)
			|| inventory.equals(gui9)) {

				if(item == null || !item.hasItemMeta()) return;
				
				if(item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + p.getName())) {
					openGUI(p);
				}
				if(item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "��������")) {
					CustomAdvancementsGUI.openMainGUI(p);
				}
				if(item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "��õ����")) {
					VoteGUI.openMainShop(p);
				}
				e.setCancelled(true);
			}
		} catch (Exception ex) {}
		
	}
	
}
