package com.mirsv.function.list.daybreak.achievements.list.base;

import com.mirsv.function.list.daybreak.achievements.Achievement;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class BaseAchievements {

	public static final Achievement[] values = {
			AchStartOfJourney.instance,
			new AchJumpIntoOcean(),
			new AchTowerOfBabel(),
			new AchPowerOverwhelming(),
			new AchIsThisPossible(),
			new AchOnlyToday(),
			new AchNoLimit()
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
