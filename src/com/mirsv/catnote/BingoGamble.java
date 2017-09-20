package com.mirsv.catnote;
import com.mirsv.MirPlugin;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
public class BingoGamble extends MirPlugin implements Listener, CommandExecutor {
    int N_Casino;
    static int[] Records = new int[11];
    ArrayList <gRoom> CasinoList = new ArrayList <gRoom>();
    ArrayList <ItemStack> ItemList = new ArrayList <ItemStack>();
    @EventHandler
    public void onBlockPistonExtend(BlockPistonExtendEvent e) {
        for(int i = 0; i < N_Casino; i++) {
	        if(e.getBlock().getLocation().getBlockX() == CasinoList.get(i).PistonX && e.getBlock().getLocation().getBlockY() == CasinoList.get(i).PistonY && e.getBlock().getLocation().getBlockZ() == CasinoList.get(i).PistonZ && e.getBlock().getWorld().getName().equalsIgnoreCase("spawn")) {
	        	Gamble(i);
	        	break;
	        }
        }
    }
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
    	if((getConfig().getBoolean("enable.BingoGamble", true)) && ((sender instanceof Player))) {
    		Player player = (Player) sender;
    		if(!player.isOp()) return false;
    		int Sum = 0;
    		for(int i = 0; i < 11; i++) Sum += Records[i];
    		if(Sum == 0) Sum = 1;
    		player.sendMessage(ChatColor.GRAY + "------- " + ChatColor.BLUE + "BingoGamble Stats" + ChatColor.GRAY + " -------");
    		for(int i = 0; i < 11; i++) {
    			double Rate = Math.round((double) Records[i] / Sum * 10000) / 100d;
    			player.sendMessage(ChatColor.AQUA + "" + i + " Bingo - " + Records[i] + " (" + Rate + "%)");
    		}
    	}
	    return false;
	}
	public void Gamble(int Num) {
	    int[][] Grid = new int[4][4];
	    for(int i = 0; i < 4; i++) {
	        for(int j = 0; j < 4; j++) {
	            Location loc = new Location(Bukkit.getWorld("spawn"), CasinoList.get(Num).GridX, CasinoList.get(Num).GridYmin + i, CasinoList.get(Num).GridZmin + j);
	            int r = (int)(Math.random() * 2);
	           	if(r == 0) loc.getBlock().setType(Material.IRON_BLOCK);
	           	else loc.getBlock().setType(Material.GOLD_BLOCK);
	           	Grid[i][j] = r;
	        }
	    }
		int Bingo = 0;
		for(int i = 0; i < 4; i++) {
		    int cnt = 0;
		    for(int j = 0; j < 4; j++) cnt += Grid[i][j];
		    if(cnt == 0 || cnt == 4) Bingo++;
		    cnt = 0;
		    for(int j = 0; j < 4; j++) cnt += Grid[j][i];
		    if(cnt == 0 || cnt == 4) Bingo++;
		}
		int cnt = 0;
		for(int i = 0; i < 4; i++) cnt += Grid[i][i];
		if(cnt == 0 || cnt == 4) Bingo++;
		cnt = 0;
		for(int i = 0; i < 4; i++) cnt += Grid[i][3 - i];
		if(cnt == 0 || cnt == 4) Bingo++;
		Player Gambler = null;
		boolean isEmpty = true;
		for(Player p: Bukkit.getOnlinePlayers()) {
		    Location ploc = p.getLocation();
		    if(CasinoList.get(Num).RoomXmin <= ploc.getBlockX() && ploc.getBlockX() <= CasinoList.get(Num).RoomXmax && CasinoList.get(Num).RoomYmin <= ploc.getBlockY() && ploc.getBlockY() <= CasinoList.get(Num).RoomYmax && CasinoList.get(Num).RoomZmin <= ploc.getBlockZ() && ploc.getBlockZ() <= CasinoList.get(Num).RoomZmax) {
			    Gambler = p;
			    isEmpty = false;
			    break;
		    }
		}
		if(!isEmpty) {
		    if(Bingo > 0) Gambler.getInventory().addItem(ItemList.get(Bingo));
		    if(Bingo > 3) {
		        String ItemName = ((ItemStack) ItemList.get(Bingo)).getItemMeta().getDisplayName();
		        if (Bingo == 5) ItemName = ItemName + " 32°³";
		        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "broadcast " + ChatColor.GOLD + ChatColor.BOLD + Gambler.getName() + " " + ChatColor.RESET + ChatColor.GREEN + "´ÔÀÌ " + (Num + 1) + "¹ø ºù°íµµ¹Ú¿¡¼­ " + ItemName + ChatColor.GREEN + "À»(¸¦) È¹µæÇÏ¼Ì½À´Ï´Ù.");
		        Gambler.playSound(Gambler.getLocation(), Sound.BLOCK_NOTE_GUITAR, 10.0F, 1.0F);
		    }
		}
		Records[Bingo]++;
	}
	public BingoGamble() {
	    N_Casino = 2;
	    CasinoList.add(new gRoom(-163, 91, -124, -162, 96, -133, -171, 95, -135, -161, 100, -128));
	    CasinoList.add(new gRoom(-163, 92, -104, -162, 96, -113, -171, 95, -115, -161, 100, -108));
	    Material[] Materials = {Material.AIR, Material.COAL, Material.COOKED_BEEF, Material.DIAMOND, Material.SLIME_BALL, Material.GOLD_NUGGET, Material.MAGMA, Material.EYE_OF_ENDER, Material.BEACON, Material.AIR, Material.DRAGON_EGG};
	    String[] Names = {"", ChatColor.GRAY + "¼®Åº", ChatColor.DARK_PURPLE + "½ºÅ×ÀÌÅ©", ChatColor.DARK_AQUA + "´ÙÀÌ¾Æ¸óµå", "¡×A¿ÏµÎÄá", ChatColor.YELLOW + "±ÝÈ­", ChatColor.DARK_RED + "ºÒ ºí·°", ChatColor.LIGHT_PURPLE + "Ä¸½¶", ChatColor.AQUA + "½ÅÈ£±â", "", ChatColor.DARK_GRAY + "µå·¡°ï ¾Ë"};
	    int[] Numbers = {0, 1, 1, 1, 1, 32, 1, 1, 1, 0, 1};
	    for(int i = 0; i < 11; i++) {
	        ItemStack item = new ItemStack(Materials[i], Numbers[i], (short) 0);
	        ItemMeta meta = item.getItemMeta();
	        if(!Materials[i].equals(Material.AIR)) {
	            meta.setDisplayName(Names[i]);
	            item.setItemMeta(meta);
	        }
	        ItemList.add(item);
	    }
	    try {
	        BufferedReader in = new BufferedReader(new FileReader("plugins/Mirsv/BingoGamble/BingoGamble.dat"));
	        String s = in.readLine();
	        String[] Array = s.split(" ");
	        for(int i = 0; i < 11; i++) Records[i] = Integer.parseInt("0" + Array[i]);
	        in.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    getCommand("gamble", this);
	    getListener(this);
	}
	public static void Save() {
	    try {
	        File f = new File("plugins/Mirsv/BingoGamble/BingoGamble.dat");
	        f.delete();
	        BufferedWriter bw = new BufferedWriter(new FileWriter("plugins/Mirsv/BingoGamble/BingoGamble.dat"));
	        String s = Records[0] + "";
	        for(int i = 1; i < 11; i++) s += " " + Records[i];
	        bw.write(s);
	        bw.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
class gRoom {
    int PistonX;
    int PistonY;
    int PistonZ;
    int GridX;
    int GridYmin;
    int GridZmin;
    int RoomXmin;
    int RoomYmin;
    int RoomZmin;
    int RoomXmax;
    int RoomYmax;
    int RoomZmax;
    public gRoom(int PistonX, int PistonY, int PistonZ, int GridX, int GridYmin, int GridZmin, int RoomXmin, int RoomYmin, int RoomZmin, int RoomXmax, int RoomYmax, int RoomZmax) {
        this.PistonX = PistonX;
        this.PistonY = PistonY;
        this.PistonZ = PistonZ;
        this.GridX = GridX;
        this.GridYmin = GridYmin;
        this.GridZmin = GridZmin;
        this.RoomXmin = RoomXmin;
        this.RoomYmin = RoomYmin;
        this.RoomZmin = RoomZmin;
        this.RoomXmax = RoomXmax;
        this.RoomYmax = RoomYmax;
        this.RoomZmax = RoomZmax;
    }
}