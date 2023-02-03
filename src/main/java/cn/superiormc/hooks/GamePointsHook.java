package cn.superiormc.hooks;

import cn.superiormc.EconomyExchange;
import cn.superiormc.configs.Messages;
import org.bukkit.entity.Player;
import su.nightexpress.gamepoints.api.GamePointsAPI;
import su.nightexpress.gamepoints.data.PointUser;

public class GamePointsHook {

    public static boolean CheckLoadGamePoints(){
        if (!EconomyExchange.instance.getServer().getPluginManager().isPluginEnabled("GamePoints")) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean CheckEnoughGamePoints(Player player, int amount){
        PointUser user = GamePointsAPI.getUserData(player);
        return user.getBalance() >= amount;
    }

    public static void GiveGamePoints(Player player, int amount){
        PointUser user = GamePointsAPI.getUserData(player);
        if(amount > 0) {
            user.addPoints(amount);
        }
        else {
            player.sendMessage(Messages.GetMessages("error-config-error"));
        }
    }

    public static void TakeGamePoints(Player player, int amount){
        PointUser user = GamePointsAPI.getUserData(player);
        if(amount > 0) {
            user.takePoints(amount);
        }
        else {
            player.sendMessage(Messages.GetMessages("error-config-error"));
        }
    }
}
