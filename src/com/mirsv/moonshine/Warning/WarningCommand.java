package com.mirsv.moonshine.Warning;

import java.io.File;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class WarningCommand
implements CommandExecutor {
	private final FileConfiguration plugin;
	private final Warning warning;
	Plugin pm = Bukkit.getPluginManager().getPlugin("Mirsv");
	File prefixListFile = new File("plugins/" + pm.getDescription().getName() + "/Warning/Warning.yml");
	String prefix = ChatColor.GOLD + "[" + ChatColor.GREEN + "�̸�����" + ChatColor.GOLD + "] " + ChatColor.RESET;

	public WarningCommand(Warning war) {
		this.warning = war;
		this.plugin = war.getConfig();
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;

		if ((this.plugin.getBoolean("enable.warning", true)) && (
				(player.isOp()) || (player.hasPermission("mirsv.admin")))) {
			if (args.length > 0) {
				if ((args[0].equalsIgnoreCase("add")) || (args[0].equalsIgnoreCase("�߰�"))) {
					if ((1 < args.length) && ((Bukkit.getPlayer(args[1]) != null))) {
						if (2 < args.length) {
							if (isNumber(args[2])) {
								Player target = Bukkit.getPlayer(args[1]);
								int warn = warning.warning.getInt(target.getName());
								warn += Integer.parseInt(args[2]);
								if (warn > warning.getConfig().getInt("Warning.maxwarning", 5)) {
									warn = warning.getConfig().getInt("Warning.maxwarning", 5);
								}
								warning.warning.set(target.getName(), warn);
								try {
									warning.warning.save(prefixListFile);
								} catch (Exception localException) {}

								player.sendMessage(prefix+ChatColor.GREEN + target.getName() + "���� ��� " + args[2] + "��ŭ �þ���ϴ�.");
								player.sendMessage(prefix+ChatColor.GREEN + target.getName() + "���� ��� Ƚ��:  " + warn);

								warnCommand(target, warn);
							}
						} else {
							Player target = Bukkit.getPlayer(args[1]);
							int warn = warning.warning.getInt(target.getName());
							warn++;
							if (warn > warning.getConfig().getInt("Warning.maxwarning", 5)) {
								warn = warning.getConfig().getInt("Warning.maxwarning", 5);
							}
							warning.warning.set(target.getName(), Integer.valueOf(warn));
							try {
								warning.warning.save(prefixListFile);
							} catch (Exception localException1) {}
							player.sendMessage(prefix+ChatColor.GREEN + target.getName() + "���� ��� 1��ŭ �þ���ϴ�.");
							player.sendMessage(prefix+ChatColor.GREEN + target.getName() + "���� ��� Ƚ��:  " + warn);

							warnCommand(target, warn);
						}
					} else if (1 < args.length && Bukkit.getOfflinePlayer(args[1]) != null) {
						if (2 < args.length) {
							if (isNumber(args[2])) {
								OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
								int warn = warning.warning.getInt(target.getName());
								warn += Integer.parseInt(args[2]);
								if (warn > warning.getConfig().getInt("Warning.maxwarning", 5)) {
									warn = warning.getConfig().getInt("Warning.maxwarning", 5);
								}
								warning.warning.set(target.getName(), Integer.valueOf(warn));
								try {
									warning.warning.save(prefixListFile);
								} catch (Exception localException) {}
								player.sendMessage(prefix+ChatColor.GREEN + target.getName() + "���� ��� " + args[2] + "��ŭ �þ���ϴ�.");

								warnCommand(target, true);
							}
						} else {
							OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
							int warn = warning.warning.getInt(target.getName());
							warn++;
							if (warn > warning.getConfig().getInt("Warning.maxwarning", 5)) {
								warn = warning.getConfig().getInt("Warning.maxwarning", 5);
							}
							warning.warning.set(target.getName(), Integer.valueOf(warn));
							try {
								warning.warning.save(prefixListFile);
							} catch (Exception localException1) {}
							player.sendMessage(prefix+ChatColor.GREEN + target.getName() + "���� ��� 1��ŭ �þ���ϴ�.");
							player.sendMessage(prefix+ChatColor.GREEN + target.getName() + "���� ��� Ƚ��:  " + warn);

							warnCommand(target, true);
						}
					}
				} else if ((args[0].equalsIgnoreCase("del")) || (args[0].equalsIgnoreCase("����"))) {
					if ((1 < args.length) && (
							(Bukkit.getPlayer(args[1]) != null))) {
						if (2 < args.length) {
							if (isNumber(args[2])) {
								Player target = Bukkit.getPlayer(args[1]);
								int warn = warning.warning.getInt(target.getName());
								warn -= Integer.parseInt(args[2]);
								if (warn < 0) {
									warn = 0;
								}
								warning.warning.set(target.getName(), Integer.valueOf(warn));
								try {
									warning.warning.save(prefixListFile);
								} catch (Exception localException2) {}
								player.sendMessage(prefix+ChatColor.GREEN + target.getName() + "���� ��� " + args[2] + "��ŭ ���ŉ���ϴ�.");
								player.sendMessage(prefix+ChatColor.GREEN + target.getName() + "���� ��� Ƚ��:  " + warn);

								warnCommand(target, warn);
							}
						} else {
							Player target = Bukkit.getPlayer(args[1]);
							int warn = warning.warning.getInt(target.getName());
							warn--;
							if (warn < 0) {
								warn = 0;
							}
							warning.warning.set(target.getName(), Integer.valueOf(warn));
							try {
								warning.warning.save(prefixListFile);
							} catch (Exception localException3) {}
							player.sendMessage(prefix+ChatColor.GREEN + target.getName() + "���� ��� 1��ŭ ���ŉ���ϴ�.");
							player.sendMessage(prefix+ChatColor.GREEN + target.getName() + "���� ��� Ƚ��:  " + warn);

							warnCommand(target, warn);
						}
					} else if (1 < args.length && Bukkit.getOfflinePlayer(args[1]) != null) {
						if (2 < args.length) {
							if (isNumber(args[2])) {
								OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
								int warn = warning.warning.getInt(target.getName());
								warn -= Integer.parseInt(args[2]);
								if (warn < 0) {
									warn = 0;
								}
								warning.warning.set(target.getName(), Integer.valueOf(warn));
								try {
									warning.warning.save(prefixListFile);
								} catch (Exception localException) {}
								player.sendMessage(prefix+ChatColor.GREEN + target.getName() + "���� ��� " + args[2] + "��ŭ ���ŉ���ϴ�.");

								warnCommand(target, true);
							}
						} else {
							OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
							int warn = warning.warning.getInt(target.getName());
							warn--;
							if (warn < 0) {
								warn = 0;
							}
							warning.warning.set(target.getName(), Integer.valueOf(warn));
							try {
								warning.warning.save(prefixListFile);
							} catch (Exception localException3) {}
							player.sendMessage(prefix+ChatColor.GREEN + target.getName() + "���� ��� 1��ŭ ���ŉ���ϴ�.");
							player.sendMessage(prefix+ChatColor.GREEN + target.getName() + "���� ��� Ƚ��:  " + warn);

							warnCommand(target, true);
						}
					}
				} else if ((args[0].equalsIgnoreCase("reload")) || (args[0].equalsIgnoreCase("���ε�"))) {
					try {
						warning.warning.save(prefixListFile);
						warning.warning.load(prefixListFile);
					} catch (Exception localException4) {}
					player.sendMessage(prefix+ChatColor.GREEN+"���ε� �Ǿ���");
				} else if ((args[0].equalsIgnoreCase("show"))) {
					if (Bukkit.getPlayer(args[1]) != null) {
						Player target = Bukkit.getPlayer(args[1]);
						int warn = warning.warning.getInt(target.getName());

						player.sendMessage(prefix+ChatColor.GREEN + target.getName() + "���� ��� Ƚ��:  " + warn);
					} else if (Bukkit.getOfflinePlayer(args[1]) != null) {
						OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
						int warn = warning.warning.getInt(target.getName());

						player.sendMessage(prefix+ChatColor.GREEN + target.getName() + "���� ��� Ƚ��:  " + warn);
					}
				}
			} else {
				player.sendMessage(prefix+ChatColor.GREEN+"/warning add [playername] [int] - ��� Ƚ���� [int]��ŭ ����");
				player.sendMessage(prefix+ChatColor.GREEN+"/warning del [playername] [int] - ��� Ƚ���� [int]��ŭ ����");
				player.sendMessage(prefix+ChatColor.GREEN+"[int]�� ���� ���� �� 1ȸ�� ����.");
				player.sendMessage(prefix+ChatColor.GREEN+"/warning show [playername] - ��� Ƚ������ ������");
				player.sendMessage(prefix+ChatColor.GREEN+"/warning reload - ���ε��մϴ�.");
			}
		}

		return true;
	}

	private boolean isNumber(String str) {
		boolean result = true;
		if (str.equals("")) {
			result = false;
		}
		for (int i = 0; i < str.length(); i++) {
			int c = str.charAt(i);
			if ((c < 48) || (c > 59)) {
				result = false;
				break;
			}
		}
		return result;
	}

	public void warnCommand(Player player, int warn) {
		List < String > command = warning.config.getStringList("Warning.warnCommand" + warn);
		if (!command.isEmpty())
			for (String Command: command)
				if (Command != null) {
					String result = Command.replace("[username]", player.getName());
					this.pm.getServer().getScheduler().runTask(pm, new Runnable() {
						@Override
						public void run() {
							pm.getServer().dispatchCommand(pm.getServer().getConsoleSender(), result);
						}
					});

				}
	}
	public void warnCommand(OfflinePlayer player, boolean bool) {
		warning.loadboolean.put(player.getName(), bool);
	}
}