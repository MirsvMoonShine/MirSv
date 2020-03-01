package com.mirsv.util.database;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class JsonDatabase {

	private static final JsonParser parser = new JsonParser();

	private final Gson gson;
	private final File file;
	private final JsonObject json;

	public JsonDatabase(File file, Gson gson) {
		this.gson = gson;
		this.file = file;
		JsonObject json;
		try {
			json = parser.parse(new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))).getAsJsonObject();
		} catch (Exception e) {
			json = new JsonObject();
		}
		this.json = json.isJsonNull() ? new JsonObject() : json;
	}

	public JsonDatabase(File file) {
		this(file, new Gson());
	}

	public JsonObject getJson() {
		return json;
	}

	public boolean save() {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
			gson.toJson(json, writer);
			writer.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

}
