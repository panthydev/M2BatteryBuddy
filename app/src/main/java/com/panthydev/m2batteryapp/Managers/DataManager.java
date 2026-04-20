package com.panthydev.m2batteryapp.Managers;

import com.panthydev.m2batteryapp.DataBase.DbHelper;
import com.panthydev.m2batteryapp.Interfaces.Callback;
import com.panthydev.m2batteryapp.data.DataObjects.BatteryTestData;

public class DataManager
{
    public static void GetBatteryDataAsync(Callback<BatteryTestData> callback)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BatteryTestData BatteryData = DbHelper.GetBatteryData();
                callback.OnResult(BatteryData);
            }
        }).start();
    }
}
