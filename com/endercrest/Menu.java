package com.endercrest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import me.xhawk87.PopupMenuAPI.MenuItem;
import me.xhawk87.PopupMenuAPI.PopupMenu;
import me.xhawk87.PopupMenuAPI.PopupMenuAPI;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Menu {

	DisplayChest plugin;
	
	ArrayList<MenuItem> items = new ArrayList<MenuItem>();
	
	public Menu(DisplayChest instance) {
		plugin = instance;
	}
	
	/**
	 * Menu
	 * 
	 * @param p - The player
	 * @param id - The id of the menu
	 */
	@SuppressWarnings("deprecation")
	public void MenuGUI(Player p, String id){
		File idFile = new File(plugin.getDataFolder() + File.separator + "Menus", id + ".yml");
		final FileConfiguration idConfig = YamlConfiguration.loadConfiguration(idFile);
		final PopupMenu GUI;
		GUI = PopupMenuAPI.createMenu(ChatColor.translateAlternateColorCodes('&', idConfig.get("Title").toString()), idConfig.getInt("Size"));
		
		for(int i=0; i<idConfig.getInt("Size")*9; i++){
			//MaterialData icon = new MaterialData(Material.getMaterial(idConfig.getInt(i + ".Item_ID")));
			ItemStack icon = new ItemStack(Material.getMaterial(idConfig.getInt(i + ".Item_ID")));
			if(idConfig.isSet(i + ".Data_Value")){
				icon.setDurability((short) idConfig.getInt(i + ".Data_Value"));
			}
			int amount = 1;
			if(idConfig.isSet(i + ".Amount")){
				amount = idConfig.getInt(i + ".Amount");
			}
			
			if(idConfig.isSet(i + ".Item_ID")){
				MenuItem item = new MenuItem(ChatColor.translateAlternateColorCodes('&', idConfig.get(i + ".Name").toString()), icon.getData(), amount){
					@Override
					public void onClick(Player player) {

					}
				};
				
				if(idConfig.isSet(i + ".Sub-Text")){
				List<String> sub = idConfig.getStringList(i + ".Sub-Text");
				for(int s = 0; s < sub.size(); s++){
					String text = sub.get(s);
					item.addDescription(ChatColor.translateAlternateColorCodes('&', text));
				}
				}
				
				GUI.addMenuItem(item, i);
			}
		}
		
		
		GUI.setExitOnClickOutside(false);
		GUI.openMenu(p);
	}
	
	/**
	 * Create a new file
	 * 
	 * 
	 * @param id - The ID of the menu.
	 * @param loc - The Location of the chest
	 */
	public void createFile(String id, String loc){
		//File idFile = new File(plugin.getDataFolder() + File.separator + "Menus", id + ".yml");
		//final FileConfiguration idConfig = YamlConfiguration.loadConfiguration(idFile);
		
		loadFile("menu.yml", id);
		
		plugin.chests.add(loc);
		plugin.chests_id.add(id);
		plugin.getConfig().set("Chests", plugin.chests);
		plugin.getConfig().set("Chests_ID", plugin.chests_id);
		plugin.saveConfig();
	}
	
	/**
	 * Delete file/Possibly relocate file
	 * 
	 * @param id The ID of the menu.
	 */
	public void deleteFile(String id, String loc){
		File idFile = new File(plugin.getDataFolder() + File.separator + "Menus", id + ".yml");
		File oldFile = new File(plugin.getDataFolder() + File.separator + "Old_Menus", id + "_old.yml");
		
		oldFile.delete();
		idFile.renameTo(oldFile);
		
		plugin.chests.remove(loc);
		plugin.chests_id.remove(id);
		plugin.getConfig().set("Chests", plugin.chests);
		plugin.getConfig().set("Chests_ID", plugin.chests_id);
		plugin.saveConfig();
	}
	
	/**
	 * Copy's default file then rename it.
	 * 
	 * @param file - The file
	 */
	public void loadFile(String file, String id){
		File t = new File(plugin.getDataFolder() + File.separator + "Menus", file);
		File idFile = new File(plugin.getDataFolder() + File.separator + "Menus", id + ".yml");
		//System.out.println("Writing new file: "+ t.getAbsolutePath());
			
			try {
				t.createNewFile();
				FileWriter out = new FileWriter(t);
				//System.out.println(file);
				InputStream is = getClass().getResourceAsStream("/"+file);
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String line;
				while ((line = br.readLine()) != null) {
					out.write(line+"\n");
					//System.out.println(line);
				}
				out.flush();
				is.close();
				isr.close();
				br.close();
				out.close();
				t.renameTo(idFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}
	
	/**
	 * Reload Config
	 * 
	 * @param id The ID of the menu
	 */
	//TODO Reload file
	public void reloadFile(String id){
		//File idFile = new File(plugin.getDataFolder() + File.separator + "Menus", id + ".yml");
		//final FileConfiguration idConfig = YamlConfiguration.loadConfiguration(idFile);
	}
	
	/**
	 * Reload all config.
	 */
	//TODO Reload all configs
	public void reloadAllFiles(){
		
	}
	
	
}
