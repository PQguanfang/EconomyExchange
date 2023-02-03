package cn.superiormc.configs;

import cn.superiormc.EconomyExchange;
import org.bukkit.entity.Player;

import java.util.List;

public class PermissionConfigs {

    public static boolean GetPermissionEnabled() {
        return EconomyExchange.instance.getConfig().getBoolean("permission.enabled");
    }

    public static boolean CheckPermission(Player player, String ruleID) {
        // 检查权限节点功能是否开启
        if(!GetPermissionEnabled())
            return true;
        // 检查对应兑换规则是否使用权限节点
        else if(GetPermissionDisabledRules(ruleID)){
            return true;
        }
        // 如果使用，返回玩家是否有这个权限节点
        else return player.hasPermission("economyexchange." + ruleID);
    }

    public static boolean GetPermissionDisabledRules(String ruleID){
        List<String> rules = EconomyExchange.instance.getConfig().getStringList("permission.disabled-rules");
        return rules.contains(ruleID);

    }

}
