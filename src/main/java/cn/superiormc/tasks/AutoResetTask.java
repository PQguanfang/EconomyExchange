package cn.superiormc.tasks;

import cn.superiormc.configs.AutoResetConfigs;
import cn.superiormc.configs.RulesConfigs;
import cn.superiormc.mysql.CheckData;
import cn.superiormc.mysql.Database;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Iterator;
import java.util.List;

public class AutoResetTask {

    public static void StartTask(){
        if(GetNowingTime().isAfter(GetSQLTime())){
            Database.DeleteTable();
        }
    }

    // 获取执行该方法时的时间
    // 两个时间，一个是现在时间，另外一个是 SQL 的时间
    // 玩家每天进服都会有一个 SQL 的时间
    // 第二天时现在时间就会晚于 SQL 的时间
    public static ZonedDateTime GetNowingTime() {
        ZonedDateTime zdt = Instant.now().atZone(ZoneId.of(AutoResetConfigs.GetAutoResetTimeZone()));
        return zdt;
    }

    public static ZonedDateTime GetSQLTime() {
        List<String> strings = RulesConfigs.GetValidRule();
        Iterator<String> iterator = strings.iterator();
        return CheckData.GetSQLTime(iterator.next());
    }
}
