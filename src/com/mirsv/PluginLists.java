package com.mirsv;

import com.mirsv.catnote.controlmessage.ControlMessage;
import com.mirsv.catnote.CallPlayer.CallPlayer;
import com.mirsv.catnote.WhistleChat.WhistleChat;
import com.mirsv.moonshine.BroadCast.BroadCast;
import com.mirsv.moonshine.ClearChat.ClearChat;
import com.mirsv.moonshine.GlobalMute.GlobalMute;
import com.mirsv.moonshine.ItemTag.ItemTag;
import com.mirsv.moonshine.Warning.Warning;
import com.mirsv.moonshine.hungry.Hungry;
import com.mirsv.moonshine.Welcome.Welcome;
import com.mirsv.moonshine.noblock.NoBlock;

public enum PluginLists {	
	GlobalMute("GlobalMute", new GlobalMute("GlobalMute")), 
	ClearChat("ClearChat", new ClearChat("ClearChat")), 
	ItemTag("ItemTag", new ItemTag("ItemTag")), 
	Warning("Warning", new Warning("Warning")), 
	BroadCast("BroadCast", new BroadCast("BroadCast")),
	Hungry("Hungry", new Hungry("Hungry")),
	Welcome("Welcome", new Welcome("Welcome")),
	NoBlock("NoBlock", new NoBlock("NoBlock")),
	CallPlayer("CallPlayer",new CallPlayer("CallPlayer")),
	ControlDeathMessage("ControlDeathMessage", new ControlMessage("ControlDeathMessage")),
	WhistleChat("WhistleChat", new WhistleChat("WhistleChat"));
	
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
