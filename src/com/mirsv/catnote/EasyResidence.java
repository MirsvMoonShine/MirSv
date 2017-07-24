package com.mirsv.catnote;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mirsv.MirPlugin;

import net.md_5.bungee.api.ChatColor;

public class EasyResidence extends MirPlugin implements CommandExecutor{
	public EasyResidence() {
		getCommand("부동산", this);
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if((getConfig().getBoolean("enable.EasyResidence", true)) && ((sender instanceof Player))) {
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("가격")) Bukkit.dispatchCommand(sender, "res select cost");
				else if(args[0].equalsIgnoreCase("목록")) Bukkit.dispatchCommand(sender, "res list");
				else if(args[0].equalsIgnoreCase("정보")) Bukkit.dispatchCommand(sender, "res info");
				else if(args[0].equalsIgnoreCase("플래그")) Bukkit.dispatchCommand(sender, "res flags");
				else sender.sendMessage(ChatColor.RED + "\'/명령어 부동산\' 또는 \'/명령어 부동산 2\'를 통해 명령어를 확인해보세요!");
			}
			else if(args.length == 2) {
				if(args[0].equalsIgnoreCase("생성")) {
					Bukkit.dispatchCommand(sender, "res create " + args[1]);
					Bukkit.dispatchCommand(sender, "res padd " + args[1] + " " + sender.getName());
					Bukkit.dispatchCommand(sender, "res pset " + args[1] + " " + sender.getName() + " destroy true");
				}
				else if(args[0].equalsIgnoreCase("삭제")) {
					Bukkit.dispatchCommand(sender, "res remove " + args[1]);
					Bukkit.dispatchCommand(sender, "res confirm");
				}
				else if(args[0].equalsIgnoreCase("정보")) Bukkit.dispatchCommand(sender, "res info " + args[1]);
				else if(args[0].equalsIgnoreCase("플래그")) Bukkit.dispatchCommand(sender, "res flags " + args[1]);
				else sender.sendMessage(ChatColor.RED + "\'/명령어 부동산\' 또는 \'/명령어 부동산 2\'를 통해 명령어를 확인해보세요!");
			}
			else if(args.length == 3) {
				if(args[0].equalsIgnoreCase("권한주기")) {
					Bukkit.dispatchCommand(sender, "res padd " + args[1] + " " + args[2]);
					Bukkit.dispatchCommand(sender, "res pset " + args[1] + " " + args[2] + " destroy true");
				}
				else if(args[0].equalsIgnoreCase("권한뺏기")) {
					Bukkit.dispatchCommand(sender, "res pdel " + args[1] + " " + args[2]);
					Bukkit.dispatchCommand(sender, "res pset " + args[1] + " " + args[2] + " destroy false");
				}
				else sender.sendMessage(ChatColor.RED + "\'/명령어 부동산\' 또는 \'/명령어 부동산 2\'를 통해 명령어를 확인해보세요!");
			}
			else if(args.length == 5) {
				if(args[0].equalsIgnoreCase("권한설정")) Bukkit.dispatchCommand(sender, "res pset " + args[1] + " " + args[2] + " " + args[3] + " " + args[4]);
				else sender.sendMessage(ChatColor.RED + "\'/명령어 부동산\' 또는 \'/명령어 부동산 2\'를 통해 명령어를 확인해보세요!");
			}
			else sender.sendMessage(ChatColor.RED + "\'/명령어 부동산\' 또는 \'/명령어 부동산 2\'를 통해 명령어를 확인해보세요!");
		}
		return false;
	}
}
