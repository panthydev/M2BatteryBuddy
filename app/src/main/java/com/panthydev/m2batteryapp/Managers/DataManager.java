package com.panthydev.m2batteryapp.Managers;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.panthydev.m2batteryapp.DataBase.DbHelper;
import com.panthydev.m2batteryapp.Interfaces.Callback;
import com.panthydev.m2batteryapp.data.DataObjects.BatteryData;
import com.panthydev.m2batteryapp.data.DataObjects.DataPack;

public class DataManager
{
    public static void GetBatteryDataAsync(Context context, Callback<DataPack<BatteryData>> callback)
    {
        DbHelper dbHelper = new DbHelper(context);
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {

                DataPack<BatteryData> dataPack = dbHelper.GetBatteryData();
                callback.OnResult(dataPack);
            }
        }).start();
    }
}
