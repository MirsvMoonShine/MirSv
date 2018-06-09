package com.mirsv.Marlang.CustomAdvancements.Reward;

import com.mirsv.Marlang.CustomAdvancements.AdvancementsList;

public enum CreatedRewards {
	//AdvancementsList의 도전과제를 적을 것
	startofjourney(AdvancementsList.StartOfJourney);
	
	private AdvancementsList l;
	
	private CreatedRewards(AdvancementsList s) {
		this.l = s;
	}

	public AdvancementsList getAdvancement() {
		return l;
	}	
	
}
