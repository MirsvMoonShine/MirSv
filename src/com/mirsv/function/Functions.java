package com.mirsv.function;

import com.google.common.base.Enums;
import com.mirsv.Mirsv;
import com.mirsv.function.list.CatNote.WhisperChat;
import com.mirsv.function.list.Cokes.AdvancedChat;
import com.mirsv.function.list.Cokes.BedProhibition;
import com.mirsv.function.list.Cokes.ClearChat;
import com.mirsv.function.list.Cokes.CustomPrefix.CustomPrefix;
import com.mirsv.function.list.Cokes.GlobalMute;
import com.mirsv.function.list.Cokes.HungerMaintenance;
import com.mirsv.function.list.Cokes.ItemTag;
import com.mirsv.function.list.Cokes.NickName;
import com.mirsv.function.list.Cokes.WordProhibition;
import com.mirsv.function.list.Cokes.party.PartyManager;
import com.mirsv.function.list.daybreak.BungeeManager;
import com.mirsv.function.list.daybreak.CombatManager;
import com.mirsv.function.list.daybreak.Convenience;
import com.mirsv.function.list.daybreak.DeliveryService;
import com.mirsv.function.list.daybreak.NoTown;
import com.mirsv.function.list.daybreak.NoHungerAtNight;
import com.mirsv.function.list.daybreak.PlayTime;
import com.mirsv.function.list.daybreak.TutorialManager;
import com.mirsv.function.list.daybreak.achievements.AchievementManager;
import com.mirsv.function.list.daybreak.firework.NoFireworkDamage;
import com.mirsv.function.list.daybreak.market.MarketManager;
import com.mirsv.function.list.daybreak.skill.SkillManager;
import com.mirsv.function.list.daybreak.stock.StockManager;
import com.mirsv.function.list.daybreak.warning.WarningManager;
import org.bukkit.Bukkit;

import java.util.function.BooleanSupplier;

public enum Functions {

	GlobalMute(new GlobalMute()),
	ClearChat(new ClearChat()),
	ItemTag(new ItemTag()),
	Hungry(new HungerMaintenance()),
	WhisperChat(new WhisperChat()),
	Party(new PartyManager()),
	NoBedInAnotherWorld(new BedProhibition()),
	ForbiddenWord(new WordProhibition()),
	AdvanceChat(new AdvancedChat(), new BooleanSupplier() {
		@Override
		public boolean getAsBoolean() {
			return Bukkit.getPluginManager().isPluginEnabled("Towny");
		}
	}),
	Nickname(new NickName()),
	CustomPrefix(new CustomPrefix()),
	NoFireworkDamage(new NoFireworkDamage()),
	Achievements(new AchievementManager(), new BooleanSupplier() {
		@Override
		public boolean getAsBoolean() {
			return Bukkit.getPluginManager().isPluginEnabled("Towny");
		}
	}, new BooleanSupplier() {
		@Override
		public boolean getAsBoolean() {
			return Mirsv.getPlugin().getEconomy() != null;
		}
	}),
	NoHungerAtNight(new NoHungerAtNight()),
	SkillManager(new SkillManager()),
	Convenience(new Convenience(), new BooleanSupplier() {
		@Override
		public boolean getAsBoolean() {
			return Mirsv.getPlugin().getEconomy() != null;
		}
	}),
	WarningManager(new WarningManager()),
	DisableCommands(new NoTown(), new BooleanSupplier() {
		@Override
		public boolean getAsBoolean() {
			return Bukkit.getPluginManager().isPluginEnabled("Towny");
		}
	}),
	CombatManager(new CombatManager(), new BooleanSupplier() {
		@Override
		public boolean getAsBoolean() {
			return Bukkit.getPluginManager().isPluginEnabled("Towny");
		}
	}),
	StockManager(new StockManager(), new BooleanSupplier() {
		@Override
		public boolean getAsBoolean() {
			return Mirsv.getPlugin().getEconomy() != null;
		}
	}),
	TutorialManager(new TutorialManager(), new BooleanSupplier() {
		@Override
		public boolean getAsBoolean() {
			return Bukkit.getWorld("SpawnWorld") != null;
		}
	}),
	DeliveryService(new DeliveryService()),
	MarketManager(new MarketManager(), new BooleanSupplier() {
		@Override
		public boolean getAsBoolean() {
			return Mirsv.getPlugin().getEconomy() != null;
		}
	}),
	PlayTime(new PlayTime(), new BooleanSupplier() {
		@Override
		public boolean getAsBoolean() {
			return Bukkit.getPluginManager().isPluginEnabled("Towny");
		}
	}),
	BungeeManager(new BungeeManager());

	private final AbstractFunction function;
	private final BooleanSupplier[] conditions;

	Functions(AbstractFunction function, BooleanSupplier... conditions) {
		this.function = function;
		this.conditions = conditions;
	}

	public boolean getCondition() {
		for (BooleanSupplier supplier : conditions) if (!supplier.getAsBoolean()) return false;
		return true;
	}

	public AbstractFunction getFunction() {
		return function;
	}

	public static Functions getFunction(String name) {
		return Enums.getIfPresent(Functions.class, name).orNull();
	}

}