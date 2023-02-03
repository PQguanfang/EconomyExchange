package cn.superiormc.mysql;

import cn.superiormc.EconomyExchange;
import cn.superiormc.configs.AutoResetConfigs;
import cn.superiormc.configs.ExchangeConfigs;
import cn.superiormc.configs.Messages;
import cn.superiormc.configs.RulesConfigs;
import cn.superiormc.manager.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.List;
import java.util.UUID;

import static cn.superiormc.EconomyExchange.instance;

public class SQLLiteData {

    public static Connection connection;
    public static void SQLLiteConnect() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            Bukkit.getConsoleSender().sendMessage(Messages.GetMessages("error-sqlite-not-installed"));
            Bukkit.getPluginManager().disablePlugin(instance);
        }
        String url = "jdbc:sqlite:"+ EconomyExchange.instance.getDataFolder() +"/data.db";
        try {
            connection = DriverManager.getConnection(url);
            List<String> strings = RulesConfigs.GetValidRule();
            Statement statement = connection.createStatement();
            for (String str : strings) {
                String sql = "CREATE TABLE IF NOT EXISTS " + str + " (" +
                        "uuid TEXT PRIMARY KEY, " +
                        "value INT, " +
                        "date TEXT, " +
                        "time TEXT, " +
                        "UNIQUE (uuid)); ";
                String sql1 = "INSERT INTO " + str + " (uuid, value, date, time) " +
                        "SELECT '" + AutoResetConfigs.GetAutoResetGlobalName() + "', '" +
                        ExchangeConfigs.GetMaxTimesServer(str) + "', '" +
                        AutoResetConfigs.GetNowTime(true) + "', '" +
                        AutoResetConfigs.GetNowTime(false) + "' " +
                        "WHERE NOT EXISTS (SELECT * FROM " + str + " WHERE uuid = '" + AutoResetConfigs.GetAutoResetGlobalName() + "');";
                statement.executeUpdate(sql);
                statement.executeUpdate(sql1);
            }
            statement.close();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(Messages.GetMessages("error-sqlite-error"));
            throw new RuntimeException(e);
        }
    }

    public static synchronized void SQLLiteDown(){
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized void CreateNewDataKey(Player player){

        try {
            UUID uuid = player.getUniqueId();
            Statement statement = connection.createStatement();
            List<String> strings = RulesConfigs.GetValidRule();
            for (String str : strings) {
                String sql = "INSERT INTO " + str + "(uuid, value, date, time) " +
                        "SELECT '" + uuid + "', '" + ExchangeConfigs.GetMaxTimesPlayer(str) + "', '" +
                        AutoResetConfigs.GetNowTime(true) + "', '" +
                        AutoResetConfigs.GetNowTime(false) + "'" +
                        "WHERE NOT EXISTS (SELECT * FROM " + str + " WHERE uuid = '" + uuid + "')";
                statement.executeUpdate(sql);
            }
            statement.close();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(Messages.GetMessages("error-sqlite-error"));
            throw new RuntimeException(e);
        }
    }

    public static synchronized int GetValueData(String ruleID){
        try {
            DataManager dm = null;
            Statement statement = SQLLiteData.connection.createStatement();
            String sql = "SELECT * " +
                    "FROM " + ruleID + " WHERE uuid = '" +
                    AutoResetConfigs.GetAutoResetGlobalName() +"';";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()){
                dm = new DataManager(rs.getInt("value"));
            }
            if (dm == null)
            {
                GetValueData(ruleID);
            }
            statement.close();
            return dm.GetDataValue();
        } catch (SQLException ex) {
            Bukkit.getConsoleSender().sendMessage(Messages.GetMessages("error-mysql-error"));
            throw new RuntimeException(ex);
        }
    }

    public static synchronized int GetValueData(Player player, String ruleID){
        try {
            DataManager dm = null;
            Statement statement = SQLLiteData.connection.createStatement();
            String sql = "SELECT * " +
                    "FROM " + ruleID + " WHERE uuid = '" +
                    player.getUniqueId() +"';";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()){
                dm = new DataManager(rs.getInt("value"));
            }
            if (dm == null)
            {
                GetValueData(player, ruleID);
            }
            statement.close();
            return dm.GetDataValue();
        } catch (SQLException ex) {
            Bukkit.getConsoleSender().sendMessage(Messages.GetMessages("error-sqlite-error"));
            throw new RuntimeException(ex);
        }
    }

    public static synchronized void SavePlayerValueData(){
        SaveValueData();
        for(String uuid : EconomyExchange.PlayerData.keySet()) {
            DataManager dm = EconomyExchange.PlayerData.get(uuid);
            List<String> ruleID = dm.GetRuleIDList();
            List<Integer> ruleValue = dm.GetRuleValueList();
            for (int i = 0; i < ruleID.size(); i++) {
                try {
                    Statement statement = SQLLiteData.connection.createStatement();
                    String sql = "UPDATE " + ruleID.get(i) + " SET value = '" +
                            ruleValue.get(i) + "' WHERE (uuid = '" + uuid +
                            "' AND EXISTS (SELECT * FROM (SELECT * FROM " + ruleID.get(i) + " WHERE uuid = '" + uuid + "')tmp));";
                    statement.executeUpdate(sql);
                    statement.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    public static synchronized void SaveValueData(){
        DataManager dm = EconomyExchange.PlayerData.get(AutoResetConfigs.GetAutoResetGlobalName());
        List<String> ruleID = dm.GetRuleIDList();
        List<Integer> ruleValue = dm.GetRuleValueList();
        for (int i = 0; i < ruleID.size(); i++) {
            try {
                Statement statement = SQLLiteData.connection.createStatement();
                String sql = "UPDATE " + ruleID.get(i) + " SET value = '" +
                        ruleValue.get(i) + "' WHERE (uuid = '" + AutoResetConfigs.GetAutoResetGlobalName() +
                        "' AND EXISTS (SELECT * FROM (SELECT * FROM " + ruleID.get(i) + " WHERE uuid = '" + AutoResetConfigs.GetAutoResetGlobalName() + "')tmp));";
                statement.executeUpdate(sql);
                statement.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static synchronized void GetDateData(String ruleID){
        try {
            DataManager dm = null;
            Statement statement = SQLLiteData.connection.createStatement();
            String sql = "SELECT * " +
                    "FROM " + ruleID + " WHERE uuid = '" +
                    AutoResetConfigs.GetAutoResetGlobalName() +"';";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()){
                dm = new DataManager(rs.getString("date"));
            }
            statement.close();
            EconomyExchange.data = dm.GetDataDate();
        } catch (SQLException ex) {
            Bukkit.getConsoleSender().sendMessage(Messages.GetMessages("error-sqlite-error"));
            throw new RuntimeException(ex);
        }
    }

    public static synchronized void GetTimeData(String ruleID){
        try {
            DataManager dm = null;
            Statement statement = SQLLiteData.connection.createStatement();
            String sql = "SELECT * " +
                    "FROM " + ruleID + " WHERE uuid = '" +
                    AutoResetConfigs.GetAutoResetGlobalName() +"';";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()){
                dm = new DataManager(rs.getString("time"));
            }
            statement.close();
            EconomyExchange.time = dm.GetDataDate();
        } catch (SQLException ex) {
            Bukkit.getConsoleSender().sendMessage(Messages.GetMessages("error-sqlite-error"));
            throw new RuntimeException(ex);
        }
    }

    public static synchronized void DeleteTable(String ruleID){
        try {
            Statement statement = SQLLiteData.connection.createStatement();
            String sql = "DELETE FROM " + ruleID + ";";
            statement.executeUpdate(sql);
            List<String> strings = RulesConfigs.GetValidRule();
            for (String str : strings) {
                String sql1 = "CREATE TABLE IF NOT EXISTS " + str + " (" +
                        "uuid TEXT PRIMARY KEY, " +
                        "value INT, " +
                        "date TEXT, " +
                        "time TEXT, " +
                        "UNIQUE (uuid)); ";
                String sql2 = "INSERT INTO " + str + " (uuid, value, date, time) " +
                        "SELECT '" + AutoResetConfigs.GetAutoResetGlobalName() + "', '" +
                        ExchangeConfigs.GetMaxTimesServer(str) + "', '" +
                        AutoResetConfigs.GetNowTime(true) + "', '" +
                        AutoResetConfigs.GetNowTime(false) + "' " +
                        "WHERE NOT EXISTS (SELECT * FROM " + str + " WHERE uuid = '" + AutoResetConfigs.GetAutoResetGlobalName() + "');";
                statement.executeUpdate(sql1);
                statement.executeUpdate(sql2);
            }
            statement.close();
        } catch (SQLException ex) {
            Bukkit.getConsoleSender().sendMessage(Messages.GetMessages("error-mysql-error"));
            throw new RuntimeException(ex);
        }
    }
}
