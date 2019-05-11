package com.mirsv.util.thread;

import org.bukkit.Bukkit;

import com.mirsv.Mirsv;
import com.mirsv.function.AutoSave.AutoSaveManager;

public class ThreadUtil {

	private static final ThreadRegistration[] registrations = {
			new ThreadUtil.ThreadRegistration(new AutoSaveManager(), 12000)
	};
	
	public static void onEnable() {
		for(ThreadRegistration registration : registrations) {
			Bukkit.getScheduler().scheduleSyncRepeatingTask(Mirsv.getPlugin(), registration.getThread(), 0, registration.getTick());
		}
	}
	
	public static void onDisable() {
		for(ThreadRegistration registration : registrations) {
			registration.getThread().onDisable();
		}
	}
	
	private static class ThreadRegistration {

		private final AbstractThread thread;
		private final int tick;
		
		private ThreadRegistration(AbstractThread thread, int tick) {
			this.thread = thread;
			this.tick = tick;
		}

		private AbstractThread getThread() {
			return thread;
		}

		private int getTick() {
			return tick;
		}
		
	}
	
}
