package cn.superiormc.commands;

import cn.superiormc.EconomyExchange;
import org.bukkit.command.CommandSender;
import cn.superiormc.configs.Messages;
import org.jetbrains.annotations.NotNull;

public class SubReload {

    public static void SubReloadCommand(@NotNull CommandSender sender)
    {
        if(sender.hasPermission("economyexchange.admin")) {
            EconomyExchange.instance.reloadConfig();
            sender.sendMessage(Messages.GetMessages("plugin-reloaded"));
        }
        else{
            sender.sendMessage(Messages.GetMessages("error-miss-permission"));
        }
    }
}
