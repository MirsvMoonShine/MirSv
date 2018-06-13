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

	Inventory mainGUI = Bukkit.createInventory(null, 18, ChatColor.DARK_GRAY + "��������");
	Inventory advanceGUI = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "��������");
	Inventory challengeGUI = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "��������");
	
	public static void openMainGUI(Player p) {
		
	}
	
	public static void openAdvanceGUI(Player p) {
		int i = 1;
		for(AchievementList l : AchievementList.values()) {
			if(CustomAchievement.getCleared(p, l)) { //���������� Ŭ�������� ���
				ItemStack advance = new ItemStack(Material.DIAMOND_BLOCK);
				ItemMeta advanceMeta = advance.getItemMeta();
				ArrayList<String> advanceLore = new ArrayList<String>();
				advanceMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b" + l.getTitle()));
				advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&7" + l.getDifficulty().getLevel()));
				advanceLore.add("");
				advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&7����:"));
				if (l.getDifficulty().equals(AchievementDifficulty.�ſ콬��)) {
					advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&7+&310&f ����ġ"));
				} else if (l.getDifficulty().equals(AchievementDifficulty.����)) {
					advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&7+&350&f ����ġ"));
				} else if (l.getDifficulty().equals(AchievementDifficulty.����)) {
					advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&7+&3100&f ����ġ"));
				} else if (l.getDifficulty().equals(AchievementDifficulty.�����)) {
					advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&7+&3300&f ����ġ"));
				} else if (l.getDifficulty().equals(AchievementDifficulty.�ſ�����)) {
					advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&7+&3500&f ����ġ"));
				}
				advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&7+&3..."));
				advanceLore.add("");
				if(!CustomAchievement.getRewarded(p, l)) { //������ �������� ���
					advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&a�� &bŬ���ؼ� ������ �����ϼ���!"));
				} else { //������ �������� �ʾ��� ���
					advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&a�̹� �� ������ �����߽��ϴ�!"));
				}
				setMeta(advance, advanceMeta, advanceLore);
				cagui.mainGUI.setItem(Database.getChestNum(i), advance);
			} else { //���������� Ŭ�������� �ʾ��� ���
				ItemStack advance = new ItemStack(Material.COAL_BLOCK);
				ItemMeta advanceMeta = advance.getItemMeta();
				ArrayList<String> advanceLore = new ArrayList<String>();
				advanceMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b" + l.getTitle()));
				advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&7" + l.getDifficulty().getLevel()));
				advanceLore.add("");
				advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&7����:"));
				if (l.getDifficulty().equals(AchievementDifficulty.�ſ콬��)) {
					advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&7+&310&f ����ġ"));
				} else if (l.getDifficulty().equals(AchievementDifficulty.����)) {
					advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&7+&350&f ����ġ"));
				} else if (l.getDifficulty().equals(AchievementDifficulty.����)) {
					advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&7+&3100&f ����ġ"));
				} else if (l.getDifficulty().equals(AchievementDifficulty.�����)) {
					advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&7+&3300&f ����ġ"));
				} else if (l.getDifficulty().equals(AchievementDifficulty.�ſ�����)) {
					advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&7+&3500&f ����ġ"));
				}
				advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&7+&3..."));
				advanceLore.add("");
				advanceLore.add(ChatColor.translateAlternateColorCodes('&', "&c���� Ŭ�������� �ʾҽ��ϴ�!"));
				setMeta(advance, advanceMeta, advanceLore);
				cagui.mainGUI.setItem(Database.getChestNum(i), advance);
			}
			
			i++;
		}
		
		ItemStack back = new ItemStack(Material.SPRUCE_DOOR_ITEM);
		ItemMeta backMeta = back.getItemMeta();
		backMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a������"));
		ArrayList<String> backLore = new ArrayList<String>();
		backLore.add(ChatColor.translateAlternateColorCodes('&', "&7�������� ������"));
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
				
				if(item.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&a������"))) {
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
