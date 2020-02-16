package com.mirsv.function.list.daybreak.achievements.list.base;

import com.mirsv.function.list.daybreak.achievements.Achievement;
import com.mirsv.function.list.daybreak.achievements.reward.MoneyReward;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class AchIsThisPossible extends Achievement implements Listener {

	public AchIsThisPossible() {
		super("isthispossible", "이게 돼?", Type.CHALLENGE, new String[]{
				"위더를 나무 검으로 죽이세요."
		}, new MoneyReward(5000));
	}

	@EventHandler
	private void onEntityDeathEvent(EntityDeathEvent e) {
		LivingEntity entity = e.getEntity();
		if (entity instanceof Wither && entity.getKiller() != null) {
			ItemStack stack = entity.getKiller().getInventory().getItemInMainHand();
			if (stack != null && stack.getType() == Material.WOOD_SWORD) {
				achieve(entity.getKiller());
			}
		}
	}

}
