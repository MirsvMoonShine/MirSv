package com.mirsv.moonshine.ItemTag;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.mirsv.MirPlugin;

public class ItemTag extends MirPlugin implements CommandExecutor{

	public ItemTag(String pluginname) {
		super(pluginname);

		getCommand("ItemTag", this);
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
		if ((getConfig().getBoolean("enable.ItemTag", true)) && ((sender instanceof Player))) {
			Player p = (Player) sender;

			if (p.hasPermission("mirsv.admin")) {
				ItemStack i = p.getItemInHand();
				if (i != new ItemStack(Material.AIR)) {
					ItemMeta item = i.getItemMeta();

					if (args.length > 0) {
						if ((args[0].equalsIgnoreCase("name")) && (args.length > 1)) {
							String name = args[1].replaceAll("&", "��").replaceAll("_", " ");
							item.setDisplayName(name);
							i.setItemMeta(item);
							p.sendMessage("��a[�̸�����] �տ� ��� �ִ� �������� �̸��� �ٲپ����ϴ�.");
						}
					} else {
						p.sendMessage("��a[�̸�����] ������ �ױ� ��ɾ�");
						p.sendMessage("��a/itemtag name [name]: �տ� ��� �ִ� �������� �̸��� �ٲߴϴ�.");
					}
				} else {
					p.sendMessage("��a[�̸�����] ��c�������� ��� ���� �ʽ��ϴ�.");
				}
			}
		}

		return true;
	}
}