package com.endercrest.displaychest;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Anything to do with commands for displaychest
 */
public class CmdDC implements CommandExecutor{

    DisplayChest plugin;
    int nextInt = 0;

    public CmdDC(DisplayChest plugin){
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender cs, Command cmd, String label, String args[]){
        if(cmd.getName().equalsIgnoreCase("displaychest")){
            if(args.length > 0){
                if(args[0].equalsIgnoreCase("create")){
                    return menuCreate(cs, args);
                }else if(args[0].equalsIgnoreCase("remove")){
                    return menuRemove(cs, args);
                }
            }else{
                return showHelp(cs);
            }
        }
        return true;
    }

    private boolean menuCreate(CommandSender cs, String args[]){
        if(isConsole(cs)){
            return true;
        }
        if(args[1].isEmpty()){
            cs.sendMessage(DisplayChest.colorize("&cYou must provide an id!"));
            return true;
        }else{
            if(!isNumbers(cs, args)){
                return true;
            }
            if(!plugin.chests_id.contains(args[1])) {
                String id = args[1];
                plugin.clicked.add(cs.getName());
                plugin.id.add(id);
                cs.sendMessage(DisplayChest.colorize("&aThe menu has been generated! The menu's id is: " + id));
                return true;
            }else{
                cs.sendMessage(DisplayChest.colorize("&cThis ID has already been taken."));
                return true;
            }
        }
    }

    private boolean menuRemove(CommandSender cs, String args[]){
        if(isConsole(cs)){
            return true;
        }
        if(args[1].isEmpty()){
            cs.sendMessage(DisplayChest.colorize("&cYou must provide a id!"));
            return true;
        }else{
            if(!isNumbers(cs, args)){
                return true;
            }
            if(plugin.chests_id.contains(args[1])) {
                String loc = plugin.chests.get(plugin.chests_id.indexOf(args[1]));
                plugin.files.deleteFile(args[1], loc);
                if (plugin.clicked.contains(cs.getName())) {
                    plugin.clicked.remove(cs.getName());
                }
                if (plugin.id.contains(args[1])) {
                    plugin.id.remove(args[1] + "");
                }
                cs.sendMessage(DisplayChest.colorize("&aThe chest has been removed"));
                return true;
            }else{
                cs.sendMessage(DisplayChest.colorize("&cThere is not menu with this ID"));
                return true;
            }
        }
    }

    private boolean showHelp(CommandSender cs){
        cs.sendMessage(DisplayChest.colorize("&9--- &aDisplayChest &9---"));
        cs.sendMessage(DisplayChest.colorize("&a'/dc create (id)'"));
        cs.sendMessage(DisplayChest.colorize("&a'/dc remove (id)'"));
        return true;
    }

    private int getNextInt(){
        int count = 0;
        while(count < 1){
            if(plugin.chests_id.contains(nextInt + "")){
                ++nextInt;
            }else{
                ++count;
            }
        }
        return nextInt;
    }

    private boolean isNumbers(CommandSender cs, String args[]){
        if(!args[1].matches("[0-9]+")){
            cs.sendMessage(DisplayChest.colorize("&cYou can only have numbers in the id."));
            return false;
        }
        return true;
    }

    private Boolean isConsole(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(DisplayChest.colorize("&4This command is only available to players!"));
            return true;
        }
        return false;
    }
}
