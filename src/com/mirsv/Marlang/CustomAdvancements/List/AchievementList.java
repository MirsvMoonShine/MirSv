package com.mirsv.Marlang.CustomAdvancements.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import com.mirsv.Marlang.UserMenu.MainGUI;

public enum AchievementList {
	
	StartOfJourney("startofjourney", "모험의 서막", AchievementDifficulty.쉬움, AchievementType.발전);
	
	private String ID;
	private String Title;
	AchievementDifficulty Difficulty;
	private AchievementType Type;
	
	private AchievementList(String i, String t, AchievementDifficulty d, AchievementType at) {
		this.ID = i;
		this.Title = t;
		this.Difficulty = d;
		this.Type = at;
	}

	public String getID() {
		return ID;
	}

	public String getTitle() {
		return Title;
	}

	public AchievementDifficulty getDifficulty() {
		return Difficulty;
	}

	public AchievementType getType() {
		return Type;
	}
	
}