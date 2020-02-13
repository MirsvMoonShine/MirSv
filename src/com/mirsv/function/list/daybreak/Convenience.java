package com.mirsv.function.list.daybreak;

import com.mirsv.Mirsv;
import com.mirsv.function.AbstractFunction;
import com.mirsv.util.users.User;
import com.mirsv.util.users.User.Flag;
import com.mirsv.util.users.UserManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
		registerListener(this);
	}

	@Override
	protected void onDisable() {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			switch (label) {
				case "아침":
					if (economy.has(player, 250)) {
						economy.withdrawPlayer(player, 250);
						updateTime(player.getWorld(), 0);
						for (Player target : player.getWorld().getPlayers()) {
							target.sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.WHITE + "님이 월드 시간을 " + ChatColor.AQUA + "아침" + ChatColor.WHITE + "으로 변경했습니다.");
						}
					} else {
						player.sendMessage(ChatColor.RED + "250원이 필요합니다.");
					}
					break;
				case "저녁":
					if (economy.has(player, 250)) {
						economy.withdrawPlayer(player, 250);
						updateTime(player.getWorld(), 14000);
						for (Player target : player.getWorld().getPlayers()) {
							target.sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.WHITE + "님이 월드 시간을 " + ChatColor.DARK_PURPLE + "저녁" + ChatColor.WHITE + "으로 변경했습니다.");
						}
					} else {
						player.sendMessage(ChatColor.RED + "250원이 필요합니다.");
					}
					break;
				case "사탕수수":
					User user = UserManager.getUser(player);
					user.toggleFlag(Flag.SUGAR_CANE_MODE);
					if (user.hasFlag(Flag.SUGAR_CANE_MODE)) {
						player.sendMessage(ChatColor.DARK_GREEN  + "사탕수수 " + ChatColor.WHITE + "수확 모드를 " + ChatColor.GREEN + "활성화했습니다.");
					} else {
						player.sendMessage(ChatColor.DARK_GREEN  + "사탕수수 " + ChatColor.WHITE + "수확 모드를 " + ChatColor.RED + "비활성화했습니다.");
					}
					break;
			}
		} else {
			sender.sendMessage(ChatColor.RED + "콘솔에서 사용할 수 없는 명령어입니다.");
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

	@EventHandler(priority = EventPriority.NORMAL)
	private void onBlockBreak(BlockBreakEvent e) {
		User user = UserManager.getUser(e.getPlayer());
		if (user.hasFlag(Flag.SUGAR_CANE_MODE) && e.getBlock().getType() == Material.SUGAR_CANE_BLOCK && e.getBlock().getRelative(BlockFace.DOWN).getType() != Material.SUGAR_CANE_BLOCK) {
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
