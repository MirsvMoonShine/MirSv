package com.mirsv;

import com.mirsv.Marlang.Commands;
import com.mirsv.Marlang.PlayerID;
import com.mirsv.Marlang.RandomTP;
import com.mirsv.catnote.BingoGamble;
import com.mirsv.catnote.BlockSpawner;
import com.mirsv.catnote.CallPlayer;
import com.mirsv.catnote.ControlMessage;
import com.mirsv.catnote.DisableCreateTown;
import com.mirsv.catnote.EasyResidence;
import com.mirsv.catnote.Guide;
import com.mirsv.catnote.HeadChange;
import com.mirsv.catnote.NoPickup;
import com.mirsv.catnote.PlayTime;
import com.mirsv.catnote.PvPTeleport;
import com.mirsv.catnote.WhisperChat;
import com.mirsv.moonshine.BroadCast;
import com.mirsv.moonshine.ClearChat;
import com.mirsv.moonshine.Disables;
import com.mirsv.moonshine.ForbiddenWord;
import com.mirsv.moonshine.GlobalMute;
import com.mirsv.moonshine.Hungry;
import com.mirsv.moonshine.ItemTag;
import com.mirsv.moonshine.NoBedInAnotherWorld;
import com.mirsv.moonshine.Welcome;
import com.mirsv.moonshine.Party.PartyMain;
import com.mirsv.moonshine.Warning.Warning;

public enum PluginLists {
	GlobalMute("GlobalMute", new GlobalMute()),
	ClearChat("ClearChat", new ClearChat()),
	ItemTag("ItemTag", new ItemTag()),
	Warning("Warning", new Warning()),
	BroadCast("BroadCast", new BroadCast()),
	Hungry("Hungry", new Hungry()),
	Welcome("Welcome", new Welcome()),
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
	BingoGamble("BingoGamble", new BingoGamble()),
	Disables("Disables", new Disables()),
	NoBedInAnotherWorld("NoBedInAnotherWorld", new NoBedInAnotherWorld()),
	ForbiddenWord("ForbiddenWord", new ForbiddenWord()),
	Commands("Commands", new Commands()),
	PlayerID("PlayerID", new PlayerID()),
	RandomTP("RandomTP", new RandomTP());

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