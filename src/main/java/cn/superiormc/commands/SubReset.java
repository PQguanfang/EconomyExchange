package cn.superiormc.commands;

import cn.superiormc.configs.Messages;
import cn.superiormc.mysql.Database;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class SubReset {

    public static void SubResetCommand(@NotNull String[] args, @NotNull CommandSender sender) {
        if (!sender.hasPermission("economyexchange.admin")) {
            sender.sendMessage(Messages.GetMessages("error-miss-permission"));
        }
        else if (args.length == 1) {
            Database.DeleteTable();
            sender.sendMessage(Messages.GetMessages("reset-success"));
        }
        else if (args.length == 2 ) {
            if (SubView.CheckValidRule(args[1])){
                Database.DeleteTable(args[1]);
                sender.sendMessage(Messages.GetMessages("reset-success-rule").replace("%rule%", args[1]));
            }
            else {
                sender.sendMessage(Messages.GetMessages("error-incorrect-args-rule"));
            }
        }
        else {
            sender.sendMessage(Messages.GetMessages("error-args"));
        }
    }
}
