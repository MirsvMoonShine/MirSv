package com.mirsv.function.list.daybreak.firework;

import com.mirsv.function.AbstractFunction;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class NoFireworkDamage extends AbstractFunction implements Listener {

	public NoFireworkDamage() {
		super("폭죽 대미지 무시", "1.0.0", "모든 폭죽의 대미지를 제거합니다.");
	}

	@Override
	protected void onEnable() {
		registerListener(this);
	}

	@Override
	protected void onDisable() {}

	@EventHandler
	private void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Firework) {
			e.setCancelled(true);
		}
	}

}
