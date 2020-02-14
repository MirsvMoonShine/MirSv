package com.mirsv.function.list.daybreak.warning;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mirsv.function.AbstractFunction;
import com.mirsv.util.Messager;
import com.mirsv.util.users.User;
import com.mirsv.util.users.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.StringJoiner;

public class WarningManager extends AbstractFunction implements CommandExecutor {

	private static final String prefix = ChatColor.translateAlternateColorCodes('&', "&4[&c경고&4] &f");

	public WarningManager() {
		super("경고 매니저", "1.0.0", "경고 시스템 관리");
	}

	@Override
	protected void onEnable() {
		registerCommand("경고", this);
		registerCommand("경고관리", this);
	}

	@Override
	protected void onDisable() {

	}

	public static JsonArray getWarnings(User user) {
		JsonObject json = user.getConfig().getJson();
		if (!json.has("warnings")) json.add("warnings", new JsonArray());
		return json.getAsJsonArray("warnings");
	}

	public static JsonArray getWarnings(OfflinePlayer player) {
		return getWarnings(UserManager.getUser(player));
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		switch (label) {
			case "경고":
				if (sender instanceof Player) {
					Player player = (Player) sender;
					User user = UserManager.getUser(player);
					JsonArray warnings = getWarnings(user);
					if (args.length == 0) {
						player.sendMessage(Messager.formatTitle(ChatColor.RED, ChatColor.WHITE, "경고"));
						player.sendMessage(ChatColor.RED + "경고" + ChatColor.WHITE + ": " + ChatColor.RED + warnings.size() + ChatColor.WHITE + "회 경고를 받으셨으며, 총 경고 개수는 " + ChatColor.RED + getWarnings(warnings) + ChatColor.WHITE + "개입니다.");
						player.sendMessage(ChatColor.RED + "특정 경고" + ChatColor.WHITE + "를 자세히 보고 싶으시다면, " + ChatColor.YELLOW + "/경고 <#>" + ChatColor.WHITE + " 명령어를 사용하세요.");
					} else {
						try {
							int index = Integer.parseInt(args[0]) - 1;
							if (index >= 0) {
								if (warnings.size() > index) {
									JsonObject warning = warnings.get(index).getAsJsonObject();
									player.sendMessage(Messager.formatTitle(ChatColor.RED, ChatColor.WHITE, "경고 " + ChatColor.RED + "(" + (index + 1) + "/" + warnings.size() + ")"));
									showWarning(player, warning);
								} else {
									player.sendMessage(ChatColor.RED + "존재하지 않는 경고입니다.");
								}
							} else {
								player.sendMessage(ChatColor.RED + "자연수로 입력되어야 합니다.");
							}
						} catch (NumberFormatException e) {
							player.sendMessage(ChatColor.RED + "자연수로 입력되어야 합니다.");
						}
					}
				} else {
					sender.sendMessage(ChatColor.RED + "콘솔에서 사용할 수 없는 명령어입니다.");
				}
				break;
			case "경고관리":
				if (sender.hasPermission("mirsv.warn")) {
					if (args.length == 0) {
						sender.sendMessage(Messager.formatTitle(ChatColor.RED, ChatColor.WHITE, "경고 관리"));
						sender.sendMessage(ChatColor.RED + "/경고관리 확인 " + ChatColor.WHITE + "<대상> [#]");
						sender.sendMessage(ChatColor.RED + "/경고관리 부여 " + ChatColor.WHITE + "<대상> <개수> <사유>");
						sender.sendMessage(ChatColor.RED + "/경고관리 제거 " + ChatColor.WHITE + "<대상> [#]");
					} else {
						switch (args[0]) {
							case "확인":
								if (args.length > 1) {
									OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
									if (player.hasPlayedBefore()) {
										User user = UserManager.getUser(player);
										JsonArray warnings = getWarnings(user);
										if (args.length == 2) {
											sender.sendMessage(Messager.formatTitle(ChatColor.RED, ChatColor.WHITE, ChatColor.YELLOW + player.getName() + ChatColor.WHITE + "의 경고"));
											sender.sendMessage(ChatColor.RED + "경고" + ChatColor.WHITE + ": " + ChatColor.RED + warnings.size() + ChatColor.WHITE + "회 경고를 받으셨으며, 총 경고 개수는 " + ChatColor.RED + getWarnings(warnings) + ChatColor.WHITE + "개입니다.");
										} else {
											try {
												int index = Integer.parseInt(args[2]) - 1;
												if (index >= 0) {
													if (warnings.size() > index) {
														JsonObject warning = warnings.get(index).getAsJsonObject();
														sender.sendMessage(Messager.formatTitle(ChatColor.RED, ChatColor.WHITE, ChatColor.YELLOW + player.getName() + ChatColor.WHITE + "의 경고 " + ChatColor.RED + "(" + (index + 1) + "/" + warnings.size() + ")"));
														showWarning(sender, warning);
													} else {
														sender.sendMessage(ChatColor.RED + "존재하지 않는 경고입니다.");
													}
												} else {
													sender.sendMessage(ChatColor.RED + "자연수로 입력되어야 합니다.");
												}
											} catch (NumberFormatException e) {
												sender.sendMessage(ChatColor.RED + "자연수로 입력되어야 합니다.");
											}
										}
									} else {
										sender.sendMessage(ChatColor.RED + args[1] + "은(는) 존재하지 않는 플레이어입니다.");
									}
								} else {
									sender.sendMessage(ChatColor.DARK_RED + "사용법: " + ChatColor.RED + "/경고관리 확인 " + ChatColor.WHITE + "<대상> [#]");
								}
								break;
							case "부여":
								if (args.length > 3) {
									OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
									if (player.hasPlayedBefore()) {
										try {
											int amount = Integer.parseInt(args[2]);
											StringJoiner joiner = new StringJoiner(" ");
											for (int i = 3; i < args.length; i++) {
												joiner.add(args[i]);
											}
											addWarning(getWarnings(player), sender, amount, joiner.toString());
											sender.sendMessage(prefix + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + "님에게 경고를 성공적으로 부여했습니다.");
											if (player.isOnline()) player.getPlayer().sendMessage(prefix + "경고 " + ChatColor.RED + amount + "개" + ChatColor.WHITE + "를 받았습니다.");
										} catch (NumberFormatException e) {
											sender.sendMessage(ChatColor.RED + "자연수로 입력되어야 합니다.");
										}
									} else {
										sender.sendMessage(ChatColor.RED + args[1] + "은(는) 존재하지 않는 플레이어입니다.");
									}
								} else {
									sender.sendMessage(ChatColor.DARK_RED + "사용법: " + ChatColor.RED + "/경고관리 부여 " + ChatColor.WHITE + "<대상> <개수> <사유>");
								}
								break;
							case "제거":
								if (args.length > 2) {
									OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
									if (player.hasPlayedBefore()) {
										User user = UserManager.getUser(player);
										JsonArray warnings = getWarnings(user);
										try {
											int index = Integer.parseInt(args[2]) - 1;
											if (index >= 0) {
												if (warnings.size() > index) {
													removeWarning(warnings, index);
													sender.sendMessage(prefix + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + "님의 경고를 성공적으로 제거했습니다.");
													if (player.isOnline()) player.getPlayer().sendMessage(prefix + ChatColor.RED + (index + 1) + "번째 " + ChatColor.WHITE + "경고가 제거되었습니다.");
												} else {
													sender.sendMessage(ChatColor.RED + "존재하지 않는 경고입니다.");
												}
											} else {
												sender.sendMessage(ChatColor.RED + "자연수로 입력되어야 합니다.");
											}
										} catch (NumberFormatException e) {
											sender.sendMessage(ChatColor.RED + "자연수로 입력되어야 합니다.");
										}
									} else {
										sender.sendMessage(ChatColor.RED + args[1] + "은(는) 존재하지 않는 플레이어입니다.");
									}
								} else {
									sender.sendMessage(ChatColor.DARK_RED + "사용법: " + ChatColor.RED + "/경고관리 제거 " + ChatColor.WHITE + "<대상> [#]");
								}
								break;
							default:
								sender.sendMessage(ChatColor.RED + "존재하지 않는 서브 메시지입니다.");
								break;
						}
					}
				} else {
					sender.sendMessage(ChatColor.RED + "권한이 부족합니다.");
				}
				break;
		}
		return true;
	}

	public static int getWarnings(JsonArray warnings) {
		int warning = 0;
		for (JsonElement element : warnings) {
			warning += element.getAsJsonObject().get("amount").getAsInt();
		}
		return warning;
	}

	public static void addWarning(JsonArray target, CommandSender admin, int amount, String reason) {
		JsonObject warning = new JsonObject();
		warning.addProperty("admin", admin.getName());
		warning.addProperty("amount", amount);
		warning.addProperty("reason", ChatColor.translateAlternateColorCodes('&', reason));
		target.add(warning);
	}

	public static void removeWarning(JsonArray target, int index) {
		target.remove(index);
	}

	public static void showWarning(CommandSender target, JsonObject warning) {
		target.sendMessage(ChatColor.RED + "담당" + ChatColor.WHITE + ": " + ChatColor.RED + "관리자 " + ChatColor.WHITE + warning.get("admin").getAsString());
		target.sendMessage(ChatColor.RED + "경고" + ChatColor.WHITE + ": " + ChatColor.RED + warning.get("amount").getAsInt() + "회");
		target.sendMessage(ChatColor.RED + "사유" + ChatColor.WHITE + ": " + warning.get("reason").getAsString());
	}

}
