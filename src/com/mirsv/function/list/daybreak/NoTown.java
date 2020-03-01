package com.mirsv.function.list.daybreak;

import com.mirsv.function.AbstractFunction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class NoTown extends AbstractFunction implements Listener {

	public NoTown() {
		super("마을 생성 금지", "1.0.0");
	}

	@Override
	protected void onEnable() {
		registerListener(this);
	}

	@Override
	protected void onDisable() {}

	private final World build = Bukkit.getWorld("world");
	private final String plugins = ChatColor.translateAlternateColorCodes('&', "&fPlugins (3): &aWelcome&f, &ato&f, &aMirsv");

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	private void onCommand(PlayerCommandPreprocessEvent e) {
		String[] split = e.getMessage().split(" ");
		if (split.length >= 2 && (split[0].equalsIgnoreCase("/t") || split[0].equalsIgnoreCase("/town") || split[0].equals("/마을")) && (split[1].equalsIgnoreCase("create") || split[1].equalsIgnoreCase("new") || split[1].equals("설립")) && !e.getPlayer().getWorld().equals(build)) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(ChatColor.RED + "건축 월드 이외의 월드에서 마을을 만드실 수 없습니다.");
			e.getPlayer().sendMessage(ChatColor.RED + "건축 월드로 이동하려면 '/건축' 명령어를 이용하세요.");
		} else if (split[0].equalsIgnoreCase("/pl") || split[0].equalsIgnoreCase("/plugins")) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(plugins);
		}
	}

}
