package com.mirsv.moonshine.Party;

import java.util.ArrayList;
import java.util.List;

public class Party {
	List<String> player;
	String PartyName;
	String owner;
	
	public Party(String owner, String Name){
		this.PartyName = Name;
		this.player = new ArrayList<>();
		player.add(owner);
		this.owner = owner;
	}
	
	public boolean isPlayerJoin(String p){
		if (player.contains(p)){
			return true;
		} else {
			return false;
		}
	}
	
	public List<String> getPlayers(){
		return player;
	}
	
	public String getPartyName(){
		return PartyName;
	}
	
	public String getOwner(){
		return owner;
	}
}
