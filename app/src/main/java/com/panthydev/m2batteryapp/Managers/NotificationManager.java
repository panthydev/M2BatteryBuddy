package com.panthydev.m2batteryapp.Managers;

import android.content.Context;
import android.content.SharedPreferences;

public class NotificationManager {

    //TODO implement super categories for: Powersaving notifs, power left notifs, call to action notifs, app specific notifs.

    // Centralised prefs helpers to avoid repetition and make future changes easier.
    private static final String PREF_NAME = "app_prefs";

    private static SharedPreferences getPrefs(Context context){
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    private static void putBooleanPref(Context context, String key, boolean value){
        getPrefs(context).edit().putBoolean(key, value).apply();
    }

    private static boolean getBooleanPref(Context context, String key, boolean defaultValue){
        return getPrefs(context).getBoolean(key, defaultValue);
    }


    // Powersave related
    public static void SetPowersaveRejseKortApp(Context context, boolean b){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("PowersaveRejseKortApp", b).apply();
    }

    public static boolean GetPowersaveRejseKortApp(Context context){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("PowersaveRejseKortApp", false);
    }

    public static void SetPowersaveNotOnAt50Percent(Context context, boolean b){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("PowersaveNotOnAt50Percent", b).apply();
    }

    public static boolean GetPowersaveNotOnAt50Percent(Context context){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("PowersaveNotOnAt50Percent", false);
    }

    public static void SetPowersaveNotOnAt30Percent(Context context, boolean b){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("PowersaveNotOnAt30Percent", b).apply();
    }

    public static boolean GetPowersaveNotOnAt30Percent(Context context){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("PowersaveNotOnAt30Percent", false);
    }

    public static void SetPowersaveNotOn2HoursLeft(Context context, boolean b){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("PowersaveNotOn2HoursLeft", b).apply();
    }

    public static boolean GetPowersaveNotOn2HoursLeft(Context context){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("PowersaveNotOn2HoursLeft", false);
    }

    public static void SetPowersaveNotOn1HourLeft(Context context, boolean b){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("PowersaveNotOn1HourLeft", b).apply();
    }

    public static boolean GetPowersaveNotOn1HourLeft(Context context){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("PowersaveNotOn1HourLeft", false);
    }

    // Amount related (hours/minutes)
    public static void SetPhoneHas3HoursLeft(Context context, boolean b){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("PhoneHas3HoursLeft", b).apply();
    }

    public static boolean GetPhoneHas3HoursLeft(Context context){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("PhoneHas3HoursLeft", false);
    }

    public static void SetPhoneHas2HoursLeft(Context context, boolean b){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("PhoneHas2HoursLeft", b).apply();
    }

    public static boolean GetPhoneHas2HoursLeft(Context context){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("PhoneHas2HoursLeft", false);
    }

    public static void SetPhoneHas1Point5HoursLeft(Context context, boolean b){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("PhoneHas1Point5HoursLeft", b).apply();
    }

    public static boolean GetPhoneHas1Point5HoursLeft(Context context){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("PhoneHas1Point5HoursLeft", false);
    }

    public static void SetPhoneHas1HourLeft(Context context, boolean b){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("PhoneHas1HourLeft", b).apply();
    }

    public static boolean GetPhoneHas1HourLeft(Context context){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("PhoneHas1HourLeft", false);
    }

    public static void SetPhoneHas30MinLeft(Context context, boolean b){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("PhoneHas30MinLeft", b).apply();
    }

    public static boolean GetPhoneHas30MinLeft(Context context){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("PhoneHas30MinLeft", false);
    }

    // Amount related (percentage)
    public static void SetPhoneHas60PercentLeft(Context context, boolean b){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("PhoneHas60PercentLeft", b).apply();
    }

    public static boolean GetPhoneHas60PercentLeft(Context context){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("PhoneHas60PercentLeft", false);
    }

    public static void SetPhoneHas50PercentLeft(Context context, boolean b){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("PhoneHas50PercentLeft", b).apply();
    }

    public static boolean GetPhoneHas50PercentLeft(Context context){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("PhoneHas50PercentLeft", false);
    }

    public static void SetPhoneHas40PercentLeft(Context context, boolean b){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("PhoneHas40PercentLeft", b).apply();
    }

    public static boolean GetPhoneHas40PercentLeft(Context context){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("PhoneHas40PercentLeft", false);
    }

    public static void SetPhoneHas30PercentLeft(Context context, boolean b){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("PhoneHas30PercentLeft", b).apply();
    }

    public static boolean GetPhoneHas30PercentLeft(Context context){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("PhoneHas30PercentLeft", false);
    }

    public static void SetPhoneHas20PercentLeft(Context context, boolean b){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("PhoneHas20PercentLeft", b).apply();
    }

    public static boolean GetPhoneHas20PercentLeft(Context context){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("PhoneHas20PercentLeft", false);
    }

    public static void SetPhoneHas10PercentLeft(Context context, boolean b){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("PhoneHas10PercentLeft", b).apply();
    }

    public static boolean GetPhoneHas10PercentLeft(Context context){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("PhoneHas10PercentLeft", false);
    }

    // Other related
    public static void SetBatteryMaxCapacityBad(Context context, boolean b){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("BatteryMaxCapacityBad", b).apply();
    }

    public static boolean GetBatteryMaxCapacityBad(Context context){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("BatteryMaxCapacityBad", false);
    }

    public static void SetDigitalMaintenanceCheck(Context context, boolean b){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("DigitalMaintenanceCheck", b).apply();
    }

    public static boolean GetDigitalMaintenanceCheck(Context context){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("DigitalMaintenanceCheck", false);
    }

    public static void SetOnlyWhenOver50Percent(Context context, boolean b){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("OnlyWhenOver50Percent", b).apply();
    }

    public static boolean GetOnlyWhenOver50Percent(Context context){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("OnlyWhenOver50Percent", false);
    }

    public static void SetReadTipsAndTricks(Context context, boolean b){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("ReadTipsAndTricks", b).apply();
    }

    public static boolean GetReadTipsAndTricks(Context context){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("ReadTipsAndTricks", false);
    }

    public static void SetGoldilocksZone(Context context, boolean b){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("GoldilocksZone", b).apply();
    }

    public static boolean GetGoldilocksZone(Context context){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("GoldilocksZone", false);
    }



}
