package com.endercrest.displaychest;

import com.endercrest.displaychest.menu.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

/**
 * Listens to any player interactions
 */
public class PlayerInteractListener implements Listener {

    DisplayChest plugin;

    public PlayerInteractListener(DisplayChest plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (plugin.clicked.contains(p.getName())) {
                if (e.getClickedBlock().getType() == Material.CHEST || e.getClickedBlock().getType() == Material.TRAPPED_CHEST) {
                    if (!plugin.chests.contains(e.getClickedBlock().getLocation().toString())) {
                        p.sendMessage(DisplayChest.colorize("&aYou clicked a chest or trapped chest"));
                        int index = plugin.clicked.indexOf(p.getName());
                        String loc = e.getClickedBlock().getLocation().toString();
                        String id = plugin.id.get(plugin.clicked.indexOf(p.getName()));
                        plugin.files.createFile(id, loc);
                        plugin.clicked.remove(p.getName());
                        plugin.id.remove(index);
                    } else {
                        p.sendMessage(DisplayChest.colorize("&cThis block has already been set."));
                    }
                } else {
                    p.sendMessage(DisplayChest.colorize("&cYou clicked the wrong type of block."));
                    int index = plugin.clicked.indexOf(p.getName());
                    plugin.clicked.remove(p.getName());
                    plugin.id.remove(index);
                }
            }
        }

        if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(plugin.chests.contains(e.getClickedBlock().getLocation().toString())){
                String id = plugin.chests_id.get(plugin.chests.indexOf(e.getClickedBlock().getLocation().toString()));
                plugin.files.MenuGUI(p, id);
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onMenuItemClicked(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        if (inventory.getHolder() instanceof Menu) {
            Menu menu = (Menu) inventory.getHolder();
            if (event.getWhoClicked() instanceof Player) {
                Player player = (Player) event.getWhoClicked();
                if (event.getSlotType() != InventoryType.SlotType.OUTSIDE) {
                    int index = event.getRawSlot();
                    if (index < inventory.getSize()) {
                        menu.selectMenuItem(player, index);
                    }
                }
            }
            event.setCancelled(true);
        }
    }
}
