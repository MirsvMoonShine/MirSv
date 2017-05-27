package com.mirsv.moonshine.ClearChat;

import com.mirsv.MirPlugin;

public class ClearChat extends MirPlugin{

	public ClearChat(String pluginname) {
		super(pluginname);
		
		getCommand("clearchat", new ClearChatCommand(getConfig()));
	}

}
