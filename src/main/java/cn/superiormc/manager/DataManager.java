package cn.superiormc.manager;

import java.util.List;

public class DataManager {

    private List<String> ruleID;

    private List<Integer> ruleValue;

    private int value;

    private String dateOrTime;


    public DataManager(int value){
        this.value = value;
    }

    public DataManager(String dateOrTime){
        this.dateOrTime = dateOrTime;
    }

    public DataManager(List<String> ruleID, List<Integer> ruleValue){
        this.ruleID = ruleID;
        this.ruleValue = ruleValue;
    }

    public int GetDataValue(){
        return this.value;
    }

    public String GetDataDate(){
        return this.dateOrTime;
    }

    public List<String> GetRuleIDList(){
        return this.ruleID;
    }

    public List<Integer> GetRuleValueList(){
        return this.ruleValue;
    }
}
