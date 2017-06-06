package com.mirsv.moonshine.Party;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class Party {
	List<Player> player;
	String PartyName;
	Player owner;
	
	public Party(Player owner, String Name){
		this.PartyName = Name;
		this.player = new ArrayList<>();
		player.add(owner);
		this.owner = owner;
	}
	
	public boolean isPlayerJoin(Player p){
		if (player.contains(p)){
			return true;
		} else {
			return false;
		}
	}
	
	public List<Player> getPlayers(){
		return player;
	}
	
	public String getPartyName(){
		return PartyName;
	}
	
	public Player getOwner(){
		return owner;
	}
}
