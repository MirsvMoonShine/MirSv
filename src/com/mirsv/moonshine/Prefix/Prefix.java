package com.mirsv.moonshine.Prefix;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.mirsv.MirPlugin;

import net.milkbowl.vault.chat.Chat;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Prefix extends MirPlugin {
	public static Chat chat = null;
	public static PermissionManager per = null;
	public static FileConfiguration prefixList;
    File prefixListFile;
    public static String cmdPrefix = ChatColor.GREEN + "[Prefix] ";
    public static String errorPrefix = ChatColor.RED + "[Prefix] ";

	public Prefix(String pluginname) {
		super(pluginname);
		this.prefixListFile = new File("plugins/" + MirPlugin.pm.getDescription().getName() + "/Prefix/prefixList.yml");
		
		
		getConfig().addDefault("Prefix.permission_enable",true);
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		setupChat();
		setupPermissionsEx();
		
		getCommand("prefix", new PrefixCommand(getConfig()));
	}
	
	public boolean setupChat() {
		RegisteredServiceProvider<Chat> chatProvider = MirPlugin.pm.getServer().getServicesManager().getRegistration(Chat.class);
		if (chatProvider != null) {
			chat = (Chat)chatProvider.getProvider();
		}
		return chat != null;
	}
	
	public boolean setupPermissionsEx() {
		try {
			per = PermissionsEx.getPermissionManager();
		} catch (Exception localException) {}
		return per != null;
	}
	
	public void configsetting() {
		File prefixListFile = new File("plugins/" + MirPlugin.pm.getDescription().getName() + "/Prefix/prefixList.yml");
		prefixList = YamlConfiguration.loadConfiguration(prefixListFile);
		try {
			if (!prefixListFile.exists()) {
				prefixList.save(prefixListFile);
			}
			prefixList.load(prefixListFile);
		}catch (Exception localException) {}
	}
}
