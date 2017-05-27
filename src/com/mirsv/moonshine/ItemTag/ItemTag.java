package com.mirsv.moonshine.ItemTag;

import com.mirsv.MirPlugin;

public class ItemTag extends MirPlugin {

	public ItemTag(String pluginname) {
		super(pluginname);
		
		getCommand("ItemTag", new ItemTagCommand(getConfig()));
	}

}
