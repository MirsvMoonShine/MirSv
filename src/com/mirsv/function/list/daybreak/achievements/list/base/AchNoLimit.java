package com.mirsv.function.list.daybreak.achievements.list.base;

import com.mirsv.function.list.daybreak.achievements.Achievement;
import com.mirsv.function.list.daybreak.achievements.reward.ExperienceReward;
import com.mirsv.function.list.daybreak.achievements.reward.MoneyReward;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.PlayerInventory;

public class AchNoLimit extends Achievement implements Listener {

	AchNoLimit() {
		super("nolimit", "한계 돌파", Type.CHALLENGE, new String[]{
				"겉날개를 이용해 y 좌표 2000을 돌파하세요."
		}, new MoneyReward(450), new ExperienceReward(ExperienceReward.Type.LEVEL, 3));
	}

	@EventHandler
	private void onMove(PlayerMoveEvent e) {
		if (!hasAchieved(getJson(e.getPlayer()))) {
			PlayerInventory inventory = e.getPlayer().getInventory();
			if (e.getFrom().getY() < 2000 && e.getTo().getY() >= 2000 && (inventory.getChestplate() != null && inventory.getChestplate().getType() == Material.ELYTRA)) {
				achieve(e.getPlayer());
			}
		}
	}

}
