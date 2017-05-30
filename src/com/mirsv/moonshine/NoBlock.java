package com.mirsv.moonshine;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.mirsv.MirPlugin;

public class NoBlock extends MirPlugin implements Listener{
	public List < Integer > blocks = new ArrayList < Integer > ();
	String prefix = ChatColor.GOLD + "[" + ChatColor.GREEN + "미르서버" + ChatColor.GOLD + "] " + ChatColor.RESET;

	public NoBlock() {
		blocks = getConfig().getIntegerList("NoBlock.list");
		
		if (blocks.size() < 1){
			blocks.add(52);

			getConfig().addDefault("NoBlock.list", blocks);
			getConfig().options().copyDefaults(true);
			saveConfig();
		}

		getListener(this);
	}
	
	@EventHandler
	@SuppressWarnings("deprecation")
	public void onPlayerBlockPlaceEvent(BlockPlaceEvent e) {
		if (getConfig().getBoolean("enable.NoBlock", true)) {
			Player p = e.getPlayer();
			for (int block: blocks) {
				if (e.getBlock().getTypeId() == block) {
					e.setCancelled(true);
					p.sendMessage(prefix+"§c이 아이템은 놓을 수 없습니다.");
				}
			}
		}
	}
}