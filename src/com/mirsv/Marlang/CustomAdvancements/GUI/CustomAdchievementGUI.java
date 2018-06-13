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
import com.mirsv.Marlang.CustomAdvancements.CustomAchievement;
import com.mirsv.Marlang.CustomAdvancements.List.AchievementDifficulty;
import com.mirsv.Marlang.CustomAdvancements.List.AchievementList;
import com.mirsv.Marlang.CustomAdvancements.Manager.RewardManager;

public class CustomAdchievementGUI extends MirPlugin implements Listener{
	
	static CustomAdchievementGUI cagui = new CustomAdchievementGUI();
	static HashMap<Player, Boolean> isOpeningGUI = new HashMap<Player, Boolean>();
	
	public CustomAdchievementGUI() {
		getListener(this);
	}

	Inventory mainGUI = Bukkit.createInventory(null, 18, ChatColor.DARK_GRAY + "도전과제");
	Inventory advanceGUI = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "도전과제");
	Inventory challengeGUI = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "도전과제");
	
	public static void openMainGUI(Player p) {
		
	}
	
	public static void openAdvanceGUI(Player p) {
		int i = 1;
		for(AchievementList l : AchievementList.values()) {
			if(CustomAchievement.getCleared(p, l)) { //도전과제를 클리어했을 경우
				ItemStack advance = new ItemStack(Material.DIAMOND_BLOCK);
				ItemMeta advanceMeta = advance.getItemMeta();
				ArrayList<String> advanceLore = new ArrayList<String>();
				advanceMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b" + l.getTitle()));
				advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&7" + l.getDifficulty().getLevel()));
				advanceLore.add("");
				advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&7보상:"));
				if (l.getDifficulty().equals(AchievementDifficulty.매우쉬움)) {
					advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&7+&310&f 경험치"));
				} else if (l.getDifficulty().equals(AchievementDifficulty.쉬움)) {
					advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&7+&350&f 경험치"));
				} else if (l.getDifficulty().equals(AchievementDifficulty.보통)) {
					advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&7+&3100&f 경험치"));
				} else if (l.getDifficulty().equals(AchievementDifficulty.어려움)) {
					advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&7+&3300&f 경험치"));
				} else if (l.getDifficulty().equals(AchievementDifficulty.매우어려움)) {
					advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&7+&3500&f 경험치"));
				}
				advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&7+&3..."));
				advanceLore.add("");
				if(!CustomAchievement.getRewarded(p, l)) { //보상을 수령했을 경우
					advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&a▶ &b클릭해서 보상을 수령하세요!"));
				} else { //보상을 수령하지 않았을 경우
					advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&a이미 이 보상을 수령했습니다!"));
				}
				setMeta(advance, advanceMeta, advanceLore);
				cagui.mainGUI.setItem(Database.getChestNum(i), advance);
			} else { //도전과제를 클리어하지 않았을 경우
				ItemStack advance = new ItemStack(Material.COAL_BLOCK);
				ItemMeta advanceMeta = advance.getItemMeta();
				ArrayList<String> advanceLore = new ArrayList<String>();
				advanceMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b" + l.getTitle()));
				advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&7" + l.getDifficulty().getLevel()));
				advanceLore.add("");
				advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&7보상:"));
				if (l.getDifficulty().equals(AchievementDifficulty.매우쉬움)) {
					advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&7+&310&f 경험치"));
				} else if (l.getDifficulty().equals(AchievementDifficulty.쉬움)) {
					advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&7+&350&f 경험치"));
				} else if (l.getDifficulty().equals(AchievementDifficulty.보통)) {
					advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&7+&3100&f 경험치"));
				} else if (l.getDifficulty().equals(AchievementDifficulty.어려움)) {
					advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&7+&3300&f 경험치"));
				} else if (l.getDifficulty().equals(AchievementDifficulty.매우어려움)) {
					advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&7+&3500&f 경험치"));
				}
				advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&7+&3..."));
				advanceLore.add("");
				advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&c아직 클리어하지 않았습니다!"));
				setMeta(advance, advanceMeta, advanceLore);
				cagui.mainGUI.setItem(Database.getChestNum(i), advance);
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
				
				for(AchievementList l : AchievementList.values()) {
					if(item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&f" + l.getTitle()))) {
						if(!CustomAchievement.getRewarded(p, l) && !CustomAchievement.getCleared(p, l)) break;
						RewardManager.rewardPlayer(p, l);
						CustomAchievement.setRewarded(p, l, true);
						openAdvanceGUI(p);
						
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
	
}
