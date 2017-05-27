package com.mirsv.catnote.AdvanceClearEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftItem;
import org.bukkit.entity.Entity;

public class ClearEntity extends TimerTask {

	@Override
	public void run() {
		String prefix = ChatColor.GOLD + "[" + ChatColor.GREEN + "미르서버" + ChatColor.GOLD + "] " + ChatColor.RESET;
	    int N_RemovedEntity = 0; int N_Entity = 0;

	    List<Entity> myEntityList = new ArrayList<Entity>();
	    List<Entity> OldEntityList = AdvanceClearEntity.getOldEntityList();
	    for (World world : Bukkit.getWorlds()) {
	      List<Entity> EntityList = world.getEntities();
	      for (Entity entity : EntityList) { if (!(entity instanceof CraftItem)) continue; myEntityList.add(entity); }
	    }
	    for (Entity entity : myEntityList) {
	      boolean isRemoved = false;
	      for (Entity oldentity : OldEntityList) {
	        if (entity.getEntityId() == oldentity.getEntityId()) {
	          entity.remove();
	          isRemoved = true;
	          break;
	        }
	      }
	      if (isRemoved) N_RemovedEntity++;
	      N_Entity++;
	    }
	    Bukkit.broadcastMessage(prefix + ChatColor.GREEN + "위이이잉 " + N_Entity + "개의 아이템 중 " + N_RemovedEntity + "개 삭제 완료!");
	    AdvanceClearEntity.setOldEntityList(myEntityList);
	}

}
