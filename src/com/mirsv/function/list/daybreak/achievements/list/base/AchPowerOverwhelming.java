package com.mirsv.function.list.daybreak.achievements.list.base;

import com.mirsv.function.list.daybreak.achievements.Achievement;
import com.mirsv.function.list.daybreak.achievements.reward.MoneyReward;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class AchPowerOverwhelming extends Achievement implements Listener {

	public AchPowerOverwhelming() {
		super("poweroverwhelming", "압도적인 힘으로", Type.CHALLENGE, new String[]{
				"좀비를 단 한 방에 때려눕히세요."
		}, new MoneyReward(400));
	}

	@EventHandler
	private void onEntityDeath(EntityDeathEvent e) {
		LivingEntity entity = e.getEntity();
		if (entity instanceof Zombie && entity.getKiller() != null && entity.getLastDamage() >= entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) {
			EntityDamageEvent event = entity.getLastDamageCause();
			if (event instanceof EntityDamageByEntityEvent && entity.getKiller().equals(((EntityDamageByEntityEvent) event).getDamager())) {
				achieve(entity.getKiller());
			}
		}
	}

}
