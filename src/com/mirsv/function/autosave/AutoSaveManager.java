package com.mirsv.function.autosave;

import java.util.ArrayList;
import java.util.List;

import com.mirsv.util.Messager;
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
		Messager.sendMessage("�ڵ� ������...");
		for(AutoSave as : autoSaves) {
			as.Save();
		}
		Messager.sendMessage("�ڵ� ���� �Ϸ�.");
	}

	@Override
	public void onDisable() {
		for(AutoSave as : autoSaves) {
			as.Save();
		}
	}
	
}
