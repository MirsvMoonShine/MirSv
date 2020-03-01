package com.mirsv.event;

import com.mirsv.Mirsv;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MirsvPluginDisableEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	private final Mirsv plugin;

	public MirsvPluginDisableEvent(Mirsv plugin) {
		this.plugin = plugin;
	}

	public Mirsv getPlugin() {
		return plugin;
	}

}
