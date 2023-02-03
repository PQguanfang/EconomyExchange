package cn.superiormc.configs;

import cn.superiormc.EconomyExchange;
import java.util.Arrays;

public class ExchangeConfigs {

    //private static final String[] EconomyTypes = {"Vault", "PlayerPoints", "PlayerTitle", "EliteMobs", "GamePoints", "PEconomy"};

    public static int GetExchangeEconomyAmount(String ruleID) {
        int value = EconomyExchange.instance.getConfig().getInt(ruleID + ".exchange-amount");
        if (value > 0) {
            return value;
        } else {
            return 0;
        }
    }

    public static String GetExchangeEconomyType(String ruleID) {
        String value = EconomyExchange.instance.getConfig().getString(ruleID + ".exchange-economy-type");
        return value;
    }

    public static int GetCostEconomyAmount(String ruleID) {
        int value = EconomyExchange.instance.getConfig().getInt(ruleID + ".cost-amount");
        if (value > 0) {
            return value;
        } else {
            return 0;
        }
    }

    public static String GetCostEconomyType(String ruleID) {
        String value = EconomyExchange.instance.getConfig().getString(ruleID + ".cost-economy-type");
        return value;
    }

    public static int GetMaxTimesPlayer(String ruleID) {
        int value = EconomyExchange.instance.getConfig().getInt(ruleID + ".max-times-per-players");
        if (value > 0) {
            return value;
        } else {
            return 0;
        }
    }

    public static int GetMaxTimesServer(String ruleID) {
        int value = EconomyExchange.instance.getConfig().getInt(ruleID + ".max-times-global-players");
        if (value > 0) {
            return value;
        } else {
            return 0;
        }
    }

}
