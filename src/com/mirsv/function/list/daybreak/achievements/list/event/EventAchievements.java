package com.mirsv.function.list.daybreak.achievements.list.event;

import com.mirsv.function.list.Cokes.ballantines.achievement.AchSweetChocolate;
import com.mirsv.function.list.Cokes.ballantines.achievement.AchSweetDeath;
import com.mirsv.function.list.Cokes.ballantines.achievement.AchWithYou;
import com.mirsv.function.list.daybreak.achievements.Achievement;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class EventAchievements {

	public static final Achievement[] values = {
			AchSweetChocolate.instance,
			AchWithYou.instance,
			AchSweetDeath.instance
	};

	public static final List<Achievement> nameBased = Arrays.asList(values);

	static {
		nameBased.sort(new Comparator<Achievement>() {
			@Override
			public int compare(Achievement a, Achievement b) {
				return a.getName().compareTo(b.getName());
			}
		});
	}

}
