package cn.superiormc.hooks;

import cn.superiormc.EconomyExchange;
import org.bukkit.entity.Player;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultHook {

    private static RegisteredServiceProvider<Economy> rsp;
    private static Economy vAPI = null;

    public static boolean CheckLoadVault() {
        if(!EconomyExchange.instance.getServer().getPluginManager().isPluginEnabled("Vault"))
            return false;
        rsp = EconomyExchange.instance.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null)
            return false;
        vAPI = rsp.getProvider();
        return vAPI != null;
    }

    public static boolean CheckEnoughVault(Player player, int amount){
        return vAPI.has(player,amount);
    }

    public static void GiveVault(Player player, int amount){
        if(amount > 0) {
            vAPI.depositPlayer(player, amount);
        }
        else {
            player.sendMessage("error-config-error");
        }
    }

    public static void TakeVault(Player player, int amount){
        if(amount > 0) {
            vAPI.withdrawPlayer(player, amount);
        }
        else {
            player.sendMessage("error-config-error");
        }
    }
}
