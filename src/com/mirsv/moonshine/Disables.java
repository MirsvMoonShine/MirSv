package com.mirsv.moonshine;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;

import com.mirsv.MirPlugin;

public class Disables extends MirPlugin implements Listener{ // 신세계에 오신걸 환영합니다
	String prefix = ChatColor.GOLD + "[" + ChatColor.GREEN + "미르서버" + ChatColor.GOLD + "] " + ChatColor.RESET;
	List<String> discommand;
	List <Integer> disblocks, disrecipe;
	
	public Disables(){
		getListener(this);
		
		discommand = getConfig().getStringList("Disables.Commands");
		if (discommand.isEmpty()){
			discommand.add("mirsv");
			getConfig().set("Disables.Commands", discommand);
			saveConfig();
		}
		
		disblocks = getConfig().getIntegerList("Disables.Place");
		if (disblocks.isEmpty()){
			disblocks.add(46);
			disblocks.add(408);
			getConfig().addDefault("Disables.Place", disblocks);
			saveConfig();
		}
		
		disrecipe = getConfig().getIntegerList("Disables.Recipe");
		if (disrecipe.isEmpty()){
			disrecipe.add(46);
			getConfig().set("Disables.Recipe", disrecipe);
			saveConfig();
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCommand(PlayerCommandPreprocessEvent event){
		String[] Array = event.getMessage().substring(1).split(" ");
		Player player = event.getPlayer();
		if (getConfig().getBoolean("enable.Disables", true)) {
			if (!player.isOp()){
				for (int a = 0; a < discommand.size(); a++){
					String loaded = discommand.get(a);
					String[] command = loaded.split(" ");
					if (Array.length >= command.length){
						int control = command.length;
						for (int b=0;  b < command.length; b++){
							if (Array[b].equalsIgnoreCase(command[b])) control -= 1;
						}
						
						if (control == 0){
							event.setCancelled(true);
							player.sendMessage(prefix+"/"+loaded+" 명령어를 사용할 수 없습니다.");
						}
					}
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	@SuppressWarnings("deprecation")
	public void onPlayerBlockPlaceEvent(BlockPlaceEvent e) {
		if (getConfig().getBoolean("enable.Disables", true)) {
			Player p = e.getPlayer();
			for (int block: disblocks) {
				if (e.getBlock().getTypeId() == block) {
					e.setCancelled(true);
					p.sendMessage(prefix+"§c이 아이템은 놓을 수 없습니다.");
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void RecipeEvent(CraftItemEvent e){
		Player p = (Player) e.getWhoClicked();
		ItemStack item = e.getCurrentItem();
		if (getConfig().getBoolean("enable.Disables", true)) {
			for (int itemcode : disrecipe){
				if (item.getTypeId() == itemcode){
					p.sendMessage(prefix+"그 아이템은 미르서버에서 조합할 수 없는 아이템 입니다.");
					e.setCancelled(true);
				}
			}
		}
	}
}
