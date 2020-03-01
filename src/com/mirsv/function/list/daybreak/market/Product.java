package com.mirsv.function.list.daybreak.market;

import com.google.gson.JsonObject;
import com.mirsv.Mirsv;
import org.bukkit.Material;

import java.util.EnumMap;
import java.util.Map;

public class Product {

	private static final Map<Material, Product> products = new EnumMap<>(Material.class);

	static {
		JsonObject json = Mirsv.database.getJson();
		if (!json.has("market")) json.add("market", new JsonObject());
		json = json.get("market").getAsJsonObject();
		if (!json.has("products")) json.add("products", new JsonObject());
	}

	public static Product getProduct(Material type) {
		return products.get(type);
	}

	private final JsonObject database;
	private final String name;
	private final Material type;
	private final double defaultPrice;

	Product(String name, Material type, double defaultPrice) {
		if (products.containsKey(type)) throw new IllegalArgumentException("이미 존재하는 타입의 상점입니다: " + type.name());
		products.put(type, this);
		JsonObject database = Mirsv.database.getJson().getAsJsonObject("market").getAsJsonObject("products");
		if (!database.has(name)) database.add(name, new JsonObject());
		this.database = database.getAsJsonObject(name);
		this.name = name;
		this.type = type;
		this.defaultPrice = defaultPrice;
	}

	public void addDemand(int count) {
		database.addProperty("demand", getDemand() + count);
	}

	public int getDemand() {
		if (!database.has("demand")) database.addProperty("demand", 1400);
		return database.get("demand").getAsInt();
	}

	public void addSupply(int count) {
		database.addProperty("supply", getSupply() + count);
	}

	public int getSupply() {
		if (!database.has("supply")) database.addProperty("supply", 1400);
		return database.get("supply").getAsInt();
	}

	public String getName() {
		return name;
	}

	public double getPrice(TransactionType type) {
		return Math.round(type.apply(defaultPrice * (getDemand() / (double) getSupply())) * 1000.0) / 1000.0;
	}

	public Material getType() {
		return type;
	}

	public enum TransactionType {

		BUYING {
			@Override
			double apply(double price) {
				return price + (price / 5);
			}
		},
		SELLING{
			@Override
			double apply(double price) {
				return price - (price / 5);
			}
		},
		NORMAL{
			@Override
			double apply(double price) {
				return price;
			}
		};

		abstract double apply(double price);

	}

}
