package cn.superiormc.tasks;

import cn.superiormc.mysql.Database;

public class AutoSaveTask {

    public static void StartTask(){
        Database.SaveData();
    }

}
