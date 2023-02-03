package cn.superiormc.manager;

import cn.superiormc.configs.Messages;
import cn.superiormc.hooks.*;
import cn.superiormc.mysql.CheckData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ExchangeManager{

    private Player player;
    private String ruleID;

    private int ExchangeEconomyAmount;

    private int CostEconomyAmount;

    private String ExchangeEconomyType;

    private String CostEconomyType;

    private int amount;

    public ExchangeManager(Player player, String ruleID, int amount, int ExchangeEconomyAmount, int CostEconomyAmount, String ExchangeEconomyType, String CostEconomyType)
    {
        this.player = player;
        this.ruleID = ruleID;
        this.amount = amount;
        this.ExchangeEconomyAmount = ExchangeEconomyAmount;
        this.ExchangeEconomyType = ExchangeEconomyType;
        this.CostEconomyAmount = CostEconomyAmount;
        this.CostEconomyType = CostEconomyType;
        StartCost();
    }

    public void StartCost(){
        if(this.CostEconomyType.equals("PlayerPoints") && PlayerPointsHook.CheckLoadPlayerPoints()){
            if(CheckData.GetDataType(this.player, this.ruleID, this.amount) && CostPlayerPoints()){
                StartExchange();
            }
        }
        else if(this.CostEconomyType.equals("Vault") && VaultHook.CheckLoadVault()){
            if(CheckData.GetDataType(this.player, this.ruleID, this.amount) && CostVault()){
                StartExchange();
            }
        }
        else if(this.CostEconomyType.equals("GamePoints") && GamePointsHook.CheckLoadGamePoints()){
            if(CheckData.GetDataType(this.player, this.ruleID, this.amount) && CostGamePoints()){
                StartExchange();
            }
        }
        else if(this.CostEconomyType.equals("PlayerTitle") && PlayerTitleHook.CheckLoadPlayerTitle()){
            if(CheckData.GetDataType(this.player, this.ruleID, this.amount) && CostPlayerTitle()){
                StartExchange();
            }
        }
        else if(this.CostEconomyType.equals("PlayerTask") && PlayerTaskHook.CheckLoadPlayerTask()){
            if(CheckData.GetDataType(this.player, this.ruleID, this.amount) && CostPlayerTask()){
                StartExchange();
            }
        }
        else if(this.CostEconomyType.equals("EliteMobs") && EliteMobsHook.CheckLoadEliteMobs()){
            if(CheckData.GetDataType(this.player, this.ruleID, this.amount) && CostEliteMobs()){
                StartExchange();
            }
        }
        else if(this.CostEconomyType.contains("PEconomy;;") && PEconomyHook.CheckLoadPEconomy()){
            String[] currencyName = this.CostEconomyType.split(";;");
            if(currencyName[1] == null){
                this.player.sendMessage(Messages.GetMessages("error-config-error-rule"));
            }
            else if(CheckData.GetDataType(this.player, this.ruleID, this.amount) && CostPEconomy(currencyName[1])){
                StartExchange();
            }
        }
        else if(this.CostEconomyType.contains("Custom;;")){
            String[] currencyInfo = this.CostEconomyType.split(";;");
            if(currencyInfo[1] == null || currencyInfo[3] == null){
                this.player.sendMessage(Messages.GetMessages("error-config-error-rule"));
            }
            else if(CheckData.GetDataType(this.player, this.ruleID, this.amount) && CostCustom(currencyInfo[1], currencyInfo[3])){
                StartExchange();
            }
        }
        else{
            player.sendMessage(Messages.GetMessages("error-config-error-rule"));
        }
    }

    public void StartExchange()
    {
        if(this.ExchangeEconomyType.equals("PlayerPoints") && PlayerPointsHook.CheckLoadPlayerPoints()){
            ExchangePlayerPoints();
            SetData();
        }
        else if(this.ExchangeEconomyType.equals("Vault") && VaultHook.CheckLoadVault()){
            ExchangeVault();
            SetData();
        }
        else if(this.ExchangeEconomyType.equals("GamePoints") && GamePointsHook.CheckLoadGamePoints()){
            ExchangeGamePoints();
            SetData();
        }
        else if(this.ExchangeEconomyType.equals("PlayerTitle") && PlayerTitleHook.CheckLoadPlayerTitle()){
            ExchangePlayerTitle();
            SetData();
        }
        else if(this.ExchangeEconomyType.equals("PlayerTask") && PlayerTaskHook.CheckLoadPlayerTask()){
            ExchangePlayerTask();
            SetData();
        }
        else if(this.ExchangeEconomyType.equals("EliteMobs") && EliteMobsHook.CheckLoadEliteMobs()){
            ExchangeEliteMobs();
            SetData();
        }
        else if(this.ExchangeEconomyType.contains("PEconomy;;") && PEconomyHook.CheckLoadPEconomy()){
            String[] currencyName = this.ExchangeEconomyType.split(";;");
            if(currencyName[1] == null) {
                this.player.sendMessage(Messages.GetMessages("error-config-error-rule"));
            }
            else {
                ExchangePEconomy(currencyName[1]);
            }
            SetData();
        }
        else if(this.ExchangeEconomyType.contains("Custom;;")){
            String[] currencyInfo = this.ExchangeEconomyType.split(";;");
            if(currencyInfo[2] == null) {
                this.player.sendMessage(Messages.GetMessages("error-config-error-rule"));
            }
            else {
                ExchangeCustom(currencyInfo[2]);
            }
            SetData();
        }
        else{
            this.player.sendMessage(Messages.GetMessages("error-config-error-rule"));
        }
    }

    public void SetData(){
        CheckData.SetValueData(this.player, this.ruleID, CheckData.GetValueData(this.player, this.ruleID) - this.amount);
        CheckData.SetValueData(this.ruleID, CheckData.GetValueData(this.ruleID) - this.amount);
    }

    public void ExchangePlayerPoints()
    {
        PlayerPointsHook.GivePoints(this.player, this.ExchangeEconomyAmount);
        this.player.sendMessage(Messages.GetMessages("exchange-success-PlayerPoints").replace("%amount%", String.valueOf(this.ExchangeEconomyAmount)));
    }

    public void ExchangeVault()
    {
        VaultHook.GiveVault(this.player, this.ExchangeEconomyAmount);
        this.player.sendMessage(Messages.GetMessages("exchange-success-Vault").replace("%amount%", String.valueOf(this.ExchangeEconomyAmount)));
    }

    public void ExchangeGamePoints()
    {
        GamePointsHook.GiveGamePoints(this.player, this.ExchangeEconomyAmount);
        this.player.sendMessage(Messages.GetMessages("exchange-success-GamePoints").replace("%amount%", String.valueOf(this.ExchangeEconomyAmount)));
    }

    public void ExchangePlayerTitle()
    {
        PlayerTitleHook.GivePlayerTitle(this.player, this.ExchangeEconomyAmount);
        this.player.sendMessage(Messages.GetMessages("exchange-success-PlayerTitle").replace("%amount%", String.valueOf(this.ExchangeEconomyAmount)));
    }

    public void ExchangePlayerTask()
    {
        PlayerTaskHook.GivePlayerTask(this.player, this.ExchangeEconomyAmount);
        this.player.sendMessage(Messages.GetMessages("exchange-success-PlayerTask").replace("%amount%", String.valueOf(this.ExchangeEconomyAmount)));
    }

    public void ExchangeEliteMobs()
    {
        EliteMobsHook.GiveEliteMobs(this.player, this.ExchangeEconomyAmount);
        this.player.sendMessage(Messages.GetMessages("exchange-success-EliteMobs").replace("%amount%", String.valueOf(this.ExchangeEconomyAmount)));
    }

    public void ExchangePEconomy(String currencyName)
    {
        PEconomyHook.GivePEconomy(currencyName, this.player, this.ExchangeEconomyAmount);
        this.player.sendMessage(Messages.GetMessages("exchange-success-PEconomy").replace("%amount%", String.valueOf(this.ExchangeEconomyAmount)));
    }

    public void ExchangeCustom(String giveCommand)
    {
        CustomHook.GiveMoney(giveCommand.replace("%amount%", String.valueOf(this.ExchangeEconomyAmount)), this.player, this.ExchangeEconomyAmount);
        this.player.sendMessage(Messages.GetMessages("exchange-success-Custom").replace("%amount%", String.valueOf(this.ExchangeEconomyAmount)));
    }

    public boolean CostPlayerPoints()
    {
        if(PlayerPointsHook.CheckEnoughPlayerPoints(this.player, this.CostEconomyAmount)) {
            PlayerPointsHook.TakePoints(this.player, this.CostEconomyAmount);
            return true;
        }
        else {
            this.player.sendMessage(Messages.GetMessages("exchange-failure-PlayerPoints"));
            return false;
        }
    }

    public boolean CostVault()
    {
        if(VaultHook.CheckEnoughVault(this.player, this.CostEconomyAmount)) {
            VaultHook.TakeVault(this.player, this.CostEconomyAmount);
            return true;
        }
        else {
            this.player.sendMessage(Messages.GetMessages("exchange-failure-Vault"));
            return false;
        }
    }

    public boolean CostGamePoints()
    {
        if(GamePointsHook.CheckEnoughGamePoints(this.player, this.CostEconomyAmount)) {
            GamePointsHook.TakeGamePoints(this.player, this.CostEconomyAmount);
            return true;
        }
        else {
            this.player.sendMessage(Messages.GetMessages("exchange-failure-GamePoints"));
            return false;
        }
    }

    public boolean CostPlayerTitle()
    {
        if(PlayerTitleHook.CheckEnoughPlayerTitle(this.player, this.CostEconomyAmount)) {
            PlayerTitleHook.TakePlayerTitle(this.player, this.CostEconomyAmount);
            return true;
        }
        else {
            this.player.sendMessage(Messages.GetMessages("exchange-failure-PlayerTitle"));
            return false;
        }
    }

    public boolean CostPlayerTask()
    {
        if(PlayerTaskHook.CheckEnoughPlayerTask(this.player, this.CostEconomyAmount)) {
            PlayerTaskHook.TakePlayerTask(this.player, this.CostEconomyAmount);
            return true;
        }
        else {
            this.player.sendMessage(Messages.GetMessages("exchange-failure-PlayerTask"));
            return false;
        }
    }

    public boolean CostEliteMobs()
    {
        if(EliteMobsHook.CheckEnoughEliteMobs(this.player, this.CostEconomyAmount)) {
            EliteMobsHook.TakeEliteMobs(this.player, this.CostEconomyAmount);
            return true;
        }
        else {
            this.player.sendMessage(Messages.GetMessages("exchange-failure-EliteMobs"));
            return false;
        }
    }

    public boolean CostPEconomy(String currencyName)
    {
        if(PEconomyHook.CheckEnoughPEconomy(currencyName, this.player, this.CostEconomyAmount)) {
            PEconomyHook.TakePEconomy(currencyName, this.player, this.CostEconomyAmount);
            return true;
        }
        else {
            this.player.sendMessage(Messages.GetMessages("exchange-failure-PEconomy"));
            return false;
        }
    }

    public boolean CostCustom(String placeholder, String takeCommand)
    {
        if(CustomHook.CheckEnoughMoney(placeholder, this.player, this.CostEconomyAmount)) {
            CustomHook.TakeMoney(takeCommand.replace("%amount%", String.valueOf(this.CostEconomyAmount)), this.player, this.CostEconomyAmount);
            return true;
        }
        else {
            this.player.sendMessage(Messages.GetMessages("exchange-failure-Custom"));
            return false;
        }
    }

}
