package cn.superiormc.hooks;

import cn.superiormc.EconomyExchange;
import cn.superiormc.configs.Messages;
import com.handy.playertitle.api.PlayerTitleApi;
import org.bukkit.entity.Player;

public class PlayerTitleHook {

    public static PlayerTitleApi pta;

    public static boolean CheckLoadPlayerTitle(){
        if (!EconomyExchange.instance.getServer().getPluginManager().isPluginEnabled("PlayerTitle")) {
            return false;
        } else {
            pta = PlayerTitleApi.getInstance();
            return pta != null;
        }
    }

    public static boolean CheckEnoughPlayerTitle(Player player, int amount){
        return pta.getPlayerCoinNum(player.getName()) >= amount;
    }

    public static void GivePlayerTitle(Player player, int amount){
        if(amount > 0) {
            pta.addCoin(player.getName(), Long.valueOf(amount));
        }
        else {
            player.sendMessage(Messages.GetMessages("error-config-error"));
        }
    }

    public static void TakePlayerTitle(Player player, int amount){
        if(amount > 0) {
            pta.subtractCoin(player.getName(), Long.valueOf(amount));
        }
        else {
            player.sendMessage(Messages.GetMessages("error-config-error"));
        }
    }
}
