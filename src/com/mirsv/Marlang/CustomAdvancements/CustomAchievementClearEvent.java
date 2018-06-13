package com.mirsv.Marlang.CustomAdvancements;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.mirsv.Marlang.CustomAdvancements.List.AchievementList;

public class CustomAchievementClearEvent extends Event{
    Player p;
    AchievementList advl;
   
    public CustomAchievementClearEvent(Player player, AchievementList advancementslist){
        p = player;
        advl = advancementslist;
    }
   
    public Player getPlayer(){
        return p;
    }
   
    public AchievementList getAdvancement(){
        return advl;
    }
 
    private static final HandlerList handlers = new HandlerList();
   
    public HandlerList getHandlers(){
        return handlers;
    }
   
    static public HandlerList getHandlerList(){
        return handlers;
    }
}
