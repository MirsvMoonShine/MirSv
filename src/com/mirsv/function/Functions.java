package com.mirsv.function;

import com.google.common.base.Enums;
import com.mirsv.function.list.CatNote.CallPlayer;
import com.mirsv.function.list.CatNote.WhisperChat;
import com.mirsv.function.list.Cokes.*;
import com.mirsv.function.list.Cokes.party.PartyManager;
import com.mirsv.function.list.Cokes.CustomPrefix.CustomPrefix;
import com.mirsv.function.list.daybreak.Convenience;
import com.mirsv.function.list.daybreak.NoHungerAtNight;
import com.mirsv.function.list.daybreak.achievements.AchievementManager;
import com.mirsv.function.list.daybreak.firework.NoFireworkDamage;
import com.mirsv.function.list.daybreak.skill.SkillManager;

public enum Functions {
	GlobalMute(new GlobalMute()),
	ClearChat(new ClearChat()),
	ItemTag(new ItemTag()),
	BroadCast(new BroadcastChat()),
	Hungry(new HungerMaintenance()),
	CallPlayer(new CallPlayer()),
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
	Convenience(new Convenience());

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