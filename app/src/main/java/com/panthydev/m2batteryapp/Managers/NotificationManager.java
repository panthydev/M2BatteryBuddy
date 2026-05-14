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

    /// Notif Bools ///
    //Percent and powersave
    public static void SetSwitchPowersave60percent(Context context, boolean b){
        putBooleanPref(context, "SwitchPowersave60percent", b);
    }
    public static boolean GetSwitchPowersave60percent(Context context){
        return getBooleanPref(context, "SwitchPowersave60percent", false);
    }

    public static void SetSwitchPowersave50percent(Context context, boolean b){
        putBooleanPref(context, "SwitchPowersave50percent", b);
    }
    public static boolean GetSwitchPowersave50percent(Context context){
        return getBooleanPref(context, "SwitchPowersave50percent", false);
    }

    public static void SetSwitchPowersave40percent(Context context, boolean b){
        putBooleanPref(context, "SwitchPowersave40percent", b);
    }
    public static boolean GetSwitchPowersave40percent(Context context){
        return getBooleanPref(context, "SwitchPowersave40percent", false);
    }

    public static void SetSwitchPowersave30percent(Context context, boolean b){
        putBooleanPref(context, "SwitchPowersave30percent", b);
    }
    public static boolean GetSwitchPowersave30percent(Context context){
        return getBooleanPref(context, "SwitchPowersave30percent", false);
    }

    public static void SetSwitchPowersave20percent(Context context, boolean b){
        putBooleanPref(context, "SwitchPowersave20percent", b);
    }
    public static boolean GetSwitchPowersave20percent(Context context){
        return getBooleanPref(context, "SwitchPowersave20percent", false);
    }

    public static void SetSwitchPowersave10percent(Context context, boolean b){
        putBooleanPref(context, "SwitchPowersave10percent", b);
    }
    public static boolean GetSwitchPowersave10percent(Context context){
        return getBooleanPref(context, "SwitchPowersave10percent", false);
    }

    //time and powersave
    public static void SetSwitchPowersave3hours(Context context, boolean b){
        putBooleanPref(context, "SwitchPowersave3hours", b);
    }
    public static boolean GetSwitchPowersave3hours(Context context){
        return getBooleanPref(context, "SwitchPowersave3hours", false);
    }

    public static void SetSwitchPowersave25hours(Context context, boolean b){
        putBooleanPref(context, "SwitchPowersave25hours", b);
    }
    public static boolean GetSwitchPowersave25hours(Context context){
        return getBooleanPref(context, "SwitchPowersave25hours", false);
    }

    public static void SetSwitchPowersave2hours(Context context, boolean b){
        putBooleanPref(context, "SwitchPowersave2hours", b);
    }
    public static boolean GetSwitchPowersave2hours(Context context){
        return getBooleanPref(context, "SwitchPowersave2hours", false);
    }

    public static void SetSwitchPowersave15hours(Context context, boolean b){
        putBooleanPref(context, "SwitchPowersave15hours", b);
    }
    public static boolean GetSwitchPowersave15hours(Context context){
        return getBooleanPref(context, "SwitchPowersave15hours", false);
    }

    public static void SetSwitchPowersave1hours(Context context, boolean b){
        putBooleanPref(context, "SwitchPowersave1hour", b);
    }
    public static boolean GetSwitchPowersave1hours(Context context){
        return getBooleanPref(context, "SwitchPowersave1hour", false);
    }

    public static void SetSwitchPowersave30min(Context context, boolean b){
        putBooleanPref(context, "SwitchPowersave30min", b);
    }
    public static boolean GetSwitchPowersave30min(Context context){
        return getBooleanPref(context, "SwitchPowersave30min", false);
    }

    //Percent
    public static void SetSwitch60percent(Context context, boolean b){
        putBooleanPref(context, "Switch60percent", b);
    }
    public static boolean GetSwitch60percent(Context context){
        return getBooleanPref(context, "Switch60percent", false);
    }

    public static void SetSwitch50percent(Context context, boolean b){
        putBooleanPref(context, "Switch50percent", b);
    }
    public static boolean GetSwitch50percent(Context context){
        return getBooleanPref(context, "Switch50percent", false);
    }

    public static void SetSwitch40percent(Context context, boolean b){
        putBooleanPref(context, "Switch40percent", b);
    }
    public static boolean GetSwitch40percent(Context context){
        return getBooleanPref(context, "Switch40percent", false);
    }

    public static void SetSwitch30percent(Context context, boolean b){
        putBooleanPref(context, "Switch30percent", b);
    }
    public static boolean GetSwitch30percent(Context context){
        return getBooleanPref(context, "Switch30percent", false);
    }

    public static void SetSwitch20percent(Context context, boolean b){
        putBooleanPref(context, "Switch20percent", b);
    }
    public static boolean GetSwitch20percent(Context context){
        return getBooleanPref(context, "Switch20percent", false);
    }

    public static void SetSwitch10percent(Context context, boolean b){
        putBooleanPref(context, "Switch10percent", b);
    }
    public static boolean GetSwitch10percent(Context context){
        return getBooleanPref(context, "Switch10percent", false);
    }

    //Time
    public static void SetSwitch3hours(Context context, boolean b){
        putBooleanPref(context, "Switch3hours", b);
    }
    public static boolean GetSwitch3hours(Context context){
        return getBooleanPref(context, "Switch3hours", false);
    }

    public static void SetSwitch25hours(Context context, boolean b){
        putBooleanPref(context, "Switch25hours", b);
    }
    public static boolean GetSwitch25hours(Context context){
        return getBooleanPref(context, "Switch25hours", false);
    }

    public static void SetSwitch2hours(Context context, boolean b){
        putBooleanPref(context, "Switch2hours", b);
    }
    public static boolean GetSwitch2hours(Context context){
        return getBooleanPref(context, "Switch2hours", false);
    }

    public static void SetSwitch15hours(Context context, boolean b){
        putBooleanPref(context, "Switch15hours", b);
    }
    public static boolean GetSwitch15hours(Context context){
        return getBooleanPref(context, "Switch15hours", false);
    }

    public static void SetSwitch1hours(Context context, boolean b){
        putBooleanPref(context, "Switch1hour", b);
    }
    public static boolean GetSwitch1hours(Context context){
        return getBooleanPref(context, "Switch1hour", false);
    }

    public static void SetSwitch30min(Context context, boolean b){
        putBooleanPref(context, "Switch30min", b);
    }
    public static boolean GetSwitch30min(Context context){
        return getBooleanPref(context, "Switch30min", false);
    }

    //Tips
    public static void SetSwitchTips(Context context, boolean b){
        putBooleanPref(context, "SwitchTips", b);
    }
    public static boolean GetSwitchTips(Context context){
        return getBooleanPref(context, "SwitchTips", false);
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
        //    public static void SetPowersaveRejseKortApp(Context Syscontext, boolean b){
        //        putBooleanPref(Syscontext, "PowersaveRejseKortApp", b);
        //    }
        //
        //    public static boolean GetPowersaveRejseKortApp(Context Syscontext){
        //        return getBooleanPref(Syscontext, "PowersaveRejseKortApp", false);
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
        //    public static void SetBatteryMaxCapacityBad(Context Syscontext, boolean b){
        //        putBooleanPref(Syscontext, "BatteryMaxCapacityBad", b);
        //    }
        //
        //    public static boolean GetBatteryMaxCapacityBad(Context Syscontext){
        //        return getBooleanPref(Syscontext, "BatteryMaxCapacityBad", false);
        //    }
        //
        //    public static void SetDigitalMaintenanceCheck(Context Syscontext, boolean b){
        //        putBooleanPref(Syscontext, "DigitalMaintenanceCheck", b);
        //    }
        //
        //    public static boolean GetDigitalMaintenanceCheck(Context Syscontext){
        //        return getBooleanPref(Syscontext, "DigitalMaintenanceCheck", false);
        //    }
        //
        //    public static void SetOnlyWhenOver50Percent(Context Syscontext, boolean b){
        //        putBooleanPref(Syscontext, "OnlyWhenOver50Percent", b);
        //    }
        //
        //    public static boolean GetOnlyWhenOver50Percent(Context Syscontext){
        //        return getBooleanPref(Syscontext, "OnlyWhenOver50Percent", false);
        //    }
        //
        //    public static void SetReadTipsAndTricks(Context Syscontext, boolean b){
        //        putBooleanPref(Syscontext, "ReadTipsAndTricks", b);
        //    }
        //
        //    public static boolean GetReadTipsAndTricks(Context Syscontext){
        //        return getBooleanPref(Syscontext, "ReadTipsAndTricks", false);
        //    }
        //
        //    public static void SetGoldilocksZone(Context Syscontext, boolean b){
        //        putBooleanPref(Syscontext, "GoldilocksZone", b);
        //    }
        //
        //    public static boolean GetGoldilocksZone(Context Syscontext){
        //        return getBooleanPref(Syscontext, "GoldilocksZone", false);
        //    }

}
