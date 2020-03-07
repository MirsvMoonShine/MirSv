package com.mirsv.function.list.daybreak;

import com.mirsv.Mirsv;
import com.mirsv.function.AbstractFunction;
import com.mirsv.util.Messager;
import com.mirsv.util.TimeUtil;
import com.mirsv.util.users.User;
import com.mirsv.util.users.User.Flag;
import com.mirsv.util.users.UserManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Convenience extends AbstractFunction implements CommandExecutor, Listener {

	public Convenience() {
		super("편의 기능", "1.0.0", "유저들의 편의를 위한 기능들");
	}

	private Economy economy;

	@Override
	protected void onEnable() {
		this.economy = Mirsv.getPlugin().getEconomy();
		registerCommand("아침", this);
		registerCommand("저녁", this);
		registerCommand("사탕수수", this);
		registerCommand("조용히", this);
		registerCommand("cl", this);
		registerCommand("forcetp", this);
		registerListener(this);
	}

	@Override
	protected void onDisable() {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		switch (label) {
			case "아침":
				if (sender instanceof Player) {
					Player player = (Player) sender;
					if (economy.has(player, 250)) {
						economy.withdrawPlayer(player, 250);
						updateTime(player.getWorld(), 0);
						for (Player target : player.getWorld().getPlayers()) {
							target.sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.WHITE + "님이 월드 시간을 " + ChatColor.AQUA + "아침" + ChatColor.WHITE + "으로 변경했습니다.");
						}
					} else {
						player.sendMessage(ChatColor.RED + "250원이 필요합니다.");
					}
				} else {
					sender.sendMessage(ChatColor.RED + "콘솔에서 사용할 수 없는 명령어입니다.");
				}
				break;
			case "저녁":
				if (sender instanceof Player) {
					Player player = (Player) sender;
					if (economy.has(player, 250)) {
						economy.withdrawPlayer(player, 250);
						updateTime(player.getWorld(), 14000);
						for (Player target : player.getWorld().getPlayers()) {
							target.sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.WHITE + "님이 월드 시간을 " + ChatColor.DARK_PURPLE + "저녁" + ChatColor.WHITE + "으로 변경했습니다.");
						}
					} else {
						player.sendMessage(ChatColor.RED + "250원이 필요합니다.");
					}
				} else {
					sender.sendMessage(ChatColor.RED + "콘솔에서 사용할 수 없는 명령어입니다.");
				}
				break;
			case "사탕수수":
				if (sender instanceof Player) {
					Player player = (Player) sender;
					User user = UserManager.getUser(player);
					user.toggleFlag(Flag.SUGAR_CANE_MODE);
					if (user.hasFlag(Flag.SUGAR_CANE_MODE)) {
						player.sendMessage(ChatColor.DARK_GREEN + "사탕수수 " + ChatColor.WHITE + "수확 모드를 " + ChatColor.GREEN + "활성화했습니다.");
					} else {
						player.sendMessage(ChatColor.DARK_GREEN + "사탕수수 " + ChatColor.WHITE + "수확 모드를 " + ChatColor.RED + "비활성화했습니다.");
					}
				} else {
					sender.sendMessage(ChatColor.RED + "콘솔에서 사용할 수 없는 명령어입니다.");
				}
				break;
			case "조용히":
				if (sender instanceof Player) {
					Player player = (Player) sender;
					User user = UserManager.getUser(player);
					user.toggleFlag(Flag.QUIET_MODE);
					if (user.hasFlag(Flag.QUIET_MODE)) {
						player.sendMessage("§c공지사항§f을 제외한 §a모든 채팅§f을 무시합니다.");
					} else {
						player.sendMessage("§c채팅§f을 더이상 무시하지 않습니다.");
					}
				} else {
					sender.sendMessage(ChatColor.RED + "콘솔에서 사용할 수 없는 명령어입니다.");
				}
				break;
			case "cl":
				if (args.length == 0) {
					sender.sendMessage(ChatColor.RED + "사용 방법" + ChatColor.WHITE + "  |  " + ChatColor.RED + "/" + label + " [대상] : [대상]을 호출합니다.");
				} else {
					if (sender instanceof Player) {
						User user = UserManager.getUser((Player) sender);
						if (!user.CALL_PLAYER.isCooldown()) {
							if (callPlayer(sender, args[0])) user.CALL_PLAYER.action();
						} else {
							sender.sendMessage(ChatColor.WHITE + TimeUtil.toString(user.CALL_PLAYER.getLeftCooldown()) + ChatColor.RED + " 뒤에 시도해주세요.");
						}
					} else {
						callPlayer(sender, args[0]);
					}
				}
				break;
			case "forcetp":
				if (sender.isOp()) {
					if (args.length < 4) {
						sender.sendMessage(ChatColor.RED + "사용 방법" + ChatColor.WHITE + "  |  " + ChatColor.RED + "/" + label + " <대상> <x> <y> <z>");
					} else {
						Player player = Bukkit.getPlayerExact(args[0]);
						if (player != null) {
							try {
								double x = Double.parseDouble(args[1]), y = Double.parseDouble(args[2]), z = Double.parseDouble(args[3]);
								try {
									player.teleport(new Location(player.getWorld(), x, y, z));
								} catch (Exception e) {
									sender.sendMessage(e.getClass().getName() + ": " + e.getMessage());
								}
							} catch (NumberFormatException e) {
								sender.sendMessage(ChatColor.RED + "좌표는 수로 입력되어야 합니다.");
							}
						} else {
							sender.sendMessage(ChatColor.RED + "존재하지 않는 플레이어입니다.");
						}
					}
				} else {
					sender.sendMessage(ChatColor.RED  + "권한이 부족합니다.");
				}
				break;
		}
		return true;
	}

	public static void updateTime(World world, long time) {
		long worldTime = world.getTime();
		long offset = (time - worldTime) / 30;
		new BukkitRunnable() {
			int count;

			@Override
			public void run() {
				world.setTime(world.getTime() + offset);
				if (++count >= 30) cancel();
			}
		}.runTaskTimer(Mirsv.getPlugin(), 0, 1);
	}

	public static boolean callPlayer(CommandSender sender, String name) {
		Player player = Bukkit.getPlayerExact(name);
		if (player != null) {
			if (!player.equals(sender)) {
				player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 10, 1);

				player.sendTitle(
						ChatColor.YELLOW + sender.getName() + ChatColor.WHITE + "님이 당신을 호출했습니다.",
						ChatColor.RED + "원하지 않는 호출이 계속될 경우 신고해주세요.",
						10, 65, 10);
				player.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + sender.getName() + ChatColor.WHITE + "님이 당신을 호출했습니다.");
				player.sendMessage(Messager.getPrefix() + ChatColor.RED + "원하지 않는 호출이 계속될 경우 신고해주세요.");
				sender.sendMessage(Messager.getPrefix() + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + "님을 호출했습니다.");
				return true;
			} else {
				sender.sendMessage(ChatColor.RED + "본인을 호출할 수 없습니다.");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "존재하지 않는 플레이어입니다.");
		}
		return false;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onBlockBreak(BlockBreakEvent e) {
		User user = UserManager.getUser(e.getPlayer());
		if (user.hasFlag(Flag.SUGAR_CANE_MODE) && e.getBlock().getType() == Material.SUGAR_CANE && e.getBlock().getRelative(BlockFace.DOWN).getType() != Material.SUGAR_CANE) {
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onJoin(PlayerJoinEvent e) {
		e.setJoinMessage(ChatColor.DARK_GREEN + "+" + ChatColor.WHITE + " | " + ChatColor.GREEN + e.getPlayer().getName() + ChatColor.WHITE + "님이 접속하셨습니다.");
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onQuit(PlayerQuitEvent e) {
		e.setQuitMessage(ChatColor.DARK_RED + "-" + ChatColor.WHITE + " | " + ChatColor.RED + e.getPlayer().getName() + ChatColor.WHITE + "님이 퇴장하셨습니다.");
	}

}
