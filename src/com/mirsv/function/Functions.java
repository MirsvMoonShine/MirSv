package com.mirsv.function;

import com.mirsv.function.list.CatNote.CallPlayer;
import com.mirsv.function.list.CatNote.WhisperChat;
import com.mirsv.function.list.Cokes.*;
import com.mirsv.function.list.Cokes.Party.PartyManager;
import com.mirsv.function.list.Cokes.Chat.AdvanceChat;

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
	AdvanceChat(new AdvanceChat()),
	Nickname(new NickName());

	private final AbstractFunction function;

	private Functions(AbstractFunction function) {
		this.function = function;
	}

	public AbstractFunction getFunction() {
		return function;
	}
	
	public static Functions getFunction(String name) {
		for(Functions f : Functions.values()) {
			if(f.toString().equalsIgnoreCase(name)) {
				return f;
			}
		}
		
		return null;
	}
	
}