package com.mirsv.util.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.bukkit.ChatColor;

import com.mirsv.util.Messager;

public class FileUtil {
	
	private FileUtil() {}
	
	private static File getDataFolder() {
		return new File("plugins/Mirsv");
	}
	
	private static boolean createDataFolder() {
		File Folder = getDataFolder();
		if(!Folder.exists()) {
			Folder.mkdirs();
			return true;
		} else {
			return false;
		}
	}
	
	public static File getFile(String file) {
		
		File f = new File(getDataFolder().getPath() + "/" + file);
		
		try {
			if(createDataFolder()) {
				Messager.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e" + getDataFolder().getPath() + "&f 폴더를 생성했습니다."));
			}
			
			if(!f.exists()) {
				f.getParentFile().mkdirs();
				f.createNewFile();
			}
			
			return f;
		} catch (IOException e) {
			Messager.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + f.getPath() + " 파일을 생성하지 못했습니다."));
			return null;
		}
	}

	public static File getFolder(String folder) {
		
		File f = new File(getDataFolder().getPath() + "/" + folder);

		if(createDataFolder()) {
			Messager.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e" + getDataFolder().getPath() + "&f 폴더를 생성했습니다."));
		}
		
		if(!f.exists()) {
			f.mkdirs();
		}
		
		return f;
	}
	
	public static void saveObject(String file, Object object) throws IOException {
		FileOutputStream fileStream = new FileOutputStream(getFile(file));
		ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
		objectStream.writeObject(object);
		objectStream.close();
	}
	
	public static <T> T loadObject(String file, Class<T> clazz) throws IOException, ClassNotFoundException, ClassCastException {
		FileInputStream fileStream = new FileInputStream(getFile(file));
		ObjectInputStream objectStream = new ObjectInputStream(fileStream);
		T object = clazz.cast(objectStream.readObject());
		objectStream.close();
		return object;
	}
	
}
