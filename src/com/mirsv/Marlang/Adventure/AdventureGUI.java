package com.mirsv.Marlang.Adventure;

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
import com.mirsv.Marlang.CustomAdvancements.GUI.CustomAdchievementGUI;
import com.mirsv.Marlang.UserMenu.MainGUI;

public class AdventureGUI extends MirPlugin implements Listener{
	
	public AdventureGUI() {
		getListener(this);
	}
	
	Inventory adventuregui = Bukkit.createInventory(null, 18, ChatColor.DARK_GRAY + "모험");
	static AdventureGUI advgui = new AdventureGUI();
	static HashMap<Player, Boolean> isOpeningGUI = new HashMap<Player, Boolean>();
	
	public static void openInv(Player p) {
		ItemStack back = new ItemStack(Material.SPRUCE_DOOR_ITEM);
		ItemMeta backMeta = back.getItemMeta();
		backMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a나가기"));
		ArrayList<String> backLore = new ArrayList<String>();
		backLore.add(ChatColor.translateAlternateColorCodes('&', "&7모험 나가기"));
		setMeta(back, backMeta, backLore);
		
		ItemStack customAdvancements = new ItemStack(Material.TOTEM);
		ItemMeta customAdvancementsMeta = customAdvancements.getItemMeta();
		ArrayList<String> customAdvancementsLore = new ArrayList<String>();
		customAdvancementsMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a도전과제"));
		customAdvancementsLore.add(ChatColor.translateAlternateColorCodes('&', "&7도전과제를 해금해서 보상을 받고"));
		customAdvancementsLore.add(ChatColor.translateAlternateColorCodes('&', "&7점점 발전해 나가세요"));
		setMeta(customAdvancements, customAdvancementsMeta, customAdvancementsLore);
		
		advgui.adventuregui.setItem(0, customAdvancements);
		advgui.adventuregui.setItem(13, back);
		
		isOpeningGUI.put(p, true);
		p.openInventory(advgui.adventuregui);
	}
	
	public static void setMeta(ItemStack is, ItemMeta im, ArrayList<String> il) {
		im.setLore(il);
		is.setItemMeta(im);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCloseInventory(InventoryCloseEvent e) {
		Player p = Bukkit.getPlayer(e.getPlayer().getName());
		Inventory inventory = e.getInventory();
		
		if(inventory.equals(adventuregui)) {
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
			if(inventory.equals(adventuregui)) {
				if(item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&a도전과제"))) {
					CustomAdchievementGUI.openMainGUI(p);
				}
				if(item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&a나가기"))) {
					MainGUI.openGUI(p);
				}
				
				e.setCancelled(true);
			}
		} catch (Exception ex) {}
	}
	
}