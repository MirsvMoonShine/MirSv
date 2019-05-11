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
		super("침대제한", "1.0", "네더월드와 엔더월드에서 침대를 사용할 수 없도록 막습니다.");
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
				Messager.sendMessage(p, Messager.getPrefix() + "침대 설치가 금지된 월드입니다.");
			}
		}
	}

}
