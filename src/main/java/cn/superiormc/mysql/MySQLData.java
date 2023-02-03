package cn.superiormc.mysql;

import cn.superiormc.EconomyExchange;
import cn.superiormc.configs.*;
import cn.superiormc.manager.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.List;
import java.util.UUID;

public class MySQLData {

    public static Connection connection;
    public static synchronized void MySQLConnect(String host, int port, String database, String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            Bukkit.getConsoleSender().sendMessage(Messages.GetMessages("error-mysql-not-installed"));
            Bukkit.getPluginManager().disablePlugin(EconomyExchange.instance);
        }
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&autoReconnect=true";
        try {
            connection = DriverManager.getConnection(url, username, password);
            List<String> strings = RulesConfigs.GetValidRule();
            Statement statement = connection.createStatement();
            for (String str : strings) {
                String sql = "CREATE TABLE IF NOT EXISTS " + str + " (" +
                        "uuid VARCHAR(100) PRIMARY KEY, " +
                        "value INT, " +
                        "date VARCHAR(20), " +
                        "time VARCHAR(20), " +
                        "UNIQUE (uuid)); ";
                String sql1 = "INSERT " + str + "(uuid, value, date, time) " +
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
            Bukkit.getConsoleSender().sendMessage(Messages.GetMessages("error-mysql-error"));
            throw new RuntimeException(e);
        }
    }

    public static synchronized void MySQLDown(){
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
                String sql = "INSERT " + str + "(uuid, value, date, time) " +
                        "SELECT '" + uuid + "', '" + ExchangeConfigs.GetMaxTimesPlayer(str) + "', '" +
                        AutoResetConfigs.GetNowTime(true) + "', '" +
                        AutoResetConfigs.GetNowTime(false) + "'" +
                        "WHERE NOT EXISTS (SELECT * FROM " + str + " WHERE uuid = '" + uuid + "')";
                statement.executeUpdate(sql);
            }
            statement.close();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(Messages.GetMessages("error-mysql-error"));
            throw new RuntimeException(e);
        }
    }

    public static synchronized int GetValueData(Player player, String ruleID){
        try {
            DataManager dm = null;
            Statement statement = MySQLData.connection.createStatement();
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
            Bukkit.getConsoleSender().sendMessage(Messages.GetMessages("error-mysql-error"));
            throw new RuntimeException(ex);
        }
    }

    public static synchronized int GetValueData(String ruleID){
        try {
            DataManager dm = null;
            Statement statement = MySQLData.connection.createStatement();
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
            throw new RuntimeException(ex);
        }
    }

    public static synchronized void SavePlayerValueData(){
        // 同时也保存一下全服数据
        SaveValueData();
        for(String uuid : EconomyExchange.PlayerData.keySet()) {
            DataManager dm = EconomyExchange.PlayerData.get(uuid);
            List<String> ruleID = dm.GetRuleIDList();
            List<Integer> ruleValue = dm.GetRuleValueList();
            for (int i = 0; i < ruleID.size(); i++) {
                try {
                    Statement statement = MySQLData.connection.createStatement();
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
                Statement statement = MySQLData.connection.createStatement();
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
            Statement statement = MySQLData.connection.createStatement();
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
            throw new RuntimeException(ex);
        }
    }

    public static synchronized void GetTimeData(String ruleID){
        try {
            DataManager dm = null;
            Statement statement = MySQLData.connection.createStatement();
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
            throw new RuntimeException(ex);
        }
    }

    public static synchronized void DeleteTable(String ruleID){
        try {
            Statement statement = MySQLData.connection.createStatement();
            String sql = "DELETE FROM " + ruleID + ";";
            statement.executeUpdate(sql);
            List<String> strings = RulesConfigs.GetValidRule();
            for (String str : strings) {
                String sql1 = "CREATE TABLE IF NOT EXISTS " + str + " (" +
                        "uuid VARCHAR(100) PRIMARY KEY, " +
                        "value INT, " +
                        "date VARCHAR(20), " +
                        "time VARCHAR(20), " +
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
            throw new RuntimeException(ex);
        }
    }
}
