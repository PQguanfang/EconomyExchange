package cn.superiormc.events;

import cn.superiormc.EconomyExchange;
import cn.superiormc.configs.*;
import cn.superiormc.manager.DataManager;
import cn.superiormc.mysql.Database;
import cn.superiormc.mysql.MySQLData;
import cn.superiormc.mysql.SQLLiteData;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;

public class JoinEvents implements Listener {

    @EventHandler
    public void JoinTip(PlayerJoinEvent e) {
        if(JoinTipConfigs.GetJoinTipEnabled() && JoinTipConfigs.GetConditionRules()) {
            Player player = e.getPlayer();
            TextComponent message = new TextComponent(Messages.GetMessages("join-tip"));
            message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + JoinTipConfigs.GetJoinTipCommand()));
            player.spigot().sendMessage(message);
        }
    }

    @EventHandler
    public void AutoReset(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        Bukkit.getScheduler().runTaskAsynchronously(EconomyExchange.instance, () -> {
            if(AutoResetConfigs.GetAutoResetEnabled()) {
                if (MySQLConfigs.GetMySQLEnabled()) {
                    MySQLData.CreateNewDataKey(player);
                } else {
                    SQLLiteData.CreateNewDataKey(player);
                }
            }
        });
    }

    @EventHandler
    public void GetPlayerData(PlayerJoinEvent e) {
        String uuid = String.valueOf(e.getPlayer().getUniqueId());
        Bukkit.getScheduler().runTaskLaterAsynchronously(EconomyExchange.instance, () -> {
            Object obj = new Object();
            synchronized(obj) {
                List<String> ruleIDList = RulesConfigs.GetValidRule();
                List<Integer> ruleValueList = new ArrayList<>();
                ;
                for (String ruleID : ruleIDList) {
                    int ruleValue = Database.GetValueData(e.getPlayer(), ruleID);
                    ruleValueList.add(ruleIDList.indexOf(ruleID), ruleValue);
                }
                DataManager dm = new DataManager(ruleIDList, ruleValueList);
                EconomyExchange.PlayerData.put(uuid, dm);
            }
        }, 5L);
    }

}

