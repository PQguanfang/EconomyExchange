package cn.superiormc.commands;

import cn.superiormc.configs.PermissionConfigs;
import cn.superiormc.manager.RuleManager;
import org.bukkit.command.CommandSender;
import cn.superiormc.configs.Messages;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class SubUse {

    public static void SubUseCommand(@NotNull String[] args, @NotNull CommandSender sender) {
        // use <兑换规则>
        // use <兑换规则> <兑换次数>
        if (!(sender instanceof Player)){
            sender.sendMessage(Messages.GetMessages("error-only-in-game"));
        } else if (args.length !=2 && args.length != 3) {
            sender.sendMessage(Messages.GetMessages("error-args"));
        } else if (!PermissionConfigs.CheckPermission((Player) sender, args[1])) {
            sender.sendMessage(Messages.GetMessages("error-miss-permission-rule"));
        } else if (args.length == 2){
            new RuleManager((Player) sender, args[1]);
        } else if (args.length == 3 && !MainExchange.isNumeric(args[2])) {
            sender.sendMessage(Messages.GetMessages("error-incorrect-args-int"));
        } else {
            new RuleManager((Player) sender, args[1], Integer.parseInt(args[2]));
        }
    }

}
