package cn.superiormc.hooks;

import cn.superiormc.EconomyExchange;
import cn.superiormc.configs.Messages;
import org.bukkit.entity.Player;
import ru.soknight.peconomy.api.PEconomyAPI;
import ru.soknight.peconomy.database.model.WalletModel;

public class PEconomyHook {

    private static PEconomyAPI peAPI = null;

    public static boolean CheckLoadPEconomy(){
        if (!EconomyExchange.instance.getServer().getPluginManager().isPluginEnabled("PEconomy")) {
            return false;
        } else {
            peAPI = PEconomyAPI.get();
            return peAPI != null;
        }
    }

    public static boolean CheckEnoughPEconomy(String currencyName, Player player, int amount){
        return peAPI.hasAmount(player.getName(), currencyName, amount);
    }

    public static void GivePEconomy(String currencyName, Player player, int amount){
        WalletModel wallet = peAPI.getWallet(player.getName());
        if(amount > 0) {
            wallet.addAmount(currencyName, amount);
            peAPI.updateWallet(wallet);
        }
        else {
            player.sendMessage(Messages.GetMessages("error-config-error"));
        }
    }

    public static void TakePEconomy(String currencyName, Player player, int amount){
        WalletModel wallet = peAPI.getWallet(player.getName());
        if(amount > 0) {
            wallet.takeAmount(currencyName, amount);
            peAPI.updateWallet(wallet);
        }
        else {
            player.sendMessage(Messages.GetMessages("error-config-error"));
        }
    }

}
