package com.mirsv.function.list.Cokes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.mirsv.function.AbstractFunction;
import com.mirsv.util.Messager;

public class ItemTag extends AbstractFunction implements CommandExecutor{

	public ItemTag() {
		super("�������±�", "1.0", "������ ���� �������� ��ƿ���Դϴ�.");
	}

	@Override
	protected void onEnable() {
		registerCommand("itemtag", this);
	}

	@Override
	protected void onDisable() {}
	
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;

			if (p.isOp()) {
				ItemStack i = p.getInventory().getItemInMainHand();
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
							p.sendMessage(Messager.getPrefix()+"��a�տ� ��� �ִ� �������� �̸��� �ٲپ����ϴ�.");
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
									p.sendMessage(Messager.getPrefix()+"��a�տ� ��� �ִ� �������� ������ �߰��Ͽ����ϴ�.");
								}
							} else if (args[1].equalsIgnoreCase("show")){
								List<String> lores = item.getLore();
								if (lores != null)
									for (int j = 0;j < lores.size(); j++)
										p.sendMessage(Messager.getPrefix()+"��a"+j+". "+lores.get(j));
							} else if (args[1].equalsIgnoreCase("remove")){
								List<String> lores = item.getLore();
								if (lores != null){
									if (args.length == 2){
										int size = lores.size() -1;
										lores.remove(size);
										item.setLore(lores);
										i.setItemMeta(item);
										p.sendMessage(Messager.getPrefix()+"��a�տ� ��� �ִ� �������� ������ ������ �����Ͽ����ϴ�.");
									} else if (args.length == 3){
										if (isNumber(args[2])){
											int index = Integer.parseInt(args[2]);
											if (index > -1 && index < lores.size()){
												lores.remove(index);
												item.setLore(lores);
												i.setItemMeta(item);
												p.sendMessage(Messager.getPrefix()+"��a�տ� ��� �ִ� �������� "+index+"��° ������ �����Ͽ����ϴ�.");
											}
										}
									}
								}
							}
						}
					} else {
						p.sendMessage(Messager.getPrefix()+"��a������ �ױ� ��ɾ�");
						p.sendMessage(Messager.getPrefix()+"��a/itemtag name [name]: �տ� ��� �ִ� �������� �̸��� �ٲߴϴ�.");
						p.sendMessage(Messager.getPrefix()+"��a/itemtag lore add [string]: �տ� ��� �ִ� �������� ������ �߰��մϴ�.");
						p.sendMessage(Messager.getPrefix()+"��a/itemtag lore show: �տ� ��� �ִ� �������� ������ ���ϴ�.");
						p.sendMessage(Messager.getPrefix()+"��a/itemtag lore remove (index): �տ� ��� �ִ� �������� ������ �����մϴ�.");
					}
				} else {
					p.sendMessage(Messager.getPrefix()+"��c�������� ��� ���� �ʽ��ϴ�.");
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