package com.mirsv.catnote;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import com.mirsv.MirPlugin;

public class BlockSpawner extends MirPlugin implements Listener {
	public BlockSpawner() {
		getListener(this);
	}
	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		if(getConfig().getBoolean("enable.BlockSpawner", true)) {
			SpawnReason Reason = event.getSpawnReason();
			EntityType Type = event.getEntityType();
			if(Reason == SpawnReason.SPAWNER && Type != EntityType.BLAZE) event.setCancelled(true);
		}
	}
}