package com.endercrest;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.ChatColor;

public class BlockBreakListener implements Listener {

	DisplayChest plugin;

	public BlockBreakListener(DisplayChest instance) {
		plugin = instance;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		Player p = event.getPlayer();
		
		if(plugin.chests.contains(p.getTargetBlock(null, 5).getLocation().toString())){
			event.setCancelled(true);
			String msg = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Break", "&cYou Can't Break This Block."));
			
			p.sendMessage(msg);
		}
	}
}
