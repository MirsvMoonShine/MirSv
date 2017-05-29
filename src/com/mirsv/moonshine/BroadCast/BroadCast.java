package com.mirsv.moonshine.BroadCast;

import java.util.ArrayList;

import com.mirsv.MirPlugin;

public class BroadCast extends MirPlugin {
	public static ArrayList < String > BCadmins = new ArrayList < String > ();

	public BroadCast(String pluginname) {
		super(pluginname);
		getConfig().addDefault("BroadCast.Prefix", "&6[&4°øÁö&6]");
		getConfig().addDefault("BroadCast.ChatColor", "&a");
		getConfig().options().copyDefaults(true);
		saveConfig();

		getCommand("bc", new BroadCastCommand(getConfig()));
		getListener(new BroadCastListener(getConfig()));
	}
}