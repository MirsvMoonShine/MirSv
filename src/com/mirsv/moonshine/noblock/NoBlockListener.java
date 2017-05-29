package com.mirsv.moonshine.noblock;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class NoBlockListener implements Listener {
	private final FileConfiguration p;

	public NoBlockListener(FileConfiguration fileConfiguration) {
		this.p = fileConfiguration;
	}

	@EventHandler
	@SuppressWarnings("deprecation")
	public void onPlayerBlockPlaceEvent(BlockPlaceEvent e) {
		if (this.p.getBoolean("enable.NoBlock", true)) {
			List < Integer > blocks = NoBlock.blocks;
			Player p = e.getPlayer();
			for (int block: blocks) {
				if (e.getBlock().getTypeId() == block) {
					e.setCancelled(true);
					p.sendMessage("§c[미르서버] 이 아이템은 놓을 수 없습니다.");
				}
			}
		}
	}
}