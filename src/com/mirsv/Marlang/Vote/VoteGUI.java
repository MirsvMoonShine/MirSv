package com.mirsv.Marlang.Vote;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
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

import net.milkbowl.vault.economy.Economy;

public class VoteGUI extends MirPlugin implements Listener{
	
	public static Economy eco = null;
	RegisteredServiceProvider <Economy> rspe = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
	static VoteGUI vg = new VoteGUI();
	static MainGUI mg = new MainGUI();
	
	public VoteGUI() {
		getListener(this);
	}
	
	Inventory gui = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "��õ����");
	Inventory mogui = Bukkit.createInventory(null, 18, ChatColor.DARK_GRAY + "�� ����");
	Inventory scgui = Bukkit.createInventory(null, 36, ChatColor.DARK_GRAY + "��ũ�� ����");
	
	public static void openMainShop(Player p) {
		eco = vg.rspe.getProvider();
		
		ItemStack info = new ItemStack(Material.DIAMOND);
		ItemMeta infoMeta = info.getItemMeta();
		infoMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a��õ����Ʈ"));
		ArrayList<String> infoLore = new ArrayList<String>();
		infoLore.add(ChatColor.translateAlternateColorCodes('&', "&7���� : &b" + VotePoint.getVotePoint(p) + "��"));
		infoMeta.setLore(infoLore);
		info.setItemMeta(infoMeta);
		
		ItemStack back = new ItemStack(Material.ARROW);
		ItemMeta backMeta = back.getItemMeta();
		backMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a�ڷ� ����"));
		ArrayList<String> backLore = new ArrayList<String>();
		backLore.add(ChatColor.translateAlternateColorCodes('&', "&7���� �гη� ���ư���"));
		backMeta.setLore(backLore);
		back.setItemMeta(backMeta);
		
		ItemStack reload = new ItemStack(Material.FEATHER);
		ItemMeta reloadMeta = reload.getItemMeta();
		reloadMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a���� ��ħ"));
		ArrayList<String> reloadLore = new ArrayList<String>();
		reloadLore.add(ChatColor.translateAlternateColorCodes('&', "&7GUI ���� ��ġ��"));
		reloadMeta.setLore(reloadLore);
		reload.setItemMeta(reloadMeta);
		
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
		vg.gui.setItem(50, reload);
		
		p.openInventory(vg.gui);
	}
	
	public static void openMoneyShop(Player p) {
		eco = vg.rspe.getProvider();
		
		ItemStack back = new ItemStack(Material.ARROW);
		ItemMeta backMeta = back.getItemMeta();
		backMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a�ڷ� ����"));
		ArrayList<String> backLore = new ArrayList<String>();
		backLore.add(ChatColor.translateAlternateColorCodes('&', "&7��õ�������� ���ư���"));
		backMeta.setLore(backLore);
		back.setItemMeta(backMeta);
		
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
		
		vg.mogui.setItem(0, money1);
		vg.mogui.setItem(1, money2);
		vg.mogui.setItem(2, money3);
		vg.mogui.setItem(12, back);
		vg.mogui.setItem(13, info);
		vg.mogui.setItem(14, reload);
		
		p.openInventory(vg.mogui);
	}
	
	public static void openScrollShop(Player p) {
		
		ItemStack back = new ItemStack(Material.ARROW);
		ItemMeta backMeta = back.getItemMeta();
		backMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a�ڷ� ����"));
		ArrayList<String> backLore = new ArrayList<String>();
		backLore.add(ChatColor.translateAlternateColorCodes('&', "&7��õ�������� ���ư���"));
		backMeta.setLore(backLore);
		back.setItemMeta(backMeta);
		
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
		
		vg.scgui.setItem(30, back);
		vg.scgui.setItem(31, info);
		vg.scgui.setItem(32, reload);
		p.openInventory(vg.scgui);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		eco = vg.rspe.getProvider();
		Player p = (Player) e.getWhoClicked();
		ClickType click = e.getClick();
		Inventory inventory = e.getClickedInventory();
		ItemStack item = e.getCurrentItem();
		try {
			if(inventory.equals(gui)) {

				if(item == null || !item.hasItemMeta()) return;
				if(item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "�ڷ� ����")) {
					mg.openGUI(p);
				}
				if(item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "���� ��ħ")) {
					openMainShop(p);
				}
				if(item.getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "��")) {
					openMoneyShop(p);
				}
				if(item.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "��ũ��")) {
					openScrollShop(p);
				}
				e.setCancelled(true);
			}
			if(inventory.equals(mogui)) {
				if(item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "�ڷ� ����")) {
					openMainShop(p);
				}
				if(item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "���� ��ħ")) {
					openMoneyShop(p);
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
			if(inventory.equals(scgui)) {
				if(item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "�ڷ� ����")) {
					openMainShop(p);
				}
				if(item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "���� ��ħ")) {
					openScrollShop(p);
				}
				e.setCancelled(true);
			}
		} catch (Exception ex) {}
		
	}
	
}
