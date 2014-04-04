package com.endercrest;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

	DisplayChest plugin;
		
	public PlayerInteractListener(DisplayChest instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
		Player p = event.getPlayer();
		
		if(event.getAction() == Action.LEFT_CLICK_BLOCK){
			if(plugin.clicked.contains(p.getName())){
				if(event.getClickedBlock().getType() == Material.CHEST || event.getClickedBlock().getType() == Material.TRAPPED_CHEST){
					if(!plugin.chests.contains(event.getClickedBlock().getLocation().toString())){
						p.sendMessage(ChatColor.GREEN + "You Clicked a Chest or trapped Chest.");
						int index = plugin.clicked.indexOf(p.getName());
						String loc = event.getClickedBlock().getLocation().toString();
						String id = plugin.id.get(plugin.clicked.indexOf(p.getName()));
						plugin.menu.createFile(id, loc);
						plugin.clicked.remove(p.getName());
						plugin.id.remove(index);
					}else{
						p.sendMessage(ChatColor.RED + "This block has already been set.");
					}
				}else{
					p.sendMessage(ChatColor.RED + "You Clicked the wrong type of block.");
					int index = plugin.clicked.indexOf(p.getName());
					plugin.clicked.remove(p.getName());
					plugin.id.remove(index);
				}
			}
		}
		
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(plugin.chests.contains(event.getClickedBlock().getLocation().toString())){
				String id = plugin.chests_id.get(plugin.chests.indexOf(event.getClickedBlock().getLocation().toString()));
				plugin.menu.MenuGUI(p, id);
				event.setCancelled(true);
			}
		}
	}
}
