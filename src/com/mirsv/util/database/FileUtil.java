package com.mirsv.util.database;

import com.mirsv.util.Messager;
import org.bukkit.ChatColor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileUtil {

	private FileUtil() {
	}

	private static File getMainDirectory() {
		return new File("plugins/Mirsv");
	}

	private static boolean createDataFolder() {
		File directory = getMainDirectory();
		if (!directory.exists()) {
			return directory.mkdirs();
		} else {
			return false;
		}
	}

	public static File newFile(String file) {

		File f = new File(getMainDirectory().getPath() + "/" + file);

		try {
			if (createDataFolder()) {
				Messager.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e" + getMainDirectory().getPath() + "&f 폴더를 생성했습니다."));
			}

			if (!f.exists()) {
				f.getParentFile().mkdirs();
				f.createNewFile();
			}

			return f;
		} catch (IOException e) {
			Messager.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + f.getPath() + " 파일을 생성하지 못했습니다."));
			return null;
		}
	}

	public static File newDirectory(String name) {
		File directory = new File(getMainDirectory().getPath() + "/" + name);
		if (createDataFolder()) Messager.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e" + getMainDirectory().getPath() + "&f 폴더를 생성했습니다."));
		if (!directory.exists()) directory.mkdirs();
		return directory;
	}

	public static File newFile(File parent, String name) {
		File file = new File(parent, name);
		try {
			if (createDataFolder()) Messager.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e" + getMainDirectory().getPath() + "&f 폴더를 생성했습니다."));

			if (!file.exists()) {
				parent.mkdirs();
				file.createNewFile();
			}

			return file;
		} catch (IOException e) {
			Messager.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + file.getPath() + " 파일을 생성하지 못했습니다."));
			throw new RuntimeException(e);
		}
	}

	public static void saveObject(String file, Object object) throws IOException {
		FileOutputStream fileStream = new FileOutputStream(newFile(file));
		ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
		objectStream.writeObject(object);
		objectStream.close();
	}

	public static <T> T loadObject(String file, Class<T> clazz) throws IOException, ClassNotFoundException, ClassCastException {
		FileInputStream fileStream = new FileInputStream(newFile(file));
		ObjectInputStream objectStream = new ObjectInputStream(fileStream);
		T object = clazz.cast(objectStream.readObject());
		objectStream.close();
		return object;
	}

}
