package com.mirsv;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

public class mainCommand
implements CommandExecutor {
	private final Mirsv plugin;
	PluginDescriptionFile profile;
	String prefix = ChatColor.GOLD + "[" + ChatColor.GREEN + "�̸�����" + ChatColor.GOLD + "] " + ChatColor.RESET;

	public mainCommand(Mirsv p) {
		this.plugin = p;
		this.profile = this.plugin.getDescription();
	}

	public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
		if ((sender instanceof Player)) {
			Player p = (Player) sender;
			if (p.hasPermission("mirsv.admin")) {
				if (args.length == 0) {
					String[] plu = (String[]) this.plugin.plugins.toArray(new String[this.plugin.plugins.size()]);
					String plugin = "";
					if (plu.length > 0) {
						plugin = plu[0];
						for (int i = 1; i < plu.length; i++) {
							plugin += ChatColor.AQUA + ", " + plu[i];
						}
					}
					p.sendMessage(prefix+ChatColor.AQUA + "�̸����� ���� �÷�����");
					p.sendMessage(prefix+ChatColor.AQUA + "����: " + this.profile.getVersion());
					p.sendMessage(prefix+ChatColor.AQUA + "�������� �÷�����: " + plugin);
					p.sendMessage(prefix+ChatColor.AQUA + "---��ɾ�---");
					p.sendMessage(prefix+ChatColor.AQUA + "/mirsv enable <plugin> - �÷������� Ȱ��ȭ�մϴ�.");
					p.sendMessage(prefix+ChatColor.AQUA + "/mirsv disable <plugin> - �÷������� ��Ȱ��ȭ�մϴ�.");
					p.sendMessage(prefix+ChatColor.AQUA + "<plugin>�� �� �������� �÷����� �̸��� ������ϸ� ��ҹ��ڸ� �����մϴ�.");
				} else if (args.length > 0) {
					if (args[0].equalsIgnoreCase("disable")) {
						if ((args.length == 2)) {
							if (this.plugin.plugins.contains(ChatColor.GREEN + args[1])) {
								if (this.plugin.getConfig().getBoolean("enable." + args[1], true)) {
									this.plugin.plugins.set(this.plugin.plugins.indexOf(ChatColor.GREEN + args[1]), ChatColor.RED + args[1]);
									this.plugin.getConfig().set("enable." + args[1], false);
									this.plugin.saveConfig();
									p.sendMessage(prefix+ChatColor.AQUA + args[1] + " �÷������� ��Ȱ��ȭ�߽��ϴ�.");
								}
							}
						}
					} else if ((args[0].equalsIgnoreCase("enable")) &&
						(args.length == 2) && (this.plugin.plugins.contains(ChatColor.RED + args[1])) &&
						(this.plugin.getConfig().getBoolean("enable." + args[1], true) == false)) {
						this.plugin.plugins.set(this.plugin.plugins.indexOf(ChatColor.RED + args[1]), ChatColor.GREEN + args[1]);
						this.plugin.getConfig().set("enable." + args[1], Boolean.valueOf(true));
						this.plugin.saveConfig();
						p.sendMessage(prefix+ChatColor.AQUA + args[1] + " �÷������� Ȱ��ȭ�߽��ϴ�.");
					}
				}
			}

		}

		return false;
	}
}