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

    /// System stuff ///

    public static void SetFirstAppCollectionOn(Context context, boolean b){
        putBooleanPref(context, "FirstAppCollection", b);
    }

    public static boolean GetFirstAppCollectionOn(Context context){
        return getBooleanPref(context, "FirstAppCollection", false);
    }

    public static void SetIntervalOn(Context context, boolean b){
        putBooleanPref(context, "Interval", b);
    }

    public static boolean GetIntervalOn(Context context){
        return getBooleanPref(context, "Interval", false);
    }

    // Powersave related
        //    public static void SetPowersaveRejseKortApp(Context context, boolean b){
        //        putBooleanPref(context, "PowersaveRejseKortApp", b);
        //    }
        //
        //    public static boolean GetPowersaveRejseKortApp(Context context){
        //        return getBooleanPref(context, "PowersaveRejseKortApp", false);
        //    }

    /// POWER SAVE PERCENT ///
    public static void SetPowersaveNotOnAt60Percent(Context context, boolean b){
        putBooleanPref(context, "PowersaveNotOnAt60Percent", b);
    }

    public static boolean GetPowersaveNotOnAt60Percent(Context context){
        return getBooleanPref(context, "PowersaveNotOnAt60Percent", false);
    }

    public static void SetPowersaveNotOnAt50Percent(Context context, boolean b){
        putBooleanPref(context, "PowersaveNotOnAt50Percent", b);
    }

    public static boolean GetPowersaveNotOnAt50Percent(Context context){
        return getBooleanPref(context, "PowersaveNotOnAt50Percent", false);
    }

    public static void SetPowersaveNotOnAt40Percent(Context context, boolean b){
        putBooleanPref(context, "PowersaveNotOnAt40Percent", b);
    }

    public static boolean GetPowersaveNotOnAt40Percent(Context context){
        return getBooleanPref(context, "PowersaveNotOnAt40Percent", false);
    }

    public static void SetPowersaveNotOnAt30Percent(Context context, boolean b){
        putBooleanPref(context, "PowersaveNotOnAt30Percent", b);
    }

    public static boolean GetPowersaveNotOnAt30Percent(Context context){
        return getBooleanPref(context, "PowersaveNotOnAt30Percent", false);
    }

    public static void SetPowersaveNotOnAt20Percent(Context context, boolean b){
        putBooleanPref(context, "PowersaveNotOnAt20Percent", b);
    }

    public static boolean GetPowersaveNotOnAt20Percent(Context context){
        return getBooleanPref(context, "PowersaveNotOnAt20Percent", false);
    }

    public static void SetPowersaveNotOnAt10Percent(Context context, boolean b){
        putBooleanPref(context, "PowersaveNotOnAt10Percent", b);
    }

    public static boolean GetPowersaveNotOnAt10Percent(Context context){
        return getBooleanPref(context, "PowersaveNotOnAt10Percent", false);
    }

    /// POWER SAVE TIME ///
    public static void SetPowersaveNotOn3HoursLeft(Context context, boolean b){
        putBooleanPref(context, "PowersaveNotOn3HoursLeft", b);
    }

    public static boolean GetPowersaveNotOn3HoursLeft(Context context){
        return getBooleanPref(context, "PowersaveNotOn3HoursLeft", false);
    }

    public static void SetPowersaveNotOn25HoursLeft(Context context, boolean b){
        putBooleanPref(context, "PowersaveNotOn25HoursLeft", b);
    }

    public static boolean GetPowersaveNotOn25HoursLeft(Context context){
        return getBooleanPref(context, "PowersaveNotOn25HoursLeft", false);
    }

    public static void SetPowersaveNotOn2HoursLeft(Context context, boolean b){
        putBooleanPref(context, "PowersaveNotOn2HoursLeft", b);
    }

    public static boolean GetPowersaveNotOn2HoursLeft(Context context){
        return getBooleanPref(context, "PowersaveNotOn2HoursLeft", false);
    }

    public static void SetPowersaveNotOn15HoursLeft(Context context, boolean b){
        putBooleanPref(context, "PowersaveNotOn15HoursLeft", b);
    }

    public static boolean GetPowersaveNotOn15HoursLeft(Context context){
        return getBooleanPref(context, "PowersaveNotOn15HoursLeft", false);
    }

    public static void SetPowersaveNotOn1HourLeft(Context context, boolean b){
        putBooleanPref(context, "PowersaveNotOn1HourLeft", b);
    }

    public static boolean GetPowersaveNotOn1HourLeft(Context context){
        return getBooleanPref(context, "PowersaveNotOn1HourLeft", false);
    }

    public static void SetPowersaveNotOn30MinLeft(Context context, boolean b){
        putBooleanPref(context, "PowersaveNotOn30MinLeft", b);
    }

    public static boolean GetPowersaveNotOn30MinLeft(Context context){
        return getBooleanPref(context, "PowersaveNotOn30MinLeft", false);
    }

    // Amount related (hours/minutes)
    public static void SetPhoneHas3HoursLeft(Context context, boolean b){
        putBooleanPref(context, "PhoneHas3HoursLeft", b);
    }

    public static boolean GetPhoneHas3HoursLeft(Context context){
        return getBooleanPref(context, "PhoneHas3HoursLeft", false);
    }

    public static void SetPhoneHas25HoursLeft(Context context, boolean b){
        putBooleanPref(context, "PhoneHas25HoursLeft", b);
    }

    public static boolean GetPhoneHas25HoursLeft(Context context){
        return getBooleanPref(context, "PhoneHas25HoursLeft", false);
    }

    public static void SetPhoneHas2HoursLeft(Context context, boolean b){
        putBooleanPref(context, "PhoneHas2HoursLeft", b);
    }

    public static boolean GetPhoneHas2HoursLeft(Context context){
        return getBooleanPref(context, "PhoneHas2HoursLeft", false);
    }

    public static void SetPhoneHas15HoursLeft(Context context, boolean b){
        putBooleanPref(context, "PhoneHas15HoursLeft", b);
    }

    public static boolean GetPhoneHas15HoursLeft(Context context){
        return getBooleanPref(context, "PhoneHas15HoursLeft", false);
    }

    public static void SetPhoneHas1HourLeft(Context context, boolean b){
        putBooleanPref(context, "PhoneHas1HourLeft", b);
    }

    public static boolean GetPhoneHas1HourLeft(Context context){
        return getBooleanPref(context, "PhoneHas1HourLeft", false);
    }

    public static void SetPhoneHas30MinLeft(Context context, boolean b){
        putBooleanPref(context, "PhoneHas30MinLeft", b);
    }

    public static boolean GetPhoneHas30MinLeft(Context context){
        return getBooleanPref(context, "PhoneHas30MinLeft", false);
    }

    // Amount related (percentage)
    public static void SetPhoneHas60PercentLeft(Context context, boolean b){
        putBooleanPref(context, "PhoneHas60PercentLeft", b);
    }

    public static boolean GetPhoneHas60PercentLeft(Context context){
        return getBooleanPref(context, "PhoneHas60PercentLeft", false);
    }

    public static void SetPhoneHas50PercentLeft(Context context, boolean b){
        putBooleanPref(context, "PhoneHas50PercentLeft", b);
    }

    public static boolean GetPhoneHas50PercentLeft(Context context){
        return getBooleanPref(context, "PhoneHas50PercentLeft", false);
    }

    public static void SetPhoneHas40PercentLeft(Context context, boolean b){
        putBooleanPref(context, "PhoneHas40PercentLeft", b);
    }

    public static boolean GetPhoneHas40PercentLeft(Context context){
        return getBooleanPref(context, "PhoneHas40PercentLeft", false);
    }

    public static void SetPhoneHas30PercentLeft(Context context, boolean b){
        putBooleanPref(context, "PhoneHas30PercentLeft", b);
    }

    public static boolean GetPhoneHas30PercentLeft(Context context){
        return getBooleanPref(context, "PhoneHas30PercentLeft", false);
    }

    public static void SetPhoneHas20PercentLeft(Context context, boolean b){
        putBooleanPref(context, "PhoneHas20PercentLeft", b);
    }

    public static boolean GetPhoneHas20PercentLeft(Context context){
        return getBooleanPref(context, "PhoneHas20PercentLeft", false);
    }

    public static void SetPhoneHas10PercentLeft(Context context, boolean b){
        putBooleanPref(context, "PhoneHas10PercentLeft", b);
    }

    public static boolean GetPhoneHas10PercentLeft(Context context){
        return getBooleanPref(context, "PhoneHas10PercentLeft", false);
    }

    // Other related
        //    public static void SetBatteryMaxCapacityBad(Context context, boolean b){
        //        putBooleanPref(context, "BatteryMaxCapacityBad", b);
        //    }
        //
        //    public static boolean GetBatteryMaxCapacityBad(Context context){
        //        return getBooleanPref(context, "BatteryMaxCapacityBad", false);
        //    }
        //
        //    public static void SetDigitalMaintenanceCheck(Context context, boolean b){
        //        putBooleanPref(context, "DigitalMaintenanceCheck", b);
        //    }
        //
        //    public static boolean GetDigitalMaintenanceCheck(Context context){
        //        return getBooleanPref(context, "DigitalMaintenanceCheck", false);
        //    }
        //
        //    public static void SetOnlyWhenOver50Percent(Context context, boolean b){
        //        putBooleanPref(context, "OnlyWhenOver50Percent", b);
        //    }
        //
        //    public static boolean GetOnlyWhenOver50Percent(Context context){
        //        return getBooleanPref(context, "OnlyWhenOver50Percent", false);
        //    }
        //
        //    public static void SetReadTipsAndTricks(Context context, boolean b){
        //        putBooleanPref(context, "ReadTipsAndTricks", b);
        //    }
        //
        //    public static boolean GetReadTipsAndTricks(Context context){
        //        return getBooleanPref(context, "ReadTipsAndTricks", false);
        //    }
        //
        //    public static void SetGoldilocksZone(Context context, boolean b){
        //        putBooleanPref(context, "GoldilocksZone", b);
        //    }
        //
        //    public static boolean GetGoldilocksZone(Context context){
        //        return getBooleanPref(context, "GoldilocksZone", false);
        //    }

}
