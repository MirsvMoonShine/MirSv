package com.mirsv.function.list.Cokes.Party;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.mirsv.util.Messager;
import com.mirsv.util.PlayerCollector;

public class PartyTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("party")) {
			switch (args.length) {
			case 1:
				ArrayList<String> sub = Messager.getStringList("create", "disband", "add", "kick", "join", "leave", "toggle", "info", "list", "chat");
				if (!args[0].isEmpty()) sub.removeIf(cc -> !cc.toLowerCase().startsWith(args[0].toLowerCase()));
				return sub;
			case 2:
				if (args[0].equalsIgnoreCase("add")) {
					List<String> adds = (List<String>) PlayerCollector.getOnlinePlayersName();
					if (sender instanceof Player) {
						Player p = (Player) sender;
						adds.remove(p.getName());
						if (PartyManager.getParty(p.getUniqueId()) != null) {
							Party party = PartyManager.getParty(p.getUniqueId());
							if (party.getOwner().equals(p.getUniqueId())) {
								for (UUID joiner : party.getPlayers()) {
									adds.remove(Bukkit.getPlayer(joiner).getName());
								}
								if (!args[1].isEmpty()) adds.removeIf(cc -> !cc.toLowerCase().startsWith(args[1].toLowerCase()));
								return adds;
							}
						}
					}
				}
				
				if (args[0].equalsIgnoreCase("kick")) {
					List<String> kickers = new ArrayList<>();
					if (sender instanceof Player) {
						Player p = (Player) sender;
						if (PartyManager.getParty(p.getUniqueId()) != null) {
							Party party = PartyManager.getParty(p.getUniqueId());
							if (party.getOwner().equals(p.getUniqueId())) {
								for (UUID joiner : party.getPlayers()) {
									kickers.add(Bukkit.getPlayer(joiner).getName());
								}
								kickers.remove(p.getName());
								if (!args[1].isEmpty()) kickers.removeIf(cc -> !cc.toLowerCase().startsWith(args[1].toLowerCase()));
								return kickers;
							}
						}
					}
				}
				
				if (args[0].equalsIgnoreCase("toggle")) {
					List<String> toggles = Messager.getStringList("pvp", "open");
					if (sender instanceof Player) {
						Player p = (Player) sender;
						if (PartyManager.getParty(p.getUniqueId()) != null) {
							Party party = PartyManager.getParty(p.getUniqueId());
							if (party.getOwner().equals(p.getUniqueId())) {
								if (!args[1].isEmpty()) toggles.removeIf(cc -> !cc.toLowerCase().startsWith(args[1].toLowerCase()));
								return toggles;
							}
						}
					}
				}
			}
		}
		return null;
	}

}
