package com.mirsv;

import com.mirsv.catnote.controlmessage.ControlMessage;
import com.mirsv.moonshine.BroadCast.BroadCast;
import com.mirsv.moonshine.ClearChat.ClearChat;
import com.mirsv.moonshine.GlobalMute.GlobalMute;
import com.mirsv.moonshine.ItemTag.ItemTag;
import com.mirsv.moonshine.Waring.Warning;
import com.mirsv.moonshine.hungry.Hungry;

public enum PluginLists {	
	GlobalMute("GlobalMute", new GlobalMute("GlobalMute")), 
	ClearChat("ClearChat", new ClearChat("ClearChat")), 
	ItemTag("ItemTag", new ItemTag("ItemTag")), 
	Warning("Warning", new Warning("Warning")), 
	BroadCast("BroadCast", new BroadCast("BroadCast")),
	Hungry("Hungry", new Hungry("Hungry")),
	Welcome("Welcome", new com.mirsv.moonshine.Welcome.Welcome("Welcome")),
	NoBlock("NoBlock", new com.mirsv.moonshine.noblock.NoBlock("NoBlock")),
	CallPlayer("CallPlayer",new com.mirsv.catnote.CallPlayer.CallPlayer("CallPlayer")),
	ControlDeathMessage("ControlDeathMessage", new ControlMessage("ControlDeathMessage")),
	AdvanceClearEntity("AdvanceClearEntity", new com.mirsv.catnote.AdvanceClearEntity.AdvanceClearEntity("AdvanceClearEntity")),
	WhistleChat("WhistleChat", new com.mirsv.catnote.WhistleChat.WhistleChat("WhistleChat"));
	
	private String PluginName;
	private MirPlugin plugin;
	
	PluginLists(String Name, MirPlugin mir){
		this.PluginName = Name;
	}
	
	public String getPluginName(){
		return PluginName;
	}
	
	public MirPlugin getPlugin(){
		return plugin;
	}
}
