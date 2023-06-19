package cn.superiormc.hooks;

import cn.superiormc.EconomyExchange;
import cn.superiormc.configs.Messages;
import org.bukkit.entity.Player;
import su.nightexpress.coinsengine.api.CoinsEngineAPI;

public class CoinsEngineHook {

    public static boolean CheckLoadCoinsEngine(){
        if (!EconomyExchange.instance.getServer().getPluginManager().isPluginEnabled("CoinsEngine")) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean CheckEnoughCoinsEngine(String currencyName, Player player, int amount){
        if (CoinsEngineAPI.getCurrency(currencyName) == null){
            player.sendMessage(Messages.GetMessages("error-config-error"));
            return false;
        }
        else {
            return CoinsEngineAPI.getBalance(player, CoinsEngineAPI.getCurrency(currencyName)) >= (double) amount;
        }
    }

    public static void GiveCoinsEngine(String currencyName, Player player, int amount){
        if (amount > 0 && CoinsEngineAPI.getCurrency(currencyName) != null) {
            CoinsEngineAPI.addBalance(player, CoinsEngineAPI.getCurrency(currencyName), amount);
        }
        else {
            player.sendMessage(Messages.GetMessages("error-config-error"));
        }
    }

    public static void TakeCoinsEngine(String currencyName, Player player, int amount){
        if (amount > 0 && CoinsEngineAPI.getCurrency(currencyName) != null) {
            CoinsEngineAPI.removeBalance(player, CoinsEngineAPI.getCurrency(currencyName), amount);
        }
        else {
            player.sendMessage(Messages.GetMessages("error-config-error"));
        }
    }
}
