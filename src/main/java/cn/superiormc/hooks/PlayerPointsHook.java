package cn.superiormc.hooks;

import cn.superiormc.EconomyExchange;
import cn.superiormc.configs.Messages;
import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.entity.Player;

public class PlayerPointsHook {
    private static PlayerPointsAPI ppAPI = null;

    public static boolean CheckLoadPlayerPoints(){
        if (!EconomyExchange.instance.getServer().getPluginManager().isPluginEnabled("PlayerPoints")) {
            return false;
        } else {
            ppAPI = PlayerPoints.getInstance().getAPI();
            return ppAPI != null;
        }
    }

    public static boolean CheckEnoughPlayerPoints(Player player, int amount){
        int hasAmount = ppAPI.look(player.getUniqueId());
        return hasAmount >= amount;
    }

    public static void GivePoints(Player player, int amount){
        if(amount > 0) {
            ppAPI.give(player.getUniqueId(), amount);
        }
        else {
            player.sendMessage(Messages.GetMessages("error-config-error"));
        }
    }

    public static void TakePoints(Player player, int amount){
        if(amount > 0) {
            ppAPI.take(player.getUniqueId(), amount);
        }
        else {
            player.sendMessage(Messages.GetMessages("error-config-error"));
        }
    }
}
