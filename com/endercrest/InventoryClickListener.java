package com.endercrest;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class InventoryClickListener implements Listener {

	DisplayChest plugin;

	public InventoryClickListener(DisplayChest instance) {
		plugin = instance;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event){
		if(plugin.chests.contains(event.getWhoClicked().getTargetBlock(null, 5).getLocation().toString())){
			if(event.getView().getType().equals(InventoryType.CHEST)){
				event.setCancelled(true);
			}
		}
	}
}
