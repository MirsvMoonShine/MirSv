package com.mirsv.function.list.daybreak.achievements.reward;

import com.mirsv.Mirsv;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MoneyReward extends Reward {
	private final int amount;

	public MoneyReward(int amount) {
		this.amount = amount;
	}

	@Override
	public void reward(Player player) {
		Mirsv.getPlugin().getEconomy().depositPlayer(player, amount);
		player.sendMessage(ChatColor.DARK_GREEN + "+ " + ChatColor.WHITE + amount + "원" + ChatColor.GREEN + "이 귀하의 계좌에 추가되었습니다.");
	}

	@Override
	public String toString() {
		return ChatColor.GOLD + "● " + ChatColor.YELLOW + amount + "원";
	}

}
