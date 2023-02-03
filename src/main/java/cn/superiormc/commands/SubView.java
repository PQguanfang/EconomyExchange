package cn.superiormc.commands;

import cn.superiormc.configs.Messages;
import cn.superiormc.configs.RulesConfigs;
import cn.superiormc.mysql.CheckData;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class SubView {

    public static void SubViewCommand(@NotNull String[] args, @NotNull CommandSender sender) {
        if (args.length < 2){
            sender.sendMessage(Messages.GetMessages("error-args"));
        } else if (!CheckValidRule(args[1])) {
            sender.sendMessage(Messages.GetMessages("error-incorrect-args-rule"));
        } else if (args.length == 2) {
            if(sender instanceof Player){
                sender.sendMessage(Messages.GetMessages("view-player").replace("%rule%", args[1]).replace("%amount%", String.valueOf(CheckData.GetValueData((Player) sender, args[1]))));
            }
            else {
                sender.sendMessage(Messages.GetMessages("error-only-in-game"));
            }
        } else if (args.length == 3 && args[2].equals("global")) {
            sender.sendMessage(Messages.GetMessages("view-player-global").replace("%rule%", args[1]).replace("%amount%", String.valueOf(CheckData.GetValueData(args[1]))));
        } else if (args.length == 4 && args[2].equals("admin")) {
            Player player1 = Bukkit.getPlayer(args[3]);
            if (sender.hasPermission("economyexchange.admin") && player1 != null) {
                sender.sendMessage(Messages.GetMessages("view-player-admin").replace("%player%", args[2]).replace("%rule%", args[1]).replace("%amount%", String.valueOf(CheckData.GetValueData(player1, args[1]))));
            } else if(player1 == null) {
                sender.sendMessage(Messages.GetMessages("error-player-not-found"));
            } else {
                sender.sendMessage(Messages.GetMessages("error-miss-permission"));
            }
        } else {
            sender.sendMessage(Messages.GetMessages("error-args"));
        }
    }

    public static boolean CheckValidRule(String ruleID) {
        List<String> strings = RulesConfigs.GetValidRule();
        for (String str : strings) {
            if (Objects.equals(str, ruleID)) {
                return true;
            }
        }
        return false;
    }
}
