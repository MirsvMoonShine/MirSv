package com.mirsv.catnote.AdvanceClearEntity;

import com.mirsv.MirPlugin;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Entity;
import java.util.Timer;

public class AdvanceClearEntity extends MirPlugin{
	private static List<Entity> OldEntityList = new ArrayList<Entity>();
	public int sec;

	public AdvanceClearEntity(String pluginname) {
		super(pluginname);
		
		int sec = getConfig().getInt("AdvanceClearEntity.Second");
	    if (sec == 0) {
	      sec = 600;
	      getConfig().set("AdvanceClearEntity.Second", Integer.valueOf(600));
	      saveConfig();
	    }
	    Timer timer = new Timer();
	    timer.schedule(new ClearEntity(), 0L, sec * 1000);
	}
	
	public static List<Entity> getOldEntityList(){
		return OldEntityList;
	}
	
	public static void setOldEntityList(List<Entity> list) {
		OldEntityList = list;
	}
}
