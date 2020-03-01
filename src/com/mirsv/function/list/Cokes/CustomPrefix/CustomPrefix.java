package com.mirsv.function.list.Cokes.CustomPrefix;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mirsv.Mirsv;
import com.mirsv.function.AbstractFunction;
import com.mirsv.util.Messager;
import com.mirsv.util.users.User;
import com.mirsv.util.users.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class CustomPrefix extends AbstractFunction implements CommandExecutor, Listener {

	public CustomPrefix() {
		super("커스텀칭호", "1.0.0", "커스텀칭호를 제공해줍니다.");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length == 3 && args[0].equalsIgnoreCase("add") && player.isOp()) {
				if (args[1].equals("@a")) {
					for (Player target : Bukkit.getOnlinePlayers()) {
						User user = UserManager.getUser(target);
						JsonArray prefix = getPrefix(user);
						String add = args[2];
						prefix.add(add);
						JsonObject json = user.getConfig().getJson();
						if (!json.has("prefix")) json.add("prefix", new JsonObject());
						json = json.get("prefix").getAsJsonObject();
						json.add("list", prefix);
						target.sendMessage(ChatColor.translateAlternateColorCodes('&', "칭호 " + add + "&f이(가) 추가되었습니다."));
					}
					player.sendMessage("칭호 추가 완료");
				} else if (Bukkit.getPlayer(args[1]) != null) {
					Player target = Bukkit.getPlayer(args[2]);
					User user = UserManager.getUser(target);
					JsonArray prefix = getPrefix(user);
					String add;
					add = args[2];
					prefix.add(add);
					JsonObject json = user.getConfig().getJson();
					if (!json.has("prefix")) json.add("prefix", new JsonObject());
					json = json.get("prefix").getAsJsonObject();
					json.add("list", prefix);
					player.sendMessage("칭호 추가 완료");
					target.sendMessage(ChatColor.translateAlternateColorCodes('&', "칭호 " + add + "&f이(가) 추가되었습니다."));
				} else {
					player.sendMessage("§c해당 플레이어는 존재하지 않습니다.");
					player.sendMessage("§c사용법: /prefix add [@a|name] [prefix]");
				}
			} else if (args.length == 2 && args[0].equalsIgnoreCase("give") && player.isOp()) {
				String prefix = args[1];
				player.getInventory().addItem(getPrefixBook(prefix));
			} else if (args.length == 0) {
				PrefixGui gui = new PrefixGui(player, player, Mirsv.getPlugin());
				gui.openGUI(1);
			} else if (args.length == 1 && player.isOp()) {
				if (Bukkit.getPlayer(args[0]) != null) {
					Player target = Bukkit.getPlayer(args[0]);
					PrefixGui gui = new PrefixGui(target, player, Mirsv.getPlugin());
					gui.openGUI(1);
				} else {
					player.sendMessage("§c해당 플레이어는 존재하지 않습니다.");
					player.sendMessage("§c사용법: /prefix [name]");
				}
			}
		}
		return false;
	}

	@Override
	protected void onEnable() {
		if (!Mirsv.getPlugin().getConfig().getBoolean("enable.AdvanceChat", true)) {
			Bukkit.broadcastMessage("AdvanceChat을 지원하지 않습니다. 해당 기능을 종료합니다.");
			Disable();
		}

		registerCommand("prefix", this);
		registerListener(this);
	}

	@Override
	protected void onDisable() {

	}

	public ItemStack getPrefixBook(String prefix) {
		ItemStack book = new ItemStack(Material.BOOK, 1);
		ItemMeta meta = book.getItemMeta();
		meta.setDisplayName("§b칭호 교환권");
		List<String> lore = Messager.getStringList("§a>> §f칭호: " + ChatColor.translateAlternateColorCodes('&', prefix), "§a>> §f해당 칭호를 등록하실려면 들고 우클릭하시오.");
		meta.setLore(lore);
		book.setItemMeta(meta);
		return book;
	}

	public static JsonArray getPrefix(User user) {
		JsonObject json = user.getConfig().getJson();
		if (!json.has("prefix")) json.add("prefix", new JsonObject());
		json = json.get("prefix").getAsJsonObject();
		if (!json.has("list")) json.add("list", new JsonArray());
		return json.get("list").getAsJsonArray();
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		User user = UserManager.getUser(player);
		ItemStack main = player.getInventory().getItemInMainHand();
		if (main.getType().equals(Material.BOOK) && main.hasItemMeta() && main.getItemMeta().getDisplayName().equals("§b칭호 교환권")) {
			List<String> lore = main.getItemMeta().getLore();
			if (lore.get(0).startsWith("§a>> §f칭호: ")) {
				String prefix = lore.get(0).replaceAll("§a>> §f칭호: ", "");
				JsonArray prefixes = getPrefix(user);
				if (!prefixes.contains(new JsonPrimitive(prefix))) {
					prefixes.add(prefix);
					JsonObject json = user.getConfig().getJson();
					if (!json.has("prefix")) json.add("prefix", new JsonObject());
					json = json.get("prefix").getAsJsonObject();
					json.add("list", prefixes);
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "칭호 " + prefix + "&f이(가) 추가되었습니다."));
					if (main.getAmount() > 1) main.setAmount(main.getAmount() - 1);
					else player.getInventory().setItemInMainHand(null);
				} else {
					player.sendMessage("§c해당 칭호를 이미 보유하고 있습니다.");
				}
			}
		}
	}
}
