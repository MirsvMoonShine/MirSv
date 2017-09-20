package com.mirsv.moonshine.Party;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Party {
	List<UUID> player;
	String PartyName;
	UUID owner;
	boolean pvp = true, open = false;
	
	public Party(UUID owner, String Name, boolean pvp, boolean open){
		this.PartyName = Name;
		this.player = new ArrayList<UUID>();
		this.pvp = pvp;
		this.open = open;
		player.add(owner);
		this.owner = owner;
	}
	
	public boolean isPlayerJoin(UUID p){
		if (player.contains(p)){
			return true;
		} else {
			return false;
		}
	}
	
	public List<UUID> getPlayers(){
		return player;
	}
	
	public String getPartyName(){
		return PartyName;
	}
	
	public UUID getOwner(){
		return owner;
	}
	public boolean TogglePvP() {
		pvp = (!pvp);
	    return pvp;
	}
	public boolean ToggleOpen() {
		open = (!open);
		return open;
	}
}