package cn.superiormc.configs;

import cn.superiormc.EconomyExchange;
import cn.superiormc.mysql.CheckData;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

public class AutoResetConfigs {

    public static ZonedDateTime time;
    public static boolean GetAutoResetEnabled() {
        return EconomyExchange.instance.getConfig().getBoolean("auto-reset.enabled");
    }

    public static String GetAutoResetTimeZone() {
        return EconomyExchange.instance.getConfig().getString("auto-reset.time-zone");
    }

    public static String GetAutoResetGlobalName() {
        return EconomyExchange.instance.getConfig().getString("mysql.global-name");
    }

    public static String GetAutoResetResetTime() {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDate.parse(Objects.requireNonNull(EconomyExchange.instance.getConfig().getString("auto-reset.reset-time")),dateTimeFormatter);
        }catch (DateTimeParseException e){
            return "00:00:00";
        }
        return EconomyExchange.instance.getConfig().getString("auto-reset.reset-time");
    }

    public static long GetAutoResetPeriodTime() {
        return EconomyExchange.instance.getConfig().getLong("auto-reset.period-time");
    }

    public static String GetNowTime(boolean a) {
        time = Instant.now().atZone(ZoneId.of(AutoResetConfigs.GetAutoResetTimeZone()));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mm:ss");
        if (a){
            return dtf.format(time);
        }
        else{
            return GetAutoResetResetTime();
        }
    }
}
