package com.mirsv.function.list.daybreak.achievements.list.base;

import com.mirsv.function.list.daybreak.achievements.Achievement;
import com.mirsv.function.list.daybreak.achievements.reward.MoneyReward;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class AchJumpIntoOcean extends Achievement implements Listener {

	AchJumpIntoOcean() {
		super("jumpintoocean", "물결 속으로", Type.CHALLENGE, new String[]{
				"출렁이는 바닷물 속으로 뛰어드세요."
		}, new MoneyReward(100));
	}

	@EventHandler
	private void onMove(PlayerMoveEvent e) {
		if (!hasAchieved(getJson(e.getPlayer()))) {
			Material type = e.getTo().getBlock().getType();
			Biome biome = e.getTo().getBlock().getBiome();
			if (type == Material.WATER || type == Material.STATIONARY_WATER && (biome == Biome.OCEAN || biome == Biome.DEEP_OCEAN || biome == Biome.FROZEN_OCEAN)) {
				achieve(e.getPlayer());
			}
		}
	}

}
