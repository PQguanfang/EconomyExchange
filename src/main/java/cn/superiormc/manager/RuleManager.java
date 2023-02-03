package cn.superiormc.manager;

import cn.superiormc.configs.ExchangeConfigs;
import cn.superiormc.configs.Messages;
import cn.superiormc.configs.RulesConfigs;
import org.bukkit.entity.Player;

import java.util.*;

public class RuleManager {

    private Player player;

    private String ruleID;

    private int ExchangeEconomyAmount;

    private int CostEconomyAmount;

    private String ExchangeEconomyType;

    private String CostEconomyType;

    private int amount;


    public RuleManager(Player player, String ruleID) {
        this.player = player;
        this.ruleID = ruleID;
        if (CheckValidRule()){
            GetRuleConfigs();
            new ExchangeManager(player, ruleID, 1, ExchangeEconomyAmount, CostEconomyAmount, ExchangeEconomyType, CostEconomyType);
        }
        else{
            player.sendMessage(Messages.GetMessages("error-incorrect-args-rule"));
        }
    }

    public RuleManager(Player player, String ruleID, int amount) {
        this.player = player;
        this.ruleID = ruleID;
        this.amount = amount;
        if (CheckValidRule()){
            GetRuleConfigs();
            new ExchangeManager(player, ruleID, amount, ExchangeEconomyAmount * amount, CostEconomyAmount * amount, ExchangeEconomyType, CostEconomyType);
        }
        else{
            player.sendMessage(Messages.GetMessages("error-incorrect-args-rule"));
        }
    }

    private boolean CheckValidRule() {
        // getKeys()方法返回的是 Set 集合，这里转换为 Tab 方法需要用的 List 集合，代价是这里的 List 不再能够被添加或者删除。
        List<String> strings = RulesConfigs.GetValidRule();
        for (String str : strings) {
            if (Objects.equals(str, this.ruleID)) {
                return true;
            }
        }
        return false;
    }

    private void GetRuleConfigs() {
        this.CostEconomyAmount = ExchangeConfigs.GetCostEconomyAmount(this.ruleID);
        this.CostEconomyType = ExchangeConfigs.GetCostEconomyType(this.ruleID);
        this.ExchangeEconomyAmount = ExchangeConfigs.GetExchangeEconomyAmount(this.ruleID);
        this.ExchangeEconomyType = ExchangeConfigs.GetExchangeEconomyType(this.ruleID);
    }

}
