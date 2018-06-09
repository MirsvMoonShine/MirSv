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

import com.mirsv.MirPlugin;
import com.mirsv.Marlang.UserMenu.MainGUI;
import com.mirsv.Marlang.Vote.VotePoint;

public class ScrollShop extends MirPlugin implements Listener{
	
	public ScrollShop() {
		getListener(this);
	}
	
	static HashMap<Player, Boolean> isOpeningGUI = new HashMap<Player, Boolean>();
	
	Inventory scgui = Bukkit.createInventory(null, 36, ChatColor.DARK_GRAY + "스크롤 상점");
	
	static ScrollShop ss = new ScrollShop();
	
	public static void openScrollShop(Player p) {
		
		ItemStack back = new ItemStack(Material.SPRUCE_DOOR_ITEM);
		ItemMeta backMeta = back.getItemMeta();
		backMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a나가기"));
		ArrayList<String> backLore = new ArrayList<String>();
		backLore.add(ChatColor.translateAlternateColorCodes('&', "&7스크롤 상점 나가기"));
		setMeta(back, backMeta, backLore);
		
		ItemStack info = new ItemStack(Material.DIAMOND);
		ItemMeta infoMeta = info.getItemMeta();
		infoMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a추천포인트"));
		ArrayList<String> infoLore = new ArrayList<String>();
		infoLore.add(ChatColor.translateAlternateColorCodes('&', "&7점수 : &b" + VotePoint.getVotePoint(p) + "점"));
		infoMeta.setLore(infoLore);
		info.setItemMeta(infoMeta);
		
		ss.scgui.setItem(30, back);
		ss.scgui.setItem(31, info);

		isOpeningGUI.put(p, true);
		p.openInventory(ss.scgui);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCloseInventory(InventoryCloseEvent e) {
		Player p = Bukkit.getPlayer(e.getPlayer().getName());
		Inventory inventory = e.getInventory();
		
		if(inventory.equals(scgui)) {
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
			if(inventory.equals(scgui)) {
				if(item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "나가기")) {
					VoteGUI.openMainShop(p);
				}
				if(item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "새로 고침")) {
					openScrollShop(p);
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
