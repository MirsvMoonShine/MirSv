package com.mirsv.Marlang.CustomAdvancements;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CustomAdvancementClearEvent extends Event{
    Player p;
    AdvancementsList advl;
   
    public CustomAdvancementClearEvent(Player player, AdvancementsList advancementslist){
        p = player;
        advl = advancementslist;
    }
   
    public Player getPlayer(){
        return p;
    }
   
    public AdvancementsList getAdvancement(){
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
