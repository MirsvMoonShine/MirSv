package com.mirsv.moonshine;

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

			if (p.hasPermission("mirsv.admin")) {
				ItemStack i = p.getItemInHand();
				if (i != new ItemStack(Material.AIR)) {
					ItemMeta item = i.getItemMeta();

					if (args.length > 0) {
						if ((args[0].equalsIgnoreCase("name")) && (args.length > 1)) {
							String name = args[1].replaceAll("&", "��").replaceAll("_", " ");
							item.setDisplayName(name);
							i.setItemMeta(item);
							p.sendMessage(prefix+"��a�տ� ��� �ִ� �������� �̸��� �ٲپ����ϴ�.");
						}
					} else {
						p.sendMessage(prefix+"��a������ �ױ� ��ɾ�");
						p.sendMessage(prefix+"��a/itemtag name [name]: �տ� ��� �ִ� �������� �̸��� �ٲߴϴ�.");
					}
				} else {
					p.sendMessage(prefix+"��c�������� ��� ���� �ʽ��ϴ�.");
				}
			}
		}

		return true;
	}
}