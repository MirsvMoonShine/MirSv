package com.mirsv.function.list.Cokes.ballantines.achievement;

import com.mirsv.function.list.daybreak.achievements.Achievement;
import com.mirsv.function.list.daybreak.achievements.reward.ExperienceReward;
import com.mirsv.function.list.daybreak.achievements.reward.MoneyReward;

public class AchWithYou extends Achievement {

	public static AchWithYou instance = new AchWithYou();

	private AchWithYou() {
		super("withyou", "그대와 함께", Type.CHALLENGE, new String[] { "초콜릿을 다른 플레이어에게 선물하세요." }, new MoneyReward(800), new ExperienceReward(ExperienceReward.Type.LEVEL, 5));
	}

}
