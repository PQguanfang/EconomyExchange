package cn.superiormc.mysql;

import cn.superiormc.EconomyExchange;
import cn.superiormc.configs.AutoResetConfigs;
import cn.superiormc.configs.Messages;
import cn.superiormc.configs.MySQLConfigs;
import org.bukkit.entity.Player;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class CheckData {

    public static boolean GetDataType(Player player, String ruleID, int amount) {
        String uuid = String.valueOf(player.getUniqueId());
        //获取对应规则id在数组中的索引
        int i = EconomyExchange.PlayerData.get(uuid).GetRuleIDList().indexOf(ruleID);
        //通过索引获得对应规则剩余次数
        int playerValue = EconomyExchange.PlayerData.get(uuid).GetRuleValueList().get(i);
        int globalValue = EconomyExchange.PlayerData.get(AutoResetConfigs.GetAutoResetGlobalName()).GetRuleValueList().get(i);
        if(playerValue >= amount && globalValue >= amount)
            return true;
        else if(playerValue < amount){
            player.sendMessage(Messages.GetMessages("error-max-times-per-players"));
            return false;
        }
        else{
            player.sendMessage(Messages.GetMessages("error-max-times-global-players"));
            return false;
        }
    }

    public static int GetValueData(Player player, String ruleID) {
        String uuid = String.valueOf(player.getUniqueId());
        int i = EconomyExchange.PlayerData.get(uuid).GetRuleIDList().indexOf(ruleID);
        int value = EconomyExchange.PlayerData.get(uuid).GetRuleValueList().get(i);
        return value;
    }

    public static int GetValueData(String ruleID) {
        int i = EconomyExchange.PlayerData.get(AutoResetConfigs.GetAutoResetGlobalName()).GetRuleIDList().indexOf(ruleID);
        int value = EconomyExchange.PlayerData.get(AutoResetConfigs.GetAutoResetGlobalName()).GetRuleValueList().get(i);
        return value;
    }

    public static ZonedDateTime GetSQLTime(String ruleID) {
        GetDateData(ruleID);
        GetTimeData(ruleID);
        String datetime = EconomyExchange.data + " " + EconomyExchange.time;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of(AutoResetConfigs.GetAutoResetTimeZone()));
        return ZonedDateTime.parse(datetime, dtf).plusDays(1L);
    }


    public static void GetDateData(String ruleID) {
        if (MySQLConfigs.GetMySQLEnabled()) {
            MySQLData.GetDateData(ruleID);
        } else {
            SQLLiteData.GetDateData(ruleID);
        }
    }

    public static void GetTimeData(String ruleID) {
        if (MySQLConfigs.GetMySQLEnabled()) {
            MySQLData.GetTimeData(ruleID);
        } else {
            SQLLiteData.GetTimeData(ruleID);
        }
    }

    public static void SetValueData(Player player, String ruleID, int value) {
        String uuid = String.valueOf(player.getUniqueId());
        int i = EconomyExchange.PlayerData.get(uuid).GetRuleIDList().indexOf(ruleID);
        EconomyExchange.PlayerData.get(uuid).GetRuleValueList().set(i, value);
    }

    public static void SetValueData(String ruleID, int value) {
        int i = EconomyExchange.PlayerData.get(AutoResetConfigs.GetAutoResetGlobalName()).GetRuleIDList().indexOf(ruleID);
        EconomyExchange.PlayerData.get(AutoResetConfigs.GetAutoResetGlobalName()).GetRuleValueList().set(i, value);
    }


}
