package com.panthydev.m2batteryapp.DataBase;

import com.panthydev.m2batteryapp.data.DataObjects.BatteryTestData;

public class DbHelper
{
    public static BatteryTestData GetBatteryData() // static for testing
    {
        // temporary for testing, replace with actual SQLite db
        var batteryData = new BatteryTestData();
        batteryData.BatteryPowerLeft = 20;
        return batteryData;
    }

}
