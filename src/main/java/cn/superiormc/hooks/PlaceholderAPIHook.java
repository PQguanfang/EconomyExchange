package cn.superiormc.hooks;

import cn.superiormc.EconomyExchange;
import cn.superiormc.configs.RulesConfigs;
import cn.superiormc.mysql.CheckData;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class PlaceholderAPIHook extends PlaceholderExpansion{

    private final EconomyExchange plugin;

    @Override
    public boolean canRegister() {
        return true;
    }

    public PlaceholderAPIHook(EconomyExchange plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getAuthor() {
        return "PQguanfang";
    }

    @Override
    public String getIdentifier() {
            return "economyexchange";
        }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        List<String> strings = RulesConfigs.GetValidRule();
        Player p = player.getPlayer();
        if(params.contains("times_player_")) {
            String index = params.substring(13);
            if (strings.contains(index)){
                return String.valueOf(CheckData.GetValueData(p, index));
            }
            else{
                return "Unknown rule";
            }
        }
        else if(params.contains("times_global_")) {
            String index = params.substring(13);
            if (strings.contains(index)){
                return String.valueOf(CheckData.GetValueData(index));
            }
            else{
                return "Unknown rule";
            }
        }
        return null;
    }

}
