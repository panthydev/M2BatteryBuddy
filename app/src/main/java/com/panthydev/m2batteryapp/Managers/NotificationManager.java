package com.panthydev.m2batteryapp.Managers;

import android.content.Context;
import android.content.SharedPreferences;

public class NotificationManager {

    //TODO implement super categories for: Powersaving notifs, power left notifs, call to action notifs, app specific notifs.


    public static void SetSystemDischarge(Context context, int discharge){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        prefs.edit().putInt("system_discharge", discharge).apply();

    }

    public static int GetSystemDischarge(Context context){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        return prefs.getInt("system_discharge", 0);
    }





}
