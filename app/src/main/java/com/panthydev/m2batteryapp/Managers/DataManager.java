package com.panthydev.m2batteryapp.Managers;

import android.content.Context;
import android.util.Log;

import com.panthydev.m2batteryapp.DataBase.DbHelper;
import com.panthydev.m2batteryapp.Interfaces.Callback;
import com.panthydev.m2batteryapp.data.DataObjects.BatteryTestData;

public class DataManager
{
    public static void GetBatteryDataAsync(Context context, Callback<BatteryTestData> callback)
    {
        DbHelper dbHelper = new DbHelper(context);
        new Thread(new Runnable() {
            @Override
            public void run() {

                BatteryTestData BatteryData = dbHelper.GetBatteryData();
                callback.OnResult(BatteryData);
            }
        }).start();
    }
}
