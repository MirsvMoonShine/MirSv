package com.mirsv.function.list.daybreak;

import com.mirsv.Mirsv;
import com.mirsv.function.AbstractFunction;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Calendar;

public class NoHungerAtNight extends AbstractFunction implements Listener {

	public NoHungerAtNight() {
		super("저녁 배고픔 무제한", "1.0.0", "새벽 0시부터 오전 7시까지 배고픔이 닳지 않습니다.");
	}

	@Override
	protected void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, Mirsv.getPlugin());
	}

	@Override
	protected void onDisable() {}

	private static final Calendar calendar = Calendar.getInstance();

	@EventHandler
	private void onFoodLevelChange(FoodLevelChangeEvent e) {
		calendar.setTimeInMillis(System.currentTimeMillis());
		if (calendar.get(Calendar.HOUR_OF_DAY) < 7) {
			if (e.getFoodLevel() < 19) e.setFoodLevel(19);
		}
	}

}
