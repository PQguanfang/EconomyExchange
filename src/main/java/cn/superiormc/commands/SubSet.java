package cn.superiormc.commands;

import cn.superiormc.configs.Messages;
import cn.superiormc.mysql.CheckData;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SubSet {

    public static void SubSetCommand(@NotNull String[] args, @NotNull CommandSender sender) {
        // ee set <规则> <玩家> <数量>
        // ee set <规则> <数量>
        if (args.length < 3) {
            sender.sendMessage(Messages.GetMessages("error-args"));
        } else if (!SubView.CheckValidRule(args[1])) {
            sender.sendMessage(Messages.GetMessages("error-incorrect-args-rule"));
        } else if (!sender.hasPermission("economyexchange.admin")) {
            sender.sendMessage(Messages.GetMessages("error-miss-permission"));
        } else if (!MainExchange.isNumeric(args[args.length - 1])) {
            sender.sendMessage(Messages.GetMessages("error-incorrect-args-int"));
        } else if (args.length == 3) {
            if (sender instanceof Player) {
                CheckData.SetValueData((Player) sender, args[1], Integer.parseInt(args[2]));
                sender.sendMessage(Messages.GetMessages("set-success-self").replace("%rule%", args[1]).replace("%amount%", args[2]));
            } else{
                sender.sendMessage(Messages.GetMessages("error-only-in-game"));
            }
        } else if (args.length == 4 && args[2].equals("global")) {
            CheckData.SetValueData(args[1], Integer.parseInt(args[3]));
            sender.sendMessage(Messages.GetMessages("set-success-global").replace("%rule%", args[1]).replace("%amount%", args[3]));
        } else if (Bukkit.getPlayer(args[2]) == null) {
            sender.sendMessage(Messages.GetMessages("error-player-not-found"));
        } else if (args.length == 4) {
            CheckData.SetValueData(Bukkit.getPlayer(args[2]), args[1], Integer.parseInt(args[3]));
            sender.sendMessage(Messages.GetMessages("set-success-player").replace("%rule%", args[1]).replace("%player%", args[2]).replace("%amount%", args[3]));
        } else {
            sender.sendMessage(Messages.GetMessages("error-args"));
        }

    }

}
