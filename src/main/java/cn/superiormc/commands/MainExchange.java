package cn.superiormc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import cn.superiormc.configs.Messages;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class MainExchange implements CommandExecutor {

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) {
            command.setUsage(null);
            sender.sendMessage(Messages.GetMessages("error-args"));
            return false;
        } else {
            SendCommandArg(sender, command, label, args);
            return true;
        }
    }

    public void SendCommandArg(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args){
        if (args[0].equals("help")) {
            SubHelp.SubHelpCommand(sender);
        }
        else if (args[0].equals("use")) {
            SubUse.SubUseCommand(args, sender);
        }
        else if (args[0].equals("reload")){
            SubReload.SubReloadCommand(sender);
        }
        else if (args[0].equals("view")){
            SubView.SubViewCommand(args, sender);
        }
        else if (args[0].equals("set")){
            SubSet.SubSetCommand(args, sender);
        }
        else if (args[0].equals("reset")){
            SubReset.SubResetCommand(args, sender);
        }
        else
        {
            sender.sendMessage(Messages.GetMessages("error-args"));
        }
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
}