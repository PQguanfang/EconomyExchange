package cn.superiormc.commands;

import org.bukkit.command.CommandSender;
import cn.superiormc.configs.Messages;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SubHelp extends MainExchange{

    public static void SubHelpCommand(@NotNull CommandSender sender) {
        if (!(sender instanceof Player)){
            sender.sendMessage(Messages.GetMessages("help-main-console"));
        } else if (sender.hasPermission("economyexchange.admin")) {
            sender.sendMessage(Messages.GetMessages("help-main-admin"));
        } else {
            sender.sendMessage(Messages.GetMessages("help-main"));
        }
    }
}
