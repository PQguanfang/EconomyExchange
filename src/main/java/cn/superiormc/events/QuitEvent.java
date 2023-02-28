package cn.superiormc.events;

import cn.superiormc.EconomyExchange;
import cn.superiormc.mysql.Database;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {
    @EventHandler
    public void SetPlayerData(PlayerQuitEvent e) {
        //TODO: ONLY SET THE QUIT PLAYER DATA
        Bukkit.getScheduler().runTaskAsynchronously(EconomyExchange.instance, () -> {
            Database.SaveData();
        });
    }

}
