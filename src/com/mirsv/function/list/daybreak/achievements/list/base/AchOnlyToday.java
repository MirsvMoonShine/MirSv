package com.mirsv.function.list.daybreak.achievements.list.base;

import com.mirsv.function.list.daybreak.achievements.Achievement;
import com.mirsv.function.list.daybreak.achievements.reward.MoneyReward;
import org.bukkit.Material;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class AchOnlyToday extends Achievement implements Listener {

	public AchOnlyToday() {
		super("onlytoday", "오늘만 사는 사람", Type.CHALLENGE, new String[]{
				"무장하지 않은 상태로 엔더맨을 도발하세요."
		}, new MoneyReward(35));
	}

	@EventHandler
	private void onEntityTarget(EntityTargetLivingEntityEvent e) {
		if (e.getTarget() instanceof Player && e.getEntity() instanceof Enderman) {
			Player player = (Player) e.getTarget();
			if (!hasAchieved(getJson(player))) {
				PlayerInventory inventory = player.getInventory();
				if (isEmpty(inventory.getHelmet()) && isEmpty(inventory.getChestplate()) && isEmpty(inventory.getLeggings()) && isEmpty(inventory.getBoots()) && isEmpty(inventory.getItemInMainHand()) && isEmpty(inventory.getItemInOffHand())) {
					achieve(player);
				}
			}
		}
	}

	private static boolean isEmpty(ItemStack stack) {
		return stack == null || stack.getType() == Material.AIR;
	}

}
