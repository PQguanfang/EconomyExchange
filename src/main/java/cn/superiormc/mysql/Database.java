package cn.superiormc.mysql;

import cn.superiormc.EconomyExchange;
import cn.superiormc.configs.MySQLConfigs;
import cn.superiormc.configs.RulesConfigs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class Database {

    public static int GetValueData(Player player, String ruleID) {
        if (MySQLConfigs.GetMySQLEnabled()) {
            return MySQLData.GetValueData(player, ruleID);
        } else {
            return SQLLiteData.GetValueData(player, ruleID);
        }
    }

    public static int GetValueData(String ruleID) {
        if (MySQLConfigs.GetMySQLEnabled()) {
            return MySQLData.GetValueData(ruleID);
        } else {
            return SQLLiteData.GetValueData(ruleID);
        }
    }

    public static void SaveData() {
        Bukkit.getScheduler().runTaskAsynchronously(EconomyExchange.instance, () -> {
            if (MySQLConfigs.GetMySQLEnabled()) {
                MySQLData.SavePlayerValueData();
            } else {
                SQLLiteData.SavePlayerValueData();
            }
        });
    }


    public static void DeleteTable(String ruleID) {
        Bukkit.getScheduler().runTaskAsynchronously(EconomyExchange.instance, () -> {
            if (MySQLConfigs.GetMySQLEnabled()) {
                MySQLData.DeleteTable(ruleID);
            } else {
                SQLLiteData.DeleteTable(ruleID);
            }
        });
    }

    public static void DeleteTable() {
        List<String> strings = RulesConfigs.GetValidRule();
        Bukkit.getScheduler().runTaskAsynchronously(EconomyExchange.instance, () -> {
            if (MySQLConfigs.GetMySQLEnabled()) {
                for (String str : strings) {
                    MySQLData.DeleteTable(str);
                }
            } else {
                for (String str : strings) {
                    SQLLiteData.DeleteTable(str);
                }
            }
        });
    }
}
