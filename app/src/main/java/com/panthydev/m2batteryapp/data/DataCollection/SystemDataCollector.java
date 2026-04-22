package com.panthydev.m2batteryapp.data.DataCollection;

import android.app.Activity;
import android.os.BatteryManager;
import android.os.Build;
import android.os.PowerManager;

import java.time.Duration;

public class SystemDataCollector extends Activity {

    public void CollectAndSendBatteryDataToDB() {
        BatteryManager BM = (BatteryManager) getSystemService(BATTERY_SERVICE); //Getting access to the Battery Manager
        PowerManager PM = (PowerManager) getSystemService(POWER_SERVICE); //Getting access to the Power Manager

        int batLevelPercent = BM.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY); //Finds Battery percentage at this moment
        int batCapMAh = BM.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER); //Entire Battery capacity in microampere-hours, as an integer.
        int batCurrentMAh = BM.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW); //Instantaneous battery current in microamperes, as an integer (positive means it is charging, negative is draining)
        boolean powerSaveOn = PM.isPowerSaveMode(); //Checks if powersaving mode is on

        //Checks if the phone which runs this, has a high enough API level (is new enough)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Duration remainingBatLife = PM.getBatteryDischargePrediction(); //Estimates the time in minutes there is left on the phone
            System.out.println("Estimated remaining battery left is: " + (remainingBatLife.toMinutes()) + " minutes");
        }
        else {
            System.out.println("Estimated remaining battery left in time is unavailable");
        }

        System.out.println("The battery level is: " + batLevelPercent);
        System.out.println("The battery capacity in microampere-hours is: " + batCapMAh);
        System.out.println("The battery current in microampere-hours is: " + batCurrentMAh);
        System.out.println("Power save mode is on: " + powerSaveOn);
    }
}
