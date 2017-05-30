package com.mirsv;

import com.mirsv.catnote.*;
import com.mirsv.moonshine.*;
import com.mirsv.moonshine.Warning.Warning;
import com.mirsv.moonshine.hungry.Hungry;
import com.mirsv.moonshine.Welcome.Welcome;
import com.mirsv.moonshine.noblock.NoBlock;

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
	WhisperChat("WhisperChat", new WhisperChat());

	private String PluginName;
	private MirPlugin plugin;

	PluginLists(String Name, MirPlugin mir) {
		this.PluginName = Name;
	}

	public String getPluginName() {
		return PluginName;
	}

	public MirPlugin getPlugin() {
		return plugin;
	}
}