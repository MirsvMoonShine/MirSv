package com.mirsv.Marlang.CustomAdvancements.Reward;

import com.mirsv.Marlang.CustomAdvancements.AdvancementsList;

public enum CreatedRewards {
	//AdvancementsList�� ���������� ���� ��
	startofjourney(AdvancementsList.StartOfJourney);
	
	private AdvancementsList l;
	
	private CreatedRewards(AdvancementsList s) {
		this.l = s;
	}

	public AdvancementsList getAdvancement() {
		return l;
	}	
	
}
