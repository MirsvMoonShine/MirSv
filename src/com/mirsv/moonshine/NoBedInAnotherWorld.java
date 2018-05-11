package com.mirsv.moonshine;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.mirsv.MirPlugin;

public class NoBedInAnotherWorld extends MirPlugin implements Listener{
	String prefix = ChatColor.GOLD + "[" + ChatColor.GREEN + "�̸�����" + ChatColor.GOLD + "] " + ChatColor.RESET;
	
	public NoBedInAnotherWorld(){
		getListener(this);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerBlockPlaceEvent(BlockPlaceEvent e) {
		if (getConfig().getBoolean("enable.NoBedInAnotherWorld", true)) {
			Player p = e.getPlayer();
			if (e.getBlock().getType() == Material.BED_BLOCK) {
				if (p.getWorld() == Bukkit.getWorld("world_nether") || p.getWorld() == Bukkit.getWorld("world_the_end")){
					e.setCancelled(true);
					p.sendMessage(prefix+"�����̳� �������忡���� ħ�븦 ��ġ�� �� �����ϴ�.");
				}
			}
		}
	}
}
