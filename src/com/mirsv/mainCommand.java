package com.mirsv;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mirsv.function.AbstractFunction;
import com.mirsv.function.Functions;
import com.mirsv.util.Messager;

public class mainCommand implements CommandExecutor {
	
	private List<String> getFunctionsAsString() {
		List<String> list = new ArrayList<String>();
		
		for(Functions f : Functions.values()) {
			if(f.getFunction().isEnabled()) {
				list.add(ChatColor.GREEN + f.toString());
			} else {
				list.add(ChatColor.RED + f.toString());
			}
		}
		
		return list;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
		List<String> functions = getFunctionsAsString();
		
		if ((sender instanceof Player)) {
			Player p = (Player) sender;
			if (p.isOp()) {
				if (args.length == 0) {
					String[] plu = (String[]) functions.toArray(new String[functions.size()]);
					String plugin = "";
					if (plu.length > 0) {
						plugin = plu[0];
						for (int i = 1; i < plu.length; i++) {
							plugin += ChatColor.WHITE + ", " + plu[i];
						}
					}
					p.sendMessage(Messager.formatTitle(ChatColor.AQUA, ChatColor.WHITE, "�̸����� ���� �÷�����"));
					p.sendMessage(Messager.getPrefix()+ChatColor.WHITE + "����: " + ChatColor.DARK_AQUA + Mirsv.getPlugin().getDescription().getVersion());
					p.sendMessage(Messager.getPrefix()+ChatColor.WHITE + "�������� �÷�����: " + plugin);
					p.sendMessage(Messager.getPrefix()+ChatColor.WHITE + "---��ɾ�---");
					p.sendMessage(Messager.getPrefix()+ChatColor.WHITE + "/mirsv enable <function> - ����� Ȱ��ȭ�մϴ�.");
					p.sendMessage(Messager.getPrefix()+ChatColor.WHITE + "/mirsv disable <function> - ����� ��Ȱ��ȭ�մϴ�.");
					p.sendMessage(Messager.getPrefix()+ChatColor.WHITE + "/mirsv info <function> - ��� ������ Ȯ���մϴ�.");
				} else if (args.length > 0) {
					if (args[0].equalsIgnoreCase("disable")) {
						if ((args.length == 2)) {
							Functions f = Functions.getFunction(args[1]);
							if(f != null) {
								if (Mirsv.getPlugin().getConfig().getBoolean("enable." + f.toString())) {
									AbstractFunction function = f.getFunction();
									Mirsv.getPlugin().getConfig().set("enable." + f.toString(), false);
									Mirsv.getPlugin().saveConfig();
									function.Disable();
									p.sendMessage(Messager.getPrefix() + ChatColor.AQUA + function.getName() + " ����� ��Ȱ��ȭ�߽��ϴ�.");
								} else {
									p.sendMessage(Messager.getPrefix() + ChatColor.AQUA + "�̹� ��Ȱ��ȭ���ֽ��ϴ�.");
								}
							} else {
								p.sendMessage(Messager.getPrefix() + ChatColor.AQUA + "�������� �ʴ� ����Դϴ�.");
							}
						}
					} else if (args[0].equalsIgnoreCase("info")) {
						if ((args.length == 2)) {
							Functions f = Functions.getFunction(args[1]);
							if(f != null) {
								AbstractFunction function = f.getFunction();
								p.sendMessage(Messager.formatTitle(ChatColor.AQUA, ChatColor.WHITE, function.getName() + " ���"));
								p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f����: &3" + function.getVersion()));
								StringJoiner joiner = new StringJoiner("\n");
								for(String s : function.getExplain()) joiner.add(s);
								p.sendMessage(joiner.toString());
							} else {
								p.sendMessage(Messager.getPrefix() + ChatColor.AQUA + "�������� �ʴ� ����Դϴ�.");
							}
						}
					}
				}
			}

		}

		return false;
	}
}