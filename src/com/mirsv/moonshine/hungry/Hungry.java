package com.mirsv.moonshine.hungry;

import com.mirsv.MirPlugin;

public class Hungry extends MirPlugin {

	public Hungry(String pluginname) {
		super(pluginname);

		getListener(new HungryListener(getConfig()));
	}

}