package com.mirsv.function.list.Cokes.ballantines;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemMaker {
	private ItemStack item;

	public ItemMaker(Material material) {
		item = new ItemStack(material, 1);
	}

	public ItemMaker(Material material, short damage) {
		item = new ItemStack(material, 1, damage);
	}

	public ItemStack parseItemStack() {
		return item;
	}

	public boolean hasDisplayName() {
		return (item.hasItemMeta() && item.getItemMeta().hasDisplayName());
	}

	public ItemMaker setDisplayName(String name) {
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(name);
		item.setItemMeta(im);
		return this;
	}

	public List<String> getLore() {
		ItemMeta im = item.getItemMeta();
		List<String> lore = im.getLore();
		if (lore == null) lore = new ArrayList<String>();
		return lore;
	}

	public ItemMaker setLore(String... strings) {
		ItemMeta im = item.getItemMeta();
		im.setLore(Arrays.asList(strings));
		item.setItemMeta(im);
		return this;
	}
}
