package com.mirsv;

import com.mirsv.catnote.*;
import com.mirsv.moonshine.*;
import com.mirsv.moonshine.Party.*;
import com.mirsv.moonshine.Warning.Warning;

public enum PluginLists {
	GlobalMute("GlobalMute", new GlobalMute()),
	ClearChat("ClearChat", new ClearChat()),
	ItemTag("ItemTag", new ItemTag()),
	Warning("Warning", new Warning()),
	BroadCast("BroadCast", new BroadCast()),
	Hungry("Hungry", new Hungry()),
	Welcome("Welcome", new Welcome()),
	NoBlock("NoBlock", new NoBlock()),
	CallPlayer("CallPlayer", new CallPlayer()),
	ControlDeathMessage("ControlDeathMessage", new ControlMessage()),
	WhisperChat("WhisperChat", new WhisperChat()),
	Party("Party", new PartyMain()),
	BlockSpawner("BlockSpawner", new BlockSpawner()),
	DisableCreateTown("DisableCreateTown", new DisableCreateTown()),
	PvPTeleport("PvPTeleport", new PvPTeleport()),
	Guide("Guide", new Guide()),
	EasyResidence("EasyResidence", new EasyResidence()),
	HeadChange("HeadChange", new HeadChange()),
	PlayTime("PlayTime", new PlayTime()),
	NoPickup("NoPickup", new NoPickup()),
	CasinoSecurity("CasinoSecurity", new CasinoSecurity());

	private String PluginName;
	private MirPlugin plugin;

	PluginLists(String Name, MirPlugin mir) {
		this.PluginName = Name;
		this.plugin = mir;
	}

	public String getPluginName() {
		return PluginName;
	}

	public MirPlugin getPlugin() {
		return plugin;
	}
}