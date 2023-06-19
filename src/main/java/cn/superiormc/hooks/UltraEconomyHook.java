package cn.superiormc.hooks;

import cn.superiormc.EconomyExchange;
import cn.superiormc.configs.Messages;
import me.TechsCode.UltraEconomy.UltraEconomy;
import me.TechsCode.UltraEconomy.UltraEconomyAPI;
import me.TechsCode.UltraEconomy.objects.Currency;
import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.entity.Player;

public class UltraEconomyHook {

    private static UltraEconomyAPI ueAPI = null;

    public static boolean CheckLoadUltraEconomy(){
        if (!EconomyExchange.instance.getServer().getPluginManager().isPluginEnabled("UltraEconomy")) {
            return false;
        } else {
            ueAPI = UltraEconomy.getAPI();
            return ueAPI != null;
        }
    }

    public static boolean CheckEnoughUltraEconomy(String currencyName, Player player, int amount){
        if (UltraEconomy.getAPI().getCurrencies().name(currencyName) == null){
            player.sendMessage(Messages.GetMessages("error-config-error"));
            return false;
        }
        else {
            float hasAmount = UltraEconomy.getAPI().getAccounts().uuid(player.getUniqueId()).get().getBalance(UltraEconomy.getAPI().getCurrencies().name(currencyName).get()).getOnHand();
            return hasAmount >= amount;
        }
    }

    public static void GiveUltraEconomy(String currencyName, Player player, int amount){
        if (amount > 0 && UltraEconomy.getAPI().getCurrencies().name(currencyName) != null) {
            UltraEconomy.getAPI().getAccounts().uuid(player.getUniqueId()).get().getBalance(UltraEconomy.getAPI().getCurrencies().name(currencyName).get()).addHand(amount);
        }
        else {
            player.sendMessage(Messages.GetMessages("error-config-error"));
        }
    }

    public static void TakeUltraEconomy(String currencyName, Player player, int amount){
        if (amount > 0 && UltraEconomy.getAPI().getCurrencies().name(currencyName) != null) {
            UltraEconomy.getAPI().getAccounts().uuid(player.getUniqueId()).get().getBalance(UltraEconomy.getAPI().getCurrencies().name(currencyName).get()).removeHand(amount);
        }
        else {
            player.sendMessage(Messages.GetMessages("error-config-error"));
        }
    }

}
