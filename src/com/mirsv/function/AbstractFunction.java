package com.mirsv.function;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import com.mirsv.Mirsv;
import com.mirsv.util.Messager;

abstract public class AbstractFunction {
	
	private final String name;
	private final String version;
	private final String[] explain;
	
	protected AbstractFunction(String name, String version, String... explain) {
		this.name = name;
		this.version = version;
		this.explain = explain;
	}

	public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}

	public String[] getExplain() {
		return explain;
	}

	private boolean Enabled = false;

	/**
	 * 기능의 활성 여부를 반환합니다.
	 */
	public boolean isEnabled() {
		return Enabled;
	}

	/**
	 * 기능을 활성화합니다.
	 */
	public void Enable() {
		if(!isEnabled()) {
			this.Enabled = true;
			onEnable();
		}
	}
	
	abstract protected void onEnable();

	/**
	 * 기능을 비활성화합니다.
	 */
	public void Disable() {
		if(isEnabled()) {
			this.Enabled = false;
			onDisable();
			
			for(Listener listener : listeners) {
				HandlerList.unregisterAll(listener);
			}
			
			for(String label : commands) {
				Bukkit.getPluginCommand(label).setExecutor(new DisabledCommand());
			}
		}
	}
	
	abstract protected void onDisable();

	private List<Listener> listeners = new ArrayList<Listener>();
	
	protected void registerListener(Listener listener) {
		if(isEnabled()) {
			Bukkit.getPluginManager().registerEvents(listener, Mirsv.getPlugin());
			if(!listeners.contains(listener)) listeners.add(listener);
		}
	}

	private List<String> commands = new ArrayList<String>();
	
	protected void registerCommand(String label, CommandExecutor executor) {
		if(isEnabled()) {
			Bukkit.getPluginCommand(label).setExecutor(executor);
			if(!commands.contains(label)) commands.add(label);
		}
	}
	
	private static class DisabledCommand implements CommandExecutor {

		@Override
		public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
			return false;
		}
		
	}
	
}