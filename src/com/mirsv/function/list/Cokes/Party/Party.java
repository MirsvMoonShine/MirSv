package com.mirsv.function.list.Cokes.Party;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Party {
	private final List<UUID> player;
	private final String PartyName;
	private final UUID owner;
	private boolean pvp = true, open = false;
	
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
		pvp = !pvp;
	    return pvp;
	}
	public boolean ToggleOpen() {
		open = (!open);
		return open;
	}

	public boolean isPVP() {
		return pvp;
	}

	public boolean isOPEN() {
		return pvp;
	}

}