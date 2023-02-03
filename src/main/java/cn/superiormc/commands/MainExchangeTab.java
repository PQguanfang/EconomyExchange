package cn.superiormc.commands;

import cn.superiormc.configs.RulesConfigs;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class MainExchangeTab implements TabCompleter {
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1)
        {
            if(sender.hasPermission("economyexchange.admin")) {
                List<String> strings = new ArrayList();
                strings.add("reload");
                strings.add("help");
                strings.add("use");
                strings.add("view");
                strings.add("set");
                strings.add("reset");
                return strings;
            }
            else{
                List<String> strings = new ArrayList();
                strings.add("help");
                strings.add("use");
                strings.add("view");
                return strings;
            }
        }
        else if(args.length == 2 && (args[0].equals("use") || args[0].equals("view")|| args[0].equals("set") || args[0].equals("reset")))
        {
            List<String> strings = RulesConfigs.GetValidRule();
            return strings;
        }
        else if(args.length == 3 && args[0].equals("view")){
            if(sender.hasPermission("economyexchange.admin")) {
                List<String> strings = new ArrayList();
                strings.add("global");
                strings.add("admin");
                return strings;
            }
            else{
                List<String> strings = new ArrayList();
                strings.add("global");
                return strings;
            }
        } else if(args.length == 3 && args[0].equals("use")){
            List<String> strings = new ArrayList();
            strings.add("10");
            strings.add("50");
            strings.add("100");
            return strings;
        } else if(args.length == 4 && args[0].equals("set")){
            List<String> strings = new ArrayList();
            strings.add("100");
            strings.add("500");
            strings.add("1000");
            return strings;
        }

        return null;
        }

}

