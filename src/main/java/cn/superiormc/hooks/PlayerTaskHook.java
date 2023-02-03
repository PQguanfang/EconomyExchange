package cn.superiormc.hooks;

import cn.superiormc.EconomyExchange;
import com.handy.playertask.api.PlayerTaskApi;
import org.bukkit.entity.Player;

public class PlayerTaskHook {
    public static PlayerTaskApi ptApi;

    public static boolean CheckLoadPlayerTask(){
        if (!EconomyExchange.instance.getServer().getPluginManager().isPluginEnabled("PlayerTask")) {
            return false;
        } else {
            ptApi = PlayerTaskApi.getInstance();
            return ptApi != null;
        }
    }

    public static boolean CheckEnoughPlayerTask(Player player, int amount){
        return ptApi.findAmountByPlayer(player) >= amount;
    }

    public static void GivePlayerTask(Player player, int amount){
        if(amount > 0) {
            ptApi.addCoin(player.getName(), amount);
        }
        else {
            player.sendMessage("error-config-error");
        }
    }

    public static void TakePlayerTask(Player player, int amount){
        if(amount > 0) {
            ptApi.subtractCoin(player.getName(), amount);
        }
        else {
            player.sendMessage("error-config-error");
        }
    }
}
