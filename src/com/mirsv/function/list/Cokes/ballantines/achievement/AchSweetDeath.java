package com.mirsv.function.list.Cokes.ballantines.achievement;

import com.mirsv.function.list.daybreak.achievements.Achievement;
import com.mirsv.function.list.daybreak.achievements.reward.MoneyReward;

public class AchSweetDeath extends Achievement {

	public static AchSweetDeath instance = new AchSweetDeath();

	private AchSweetDeath() {
		super("sweetdeath", "달콤한 죽음", Type.CHALLENGE, new String[] { "초콜릿으로 다른 플레이어를 죽이세요." }, new MoneyReward(500));
	}

}
