package com.mirsv.function;

import com.google.common.base.Enums;
import com.mirsv.function.list.CatNote.WhisperChat;
import com.mirsv.function.list.Cokes.AdvancedChat;
import com.mirsv.function.list.Cokes.BedProhibition;
import com.mirsv.function.list.Cokes.BroadcastChat;
import com.mirsv.function.list.Cokes.ClearChat;
import com.mirsv.function.list.Cokes.CustomPrefix.CustomPrefix;
import com.mirsv.function.list.Cokes.GlobalMute;
import com.mirsv.function.list.Cokes.HungerMaintenance;
import com.mirsv.function.list.Cokes.ItemTag;
import com.mirsv.function.list.Cokes.NickName;
import com.mirsv.function.list.Cokes.WordProhibition;
import com.mirsv.function.list.Cokes.ballantines.BallantinesDay;
import com.mirsv.function.list.Cokes.party.PartyManager;
import com.mirsv.function.list.daybreak.CombatManager;
import com.mirsv.function.list.daybreak.Convenience;
import com.mirsv.function.list.daybreak.DisableCommands;
import com.mirsv.function.list.daybreak.NoHungerAtNight;
import com.mirsv.function.list.daybreak.achievements.AchievementManager;
import com.mirsv.function.list.daybreak.firework.NoFireworkDamage;
import com.mirsv.function.list.daybreak.skill.SkillManager;
import com.mirsv.function.list.daybreak.tutorial.TutorialManager;
import com.mirsv.function.list.daybreak.warning.WarningManager;

public enum Functions {
	GlobalMute(new GlobalMute()),
	ClearChat(new ClearChat()),
	ItemTag(new ItemTag()),
	BroadCast(new BroadcastChat()),
	Hungry(new HungerMaintenance()),
	WhisperChat(new WhisperChat()),
	Party(new PartyManager()),
	NoBedInAnotherWorld(new BedProhibition()),
	ForbiddenWord(new WordProhibition()),
	AdvanceChat(new AdvancedChat()),
	Nickname(new NickName()),
	CustomPrefix(new CustomPrefix()),
	NoFireworkDamage(new NoFireworkDamage()),
	Achievements(new AchievementManager()),
	NoHungerAtNight(new NoHungerAtNight()),
	SkillManager(new SkillManager()),
	Convenience(new Convenience()),
	WarningManager(new WarningManager()),
	TutorialManager(new TutorialManager()),
	BallantinesDay(new BallantinesDay()),
	DisableCommands(new DisableCommands()),
	CombatManager(new CombatManager());

	private final AbstractFunction function;

	Functions(AbstractFunction function) {
		this.function = function;
	}

	public AbstractFunction getFunction() {
		return function;
	}

	public static Functions getFunction(String name) {
		return Enums.getIfPresent(Functions.class, name).orNull();
	}

}