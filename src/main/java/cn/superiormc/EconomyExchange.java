package cn.superiormc;

import cn.superiormc.commands.MainExchange;
import cn.superiormc.commands.MainExchangeTab;
import cn.superiormc.configs.AutoResetConfigs;
import cn.superiormc.configs.MySQLConfigs;
import cn.superiormc.configs.RulesConfigs;
import cn.superiormc.events.JoinEvents;
import cn.superiormc.events.QuitEvent;
import cn.superiormc.hooks.PlaceholderAPIHook;
import cn.superiormc.manager.DataManager;
import cn.superiormc.mysql.Database;
import cn.superiormc.mysql.MySQLData;
import cn.superiormc.mysql.SQLLiteData;
import cn.superiormc.tasks.AutoResetTask;
import cn.superiormc.tasks.AutoSaveTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static cn.superiormc.hooks.EliteMobsHook.CheckLoadEliteMobs;
import static cn.superiormc.hooks.GamePointsHook.CheckLoadGamePoints;
import static cn.superiormc.hooks.PEconomyHook.CheckLoadPEconomy;
import static cn.superiormc.hooks.PlayerPointsHook.CheckLoadPlayerPoints;
import static cn.superiormc.hooks.PlayerTaskHook.CheckLoadPlayerTask;
import static cn.superiormc.hooks.PlayerTitleHook.CheckLoadPlayerTitle;
import static cn.superiormc.hooks.VaultHook.CheckLoadVault;

public class EconomyExchange extends JavaPlugin {

    // 提前创建静态变量
    public static JavaPlugin instance;

    private PlaceholderAPIHook papi = null;

    public static Map<String, DataManager> PlayerData = new HashMap();

    public static String data;

    public static String time;


    @Override
    public void onLoad() {
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        // 将提前创建的变量指向当前插件实例，伴随插件
        instance = this;
        GetServerData();
        Events();
        Commands();
        Hooks();
        MySQL();
        if(MySQLConfigs.GetAutoSaveEnabled()){
            AutoSave();
        }
        if(AutoResetConfigs.GetAutoResetEnabled()){
            AutoReset();
        }
        Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[EconomyExchange] §fPlugin is loaded. Author: PQguanfang.");
    }

    @Override
    public void onDisable(){
        if (MySQLConfigs.GetMySQLEnabled()) {
            MySQLData.SavePlayerValueData();
        } else {
            SQLLiteData.SavePlayerValueData();
        }
        Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[EconomyExchange] §fPlugin is disabled. Author: PQguanfang.");
    }

    public void AutoReset() {
        (new BukkitRunnable() {

            @Override
            public void run() {
                AutoResetTask.StartTask();
            }

        }).runTaskTimer(EconomyExchange.instance, 0L, AutoResetConfigs.GetAutoResetPeriodTime());
    }

    public void AutoSave() {
        (new BukkitRunnable() {

            @Override
            public void run() {
                AutoSaveTask.StartTask();
            }

        }).runTaskTimerAsynchronously(EconomyExchange.instance, MySQLConfigs.GetMySQLSaveTime(), MySQLConfigs.GetMySQLSaveTime());
    }

    public void Hooks(){
        Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[EconomyExchange] §fHooking into economy plugins...");
        int hookAmount = 0;
        if (CheckLoadPlayerPoints()) {
            hookAmount++;
            Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[EconomyExchange] §fFound No." + hookAmount + " economy plugin: PlayerPoints!");
        }
        if (CheckLoadVault()) {
            hookAmount++;
            Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[EconomyExchange] §fFound No." + hookAmount + " economy plugin: Vault!");
        }
        if (CheckLoadGamePoints()) {
            hookAmount++;
            Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[EconomyExchange] §fFound No." + hookAmount + " economy plugin: GamePoints!");
        }
        if (CheckLoadPlayerTitle()) {
            hookAmount++;
            Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[EconomyExchange] §fFound No." + hookAmount + " economy plugin: PlayerTitle!");
        }
        if (CheckLoadPlayerTask()) {
            hookAmount++;
            Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[EconomyExchange] §fFound No." + hookAmount + " economy plugin: PlayerTask!");
        }
        if (CheckLoadEliteMobs()) {
            hookAmount++;
            Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[EconomyExchange] §fFound No." + hookAmount + " economy plugin: EliteMobs!");
        }
        if (CheckLoadPEconomy()) {
            hookAmount++;
            Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[EconomyExchange] §fFound No." + hookAmount + " economy plugin: PEconomy!");
        }
        if (hookAmount < 2) {
            Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[EconomyExchange] §cYour economy plugin less than 2, plugin is disabling!");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        if (hookAmount >=2) {
            Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[EconomyExchange] §aFinished hook!");
        }
        if (EconomyExchange.instance.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            papi = new PlaceholderAPIHook(this);
            Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[EconomyExchange] §fHooking into PlaceholderAPI...");
            if (papi.register()){
                Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[EconomyExchange] §fFinished hook!");
            }
        }
    }

    public void Commands() {
        Objects.requireNonNull(Bukkit.getPluginCommand("economyexchange")).setExecutor(new MainExchange());
        Objects.requireNonNull(Bukkit.getPluginCommand("economyexchange")).setTabCompleter(new MainExchangeTab());
    }

    public void Events() {
        Bukkit.getPluginManager().registerEvents(new JoinEvents(), this);
        Bukkit.getPluginManager().registerEvents(new QuitEvent(), this);
    }

    public void MySQL(){
        Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[EconomyExchange] §fConnecting to database...");
        if(MySQLConfigs.GetMySQLEnabled()){
            Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[EconomyExchange] §fFound you set database to MySQL, connecting...");
            MySQLConfigs.GetMySQLSettings();
        }
        else{
            Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[EconomyExchange] §fFound you set database to SQLLite, connecting...");
            SQLLiteData.SQLLiteConnect();
        }
    }

    public void GetServerData() {
        List<String> ruleIDList = RulesConfigs.GetValidRule();
        List<Integer> ruleValueList = new ArrayList<>();
        Bukkit.getScheduler().runTaskLaterAsynchronously(EconomyExchange.instance, () -> {
            Object obj = new Object();
            synchronized(obj) {
                for (String ruleID : ruleIDList) {
                    int ruleValue = Database.GetValueData(ruleID);
                    ruleValueList.add(ruleIDList.indexOf(ruleID), ruleValue);
                }
                DataManager dm1 = new DataManager(ruleIDList, ruleValueList);
                EconomyExchange.PlayerData.put(AutoResetConfigs.GetAutoResetGlobalName(), dm1);
            }
        }, 5L);
    }


}
