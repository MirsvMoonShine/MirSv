package com.mirsv.function.autosave;

import com.mirsv.util.Messager;
import com.mirsv.util.thread.AbstractThread;

import java.util.ArrayList;
import java.util.List;

public class AutoSaveManager extends AbstractThread {

	private static List<AutoSave> autoSaves = new ArrayList<>();
	
	public static void registerAutoSave(AutoSave autoSave) {
		if(!autoSaves.contains(autoSave)) {
			autoSaves.add(autoSave);
		}
	}
	
	@Override
	public void run() {
		Messager.sendMessage("자동 저장중...");
		for(AutoSave as : autoSaves) {
			as.Save();
		}
		Messager.sendMessage("자동 저장 완료.");
	}

	@Override
	public void onDisable() {
		for(AutoSave as : autoSaves) {
			as.Save();
		}
	}
	
}
