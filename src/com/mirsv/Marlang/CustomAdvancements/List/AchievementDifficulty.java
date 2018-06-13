package com.mirsv.Marlang.CustomAdvancements.List;

public enum AchievementDifficulty {
	
	매우쉬움("석탄 단계"),
	쉬움("철 단계"),
	보통("금 단계"),
	어려움("다이아몬드 단계"),
	매우어려움("에메랄드 단계");
	
	String level;
	
	AchievementDifficulty(String l) {
		this.level = l;
	}

	public String getLevel() {
		return level;
	}
	
}
