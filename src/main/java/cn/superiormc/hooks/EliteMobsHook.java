package cn.superiormc.hooks;

import cn.superiormc.EconomyExchange;
import com.magmaguy.elitemobs.economy.EconomyHandler;
import org.bukkit.entity.Player;

public class EliteMobsHook {


    public static boolean CheckLoadEliteMobs(){
        if (!EconomyExchange.instance.getServer().getPluginManager().isPluginEnabled("EliteMobs")) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean CheckEnoughEliteMobs(Player player, int amount){
        return EconomyHandler.checkCurrency(player.getUniqueId()) >= amount;
    }

    public static void GiveEliteMobs(Player player, int amount){
        if(amount > 0) {
            EconomyHandler.addCurrency(player.getUniqueId(), amount);
        }
        else {
            player.sendMessage("error-config-error");
        }
    }

    public static void TakeEliteMobs(Player player, int amount){
        if(amount > 0) {
            EconomyHandler.subtractCurrency(player.getUniqueId(), amount);
        }
        else {
            player.sendMessage("error-config-error");
        }
    }

}
