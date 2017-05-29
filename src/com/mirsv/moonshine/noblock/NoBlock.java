package com.mirsv.moonshine.noblock;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.mirsv.MirPlugin;

public class NoBlock extends MirPlugin implements Listener{
	public static List < Integer > blocks = new ArrayList < Integer > ();

	@SuppressWarnings("unchecked")
	public NoBlock(String pluginname) {
		super(pluginname);

		blocks.add(52);

		getConfig().addDefault("NoBlock.list", blocks);
		getConfig().options().copyDefaults(true);
		saveConfig();

		blocks.clear();
		blocks = (List < Integer > ) getConfig().getList("NoBlock.list");

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
					p.sendMessage("§c[미르서버] 이 아이템은 놓을 수 없습니다.");
				}
			}
		}
	}
}