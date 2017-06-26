package com.mirsv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.mirsv.moonshine.Party.Party;

public class Mirsv extends JavaPlugin {
	PluginManager pm = getServer().getPluginManager();
	ArrayList < String > plugins = new ArrayList < String > ();
	public PluginLists lists;
	public static List<Party> partys = new ArrayList<>();
	@Override
	public void onEnable() {
		getConfig().options().copyDefaults(true);
		saveConfig();

		getCommand("Mirsv").setExecutor(new mainCommand(this));
		System.out.println("[미르서버] 종합 플러그인 가동");
		InstallPlugins();

		String[] plu = (String[]) this.plugins.toArray(new String[this.plugins.size()]);
		String plugin = "";
		if (plu.length > 0) {
			plugin = plu[0];
			for (int i = 1; i < plu.length; i++) {
				plugin = plugin + ", " + plu[i];
			}
		}
		try {
			BufferedReader in = new BufferedReader(new FileReader("plugins/Mirsv/Party/Party.dat"));
			String s;
			while((s = in.readLine()) != null) {
				String[] Array = s.split(" ");
				Party party = new Party(UUID.fromString(Array[1]), Array[0]);
				for(int i = 2; i < Array.length; i++) party.getPlayers().add(UUID.fromString(Array[i]));
				partys.add(party);
			}
			in.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		System.out.println("[미르서버] 가동 된 플러그인들: " + plugin);
	}
	@Override
	public void onDisable() {
		List<Party> Save = com.mirsv.moonshine.Party.PartyMain.getPartys();
		try {
			File f = new File("plugins/Mirsv/Party.dat");
			f.delete();
            BufferedWriter bw = new BufferedWriter(new FileWriter("plugins/Mirsv/Party/Party.dat"));
            for(Party party: Save) {
            	String s = party.getPartyName() + " " + party.getOwner() + " ";
            	for(UUID u: party.getPlayers()) if(!party.getOwner().equals(u)) s += u + " ";
            	bw.write(s);
            	bw.newLine();
            }
            bw.close();
        }
		catch (IOException e) {
            e.printStackTrace();
		}
	}
	public void InstallPlugins() {
		plugins.clear();

		PluginLists[] plugin = PluginLists.values();
		for (PluginLists plu: plugin) {
			if (getConfig().getBoolean("enable." + plu.getPluginName(), true)) {
				plugins.add(ChatColor.GREEN + plu.getPluginName());
				plu.getPlugin();
				getConfig().set("enable." + plu.getPluginName(), true);
			} else {
				plugins.add(ChatColor.RED + plu.getPluginName());
				getConfig().set("enable." + plu.getPluginName(), false);
			}
		}
	}
	public static List<Party> getPartys() {
		return partys;
	}
}