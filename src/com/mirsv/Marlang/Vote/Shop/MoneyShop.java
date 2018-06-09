package com.mirsv.Marlang.Vote.Shop;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
import org.bukkit.plugin.RegisteredServiceProvider;

import com.mirsv.MirPlugin;
import com.mirsv.Marlang.Vote.VotePoint;

import net.milkbowl.vault.economy.Economy;

public class MoneyShop extends MirPlugin implements Listener{
	
	public MoneyShop() {
		getListener(this);
	}
	
	public static Economy eco = null;
	RegisteredServiceProvider <Economy> rspe = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);

	Inventory mogui = Bukkit.createInventory(null, 18, ChatColor.DARK_GRAY + "�� ����");
	
	static MoneyShop ms = new MoneyShop();
	static HashMap<Player, Boolean> isOpeningGUI = new HashMap<Player, Boolean>();
	
	public static void openMoneyShop(Player p) {
		eco = ms.rspe.getProvider();
		
		ItemStack back = new ItemStack(Material.SPRUCE_DOOR_ITEM);
		ItemMeta backMeta = back.getItemMeta();
		backMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a������"));
		ArrayList<String> backLore = new ArrayList<String>();
		backLore.add(ChatColor.translateAlternateColorCodes('&', "&7�� ���� ������"));
		setMeta(back, backMeta, backLore);
		
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
		
		ms.mogui.setItem(0, money1);
		ms.mogui.setItem(1, money2);
		ms.mogui.setItem(2, money3);
		ms.mogui.setItem(12, back);
		ms.mogui.setItem(13, info);
		
		isOpeningGUI.put(p, true);
		p.openInventory(ms.mogui);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCloseInventory(InventoryCloseEvent e) {
		Player p = Bukkit.getPlayer(e.getPlayer().getName());
		Inventory inventory = e.getInventory();
		
		if(inventory.equals(mogui)) {
			isOpeningGUI.put(p, false);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClick(InventoryClickEvent e) {
		eco = ms.rspe.getProvider();
		Player p = (Player) e.getWhoClicked();
		ClickType click = e.getClick();
		Inventory inventory = e.getClickedInventory();
		ItemStack item = e.getCurrentItem();
		
		if(isOpeningGUI.get(p)) {
			e.setCancelled(true);
		}
		
		try {
			if(inventory.equals(mogui)) {
				if(item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "������")) {
					VoteGUI.openMainShop(p);
				}
				if(item.getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "300��")) {
					if(!VotePoint.hasPoint(p, 1)) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c��õ����Ʈ�� �����մϴ�!"));
						e.setCancelled(true);
						return;
					}
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&b��õ����Ʈ 1��&f�� &e300��&f���� ��ȯ�߽��ϴ�!"));
					VotePoint.minusVotePoint(p, 1);
					eco.depositPlayer(p, 300);
				}
				if(item.getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "1000��")) {
					if(!VotePoint.hasPoint(p, 3)) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c��õ����Ʈ�� �����մϴ�!"));
						e.setCancelled(true);
						return;
					}
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&b��õ����Ʈ 3��&f�� &e1000��&f���� ��ȯ�߽��ϴ�!"));
					VotePoint.minusVotePoint(p, 3);
					eco.depositPlayer(p, 1000);
				}
				if(item.getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "3300��")) {
					if(!VotePoint.hasPoint(p, 10)) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c��õ����Ʈ�� �����մϴ�!"));
						e.setCancelled(true);
						return;
					}
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&b��õ����Ʈ 10��&f�� &e3300��&f���� ��ȯ�߽��ϴ�!"));
					VotePoint.minusVotePoint(p, 10);
					eco.depositPlayer(p, 3300);
				}
				e.setCancelled(true);
			}
		} catch (Exception ex) {}
		
	}
	
	public static void setMeta(ItemStack is, ItemMeta im, ArrayList<String> il) {
		im.setLore(il);
		is.setItemMeta(im);
	}
	
}
