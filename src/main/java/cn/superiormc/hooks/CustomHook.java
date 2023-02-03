package cn.superiormc.hooks;

import cn.superiormc.configs.Messages;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CustomHook {

    public static boolean CheckEnoughMoney(String placeholder, Player player, int amount){
        try {
            placeholder = PlaceholderAPI.setPlaceholders(player, placeholder);
            return Integer.parseInt(placeholder) >= amount;
        }
        catch (NumberFormatException e){
            player.sendMessage(Messages.GetMessages("error-config-error"));
            return false;
        }
    }

    public static void GiveMoney(String command, Player player, int amount){
        if(amount > 0) {
            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), command.replace("%player%", player.getName()));
        }
        else {
            player.sendMessage(Messages.GetMessages("error-config-error"));
        }
    }

    public static void TakeMoney(String command, Player player, int amount){
        if(amount > 0) {
            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), command.replace("%player%", player.getName()));
        }
        else {
            player.sendMessage(Messages.GetMessages("error-config-error"));
        }
    }

}
