package com.mirsv.catnote;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.mirsv.MirPlugin;

public class NoPickup extends MirPlugin implements Listener, CommandExecutor {
	final String prefix = ChatColor.GOLD + "[" + ChatColor.GREEN + "미르서버" + ChatColor.GOLD + "] " + ChatColor.RESET;
	static HashMap <UUID, ArrayList <String>> BItemList = new HashMap <UUID, ArrayList <String>>();
	public NoPickup() {
		try {
			BufferedReader in = new BufferedReader(new FileReader("plugins/Mirsv/NoPickup/NoPickup.dat"));
			String s;
			while((s = in.readLine()) != null) {
				String[] Array = s.split(" ");
				UUID uuid = UUID.fromString(Array[0]);
				ArrayList <String> ItemList = new ArrayList <String>();
				for(int i = 1; i < Array.length; i++) ItemList.add(Array[i]);
				BItemList.put(uuid, ItemList);
			}
			in.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		getCommand("np", this);
		getListener(this);
	}
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) return false;
		Player player = (Player) sender;
		if(getConfig().getBoolean("enable.NoPickup", true)) {
			if(args.length == 0) {
				player.sendMessage(prefix + ChatColor.DARK_AQUA + "/np " + ChatColor.GRAY + "add" + ChatColor.WHITE + " - 손에 든 아이템을 리스트에 추가");
				player.sendMessage(prefix + ChatColor.DARK_AQUA + "/np " + ChatColor.GRAY + "remove <아이템이름>" + ChatColor.WHITE + " - <아이템이름>을 리스트에서 해제");
				player.sendMessage(prefix + ChatColor.DARK_AQUA + "/np " + ChatColor.GRAY + "list" + ChatColor.WHITE + " - 블랙리스트에 추가된 아이템 확인");
				player.sendMessage(prefix + ChatColor.AQUA + "리스트에 추가된 아이템은 주워지지 않으며, 대신 사라집니다.");
			}
			else {
				if(args[0].equalsIgnoreCase("add")) {
					ItemStack i = player.getItemInHand();
					if(i.getType().equals(Material.AIR)) {
						player.sendMessage(prefix + ChatColor.YELLOW + "손에 아무것도 들고 있지 않습니다.");
						return false;
					}
					int itemTypeId = i.getTypeId();
					short itemDur = i.getDurability();
					if(!BItemList.containsKey(player.getUniqueId())) BItemList.put(player.getUniqueId(), new ArrayList <String>());
					String ItemName = i.getType().name().charAt(0) + (i.getType().name().substring(1).toLowerCase());
					for(String s: BItemList.get(player.getUniqueId())) {
						String lItemName = s.split(":")[2];
						if(ItemName.equalsIgnoreCase(lItemName)) {
							player.sendMessage(prefix + ChatColor.WHITE + ItemName + ChatColor.YELLOW + " 아이템은 이미 등록되어 있습니다.");
							return false;
						}
					}
					BItemList.get(player.getUniqueId()).add(itemTypeId + ":" + itemDur + ":" + ItemName);
					player.sendMessage(prefix + ChatColor.WHITE + ItemName + ChatColor.AQUA + " 아이템이 리스트에 추가되었습니다.");
				}
				else if(args[0].equalsIgnoreCase("remove")) {
					if(!BItemList.containsKey(player.getUniqueId())) {
						player.sendMessage(prefix + ChatColor.WHITE + args[1] + ChatColor.YELLOW + " 아이템은 리스트에 없습니다.");
						return false;
					}
					for(String s: BItemList.get(player.getUniqueId())) {
						String ItemName = s.split(":")[2];
						if(args[1].equalsIgnoreCase(ItemName)) {
							BItemList.get(player.getUniqueId()).remove(s);
							player.sendMessage(prefix + ChatColor.WHITE + ItemName + ChatColor.AQUA + " 아이템을 리스트에서 삭제하였습니다.");
							Save();
							return false;
						}
					}
					player.sendMessage(prefix + ChatColor.WHITE + args[1] + ChatColor.YELLOW + " 아이템은 리스트에 없습니다.");
				}
				else if(args[0].equalsIgnoreCase("list")) {
					if(!BItemList.containsKey(player.getUniqueId()) || BItemList.get(player.getUniqueId()).size() == 0) {
						player.sendMessage(prefix + ChatColor.YELLOW + "리스트에 등록된 아이템이 없습니다.");
						return false;
					}
					String ItemList = "";
					for(String s: BItemList.get(player.getUniqueId())) {
						String ItemName = s.split(":")[2];
						ItemList += ChatColor.WHITE + ItemName + ChatColor.AQUA + ", ";
					}
					ItemList = ItemList.substring(0, ItemList.length() - 2);
					player.sendMessage(prefix + ChatColor.AQUA + "아이템 블랙리스트: " + ItemList);
				}
				else {
					player.sendMessage(prefix + ChatColor.DARK_AQUA + "/np " + ChatColor.GRAY + "add" + ChatColor.WHITE + " - 손에 든 아이템을 리스트에 추가");
					player.sendMessage(prefix + ChatColor.DARK_AQUA + "/np " + ChatColor.GRAY + "remove <아이템이름>" + ChatColor.WHITE + " - <아이템이름>을 리스트에서 해제");
					player.sendMessage(prefix + ChatColor.DARK_AQUA + "/np " + ChatColor.GRAY + "list" + ChatColor.WHITE + " - 블랙리스트에 추가된 아이템 확인");
					player.sendMessage(prefix + ChatColor.AQUA + "리스트에 추가된 아이템은 주워지지 않으며, 대신 사라집니다.");
				}
				Save();
			}
		}
		return false;
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	private void PickupItem(PlayerPickupItemEvent e) {
		if(getConfig().getBoolean("enable.NoPickup", true)) {
			if(e.isCancelled()) return;
			ItemStack i = e.getItem().getItemStack();
			int itemTypeId = i.getTypeId();
			short itemDur = i.getDurability();
			if(BItemList.containsKey(e.getPlayer().getUniqueId())) {
				for(String s: BItemList.get(e.getPlayer().getUniqueId())) {
					int lItemTypeId = Integer.parseInt(s.split(":")[0]);
					short lItemDur = Short.parseShort(s.split(":")[1]);
					if(itemTypeId == lItemTypeId && itemDur == lItemDur) {
						e.setCancelled(true);
						e.getItem().remove();
						return;
					}
				}
			}
		}
	}
	public static void Save() {
		try {
			File f = new File("plugins/Mirsv/NoPickup/NoPickup.dat");
			f.delete();
            BufferedWriter bw = new BufferedWriter(new FileWriter("plugins/Mirsv/NoPickup/NoPickup.dat"));
            for(UUID uuid: BItemList.keySet()) {
            	String Output = uuid + " ";
            	for(String s: BItemList.get(uuid)) Output += s + " ";
            	bw.write(Output);
            	bw.newLine();
            }
            bw.close();
        }
		catch (IOException e) {
            e.printStackTrace();
		}
	}
}
