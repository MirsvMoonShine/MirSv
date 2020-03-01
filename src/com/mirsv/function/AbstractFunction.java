package com.mirsv.function;

import com.mirsv.Mirsv;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

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

	private boolean enabled = false;

	/**
	 * 기능의 활성 여부를 반환합니다.
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * 기능을 활성화합니다.
	 */
	public void Enable() {
		if (!isEnabled()) {
			this.enabled = true;
			onEnable();
		}
	}

	abstract protected void onEnable();

	/**
	 * 기능을 비활성화합니다.
	 */
	public void Disable() {
		if (isEnabled()) {
			this.enabled = false;
			onDisable();

			for (Listener listener : listeners) {
				HandlerList.unregisterAll(listener);
			}

			for (String label : commands) {
				Bukkit.getPluginCommand(label).setExecutor(DisabledCommand.instance);
				Bukkit.getPluginCommand(label).setTabCompleter(DisabledTabCompleter.instance);
			}
		}
	}

	abstract protected void onDisable();

	private List<Listener> listeners = new ArrayList<>();

	/**
	 * 기능에 Listener를 추가합니다.
	 *
	 * @param listener 추가할 Listener
	 */
	protected void registerListener(Listener listener) {
		if (isEnabled()) {
			Bukkit.getPluginManager().registerEvents(listener, Mirsv.getPlugin());
			if (!listeners.contains(listener)) listeners.add(listener);
		}
	}

	private List<String> commands = new ArrayList<String>();

	/**
	 * 기능에 명령어를 추기합니다.
	 *
	 * @param label    명령어 앞
	 * @param executor 명령어를 인식할 command executor
	 */
	protected void registerCommand(String label, CommandExecutor executor) {
		if (isEnabled()) {
			Bukkit.getPluginCommand(label).setExecutor(executor);
			if (!commands.contains(label)) commands.add(label);
		}
	}

	/**
	 * 기능에 명령어를 추기합니다.
	 *
	 * @param label     명령어 앞
	 * @param executor  명령어를 인식할 command executor
	 * @param completer tab키로 명령어를 인식해줄 tab completer
	 */
	protected void registerCommand(String label, CommandExecutor executor, TabCompleter completer) {
		if (isEnabled()) {
			Bukkit.getPluginCommand(label).setExecutor(executor);
			Bukkit.getPluginCommand(label).setTabCompleter(completer);
			if (!commands.contains(label)) commands.add(label);
		}
	}

	private static class DisabledCommand implements CommandExecutor {

		private static final DisabledCommand instance = new DisabledCommand();

		@Override
		public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
			return false;
		}

	}

	private static class DisabledTabCompleter implements TabCompleter {

		private static final DisabledTabCompleter instance = new DisabledTabCompleter();

		@Override
		public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
			return null;
		}

	}
}