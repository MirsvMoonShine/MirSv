package com.mirsv.moonshine;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.mirsv.MirPlugin;

public class ItemTag extends MirPlugin implements CommandExecutor{
	String prefix = ChatColor.GOLD + "[" + ChatColor.GREEN + "�̸�����" + ChatColor.GOLD + "] " + ChatColor.RESET;

	public ItemTag() {
		getCommand("ItemTag", this);
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
		if ((getConfig().getBoolean("enable.ItemTag", true)) && ((sender instanceof Player))) {
			Player p = (Player) sender;

			if (p.isOp()) {
				ItemStack i = p.getItemInHand();
				Material m = i.getType();
				if (!m.equals(Material.AIR)) {
					ItemMeta item = i.getItemMeta();
					if (args.length > 0) {
						if ((args[0].equalsIgnoreCase("name")) && (args.length > 1)) {
							String name = args[1];
							for (int a = 2; a < args.length;a++) name += " "+args[a];
							name = name.replaceAll("&", "��");
							item.setDisplayName(name);
							i.setItemMeta(item);
							p.sendMessage(prefix+"��a�տ� ��� �ִ� �������� �̸��� �ٲپ����ϴ�.");
						} else if (args[0].equalsIgnoreCase("lore")){
							if (args[1].equalsIgnoreCase("add")){
								if (args.length > 2){
									String lore = args[2];
									for (int a = 3; a < args.length;a++) lore += " "+args[a];
									lore = lore.replaceAll("&", "��");
									List<String> lores = item.getLore();
									if (lores == null) lores = new ArrayList<String>();
									lores.add(lore);
									item.setLore(lores);
									i.setItemMeta(item);
									p.sendMessage(prefix+"��a�տ� ��� �ִ� �������� ������ �߰��Ͽ����ϴ�.");
								}
							} else if (args[1].equalsIgnoreCase("show")){
								List<String> lores = item.getLore();
								if (lores != null)
									for (int j = 0;j < lores.size(); j++)
										p.sendMessage(prefix+"��a"+j+". "+lores.get(j));
							} else if (args[1].equalsIgnoreCase("remove")){
								List<String> lores = item.getLore();
								if (lores != null){
									if (args.length == 2){
										int size = lores.size() -1;
										lores.remove(size);
										item.setLore(lores);
										i.setItemMeta(item);
										p.sendMessage(prefix+"��a�տ� ��� �ִ� �������� ������ ������ �����Ͽ����ϴ�.");
									} else if (args.length == 3){
										if (isNumber(args[2])){
											int index = Integer.parseInt(args[2]);
											if (index > -1 && index < lores.size()){
												lores.remove(index);
												item.setLore(lores);
												i.setItemMeta(item);
												p.sendMessage(prefix+"��a�տ� ��� �ִ� �������� "+index+"��° ������ �����Ͽ����ϴ�.");
											}
										}
									}
								}
							}
						}
					} else {
						p.sendMessage(prefix+"��a������ �ױ� ��ɾ�");
						p.sendMessage(prefix+"��a/itemtag name [name]: �տ� ��� �ִ� �������� �̸��� �ٲߴϴ�.");
						p.sendMessage(prefix+"��a/itemtag lore add [string]: �տ� ��� �ִ� �������� ������ �߰��մϴ�.");
						p.sendMessage(prefix+"��a/itemtag lore show: �տ� ��� �ִ� �������� ������ ���ϴ�.");
						p.sendMessage(prefix+"��a/itemtag lore remove (index): �տ� ��� �ִ� �������� ������ �����մϴ�.");
					}
				} else {
					p.sendMessage(prefix+"��c�������� ��� ���� �ʽ��ϴ�.");
				}
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
}