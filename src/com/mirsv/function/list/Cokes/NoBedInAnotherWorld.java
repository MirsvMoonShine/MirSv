package com.mirsv.function.list.Cokes;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.mirsv.function.AbstractFunction;
import com.mirsv.util.Messager;

public class NoBedInAnotherWorld extends AbstractFunction implements Listener {

	public NoBedInAnotherWorld() {
		super("ħ������", "1.0", "�״������ �������忡�� ħ�븦 ����� �� ������ �����ϴ�.");
	}

	@Override
	protected void onEnable() {
		registerListener(this);
	}

	@Override
	protected void onDisable() {}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerBlockPlaceEvent(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if (Arrays.asList(e.getBlock().getType().toString().split("_")).contains("BED")) {
			if (p.getWorld().getEnvironment().equals(Environment.NETHER) || p.getWorld().getEnvironment().equals(Environment.THE_END)){
				e.setCancelled(true);
				Messager.sendMessage(p, Messager.getPrefix() + "ħ�� ��ġ�� ������ �����Դϴ�.");
			}
		}
	}

}
