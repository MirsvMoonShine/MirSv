package com.mirsv.catnote;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import com.mirsv.MirPlugin;

public class DisableCraft extends MirPlugin implements Listener {
	String prefix = ChatColor.GOLD + "[" + ChatColor.GREEN + "미르서버" + ChatColor.GOLD + "] " + ChatColor.RESET;
	public DisableCraft() {
		getListener(this);
	}
	@EventHandler
	public void craftItem(PrepareItemCraftEvent event) {
		if(getConfig().getBoolean("enable.DisableCraft", true)) {
			Material itemType = event.getRecipe().getResult().getType();
			if(itemType == Material.HOPPER_MINECART) {
				event.getInventory().setResult(new ItemStack(Material.AIR));
				for(HumanEntity he: event.getViewers()) if(he instanceof Player) ((Player) he).sendMessage(prefix + ChatColor.RED + "깔대기 카트는 금지된 아이템입니다!");
	 		}
		}
	}
}