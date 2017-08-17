package com.mirsv.catnote;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.mirsv.MirPlugin;

public class HeadChange extends MirPlugin implements Listener, CommandExecutor {
	final String prefix = ChatColor.GOLD + "[" + ChatColor.GREEN + "미르서버" + ChatColor.GOLD + "] " + ChatColor.RESET;
	public HeadChange() {
		getCommand("머리", this);
		getListener(this);
	}
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) return false;
		Player player = (Player) sender;
		if(getConfig().getBoolean("enable.HeadChange", true)) {
			if(args.length > 0) {
				boolean isPlayerHasItem = false;
				Inventory inv = player.getInventory();
				for(int i = 0; i < inv.getSize(); i++) {
					ItemStack item = inv.getItem(i);
					if(item == null || item.getType().equals(Material.AIR)) continue;
					ItemMeta iMeta = item.getItemMeta();
					if(item.getType().equals(Material.NAME_TAG) && iMeta.getDisplayName().equals(ChatColor.GOLD + "" + ChatColor.BOLD + "머리교환권")) {
						isPlayerHasItem = true;
						if(item.getAmount() > 1) {
							item.setAmount(item.getAmount() - 1);
							inv.setItem(i, item);
						}
						else {
							item = new ItemStack(Material.AIR, 1, (short) 0);
							inv.setItem(i, item);
						}
						break;
					}
				}
				if(isPlayerHasItem) {
					ItemStack Head = new ItemStack(397, 1, (short) 3);
					SkullMeta Meta = (SkullMeta) Head.getItemMeta();
					Meta.setOwner(args[0]);
					Head.setItemMeta(Meta);
					player.getInventory().addItem(Head);
					player.sendMessage(prefix + ChatColor.WHITE + args[0] + ChatColor.GREEN + "님의 " + ChatColor.WHITE + "머리" + ChatColor.GREEN + "를 획득하였습니다!");
				}
				else player.sendMessage(prefix + ChatColor.YELLOW + "머리교환권을 가지고 있지 않습니다.");
			}
			else player.sendMessage(prefix + ChatColor.YELLOW + "사용 방법: /머리 <닉네임>");
		}
		return false;
	}
}
