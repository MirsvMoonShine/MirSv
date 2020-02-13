package com.mirsv.function.list.daybreak.skill;

import com.mirsv.function.AbstractFunction;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SkillManager extends AbstractFunction implements Listener {

	public SkillManager() {
		super("스킬매니저", "1.0.0", "밀치기 막대기 스킬과 같은 스킬을 관리합니다.");
	}

	@Override
	protected void onEnable() {
		registerListener(this);
	}

	@Override
	protected void onDisable() {

	}

	@EventHandler
	private void onInteract(PlayerInteractEvent e) {
		if (e.getMaterial() == Material.BLAZE_ROD && e.getItem().hasItemMeta() && e.getItem().getItemMeta().hasDisplayName() && e.getItem().getItemMeta().getDisplayName().equals("§a§l밀치기§a➠")) {
			Player p = e.getPlayer();
			if (p.isOp()) {
				p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER, 8, 1);
				if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					for (Entity entity : p.getNearbyEntities(5, 5, 5)) {
						entity.setVelocity(entity.getLocation().toVector().subtract(p.getLocation().toVector()).setY(2));
						if (entity instanceof Player) ((Player) entity).playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER, 8, 1);
					}
				} else if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
					for (Entity entity : p.getNearbyEntities(5, 5, 5)) {
						entity.setVelocity(p.getLocation().toVector().subtract(entity.getLocation().toVector()).multiply(0.4));
						if (entity instanceof Player) ((Player) entity).playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER, 8, 1);
					}
				}
			}
		}
	}

}
