package com.mirsv;

import com.mirsv.catnote.controlmessage.ControlMessage;
import com.mirsv.moonshine.BroadCast.BroadCast;
import com.mirsv.moonshine.ClearChat.ClearChat;
import com.mirsv.moonshine.GlobalMute.GlobalMute;
import com.mirsv.moonshine.ItemTag.ItemTag;
import com.mirsv.moonshine.Waring.Warning;
import com.mirsv.moonshine.hungry.Hungry;
import com.mirsv.moonshine.noblock.NoBlock;
import com.mirsv.catnote.AdvanceClearEntity.*;
import com.mirsv.moonshine.Welcome.*;
import com.mirsv.catnote.CallPlayer.*;

public enum PluginLists {	
	GlobalMute("GlobalMute", new GlobalMute("GlobalMute")), 
	ClearChat("ClearChat", new ClearChat("ClearChat")), 
	ItemTag("ItemTag", new ItemTag("ItemTag")), 
	Warning("Warning", new Warning("Warning")), 
	BroadCast("BroadCast", new BroadCast("BroadCast")),
	Hungry("Hungry", new Hungry("Hungry")),
	NoBlock("NoBlock", new NoBlock("NoBlock")),
	Welcome("Welcome", new Welcome("Welcome")),
	CallPlayer("CallPlayer",new CallPlayer("CallPlayer")),
	ControlDeathMessage("ControlDeathMessage", new ControlMessage("ControlDeathMessage")),
	AdvanceClearEntity("AdvanceClearEntity", new AdvanceClearEntity("AdvanceClearEntity"));
	
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
