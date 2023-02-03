package cn.superiormc.configs;

import cn.superiormc.EconomyExchange;
import cn.superiormc.mysql.CheckData;

import java.util.List;

public class JoinTipConfigs {

    public static boolean GetJoinTipEnabled() {
        return EconomyExchange.instance.getConfig().getBoolean("join-tip.enabled");
    }

    public static String GetJoinTipCommand() {
        return EconomyExchange.instance.getConfig().getString("join-tip.command");
    }

    public static boolean GetConditionRules(){
        List<String> strings = EconomyExchange.instance.getConfig().getStringList("join-tip.condition-rule");
        int i = 0;
        for (String str : strings){
            if(CheckData.GetValueData(str) > 0)
                i = i + 1;
        }
        return i > 0;
    }
}
