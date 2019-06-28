package com.mirsv.function.list.Cokes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.mirsv.function.AbstractFunction;
import com.mirsv.util.Messager;
import com.mirsv.util.data.FileUtil;

public class WordProhibition extends AbstractFunction implements Listener, CommandExecutor {
	
	private List<String> forbidden = new ArrayList<String>();
	private File folder = FileUtil.getFolder("ForbiddenWord");
	private File f = FileUtil.getFile("ForbiddenWord/ForbiddenWord.yml");
	
	public WordProhibition() {
		super("��Ģ��", "1.0", "��Ģ� �����մϴ�.", "��Ģ��� ������ �ܾ�� ä�ÿ��� ����� �� �����ϴ�.");
	}

	@Override
	protected void onEnable() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String s = br.readLine();
			if (s != null) {String[] str = s.split("_");
			for (String add : str) forbidden.add(add); }
			br.close();
		} catch (IOException e) {}
		registerCommand("forbidden", this);
		registerListener(this);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChat(AsyncPlayerChatEvent e){
		String message = e.getMessage();
		for (String forb : forbidden){
			if (message.contains(forb)){
				e.setCancelled(true);
				e.getPlayer().sendMessage(Messager.getPrefix() + "��Ģ��� ������ �ܾ ���Ե��ֽ��ϴ�.");
				break;
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		
		if (player.isOp() || player.hasPermission("mirsv.admin")) {
			if (args.length > 0) {
				if ((args[0].equalsIgnoreCase("add")) || (args[0].equalsIgnoreCase("�߰�"))) {
					if (args.length == 2){
						if (!forbidden.contains(args[1])){
							forbidden.add(args[1]);
							try {
								BufferedReader br = new BufferedReader(new FileReader(f));
								String s = br.readLine();
								BufferedWriter bw = new BufferedWriter(new FileWriter(f));
								bw.write(s+args[1]+"_");
								br.close();
								bw.close();
							} catch (IOException e) {}
							player.sendMessage(Messager.getPrefix()+"��Ģ� "+args[1]+ "��(��) �߰��Ǿ����ϴ�.");
						}
					}
				} else if ((args[0].equalsIgnoreCase("delete")) || (args[0].equalsIgnoreCase("����"))) {
					if (args.length == 2){
						if (forbidden.contains(args[1])){
							forbidden.remove(args[1]);
							f.delete();
							try {
								f.createNewFile();
								BufferedWriter br = new BufferedWriter(new FileWriter(f));
								for (String s : forbidden) br.write(s + "_");
								br.close();
							} catch (IOException e) {}
							player.sendMessage(Messager.getPrefix()+ args[1]+ "��(��) ���̻� ��Ģ� �ƴմϴ�.");
						}
					}
				} else if ((args[0].equalsIgnoreCase("list")) || (args[0].equalsIgnoreCase("���"))) {
					if (args.length == 1){
						if (forbidden.size() != 0){
							String s = forbidden.get(0);
							for (int a = 1;a<forbidden.size();a++) s = s+", "+forbidden.get(a);
							player.sendMessage(Messager.getPrefix()+ "��Ģ�� ���");
							player.sendMessage(Messager.getPrefix()+ s);
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	protected void onDisable() {}
	
}
