package com.endercrest;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class DisplayChest extends JavaPlugin {

	public List<String> chests;
	
	@Override
	public void onEnable(){
		this.saveDefaultConfig();
		getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
		getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
		chests = (List<String>) this.getConfig().getStringList("Locations");
	}
	
	@Override
	public void onDisable(){
		this.saveConfig();
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		Player p = (Player)sender;
		if(p.hasPermission("dc.create")){
			if(cmd.getName().equalsIgnoreCase("displaychest")){
				if(p instanceof Player){
					if(args.length > 1){
						p.sendMessage("You have too many arguments");
					}else if(args.length == 0){
						p.sendMessage("To use this command: /dc [create/remove]");
						return true;
					}else if(args.length == 1){
						if(args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("remove")){
							if(args[0].equalsIgnoreCase("create")){
								//Code Goes Here To create Chest
								if(p.getTargetBlock(null, 5).getType() == Material.CHEST || p.getTargetBlock(null, 5).getType() == Material.TRAPPED_CHEST){
									if(chests.contains(p.getTargetBlock(null, 5).getLocation().toString())){
										p.sendMessage("This Chest is already a display chest.");
									}else{
									p.sendMessage("Chest Added! The Chest is now locked");
									 
									chests.add(p.getTargetBlock(null, 5).getLocation().toString());
									this.getConfig().set("Locations", chests);
									this.saveConfig();
									}
								}
								return true;
							}
							if(args[0].equalsIgnoreCase("remove")){
								//code goes here to remove chest
								if(p.getTargetBlock(null, 5).getType() == Material.CHEST && p.getTargetBlock(null, 5).getType() == Material.TRAPPED_CHEST){
									chests.remove(p.getTargetBlock(null, 5).getLocation().toString());
								}
								return true;
							}
						}
					}
				}
			}
		}
		return true;
	}
}
