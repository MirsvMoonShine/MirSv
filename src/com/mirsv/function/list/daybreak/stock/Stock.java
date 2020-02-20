package com.mirsv.function.list.daybreak.stock;

import org.bukkit.event.Listener;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Stock implements Listener {

	public interface Observer {
		void onUpdate();
	}

	private static final Map<String, Stock> stocks = new HashMap<>();

	private final String name, code;
	private int price;
	private final Set<Observer> observers = new HashSet<>();
	private final Runnable updater = new Runnable() {
		@Override
		public void run() {
			if (update()) {
				for (Observer observer : observers) observer.onUpdate();
			}
		}
	};

	private Stock(String name, String code) {
		this.name = name;
		this.code = code;
		if (!stocks.containsKey(name)) {
			stocks.put(name, this);
		} else {
			throw new IllegalArgumentException("이미 존재하는 주식 이름입니다: " + name);
		}
		if (!update()) {
			throw new RuntimeException(name + " 주식의 기본값 설정 중 오류가 발생하였습니다.");
		}
		Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(updater, 4, 4, TimeUnit.SECONDS);
	}

	public void attachObserver(Observer observer) {
		observers.add(observer);
	}

	public void detachObserver(Observer observer) {
		observers.remove(observer);
	}

	public boolean update() {
		try {
			int price = Integer.parseInt(Jsoup.connect("https://finance.naver.com/item/main.nhn?code=" + code).get().getElementsByClass("blind").get(16).text().replace(",", ""));
			if (price != this.price) {
				this.price = price;
				return true;
			}
		} catch (IOException ignored) {}
		return false;
	}

}
