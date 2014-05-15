package com.endercrest.displaychest.file;

import com.endercrest.displaychest.DisplayChest;
import com.endercrest.displaychest.menu.Menu;
import com.endercrest.displaychest.menu.MenuItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * All the files and menu stuff
 */
public class FileSystem {

    DisplayChest plugin;

    public FileSystem(DisplayChest plugin){
        this.plugin = plugin;
    }

    public void MenuGUI(Player p, String id){
        File idFile = new File(plugin.getDataFolder() + File.separator + "Menus", id + ".yml");
        final FileConfiguration idConfig = YamlConfiguration.loadConfiguration(idFile);
        Menu menu;
        menu = DisplayChest.createMenu(DisplayChest.colorize(idConfig.getString("Title")), idConfig.getInt("Size"));

        for(int i=0; i<idConfig.getInt("Size")*9; i++){
            //MaterialData icon = new MaterialData(Material.getMaterial(idConfig.getInt(i + ".Item_ID")));
            byte data_value = 0;
            if(idConfig.isSet(i + ".Data_Value")){
                data_value = (byte) idConfig.getInt(i + ".Data_Value");
            }
            int amount = 1;
            if(idConfig.isSet(i + ".Amount")){
                amount = idConfig.getInt(i + ".Amount");
            }
            ItemStack icon = new ItemStack(Material.getMaterial(idConfig.getInt(i + ".Item_ID")), amount, data_value);

            if(idConfig.isSet(i + ".Item_ID")){
                if(idConfig.getInt(i + ".Item_ID") == 373){
                    if(idConfig.getInt(i + ".Item_ID") > 16000){
                        //TODO Splash Potions
                    }
                }
            }

            if(idConfig.isSet(i + ".Item_ID")){
                MenuItem item = new MenuItem(DisplayChest.colorize(idConfig.get(i + ".Name", "").toString()), icon.getData(), amount) {
                    @Override
                    public void onClick(Player player) {
                        if(plugin.getConfig().getBoolean("Click_MSG_Show")){
                            player.sendMessage(DisplayChest.colorize(plugin.getConfig().getString("Click_MSG")));
                        }
                    }
                };

                if(idConfig.isSet(i + ".Sub-Text")){
                    List<String> sub = idConfig.getStringList(i + ".Sub-Text");
                    for(int s = 0; s < sub.size(); s++){
                        String text = sub.get(s);
                        item.addDescription(DisplayChest.colorize(text));
                    }
                }

                if(idConfig.isSet(i + ".Enchantments")){
                    if(idConfig.isSet(i + ".Enchantments.ARROW_DAMAGE")){
                        item.addEnchantment(Enchantment.ARROW_DAMAGE, idConfig.getInt(i + ".Enchantments.ARROW_DAMAGE"));
                    }
                    if(idConfig.isSet(i + ".Enchantments.ARROW_FIRE")){
                        item.addEnchantment(Enchantment.ARROW_FIRE, idConfig.getInt(i + ".Enchantments.ARROW_FIRE"));
                    }
                    if(idConfig.isSet(i + ".Enchantments.ARROW_INFINITE")){
                        item.addEnchantment(Enchantment.ARROW_INFINITE, idConfig.getInt(i + ".Enchantments.ARROW_INFINITE"));
                    }
                    if(idConfig.isSet(i + ".Enchantments.ARROW_KNOCKBACK")){
                        item.addEnchantment(Enchantment.ARROW_KNOCKBACK, idConfig.getInt(i + ".Enchantments.ARROW_KNOCKBACK"));
                    }
                    if(idConfig.isSet(i + ".Enchantments.DAMAGE_ALL")){
                        item.addEnchantment(Enchantment.DAMAGE_ALL, idConfig.getInt(i + ".Enchantments.DAMAGE_ALL"));
                    }
                    if(idConfig.isSet(i + ".Enchantments.DAMAGE_ARTHROPODS")){
                        item.addEnchantment(Enchantment.DAMAGE_ARTHROPODS, idConfig.getInt(i + ".Enchantments.ARTHROPODS"));
                    }
                    if(idConfig.isSet(i + ".Enchantments.DAMAGE_UNDEAD")){
                        item.addEnchantment(Enchantment.DAMAGE_UNDEAD, idConfig.getInt(i + ".Enchantments.DAMAGE_UNDEAD"));
                    }
                    if(idConfig.isSet(i + ".Enchantments.DIG_SPEED")){
                        item.addEnchantment(Enchantment.DIG_SPEED, idConfig.getInt(i + ".Enchantments.DIG_SPEED"));
                    }
                    if(idConfig.isSet(i + ".Enchantments.DURABILITY")){
                        item.addEnchantment(Enchantment.DURABILITY, idConfig.getInt(i + ".Enchantments.DURABILTIY"));
                    }
                    if(idConfig.isSet(i + ".Enchantments.FIRE_ASPECT")){
                        item.addEnchantment(Enchantment.FIRE_ASPECT, idConfig.getInt(i + ".Enchantments.FIRE_ASPECT"));
                    }
                    if(idConfig.isSet(i + ".Enchantments.KNOCKBACK")){
                        item.addEnchantment(Enchantment.KNOCKBACK, idConfig.getInt(i + ".Enchantments.KNOCKBACK"));
                    }
                    if(idConfig.isSet(i + ".Enchantments.LOOT_BONUS_BLOCKS")){
                        item.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, idConfig.getInt(i + ".Enchantments.LOOT_BONUS_BLOCKS"));
                    }
                    if(idConfig.isSet(i + ".Enchantments.LOOT_BONUS_MOBS")){
                        item.addEnchantment(Enchantment.LOOT_BONUS_MOBS, idConfig.getInt(i + ".Enchantments.LOOT_BONUS_MOBS"));
                    }
                    if(idConfig.isSet(i + ".Enchantments.OXYGEN")){
                        item.addEnchantment(Enchantment.OXYGEN, idConfig.getInt(i + ".Enchantments.OXYGEN"));
                    }
                    if(idConfig.isSet(i + ".Enchantments.PROTECTION_ENVIRONMENTAL")){
                        item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, idConfig.getInt(i + ".Enchantments.PROTECTION_ENVIRONMENT"));
                    }
                    if(idConfig.isSet(i + ".Enchantments.PROTECTION_FALL")){
                        item.addEnchantment(Enchantment.PROTECTION_FALL, idConfig.getInt(i + ".Enchantments.PROTECTION_FALL"));
                    }
                    if(idConfig.isSet(i + ".Enchantments.PROTECTION_EXPLOSIONS")){
                        item.addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, idConfig.getInt(i + ".Enchantments.PROTECTION_EXPLOSIONS"));
                    }
                    if(idConfig.isSet(i + ".Enchantments.PROTECTION_FIRE")){
                        item.addEnchantment(Enchantment.PROTECTION_FIRE, idConfig.getInt(i + ".Enchantments.PROTECTION_FIRE"));
                    }
                    if(idConfig.isSet(i + ".Enchantments.PROTECTION_PROJECTILE")){
                        item.addEnchantment(Enchantment.PROTECTION_PROJECTILE, idConfig.getInt(i + ".Enchantments.PROTECTION_PROJECTILE"));
                    }
                    if(idConfig.isSet(i + ".Enchantments.SILK_TOUCH")){
                        item.addEnchantment(Enchantment.SILK_TOUCH, idConfig.getInt(i + ".Enchantments.SILK_TOUCH"));
                    }
                    if(idConfig.isSet(i + ".Enchantments.THORNS")){
                        item.addEnchantment(Enchantment.THORNS, idConfig.getInt(i + ".Enchantments.THORNS"));
                    }
                    if(idConfig.isSet(i + ".Enchantments.WATER_WORKER")){
                        item.addEnchantment(Enchantment.WATER_WORKER, idConfig.getInt(i + ".Enchantments.WATER_WORKER"));
                    }

                }

                menu.addMenuItem(item, i);
            }
        }

        menu.openMenu(p);
    }


    /**
     * Create a new file
     * @param id The ID
     * @param loc The Location
     */
    public void createFile(String id, String loc){
        loadFile("menu.yml", id);

        plugin.chests.add(loc);
        plugin.chests_id.add(id);
        plugin.getConfig().set("Chests", plugin.chests);
        plugin.getConfig().set("Chests_ID", plugin.chests_id);
        plugin.saveConfig();
    }

    /**
     * Delete the file and move to a new location
     * @param id The ID
     * @param loc The Location
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
    }

    /**
     * Copies default file then rename it.
     * @param file The file
     * @param id The ID
     */
    public void loadFile(String file, String id){
        File t = new File(plugin.getDataFolder() + File.separator + "Menus", file);
        File idFile = new File(plugin.getDataFolder() + File.separator + "Menus", id + ".yml");

        try{
            t.createNewFile();
            FileWriter out = new FileWriter(t);
            InputStream is = getClass().getResourceAsStream("/"+file);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while((line = br.readLine()) != null){
                out.write(line + "\n");
            }
            out.flush();
            is.close();
            isr.close();
            br.close();
            out.close();
            t.renameTo(idFile);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
