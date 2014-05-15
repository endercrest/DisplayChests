package com.endercrest.displaychest.menu;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.*;

/**
 * Items that get added to the Menu
 */
public abstract class MenuItem {

    private Menu menu;
    private int number;
    private MaterialData icon;
    private String title;
    private List<String> descriptions = new ArrayList<String>();
    private HashMap<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>();

    /**
     * Creates a new MenuItem
     * @param title The title of the item(Hover over)
     */
    public MenuItem(String title){
        this(title, new MaterialData(Material.CHEST));
    }

    /**
     * Create a new MenuItem
     * @param title The title of the item(Hover over)
     * @param icon The MaterialData(Icon/item)
     */
    public MenuItem(String title, MaterialData icon){
        this(title, icon, 1);
    }

    /**
     * Creates a new MenuItem
     * @param title The title of the item(Hover over)
     * @param icon The MaterialData(Icon/item)
     * @param number The number of items
     */
    public MenuItem(String title, MaterialData icon, int number){
        this.title = title;
        this.icon = icon;
        this.number = number;
    }

    protected void addItem(Menu menu){
        this.menu = menu;
    }

    protected void removeItem(Menu menu){
        if(this.menu == menu){
            this.menu = null;
        }
    }

    /**
     * Get the menu that the item is part of.
     * @return The Menu
     */
    public Menu getMenu(){
        return menu;
    }

    /**
     * Returns the number displayed on this item displayed
     * @return
     */
    public int getNumber(){
        return number;
    }

    /**
     * Get the icon of the item in the menu
     * @return The icon
     */
    public MaterialData getIcon(){
        return icon;
    }

    /**
     * Get the title of the item
     * @return The title
     */
    public String getTitle(){
        return title;
    }

    /**
     * Add a new line to the description
     * @param line The text on that line
     */
    public void addDescription(String line){
        descriptions.add(line);
    }

    public void addEnchantment(Enchantment enchant, Integer level){
        enchantments.put(enchant, level);
    }

    protected ItemStack getItemStack(){
        ItemStack slot = new ItemStack(getIcon().getItemType(), getNumber(), getIcon().getData());
        ItemMeta meta = slot.getItemMeta();
        meta.setLore(descriptions);
        meta.setDisplayName(getTitle());
        slot.setItemMeta(meta);
        slot.addUnsafeEnchantments(enchantments);
        return slot;
    }

    public abstract void onClick(Player player);
}
