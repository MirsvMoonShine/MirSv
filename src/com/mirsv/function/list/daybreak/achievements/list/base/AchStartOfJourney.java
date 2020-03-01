package com.mirsv.function.list.daybreak.achievements.list.base;

import com.mirsv.function.list.daybreak.achievements.Achievement;
import com.mirsv.function.list.daybreak.achievements.reward.MoneyReward;

public class AchStartOfJourney extends Achievement {

	public static final AchStartOfJourney instance = new AchStartOfJourney();

	private AchStartOfJourney() {
		super("startofjourney", "모험의 서막", Type.ADVANCE, new String[]{ "튜토리얼을 클리어하세요." }, new MoneyReward(100));
	}

}
