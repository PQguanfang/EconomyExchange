package cn.superiormc.configs;

import cn.superiormc.EconomyExchange;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RulesConfigs {

    public static List<String> GetValidRule() {
        Set<String> tmpStr = EconomyExchange.instance.getConfig().getKeys(false);
        // getKeys()方法返回的是 Set 集合，这里转换为 Tab 方法需要用的 List 集合，代价是这里的 List 不再能够被添加或者删除。
        List<String> strings = new ArrayList<>(tmpStr);
        strings.remove("messages");
        strings.remove("permission");
        strings.remove("join-tip");
        strings.remove("mysql");
        strings.remove("auto-reset");
        return strings;
    }


}
