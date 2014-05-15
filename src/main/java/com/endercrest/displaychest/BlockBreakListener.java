package com.endercrest.displaychest;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Created by Thomas on 4/30/2014.
 */
public class BlockBreakListener implements Listener {

    DisplayChest plugin;

    public BlockBreakListener(DisplayChest plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerBreak(BlockBreakEvent e){
        if(plugin.chests.contains(e.getBlock().getLocation().toString())){
            if(e.getBlock().getType() == Material.CHEST || e.getBlock().getType() == Material.TRAPPED_CHEST){
                e.setCancelled(true);
            }
        }
    }
}
