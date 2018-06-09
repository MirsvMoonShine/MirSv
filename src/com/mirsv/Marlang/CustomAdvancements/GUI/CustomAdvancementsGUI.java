package com.mirsv.Marlang.CustomAdvancements.GUI;

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
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.mirsv.MirPlugin;
import com.mirsv.Marlang.Adventure.AdventureGUI;
import com.mirsv.Marlang.CustomAdvancements.AdvancementsList;
import com.mirsv.Marlang.CustomAdvancements.CustomAdvancements;
import com.mirsv.Marlang.CustomAdvancements.Reward.CreatedRewards;
import com.mirsv.Marlang.CustomAdvancements.Reward.CustomAdvancementRewardManager;
import com.mirsv.Marlang.CustomAdvancements.Reward.NoRewardException;

public class CustomAdvancementsGUI extends MirPlugin implements Listener{
	
	static CustomAdvancementsGUI cagui = new CustomAdvancementsGUI();
	static HashMap<Player, Boolean> isOpeningGUI = new HashMap<Player, Boolean>();
	
	public CustomAdvancementsGUI() {
		getListener(this);
	}
	
	Inventory mainGUI = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "도전과제");
	
	public static void openMainGUI(Player p) {
		int i = 0;
		for(AdvancementsList s : AdvancementsList.values()) {
			if(CustomAdvancements.getCleared(p, s)) {
				ItemStack advancement = new ItemStack(Material.DIAMOND_BLOCK);
				ItemMeta advancementMeta = advancement.getItemMeta();
				ArrayList<String> advancementLore = new ArrayList<String>();
				advancementMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f" + s.getAdvname()));
				advancementLore.add("");
				if(!CustomAdvancements.getRewarded(p, s)) {
					advancementLore.add(ChatColor.translateAlternateColorCodes('&', "&a▶ &f보상을 수령하려면 클릭하세요"));
				} else {
					advancementLore.add(ChatColor.translateAlternateColorCodes('&', "&c보상을 받았습니다!"));
				}
				setMeta(advancement, advancementMeta, advancementLore);
				cagui.mainGUI.setItem(i, advancement);
			} else {
				ItemStack advancement = new ItemStack(Material.COAL_BLOCK);
				ItemMeta advancementMeta = advancement.getItemMeta();
				ArrayList<String> advancementLore = new ArrayList<String>();
				advancementMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f" + s.getAdvname()));
				advancementLore.add("");
				advancementLore.add(ChatColor.translateAlternateColorCodes('&', "&c아직 클리어하지 않았습니다!"));
				setMeta(advancement, advancementMeta, advancementLore);
				cagui.mainGUI.setItem(i, advancement);
			}
			
			i++;
		}
		
		ItemStack back = new ItemStack(Material.SPRUCE_DOOR_ITEM);
		ItemMeta backMeta = back.getItemMeta();
		backMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a나가기"));
		ArrayList<String> backLore = new ArrayList<String>();
		backLore.add(ChatColor.translateAlternateColorCodes('&', "&7도전과제 나가기"));
		setMeta(back, backMeta, backLore);
		cagui.mainGUI.setItem(48, back);
		
		isOpeningGUI.put(p, true);
		p.openInventory(cagui.mainGUI);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCloseInventory(InventoryCloseEvent e) {
		Player p = Bukkit.getPlayer(e.getPlayer().getName());
		Inventory inventory = e.getInventory();
		
		if(inventory.equals(mainGUI)) {
			isOpeningGUI.put(p, false);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		ClickType click = e.getClick();
		Inventory inventory = e.getClickedInventory();
		ItemStack item = e.getCurrentItem();
		InventoryAction action = e.getAction();
		
		if(isOpeningGUI.get(p)) {
			e.setCancelled(true);
		}
		
		try {
			if(inventory.equals(mainGUI)) {
				
				if(item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&a나가기"))) {
					AdventureGUI.openInv(p);
				}
				
				for(AdvancementsList s : AdvancementsList.values()) {
					if(item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&f" + s.getAdvname()))) {
						if(!CustomAdvancements.getRewarded(p, s) && !CustomAdvancements.getCleared(p, s)) break;
						try {
							if(!createdRewardsContain(s)) throw new NoRewardException();
							CustomAdvancementRewardManager.rewardPlayer(p, s);
							CustomAdvancements.setRewarded(p, s, true);
							openMainGUI(p);
						} catch (NoRewardException ex) {
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cERROR CODE 001"));
						}
						
					}
				}
				
				e.setCancelled(true);
			}
		} catch (Exception ex) {e.setCancelled(true);}
	}
	
	public static void setMeta(ItemStack is, ItemMeta im, ArrayList<String> il) {
		im.setLore(il);
		is.setItemMeta(im);
	}
	
	public static boolean createdRewardsContain(AdvancementsList l) {
		for(CreatedRewards cr : CreatedRewards.values()) {
			if(cr.getAdvancement().equals(l)) return true;
		}
		return false;
	}
	
}
