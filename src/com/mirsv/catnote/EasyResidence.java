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
		getCommand("�ε���", this);
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if((getConfig().getBoolean("enable.EasyResidence", true)) && ((sender instanceof Player))) {
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("����")) Bukkit.dispatchCommand(sender, "res select cost");
				else if(args[0].equalsIgnoreCase("���")) Bukkit.dispatchCommand(sender, "res list");
				else if(args[0].equalsIgnoreCase("����")) Bukkit.dispatchCommand(sender, "res info");
				else if(args[0].equalsIgnoreCase("�÷���")) Bukkit.dispatchCommand(sender, "res flags");
				else sender.sendMessage(ChatColor.RED + "\'/��ɾ� �ε���\' �Ǵ� \'/��ɾ� �ε��� 2\'�� ���� ��ɾ Ȯ���غ�����!");
			}
			else if(args.length == 2) {
				if(args[0].equalsIgnoreCase("����")) {
					Bukkit.dispatchCommand(sender, "res create " + args[1]);
					Bukkit.dispatchCommand(sender, "res padd " + args[1] + " " + sender.getName());
					Bukkit.dispatchCommand(sender, "res pset " + args[1] + " " + sender.getName() + " destroy true");
				}
				else if(args[0].equalsIgnoreCase("����")) {
					Bukkit.dispatchCommand(sender, "res remove " + args[1]);
					Bukkit.dispatchCommand(sender, "res confirm");
				}
				else if(args[0].equalsIgnoreCase("����")) Bukkit.dispatchCommand(sender, "res info " + args[1]);
				else if(args[0].equalsIgnoreCase("�÷���")) Bukkit.dispatchCommand(sender, "res flags " + args[1]);
				else sender.sendMessage(ChatColor.RED + "\'/��ɾ� �ε���\' �Ǵ� \'/��ɾ� �ε��� 2\'�� ���� ��ɾ Ȯ���غ�����!");
			}
			else if(args.length == 3) {
				if(args[0].equalsIgnoreCase("�����ֱ�")) {
					Bukkit.dispatchCommand(sender, "res padd " + args[1] + " " + args[2]);
					Bukkit.dispatchCommand(sender, "res pset " + args[1] + " " + args[2] + " destroy true");
				}
				else if(args[0].equalsIgnoreCase("���ѻ���")) {
					Bukkit.dispatchCommand(sender, "res pdel " + args[1] + " " + args[2]);
					Bukkit.dispatchCommand(sender, "res pset " + args[1] + " " + args[2] + " destroy false");
				}
				else sender.sendMessage(ChatColor.RED + "\'/��ɾ� �ε���\' �Ǵ� \'/��ɾ� �ε��� 2\'�� ���� ��ɾ Ȯ���غ�����!");
			}
			else if(args.length == 5) {
				if(args[0].equalsIgnoreCase("���Ѽ���")) Bukkit.dispatchCommand(sender, "res pset " + args[1] + " " + args[2] + " " + args[3] + " " + args[4]);
				else sender.sendMessage(ChatColor.RED + "\'/��ɾ� �ε���\' �Ǵ� \'/��ɾ� �ε��� 2\'�� ���� ��ɾ Ȯ���غ�����!");
			}
			else sender.sendMessage(ChatColor.RED + "\'/��ɾ� �ε���\' �Ǵ� \'/��ɾ� �ε��� 2\'�� ���� ��ɾ Ȯ���غ�����!");
		}
		return false;
	}
}
