package com.endercrest;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

	DisplayChest plugin;
	
	public BlockBreakListener(DisplayChest instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onBreakBreak(BlockBreakEvent event){
		if(plugin.chests.contains(event.getBlock().getLocation().toString())){
			if(event.getBlock().getType() == Material.CHEST || event.getBlock().getType() == Material.TRAPPED_CHEST){
				event.setCancelled(true);
			}
		}
	}
	
}
