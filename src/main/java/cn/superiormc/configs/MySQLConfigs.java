package cn.superiormc.configs;

import cn.superiormc.EconomyExchange;
import cn.superiormc.mysql.MySQLData;

public class MySQLConfigs {
    public static boolean GetMySQLEnabled(){
        return EconomyExchange.instance.getConfig().getBoolean("mysql.enabled");
    }

    public static void GetMySQLSettings() {
        String host = EconomyExchange.instance.getConfig().getString("mysql.host");
        int port = EconomyExchange.instance.getConfig().getInt("mysql.port");
        String database = EconomyExchange.instance.getConfig().getString("mysql.database");
        String username = EconomyExchange.instance.getConfig().getString("mysql.username");
        String password = EconomyExchange.instance.getConfig().getString("mysql.password");
        String className = EconomyExchange.instance.getConfig().getString("mysql.jdbc-class");
        MySQLData.MySQLConnect(className, host, port, database, username, password);
    }

    public static long GetMySQLSaveTime() {
        return EconomyExchange.instance.getConfig().getLong("auto-save.period-time");
    }

    public static boolean GetAutoSaveEnabled() {
        return EconomyExchange.instance.getConfig().getBoolean("auto-save.enabled");
    }
}
