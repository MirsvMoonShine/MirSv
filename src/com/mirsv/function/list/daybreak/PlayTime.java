package com.mirsv.function.list.daybreak;

import com.google.gson.JsonParser;
import com.mirsv.Mirsv;
import com.mirsv.function.AbstractFunction;
import com.mirsv.util.Messager;
import com.mirsv.util.TimeUtil;
import com.mirsv.util.users.User;
import com.mirsv.util.users.UserManager;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class PlayTime extends AbstractFunction implements CommandExecutor {

	private static final JsonParser parser = new JsonParser();

	public PlayTime() {
		super("플레이타임", "1.0.0");
	}

	@Override
	protected void onEnable() {
		registerCommand("playtime", this);
	}

	@Override
	protected void onDisable() {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			User user = UserManager.getUser(player);
			if (args.length == 0) {
				if (!user.PLAYTIME.isCooldown()) {
					user.PLAYTIME.action();
					int ticks = 0;
					try {
						ticks = parser.parse(new FileReader("world/stats/" + player.getUniqueId() + ".json")).getAsJsonObject().get("stat.playOneMinute").getAsInt();
					} catch (IOException ignored) {
					}
					player.sendMessage(Messager.getPrefix() + "플레이 시간: " + ChatColor.DARK_AQUA + TimeUtil.parseTicks(ticks));
				} else {
					sender.sendMessage(ChatColor.WHITE + TimeUtil.toString(user.PLAYTIME.getLeftCooldown()) + ChatColor.RED + " 뒤에 시도해주세요.");
				}
			} else {
				if (args[0].equalsIgnoreCase("?") || args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("info")) {
					player.sendMessage(Messager.getPrefix() + ChatColor.DARK_AQUA + "/playtime " + ChatColor.WHITE + "- 본인의 플레이 시간 확인");
					player.sendMessage(Messager.getPrefix() + ChatColor.DARK_AQUA + "/playtime " + ChatColor.GRAY + "<대상> " + ChatColor.WHITE + " - 대상의 플레이 시간 확인");
				} else if (args[0].equalsIgnoreCase("search")) {
					if (player.isOp()) {
						if (args.length >= 2) {
							try {
								int ticks = Integer.parseInt(args[1]);
								player.sendMessage(ChatColor.GOLD + "유저들의 플레이 시간을 확인하고 있습니다...");
								CompletableFuture.runAsync(new Runnable() {
									@Override
									public void run() {
										try {
											List<UUID> players = getPlayersOver(ticks);
											StringJoiner joiner = new StringJoiner(", ");
											for (UUID uuid : players) {
												OfflinePlayer offline = Bukkit.getOfflinePlayer(uuid);
												if (offline.hasPlayedBefore()) joiner.add(offline.getName());
											}
											new BukkitRunnable() {
												@Override
												public void run() {
													player.sendMessage(Messager.getPrefix() + ChatColor.DARK_AQUA + TimeUtil.parseTicks(ticks) + " " + ChatColor.WHITE + "이상 플레이한 유저 목록");
													player.sendMessage(joiner.toString());
												}
											}.runTask(Mirsv.getPlugin());
										} catch (IOException | ExecutionException | InterruptedException e) {
											e.printStackTrace();
										}
									}
								});
							} catch (NumberFormatException e) {
								player.sendMessage(ChatColor.RED + "자연수로 입력되어야 합니다.");
							}
						} else {
							player.sendMessage(ChatColor.RED + "사용법: /playtime search <틱>");
						}
					} else {
						player.sendMessage(ChatColor.RED + "권한이 부족합니다.");
					}
				} else {
					if (!user.PLAYTIME.isCooldown()) {
						OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
						if (!target.hasPlayedBefore()) {
							player.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + "존재하지 않는 플레이어입니다.");
						} else {
							user.PLAYTIME.action();
							int ticks = 0;
							try {
								ticks = parser.parse(new FileReader("world/stats/" + target.getUniqueId() + ".json")).getAsJsonObject().get("stat.playOneMinute").getAsInt();
							} catch (IOException ignored) {
							}
							player.sendMessage(Messager.getPrefix() + ChatColor.WHITE + target.getName() + ChatColor.AQUA + "님의 플레이 시간: " + ChatColor.DARK_AQUA + TimeUtil.parseTicks(ticks));
						}
					} else {
						sender.sendMessage(ChatColor.WHITE + TimeUtil.toString(user.PLAYTIME.getLeftCooldown()) + ChatColor.RED + " 뒤에 시도해주세요.");
					}
				}
			}
		} else {
			sender.sendMessage(ChatColor.RED + "콘솔에서 사용할 수 없는 명령어입니다.");
		}
		return true;
	}

	public static List<UUID> getPlayersOver(int ticks) throws IOException, ExecutionException, InterruptedException {
		final List<UUID> players = new LinkedList<>();
		for (File file : new File("world/stats").listFiles()) {
			if (parser.parse(new BufferedReader(new FileReader(file))).getAsJsonObject().get("stat.playOneMinute").getAsInt() >= ticks) players.add(UUID.fromString(FilenameUtils.removeExtension(file.getName())));
		}
		return players;
	}

}
