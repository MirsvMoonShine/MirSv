package com.mirsv.function.list.daybreak.achievements.list.base;

import com.mirsv.function.list.daybreak.achievements.Achievement;
import com.mirsv.function.list.daybreak.achievements.reward.ExperienceReward;
import com.mirsv.function.list.daybreak.achievements.reward.MoneyReward;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AchTowerOfBabel extends Achievement implements Listener {

	AchTowerOfBabel() {
		super("towerofbabel", "바벨탑", Type.CHALLENGE, new String[] {
				"50개 이상의 블록을 쌓아 세계의 가장 높은 위치에 도달하세요."
		}, new MoneyReward(250), new ExperienceReward(ExperienceReward.Type.LEVEL, 3));
	}

	private final Map<UUID, Tower> towerMap = new HashMap<>();

	@EventHandler
	private void onBlockPlace(BlockPlaceEvent e) {
		UUID uuid = e.getPlayer().getUniqueId();
		if (towerMap.containsKey(uuid)) {
			if (towerMap.get(uuid).isTower(e.getBlock()) && e.getPlayer().getLocation().getY() > 256) {
				achieve(e.getPlayer());
			}
		} else {
			towerMap.put(uuid, new Tower(e.getBlock()));
		}
	}

	@EventHandler
	private void onQuit(PlayerQuitEvent e) {
		towerMap.remove(e.getPlayer().getUniqueId());
	}

}

class Tower {

	private Block lastBlock;
	private int towerHeight = 1;

	public Tower(Block startBlock) {
		this.lastBlock = startBlock;
	}

	public boolean isTower(Block block) {
		if (lastBlock.getRelative(BlockFace.UP).equals(block) && isEmptyNearby(block)) towerHeight += 1;
		else this.towerHeight = 1;
		this.lastBlock = block;
		return this.towerHeight > 9;
	}

	private boolean isEmptyNearby(Block block) {
		return block.getRelative(BlockFace.EAST).isEmpty() && block.getRelative(BlockFace.WEST).isEmpty() && block.getRelative(BlockFace.SOUTH).isEmpty() && block.getRelative(BlockFace.NORTH).isEmpty();
	}

}