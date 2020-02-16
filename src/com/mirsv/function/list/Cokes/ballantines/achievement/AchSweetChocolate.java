package com.mirsv.function.list.Cokes.ballantines.achievement;

import com.mirsv.function.list.daybreak.achievements.Achievement;
import com.mirsv.function.list.daybreak.achievements.reward.MoneyReward;

public class AchSweetChocolate extends Achievement {

	public static AchSweetChocolate instance = new AchSweetChocolate();

	private AchSweetChocolate() {
		super("sweetchocolate", "달콤한 초콜릿", Type.CHALLENGE, new String[] { "초콜릿을 조합하세요." }, new MoneyReward(200));
	}

}
