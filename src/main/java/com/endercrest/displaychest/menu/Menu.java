package com.endercrest.displaychest.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * File that creates items
 */
public class Menu implements InventoryHolder {

    private HashMap<Integer, MenuItem> items = new HashMap<Integer, MenuItem>();
    private Inventory inventory;
    private String title;
    private int size;

    /**
     * Sets the title and the number of rows
     * @param title The title
     * @param rows The # of rows
     */
    public Menu(String title, int rows){
        this.title = title;
        this.size = rows * 9;
    }

    /**
     * Add a new item to the menu using the x & y coordinate
     * @param item The Item
     * @param x The x-coordinate
     * @param y The y-coordinate
     * @return True, if item was added
     */
    public boolean addMenuItem(MenuItem item, int x, int y){
        return addMenuItem(item, y * 9 + x);
    }

    /**
     * Add an Item to the menu using the index
     * @param item The item
     * @param index The index
     * @return True, if item was added
     */
    public boolean addMenuItem(MenuItem item, int index){
        ItemStack ifFilled = getInventory().getItem(index);
        if(ifFilled != null && ifFilled.getType() != Material.AIR){
            return false;
        }
        getInventory().setItem(index, item.getItemStack());
        items.put(index, item);
        item.addItem(this);
        return true;
    }

    /**
     * Remove item from menu using the x & y coordinate
     * @param x The x-coordinate
     * @param y The y-coordinate
     * @return true if there is an item to remove
     */
    public boolean removeMenuItem(int x, int y){
        return removeMenuItem(y * 9 + x);
    }

    /**
     *
     * @param index
     * @return True, if item removed
     */
    public boolean removeMenuItem(int index){
        ItemStack ifFilled = getInventory().getItem(index);
        if(ifFilled == null || ifFilled.getType() == Material.AIR){
            return false;
        }
        getInventory().clear(index);
        items.remove(index).removeItem(this);
        return true;
    }


    public void selectMenuItem(Player player, int index){
        if(items.containsKey(index)){
            MenuItem item = items.get(index);
            item.onClick(player);
        }
    }

    /**
     * Opens menu for player
     * @param player The player
     */
    public void openMenu(Player player){
        if(getInventory().getViewers().contains(player)){
            throw new IllegalArgumentException(player.getName() + " has already opened " + getInventory().getTitle());
        }
        player.openInventory(getInventory());
    }

    /**
     * Closes menu for player
     * @param player
     */
    public void closeMenu(Player player){
        if(getInventory().getViewers().contains(player)){
        InventoryCloseEvent event = new InventoryCloseEvent(player.getOpenInventory());
        Bukkit.getPluginManager().callEvent(event);
        player.closeInventory();
        getInventory().getViewers().remove(player);
    }
}

    public void updateMenu(){
        for(HumanEntity entity : getInventory().getViewers()){
            if(entity instanceof Player){
                Player player = (Player) entity;
                player.updateInventory();
            }
        }
    }

    /**
     * Gets the inventory
     * @return The inventory
     */
    @Override
    public Inventory getInventory() {
        if(inventory == null){
            inventory = Bukkit.createInventory(this, size, title);
        }
        return inventory;
    }
}
