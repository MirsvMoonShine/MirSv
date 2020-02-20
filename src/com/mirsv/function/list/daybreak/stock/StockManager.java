package com.mirsv.function.list.daybreak.stock;

import com.mirsv.Mirsv;
import com.mirsv.function.AbstractFunction;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class StockManager extends AbstractFunction implements CommandExecutor {

	public StockManager() {
		super("주식", "1.0.0");
	}

	@Override
	protected void onEnable() {
		registerCommand("주식", this);
	}

	public static void showStock(CommandSender sender, String code) {
		CompletableFuture.runAsync(new Runnable() {
			@Override
			public void run() {
				try {
					Document document = Jsoup.connect("https://finance.naver.com/item/main.nhn?code=" + code).get();
					String company = document.getElementsByAttributeValue("href", "#").get(2).text();
					int stock = Integer.parseInt(document.getElementsByClass("blind").get(16).text().replace(",", ""));
					new BukkitRunnable() {
						@Override
						public void run() {
							sender.sendMessage(company + ": " + stock + "원");
						}
					}.runTask(Mirsv.getPlugin());
				} catch (IOException ignored) {
					new BukkitRunnable() {
						@Override
						public void run() {
							sender.sendMessage(ChatColor.RED + "오류");
						}
					}.runTask(Mirsv.getPlugin());
				}
			}
		});
	}

	@Override
	protected void onDisable() {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
		if (sender.isOp() && args.length >= 1) {
			showStock(sender, args[0]);
		}
		return true;
	}

}
