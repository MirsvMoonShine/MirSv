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
	
	Inventory adventuregui = Bukkit.createInventory(null, 18, ChatColor.DARK_GRAY + "����");
	static AdventureGUI advgui = new AdventureGUI();
	static HashMap<Player, Boolean> isOpeningGUI = new HashMap<Player, Boolean>();
	
	public static void openInv(Player p) {
		ItemStack back = new ItemStack(Material.SPRUCE_DOOR_ITEM);
		ItemMeta backMeta = back.getItemMeta();
		backMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a������"));
		ArrayList<String> backLore = new ArrayList<String>();
		backLore.add(ChatColor.translateAlternateColorCodes('&', "&7���� ������"));
		setMeta(back, backMeta, backLore);
		
		ItemStack customAdvancements = new ItemStack(Material.TOTEM);
		ItemMeta customAdvancementsMeta = customAdvancements.getItemMeta();
		ArrayList<String> customAdvancementsLore = new ArrayList<String>();
		customAdvancementsMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a��������"));
		customAdvancementsLore.add(ChatColor.translateAlternateColorCodes('&', "&7���������� �ر��ؼ� ������ �ް�"));
		customAdvancementsLore.add(ChatColor.translateAlternateColorCodes('&', "&7���� ������ ��������"));
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
				if(item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&a��������"))) {
					CustomAdchievementGUI.openMainGUI(p);
				}
				if(item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&a������"))) {
					MainGUI.openGUI(p);
				}
				
				e.setCancelled(true);
			}
		} catch (Exception ex) {}
	}
	
}