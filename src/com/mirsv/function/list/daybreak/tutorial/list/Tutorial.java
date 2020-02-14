package com.mirsv.function.list.daybreak.tutorial.list;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class Tutorial {

	private static final Map<Player, Tutorial> tutorialMap = new HashMap<>();

	public static boolean isTutorial(Player player) {
		return tutorialMap.containsKey(player);
	}

	public static void start(Player player) {
		if (!isTutorial(player)) {
			Tutorial tutorial = new Tutorial(player);
			tutorialMap.put(player, tutorial);
			tutorial.next();
		}
	}

	public static void stop(Player player) {
		if (isTutorial(player)) {
			tutorialMap.get(player).stop();
		}
	}

	public static void toggle(Player player) {
		if (isTutorial(player)) {
			stop(player);
		} else {
			start(player);
		}
	}

	final Player player;
	private TutorialSequence current;

	private Tutorial(Player player) {
		this.player = player;
		tutorialMap.put(player, this);
	}

	void next() {
		if (current == null) {
			this.current = new SpawnShow(this);
			current.start();
		} else {
			current.reset();
			this.current = current.next();
			if (current == null) {
				tutorialMap.remove(player);
			} else {
				current.start();
			}
		}
	}

	private void stop() {
		if (current == null) {
			current.stop();
			tutorialMap.remove(player);
		}
	}

	private static final String sneakMessage = ChatColor.translateAlternateColorCodes('&', "&f다음으로 넘어가려면 &c웅크리세요&f.");

	public abstract class TutorialSequence {

		protected Tutorial tutorial;

		TutorialSequence() {
			this.tutorial = Tutorial.this;
		}

		public abstract void start();
		public abstract void stop();
		public abstract void reset();
		public abstract TutorialSequence next();

		protected void sendTitle(String title) {
			tutorial.player.sendTitle(ChatColor.translateAlternateColorCodes('&', title), sneakMessage, 0, 20000, 0);
		}

	}

}
