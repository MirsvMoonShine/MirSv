package com.mirsv.function.AutoSave;

import java.util.ArrayList;
import java.util.List;

import com.mirsv.util.thread.AbstractThread;

public class AutoSaveManager extends AbstractThread {

	private static List<AutoSave> autoSaves = new ArrayList<AutoSave>();
	
	public static void registerAutoSave(AutoSave autoSave) {
		if(!autoSaves.contains(autoSave)) {
			autoSaves.add(autoSave);
		}
	}
	
	@Override
	public void run() {
		for(AutoSave as : autoSaves) {
			as.Save();
		}
	}

	@Override
	public void onDisable() {
		for(AutoSave as : autoSaves) {
			as.Save();
		}
	}
	
}
