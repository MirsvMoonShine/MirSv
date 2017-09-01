package com.mirsv.catnote;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.mirsv.MirPlugin;

public class CasinoSecurity extends MirPlugin implements Listener {
	public CasinoSecurity() {
		getListener(this);
	}
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if(getConfig().getBoolean("enable.CasinoSecurity", true)) {
			if(e.getPlayer().getLocation().getWorld().getName().equalsIgnoreCase("spawn") && !e.getPlayer().isOp()) {
				if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
					if(e.getClickedBlock().getType().equals(Material.HOPPER) || e.getClickedBlock().getType().equals(Material.DISPENSER)) {
						e.setCancelled(true);
						e.getPlayer().sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "이제 그만 하시죠.");
					}
				}
			}
		}
	}
}
