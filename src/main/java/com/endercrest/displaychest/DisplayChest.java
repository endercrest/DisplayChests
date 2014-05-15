package com.endercrest.displaychest;

import com.endercrest.displaychest.file.FileSystem;
import com.endercrest.displaychest.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Main Class for DisplayChest
 */
public class DisplayChest extends JavaPlugin {

    public List<String> chests;
    public List<String> chests_id;
    public List<String> clicked = new ArrayList<String>();
    public List<String> id = new ArrayList<String>();

    FileSystem files;

    @Override
    public void onEnable(){

        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        if(getConfig().contains("Chests") && getConfig().contains("Chests_ID")){
            chests = this.getConfig().getStringList("Chests");
            chests_id = this.getConfig().getStringList("Chests_ID");
        }else{
            chests = new ArrayList<String>();
            chests_id = new ArrayList<String>();
        }
        files = new FileSystem(this);
        loadConfiguration();
        createFolders();
        getCommand("displaychest").setExecutor(new CmdDC(this));
        log("&ev" + getDescription().getVersion() + " by EnderCrest enabled");
    }

    @Override
    public void onDisable(){

    }

    public void loadConfiguration(){
        if(!getConfig().contains("color-logs")) getConfig().addDefault("color-logs", true);
        if(!getConfig().contains("Click_MSG_Show")) getConfig().addDefault("Click_MSG_Show", false);
        if(!getConfig().contains("Click_MSG")) getConfig().addDefault("Click_MSG", "");
        if(!getConfig().contains("Chests")) getConfig().addDefault("Chests", chests);
        if(!getConfig().contains("Chests_ID")) getConfig().addDefault("Chests_ID", chests_id);
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public void createFolders(){
        File folder = new File(this.getDataFolder() + File.separator + "Menus");
        if(!folder.exists()){
            folder.mkdir();
        }
        File folder2 = new File(this.getDataFolder() + File.separator + "Old_Menus");
        if(!folder2.exists()){
            folder2.mkdir();
        }
    }

    public void log (Object obj) {
        if (getConfig().getBoolean("color-logs", true)) {
            getServer().getConsoleSender().sendMessage(colorize("&3[&d" +  getName() + "&3]&r " + obj));
        } else {
            Bukkit.getLogger().log(Level.INFO, "[" + getName() + "] " + ((String) obj).replaceAll("(?)\u00a7([a-f0-9k-or])", ""));
        }
    }

    public static String colorize(String str) {
        return str.replaceAll("(?i)&([a-f0-9k-or])", "\u00a7$1");
    }

    /**
     * Creates new Menu
     * @param title The Title
     * @param rows The # of Rows
     * @return
     */
    public static Menu createMenu(String title, int rows){
        return new Menu(title, rows);
    }

    /**
     * Removes the Menu
     * @param menu The Menu
     */
    public static void removeMenu(Menu menu){
        for(HumanEntity viewer : menu.getInventory().getViewers()){
            if(viewer instanceof Player){
                menu.closeMenu((Player) viewer);
            }else{
                viewer.closeInventory();
            }
        }
    }
}
