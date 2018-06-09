package com.mirsv.Marlang.CustomAdvancements;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import com.mirsv.Marlang.UserMenu.MainGUI;

public enum AdvancementsList {
	
	StartOfJourney("startofjourney", "발전과제 알아보기", AdvancementTypes.발전),
	Community("community", "공동체", AdvancementTypes.발전 );
	
	private String advid;
	private String advname;
	private AdvancementTypes advtype;
	
	private AdvancementsList(String advID, String advName, AdvancementTypes advType) {
		this.advid = advID;
		this.advname = advName;
		this.advtype = advType;
	}

	public String getAdvid() {
		return advid;
	}

	public String getAdvname() {
		return advname;
	}
	
	public AdvancementTypes getAdvType() {
		return advtype;
	}
	
}