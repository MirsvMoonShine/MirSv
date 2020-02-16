package com.mirsv.function.list.Cokes.ballantines;

import com.mirsv.Mirsv;
import com.mirsv.function.AbstractFunction;
import com.mirsv.function.list.Cokes.ballantines.achievement.AchSweetChocolate;
import com.mirsv.function.list.Cokes.ballantines.achievement.AchSweetDeath;
import com.mirsv.function.list.Cokes.ballantines.achievement.AchWithYou;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.material.MaterialData;

import java.util.HashMap;
import java.util.Map;

public class BallantinesDay extends AbstractFunction implements Listener, CommandExecutor {
	/*
	 * 코코아콩 2세트 : [파란색이름] 코코아콩
	 * 화로+화로 : 전자레인지
	 * 우유*9 : 생크림
	 * 초콜릿틀 : 나무그릇(8)+철괴
	 * 믹싱볼 : 나무그릇(9)
	 * 스크래퍼 : 나무막대(8)+가죽(1)
	 * 
	 * [파란색이름]코코아콩 + 전자레인지 + 생크림 + 틀 + 믹싱볼 + 스크래퍼 = [초콜릿]
	 */
	
	private ShapelessRecipe micro;
	private ShapedRecipe cream;
	private ShapedRecipe choco;
	private ShapedRecipe mixing;
	private ShapedRecipe scraper;

	private final ItemStack cocoaItem = new ItemMaker(Material.INK_SACK, (short)3).setDisplayName("§b§l코코아빈").parseItemStack(),
			microItem = new ItemMaker(Material.FURNACE).setDisplayName("§d§l전자레인지").parseItemStack(),
			creamItem = new ItemMaker(Material.SUGAR).setDisplayName("§f§l생크림").parseItemStack(),
			bowlItem = new ItemMaker(Material.BOWL).setDisplayName("§f§l초콜릿 틀").parseItemStack(),
			mixingItem = new ItemMaker(Material.BUCKET).setDisplayName("§f§l믹싱 볼").parseItemStack(),
			scraperItem = new ItemMaker(Material.IRON_SPADE).setDisplayName("§f§l스크래퍼").parseItemStack(),
			resultItem = new ItemMaker(Material.STICK).setDisplayName("§6§l초콜릿").parseItemStack();

	public BallantinesDay() {
		super("발렌타인 이벤트", "1.0.0", "발렌타인 이벤트 매니저");
	}

	public void onEnable() {
		registerListener(this);
		registerCommand("발렌타인", this);
		NamespacedKey key;

		try {
			key = new NamespacedKey(Mirsv.getPlugin(), "microwave");
			micro = new ShapelessRecipe(key, microItem);
			micro.addIngredient(2, Material.FURNACE);
			Bukkit.addRecipe(micro);

			key = new NamespacedKey(Mirsv.getPlugin(), "raw_cream");
			cream = new ShapedRecipe(key, creamItem);
			cream.shape("MMM","MMM","MMM");
			cream.setIngredient('M', new MaterialData(Material.MILK_BUCKET));
			Bukkit.addRecipe(cream);

			key = new NamespacedKey(Mirsv.getPlugin(), "choco_base");
			choco = new ShapedRecipe(key, bowlItem);
			choco.shape("BBB","BIB","BBB");
			choco.setIngredient('B', Material.BOWL);
			choco.setIngredient('I', Material.IRON_INGOT);
			Bukkit.addRecipe(choco);

			key = new NamespacedKey(Mirsv.getPlugin(), "mixing");
			mixing = new ShapedRecipe(key, mixingItem);
			mixing.shape("BBB","BBB","BBB");
			mixing.setIngredient('B', Material.BOWL);
			Bukkit.addRecipe(mixing);

			key = new NamespacedKey(Mirsv.getPlugin(), "scraper");
			scraper = new ShapedRecipe(key, scraperItem);
			scraper.shape("SSS","SIS","SSS");
			scraper.setIngredient('S', Material.STICK);
			scraper.setIngredient('I', Material.IRON_INGOT);
			Bukkit.addRecipe(scraper);
		} catch (IllegalStateException ignored) {}
	}
	
	public void onDisable() {
		Bukkit.resetRecipes();
	}
	
	@EventHandler
	public void onPreCraftItem(PrepareItemCraftEvent e) {
		int cocoa = 0, micro = 0, cream = 0, bowl = 0, scraper = 0, mixing = 0, empty = 0;
		for (int a = 1 ; a <= 9 ; a++) {
			ItemStack stack = e.getInventory().getItem(a);
			if (stack != null) {
				if (stack.equals(cocoaItem)) {
					cocoa += 1;
				} else if (stack.equals(microItem)) {
					micro += 1;
				} else if (stack.equals(creamItem)) {
					cream += 1;
				} else if (stack.equals(bowlItem)) {
					bowl += 1;
				} else if (stack.equals(scraperItem)) {
					scraper += 1;
				} else if (stack.equals(mixingItem)) {
					mixing += 1;
				}
			} else {
				empty += 1;
			}
		}

		if (cocoa == 1 && micro == 1 && cream == 1 && bowl == 1 && scraper == 1 && mixing == 1 && empty == 3 && e.getInventory() != null) {
			e.getInventory().setResult(resultItem);
		}
	}

	@EventHandler
	public void onCraftItem(InventoryClickEvent e) {
		if (e.getInventory() instanceof CraftingInventory && e.getSlot() == 0) {
			ItemStack result = ((CraftingInventory) e.getInventory()).getResult();
			if (result != null && isChocolate(result)) {
				HumanEntity human = e.getView().getPlayer();
				if (human instanceof Player) {
					AchSweetChocolate ach = AchSweetChocolate.instance;
					Player player = (Player) human;
					if (!ach.hasAchieved(ach.getJson(player))) {
						ach.achieve(player);
					}
				}
			}
		}
	}

	private final Map<Item, OfflinePlayer> dropMap = new HashMap<>();

	@EventHandler
	private void onDropItem(PlayerDropItemEvent e) {
		if (isChocolate(e.getItemDrop().getItemStack())) {
			dropMap.put(e.getItemDrop(), e.getPlayer());
		}
	}

	@EventHandler
	private void onItemDespawn(ItemDespawnEvent e) {
		dropMap.remove(e.getEntity());
	}

	@EventHandler
	private void onPickupItem(EntityPickupItemEvent e) {
		if (e.getEntity() instanceof Player) {
			Item item = e.getItem();
			if (isChocolate(item.getItemStack())) {
				Player pickup = (Player) e.getEntity();
				OfflinePlayer droppedOffline = dropMap.get(item);
				if (droppedOffline.isOnline()) {
					Player dropped = droppedOffline.getPlayer();
					if (pickup.getLocation().distanceSquared(dropped.getLocation()) <= 144) {
						AchWithYou ach = AchWithYou.instance;
						if (!ach.hasAchieved(ach.getJson(dropped))) {
							ach.achieve(dropped);
						}
					}
				}
			}
		}
		dropMap.remove(e.getItem());
	}

	@EventHandler
	private void onEntityDeathEvent(EntityDeathEvent e) {
		LivingEntity entity = e.getEntity();
		if (entity instanceof Player && entity.getKiller() != null && isChocolate(entity.getKiller().getInventory().getItemInMainHand())) {
			AchSweetDeath ach = AchSweetDeath.instance;
			if (!ach.hasAchieved(ach.getJson(entity.getKiller()))) {
				ach.achieve(entity.getKiller());
			}
		}
	}

	private static boolean isChocolate(ItemStack stack) {
		return stack != null && stack.hasItemMeta() && stack.getType() == Material.STICK && stack.getItemMeta().getDisplayName().equals("§6§l초콜릿");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.isOp()) {
				if (args.length == 1) {
					String arg = args[0];
					if (arg.equalsIgnoreCase("코코아빈")) {
						player.getInventory().addItem(cocoaItem);
					} else if (arg.equalsIgnoreCase("전자레인지")) {
						player.getInventory().addItem(microItem);
					} else if (arg.equalsIgnoreCase("생크림")) {
						player.getInventory().addItem(creamItem);
					} else if (arg.equalsIgnoreCase("초콜릿틀")) {
						player.getInventory().addItem(bowlItem);
					} else if (arg.equalsIgnoreCase("스크래퍼")) {
						player.getInventory().addItem(scraperItem);
					} else if (arg.equalsIgnoreCase("믹싱볼")) {
						player.getInventory().addItem(mixingItem);
					} else if (arg.equalsIgnoreCase("초콜릿")) {
						player.getInventory().addItem(resultItem);
					}
				}
			}
		}
		return false;
	}
}
