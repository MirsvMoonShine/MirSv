package com.mirsv.moonshine.GlobalMute;

import com.mirsv.MirPlugin;

public class GlobalMute extends MirPlugin {

	public GlobalMute(String pluginname) {
		super(pluginname);

		getCommand("gmute", new MuteCommand(getConfig()));
		getListener(new MuteListener(getConfig()));
	}
}