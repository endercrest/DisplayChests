package com.endercrest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class DisplayChest extends JavaPlugin {

	public String version = "1.0.1";
	
	public List<String> chests;
	public List<String> chests_id;
	public List<String> clicked = new ArrayList<String>();
	public List<String> id = new ArrayList<String>();
	
	Server server = this.getServer();
	ConsoleCommandSender console = server.getConsoleSender();
	
	Menu menu;
	
	@Override
	public void onEnable(){
		if(getServer().getPluginManager().getPlugin("PopupMenuAPI") == null){
			this.setEnabled(false);
			System.out.println("DisplayChest has been disabled. You are missing PopupMenuAPI");
		}else{
			this.saveDefaultConfig();
			menu = new Menu(this);
			getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
			getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
			chests = (List<String>) this.getConfig().getStringList("Chests");
			chests_id = (List<String>) this.getConfig().getStringList("Chests_ID");
			File folder = new File(this.getDataFolder() + File.separator + "Menus");
			if(!folder.exists()){
				folder.mkdir();
			}
			File folder2 = new File(this.getDataFolder() + File.separator + "Old_Menus");
			if(!folder2.exists()){
				folder2.mkdir();
			}
		}
	}
	
	@Override
	public void onDisable(){
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
			if(cmd.getName().equalsIgnoreCase("displaychest")){
				if(sender instanceof Player){
					Player p = (Player)sender;
					if(p.hasPermission("dc.admin")){
				if(args.length == 0){
					p.sendMessage(ChatColor.GREEN + "This plugin allows you to create a display chest.");
					p.sendMessage(ChatColor.GREEN + "To use: '/dc (create/remove/reload) (id)'");
					p.sendMessage(ChatColor.GREEN + "This plugins current version is: " + version);
					p.sendMessage(ChatColor.GREEN + "This plugin is developed by: " + ChatColor.BOLD + "tcvs");
				}else if(args.length == 1){
					if(args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("reload")){
						if(args[0].equalsIgnoreCase("create")){
							p.sendMessage(ChatColor.RED + "You must include an id");
							return true;	
							}
							if(args[0].equalsIgnoreCase("remove")){
							p.sendMessage(ChatColor.RED + "You must include an id");
							return true;	
							}
							
							if(args[0].equalsIgnoreCase("reload")){
							p.sendMessage(ChatColor.RED + "This has not yet been implemented");
							return true;
							}
					}
				}else if(args.length == 2){
					if(args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("reload")){
						if(!clicked.contains(p.getName())){
						if(args[1].matches("[1-9]+")){
							if(args[0].equalsIgnoreCase("create")){
								if(!chests_id.contains(args[1])){
							p.sendMessage(ChatColor.GREEN + "The menu has been generated! This is menu id is: " + args[1]);
							clicked.add(p.getName());
							id.add(args[1] + "");
							return true;	
							}else{
								p.sendMessage(ChatColor.RED + "This ID has already been used.");
							}
							}
							if(args[0].equalsIgnoreCase("remove")){
								String loc = chests.get(chests_id.indexOf(args[1].toString()));
								menu.deleteFile(args[1], loc);
								p.sendMessage(ChatColor.GREEN + "The menu has been removed!");
								if(clicked.contains(p.getName())){
								clicked.remove(p.getName());
								}
								if(id.contains(args[1])){
								id.remove(args[1] + "");
								}
								return true;	
							}
							
							if(args[0].equalsIgnoreCase("reload")){
							p.sendMessage(ChatColor.RED + "This has not yet been implemented");
							return true;
							}
						}else{
							p.sendMessage(ChatColor.RED + "Your id contains an invalid character.");
						}
					}else{
						p.sendMessage(ChatColor.RED + "You Still need to click on a chest before making another menu.");
					}
					}
				}else if(args.length > 2){
					p.sendMessage(ChatColor.RED + "Too Many Arguments.");
				}
			}
			}else{
				//console.sendMessage(Color.RED + "You must be a player to use that command.");
			}
		}
		return true;
	}
}
