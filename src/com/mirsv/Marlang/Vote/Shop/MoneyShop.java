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

	Inventory mogui = Bukkit.createInventory(null, 18, ChatColor.DARK_GRAY + "돈 상점");
	
	static MoneyShop ms = new MoneyShop();
	static HashMap<Player, Boolean> isOpeningGUI = new HashMap<Player, Boolean>();
	
	public static void openMoneyShop(Player p) {
		eco = ms.rspe.getProvider();
		
		ItemStack back = new ItemStack(Material.SPRUCE_DOOR_ITEM);
		ItemMeta backMeta = back.getItemMeta();
		backMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a나가기"));
		ArrayList<String> backLore = new ArrayList<String>();
		backLore.add(ChatColor.translateAlternateColorCodes('&', "&7돈 상점 나가기"));
		setMeta(back, backMeta, backLore);
		
		ItemStack info = new ItemStack(Material.DIAMOND);
		ItemMeta infoMeta = info.getItemMeta();
		infoMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a추천포인트"));
		ArrayList<String> infoLore = new ArrayList<String>();
		infoLore.add(ChatColor.translateAlternateColorCodes('&', "&7점수 : &b" + VotePoint.getVotePoint(p) + "점"));
		infoMeta.setLore(infoLore);
		info.setItemMeta(infoMeta);
		
		ItemStack money1 = new ItemStack(Material.CONCRETE, 1, (short) 14);
		ItemMeta money1Meta = money1.getItemMeta();
		money1Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e300원"));
		ArrayList<String> money1Lore = new ArrayList<String>();
		money1Lore.add(ChatColor.translateAlternateColorCodes('&', "&7가격 : &b추천포인트 1점"));
		money1Lore.add(" ");
		money1Lore.add(ChatColor.translateAlternateColorCodes('&', "&a▶ &7구매하려면 클릭하세요!"));
		money1Meta.setLore(money1Lore);
		money1.setItemMeta(money1Meta);
		
		ItemStack money2 = new ItemStack(Material.CONCRETE, 1, (short) 1);
		ItemMeta money2Meta = money2.getItemMeta();
		money2Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e1000원"));
		ArrayList<String> money2Lore = new ArrayList<String>();
		money2Lore.add(ChatColor.translateAlternateColorCodes('&', "&7가격 : &b추천포인트 3점"));
		money2Lore.add(" ");
		money2Lore.add(ChatColor.translateAlternateColorCodes('&', "&a▶ &7구매하려면 클릭하세요!"));
		money2Meta.setLore(money2Lore);
		money2.setItemMeta(money2Meta);
		
		ItemStack money3 = new ItemStack(Material.CONCRETE, 1, (short) 4);
		ItemMeta money3Meta = money3.getItemMeta();
		money3Meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e3300원"));
		ArrayList<String> money3Lore = new ArrayList<String>();
		money3Lore.add(ChatColor.translateAlternateColorCodes('&', "&7가격 : &b추천포인트 10점"));
		money3Lore.add(" ");
		money3Lore.add(ChatColor.translateAlternateColorCodes('&', "&a▶ &7구매하려면 클릭하세요!"));
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
				if(item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "나가기")) {
					VoteGUI.openMainShop(p);
				}
				if(item.getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "300원")) {
					if(!VotePoint.hasPoint(p, 1)) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c추천포인트가 부족합니다!"));
						e.setCancelled(true);
						return;
					}
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&b추천포인트 1점&f을 &e300원&f으로 교환했습니다!"));
					VotePoint.minusVotePoint(p, 1);
					eco.depositPlayer(p, 300);
				}
				if(item.getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "1000원")) {
					if(!VotePoint.hasPoint(p, 3)) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c추천포인트가 부족합니다!"));
						e.setCancelled(true);
						return;
					}
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&b추천포인트 3점&f을 &e1000원&f으로 교환했습니다!"));
					VotePoint.minusVotePoint(p, 3);
					eco.depositPlayer(p, 1000);
				}
				if(item.getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "3300원")) {
					if(!VotePoint.hasPoint(p, 10)) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c추천포인트가 부족합니다!"));
						e.setCancelled(true);
						return;
					}
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&b추천포인트 10점&f을 &e3300원&f으로 교환했습니다!"));
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
