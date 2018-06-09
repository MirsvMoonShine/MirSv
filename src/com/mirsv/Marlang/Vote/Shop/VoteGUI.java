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
import com.mirsv.Marlang.UserMenu.MainGUI;
import com.mirsv.Marlang.Vote.VotePoint;

import net.milkbowl.vault.economy.Economy;

public class VoteGUI extends MirPlugin implements Listener{
	
	static VoteGUI vg = new VoteGUI();
	static MainGUI mg = new MainGUI();
	static HashMap<Player, Boolean> isOpeningGUI = new HashMap<Player, Boolean>();
	
	public VoteGUI() {
		getListener(this);
	}
	
	Inventory gui = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "��õ����");
	
	public static void openMainShop(Player p) {
		
		ItemStack info = new ItemStack(Material.DIAMOND);
		ItemMeta infoMeta = info.getItemMeta();
		infoMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a��õ����Ʈ"));
		ArrayList<String> infoLore = new ArrayList<String>();
		infoLore.add(ChatColor.translateAlternateColorCodes('&', "&7���� : &b" + VotePoint.getVotePoint(p) + "��"));
		infoMeta.setLore(infoLore);
		info.setItemMeta(infoMeta);
		
		ItemStack back = new ItemStack(Material.SPRUCE_DOOR_ITEM);
		ItemMeta backMeta = back.getItemMeta();
		backMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a������"));
		ArrayList<String> backLore = new ArrayList<String>();
		backLore.add(ChatColor.translateAlternateColorCodes('&', "&7��õ���� ������"));
		setMeta(back, backMeta, backLore);
		
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
		
		vg.gui.setItem(0, moneyShop);
		vg.gui.setItem(1, scrollShop);
		vg.gui.setItem(48, back);
		vg.gui.setItem(49, info);
		
		isOpeningGUI.put(p, true);
		p.openInventory(vg.gui);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCloseInventory(InventoryCloseEvent e) {
		Player p = Bukkit.getPlayer(e.getPlayer().getName());
		Inventory inventory = e.getInventory();
		
		if(inventory.equals(gui)) {
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
			if(inventory.equals(gui)) {

				if(item == null || !item.hasItemMeta()) return;
				if(item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "������")) {
					MainGUI.openGUI(p);
				}
				if(item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "���� ��ħ")) {
					openMainShop(p);
				}
				if(item.getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "��")) {
					MoneyShop.openMoneyShop(p);
				}
				if(item.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "��ũ��")) {
					ScrollShop.openScrollShop(p);
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
