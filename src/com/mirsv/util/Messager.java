package com.mirsv.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Messager {
	
	private Messager() {}
	
	private static final String Prefix = ChatColor.translateAlternateColorCodes('&', "&3[&bMirsv&3]&f ");
	
	public static String getPrefix() {
		return Prefix;
	}
	
	/**
	 * 콘솔에 메시지를 전송합니다.
	 */
	public static void sendMessage(String msg) {
		Bukkit.getConsoleSender().sendMessage(Prefix + msg);
	}
	
	/**
	 * 콘솔에 메시지를 전송합니다.
	 */
	public static void sendMessage(List<String> messages) {
		for(String msg : messages) {
			sendMessage(msg);
		}
	}

	/**
	 * 플레이어에게 메시지를 전송합니다.
	 */
	public static void sendMessage(Player p, String msg) {
		p.sendMessage(msg);
	}
	
	/**
	 * 명령어를 실행한 객체에게 메시지를 전송합니다.
	 */
	public static void sendMessage(CommandSender sender, String msg) {
		sender.sendMessage(msg);
	}
	
	/**
	 * 콘솔에 오류 메시지를 전송합니다.
	 */
	public static void sendErrorMessage(String msg) {
		System.out.println(ChatColor.translateAlternateColorCodes('&', "&f&l[&c&l!&f&l] &r&c" + msg));
	}

	/**
	 * 콘솔에 오류 메시지를 전송합니다.
	 */
	public static void sendErrorMessage() {
		System.out.println(ChatColor.translateAlternateColorCodes('&', "&f&l[&c&l!&f&l] &r&c오류가 발생하였습니다."));
	}
	
	/**
	 * 플레이어에게 오류 메시지를 전송합니다.
	 */
	public static void sendErrorMessage(Player p, String msg) {
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[&c&l!&f&l] &f") + msg);
	}
	
	/**
	 * 명령어를 실행한 객체에게 오류 메시지를 전송합니다.
	 */
	public static void sendErrorMessage(CommandSender sender, String msg) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[&c&l!&f&l] &f") + msg);
	}
	
	/**
	 * 오류 메시지를 공지합니다.
	 */
	public static void broadcastErrorMessage(String msg) {
		broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[&c&l!&f&l] &f") + msg);
	}
	
	/**
	 * 메시지를 공지합니다.
	 */
	public static void broadcastMessage(String msg) {
		for(Player p : Bukkit.getOnlinePlayers()) p.sendMessage(msg);
		System.out.println(msg);
	}

	public static void broadcastMessage(List<String> messages) {
		for(String msg : messages) {
			broadcastMessage(msg);
		}
	}
	
	/**
	 * 제목을 구성합니다.
	 */
	public static String formatTitle(String title) {
		String Base = "___________________________________________________________________";
		int Pivot = Base.length() / 2;
		String Center = ChatColor.translateAlternateColorCodes('&', "[ " + "&e" + title + "&6" + " ]&m&l");
		String Return = ChatColor.translateAlternateColorCodes('&', "&6&m&l" + Base.substring(0, Math.max(0, (Pivot - Center.length() / 2))) + "&r&6");
		Return += Center + Base.substring(Pivot + Center.length() / 2);
		return Return;
	}
	
	/**
	 * 짧은 제목을 구성합니다.
	 */
	public static String formatShortTitle(String title) {
		String Base = "_____________________________________";
		int Pivot = Base.length() / 2;
		String Center = ChatColor.translateAlternateColorCodes('&', "[ " + "&e" + title + "&6" + " ]&m&l");
		String Return = ChatColor.translateAlternateColorCodes('&', "&6&m&l" + Base.substring(0, Math.max(0, (Pivot - Center.length() / 2))) + "&r&6");
		Return += Center + Base.substring(Pivot + Center.length() / 2);
		return Return;
	}

	/**
	 * 제목을 구성합니다.
	 */
	public static String formatTitle(ChatColor First, ChatColor Second, String title) {
		String Base = "___________________________________________________________________";
		int Pivot = Base.length() / 2;
		String Center = ChatColor.translateAlternateColorCodes('&', "[ " + Second + title + First + " ]&m&l");
		String Return = ChatColor.translateAlternateColorCodes('&', First + "&m&l" + Base.substring(0, Math.max(0, (Pivot - Center.length() / 2))) + "&r" + First);
		Return += Center + Base.substring(Pivot + Center.length() / 2);
		return Return;
	}

	/**
	 * 짧은 제목을 구성합니다.
	 */
	public static String formatShortTitle(ChatColor First, ChatColor Second, String title) {
		String Base = "_____________________________________";
		int Pivot = Base.length() / 2;
		String Center = ChatColor.translateAlternateColorCodes('&', "[ " + Second + title + First + " ]&m&l");
		String Return = ChatColor.translateAlternateColorCodes('&', First + "&m&l" + Base.substring(0, Math.max(0, (Pivot - Center.length() / 2))) + "&r" + First);
		Return += Center + Base.substring(Pivot + Center.length() / 2);
		return Return;
	}

	/**
	 * 명령어 도움말을 구성합니다.
	 */
	public static String formatCommand(String Label, String Command, String Help, boolean AdminCommand) {
		if(!AdminCommand) {
			return ChatColor.translateAlternateColorCodes('&', "&a유  저: &6/" + Label + " &e" + Command + " &7: &f" + Help);
		} else {
			return ChatColor.translateAlternateColorCodes('&', "&c관리자: &6/" + Label + " &e" + Command + " &7: &f" + Help);
		}
	}

	/**
	 * 명령어 도움말을 구성합니다.
	 */
	public static String formatCommand(String Label, String Command, String Help) {
		return ChatColor.translateAlternateColorCodes('&', "&6/" + Label + " &e" + Command + " &7: &f" + Help);
	}

	/**
	 * String ArrayList를 만듭니다.
	 */
	public static ArrayList<String> getStringList(String[] arr, String... str) {
		ArrayList<String> Return = new ArrayList<String>();
		for(String s : arr) {
			Return.add(s);
		}
		
		for(String s : str) {
			Return.add(s);
		}
		
		return Return;
	}
	
	/**
	 * String ArrayList를 만듭니다.
	 */
	public static ArrayList<String> getStringList(String... str) {
		ArrayList<String> Return = new ArrayList<String>();
		for(String s : str) {
			Return.add(s);
		}
		
		return Return;
	}
	
	/**
	 * 메시지 목록을 공지합니다.
	 */
	public static void broadcastStringList(List<String> msg) {
		for(String s : msg) {
			broadcastMessage(s);
		}
	}
	
	/**
	 * 명령어를 실행한 객체에게 메시지 목록을 전송합니다.
	 */
	public static void sendStringList(CommandSender sender, ArrayList<String> msg) {
		for(String s : msg) {
			sender.sendMessage(s);
		}
	}

	/**
	 * 플레이어에게 메시지 목록을 전송합니다.
	 */
	public static void sendStringList(Player p, ArrayList<String> msg) {
		for(String s : msg) {
			p.sendMessage(s);
		}
	}
	
	/**
	 * String 배열에서 첫번째 인수를 삭제합니다.
	 */
	public static String[] removeFirstArg(String[] args) {
		return removeArgs(args, 1);
	}
	
	/**
	 * String 배열에서 인수를 삭제합니다.
	 */
	public static String[] removeArgs(String[] args, int startIndex) {
		if (args.length == 0)
			return args;
		else if (args.length < startIndex)
			return new String[0];
		else {
			String[] newArgs = new String[args.length - startIndex];
			System.arraycopy(args, startIndex, newArgs, 0, args.length - startIndex);
			return newArgs;
		}
	}

}
