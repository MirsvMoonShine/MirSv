package com.mirsv.catnote.AdvanceClearEntity;

import com.mirsv.MirPlugin;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Entity;
import java.util.Timer;

public class AdvanceClearEntity extends MirPlugin{
	private static List<Entity> OldEntityList = new ArrayList<Entity>();
	public AdvanceClearEntity(String pluginname) {
		super(pluginname);
	    Timer timer = new Timer();
	    timer.schedule(new ClearEntity(), 10000, 120000);
	}
	
	public static List<Entity> getOldEntityList(){
		return OldEntityList;
	}
	
	public static void setOldEntityList(List<Entity> list) {
		OldEntityList = list;
	}
}
