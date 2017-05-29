package com.mirsv.moonshine.noblock;

import java.util.ArrayList;
import java.util.List;

import com.mirsv.MirPlugin;

public class NoBlock extends MirPlugin {
	public static List < Integer > blocks = new ArrayList < Integer > ();

	@SuppressWarnings("unchecked")
	public NoBlock(String pluginname) {
		super(pluginname);

		blocks.add(52);

		getConfig().addDefault("NoBlock.list", blocks);
		getConfig().options().copyDefaults(true);
		saveConfig();

		blocks.clear();
		blocks = (List < Integer > ) getConfig().getList("NoBlock.list");

		getListener(new NoBlockListener(getConfig()));
	}
}