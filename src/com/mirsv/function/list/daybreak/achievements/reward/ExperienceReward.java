package com.mirsv.function.list.daybreak.achievements.reward;

import com.mirsv.Mirsv;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ExperienceReward extends Reward {

	private final Type type;
	private final int amount;

	public ExperienceReward(Type type, int amount) {
		this.type = type;
		this.amount = amount;
	}

	@Override
	public void reward(Player player) {
		if (type == Type.EXP) {
			player.giveExp(amount);
			player.sendMessage(ChatColor.DARK_GREEN + "+ " + ChatColor.WHITE + "경험치를 " + amount + ChatColor.GREEN + "만큼 얻으셨습니다.");
		} else {
			player.giveExpLevels(amount);
			player.sendMessage(ChatColor.DARK_GREEN + "+ " + ChatColor.WHITE + "경험치를 " + amount + "레벨" + ChatColor.GREEN + "만큼 얻으셨습니다.");
		}
	}

	@Override
	public String toString() {
		return ChatColor.DARK_AQUA + "● " + ChatColor.AQUA + amount + (type == Type.EXP ? " 경험치" : " 경험치 레벨");
	}

	public enum Type {
		EXP, LEVEL
	}

}
