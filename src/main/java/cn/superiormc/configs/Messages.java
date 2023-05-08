package cn.superiormc.configs;

import cn.superiormc.EconomyExchange;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Messages {

    public static String GetMessages(String textName){
        String textValue = EconomyExchange.instance.getConfig().getString("messages." + textName);

        if (textValue == null)
            return "§x§9§8§F§B§9§8[EconomyExchange] §cThere is something wrong in your config file!";
        else {
            textValue = ColorParser.parse(textValue);
            return textValue;
        }
    }

}
