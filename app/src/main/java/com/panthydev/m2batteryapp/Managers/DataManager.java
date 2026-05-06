package com.panthydev.m2batteryapp.Managers;

import android.content.Context;

import com.panthydev.m2batteryapp.DataBase.DbHelper;
import com.panthydev.m2batteryapp.Interfaces.Callback;
import com.panthydev.m2batteryapp.data.DataObjects.BatteryData;

public class DataManager
{
    public static void GetBatteryDataAsync(Context context, Callback<BatteryData> callback)
    {
        DbHelper dbHelper = new DbHelper(context);
        new Thread(new Runnable() {
            @Override
            public void run() {

                BatteryData BatteryData = dbHelper.GetBatteryData();
                callback.OnResult(BatteryData);
            }
        }).start();
    }
}
