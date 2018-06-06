package com.mirsv.Marlang.Util.CustomHead;

import org.bukkit.craftbukkit.Main;
import org.bukkit.inventory.ItemStack;

public enum Heads {
	
	alphabetT("NjRjNzU2MTliOTFkMjQxZjY3ODM1MGFkOTIzN2MxMzRjNWUwOGQ4N2Q2ODYwNzQxZWRlMzA2YTRlZjkxIn19fQ==","alphabetT"),
	alphabetP("ZjlkZTYwMWRlZTNmZmVjYTRkNTQ1OTVmODQ0MjAxZDBlZDIwOTFhY2VjNDU0OGM2OTZiYjE2YThhMTU4ZjYifX19", "alphabetP");
   
    private ItemStack item;
    private String idTag;
    private String prefix = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv";
    private Heads(String texture, String id)
    {
        item = Head.createSkull(prefix + texture, id);
        idTag = id;
    }
   
   
    public ItemStack getItemStack()
    {
        return item;
    }
   
    public String getName()
    {
        return idTag;
    }
	
}
