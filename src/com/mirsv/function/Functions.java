package com.mirsv.function;

import com.mirsv.function.list.CatNote.CallPlayer;
import com.mirsv.function.list.CatNote.PlayTime;
import com.mirsv.function.list.CatNote.WhisperChat;
import com.mirsv.function.list.Cokes.BroadCast;
import com.mirsv.function.list.Cokes.ClearChat;
import com.mirsv.function.list.Cokes.ForbiddenWord;
import com.mirsv.function.list.Cokes.GlobalMute;
import com.mirsv.function.list.Cokes.Hungry;
import com.mirsv.function.list.Cokes.ItemTag;
import com.mirsv.function.list.Cokes.NoBedInAnotherWorld;
import com.mirsv.function.list.Cokes.Party.PartyManager;

public enum Functions {
	GlobalMute(new GlobalMute()),
	ClearChat(new ClearChat()),
	ItemTag(new ItemTag()),
	BroadCast(new BroadCast()),
	Hungry(new Hungry()),
	CallPlayer(new CallPlayer()),
	WhisperChat(new WhisperChat()),
	Party(new PartyManager()),
	PlayTime(new PlayTime()),
	NoBedInAnotherWorld(new NoBedInAnotherWorld()),
	ForbiddenWord(new ForbiddenWord());

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