package com.panthydev.m2batteryapp.data.DataCollection;

import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE;
import static android.content.Context.BATTERY_SERVICE;
import static android.content.Context.POWER_SERVICE;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.PowerManager;

import androidx.annotation.RequiresApi;

import com.panthydev.m2batteryapp.Managers.DataManager;
import com.panthydev.m2batteryapp.data.DataObjects.App;
import com.panthydev.m2batteryapp.data.DataObjects.BatteryData;
import com.panthydev.m2batteryapp.data.DataObjects.DataPack;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Date;
import java.util.List;

public class SystemDataCollector{

    App[] appArray;
    Context context;

    public SystemDataCollector(Context context){
        this.context = context;
    }

    public void CollectAndSendBatteryDataToDB() { //Needs to run on the interval
        BatteryManager BM = (BatteryManager) context.getSystemService(BATTERY_SERVICE); //Getting access to the Battery Manager
        PowerManager PM = (PowerManager) context.getSystemService(POWER_SERVICE); //Getting access to the Power Manager

        int batLevelPercent = BM.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY); //Finds Battery percentage at this moment
        int batCapMAh = BM.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER); //Entire Battery capacity in microampere-hours, as an integer.
        int batCurrentMAh = BM.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW); //Instantaneous battery current in microamperes, as an integer (positive means it is charging, negative is draining)
        boolean powerSaveOn = PM.isPowerSaveMode(); //Checks if powersaving mode is on

        Duration remainingBatLife = null;
        //Checks if the phone which runs this, has a high enough API level (is new enough)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            try {
                // Use reflection to avoid potential signature mismatches with desugared java.time types on some platform versions
                Method getPredictionMethod = PowerManager.class.getMethod("getBatteryDischargePrediction");
                Object result = getPredictionMethod.invoke(PM);
                if (result != null) {
                    // Use reflection to get minutes from the returned Duration object (java.time.Duration)
                    Method toMinutesMethod = result.getClass().getMethod("toMinutes");
                    long minutes = (long) toMinutesMethod.invoke(result);
                    System.out.println("Estimated remaining battery left is: " + minutes + " minutes");
                    remainingBatLife = Duration.ofMinutes(minutes);
                }
            } catch (Throwable t) {
                // Method might not be present on this specific device implementation even if API >= 31, or other reflection error
                System.out.println("Could not get battery discharge prediction: " + t.getMessage());
                //TODO implement all the other stuff to let user know it doesnt work + add to prefs
            }
        }

        BatteryData batData = new BatteryData(batLevelPercent, batCapMAh, remainingBatLife, batCapMAh, powerSaveOn); //Making a battery data object with the collected data
        var datapack = new DataPack<BatteryData>();
        datapack.AddData(batData); //Adding the battery data object to the data pack
        DataManager.SetBatteryDataAsync(context,datapack) ; //Sending the battery data object to the database
    }

    /**
     * should only run once when the app is first opened
     */
    public void CollectAndSendUsageDataToDB () { // Needs to run once when the app is first opened
        List<ApplicationInfo> applicationInfoList = context.getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA); // Get a list of packages with applications

        appArray = new App[applicationInfoList.size()]; // Making an array for all the app objects ToBeMade
        int i = 0; // just a Counter, dont worry :)

        for (ApplicationInfo applicationInfo : applicationInfoList) {
            String appName = applicationInfo.packageName; // Finding the app name
            int appCat = -1; // CATEGORY_UNDEFINED
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                appCat = applicationInfo.category; // Finding the app category
            }
            int appDischarge = 0; // No discharge data collected yet

            if (appCat != -1) { //Only makes objects of importance, based on the category number (aka all but -1)
                appArray[i] = new App(appName, appCat, appDischarge); // Making all the app objects
                System.out.println("here is the app: " + appArray[i].getAppName() + " The Category: " + appArray[i].getAppCategory());
            }
            i++;
        }
        var datapack = new DataPack<App>();

        for (App app : appArray) {
            if (app != null) {
                datapack.AddData(app); // Adding the app data object to the data pack
            }
        }
        DataManager.SetAppDataAsync(context,datapack) ; //Sending the app data

        //note from panthy: why have they not used enums, are they weird ?

        // When it gathers categories, an int is assigned, so here is every int and their respective category:
        // -1 = CATEGORY_UNDEFINED (Value when category is undefined.)
        // 0 = CATEGORY_GAME (Category for apps which are primarily games.)
        // 1 = CATEGORY_AUDIO (Category for apps which primarily work with audio or music, such as music players.)
        // 2 = CATEGORY_VIDEO (Category for apps which primarily work with video or movies, such as streaming video apps.)
        // 3 = CATEGORY_IMAGE (Category for apps which primarily work with images or photos, such as camera or gallery apps.)
        // 4 = CATEGORY_SOCIAL (Category for apps which are primarily social apps, such as messaging, communication, email, or social network apps.)
        // 5 = CATEGORY_NEWS (Category for apps which are primarily news apps, such as newspapers, magazines, or sports apps.)
        // 6 = CATEGORY_MAPS (Category for apps which are primarily maps apps, such as navigation apps.)
        // 7 = CATEGORY_PRODUCTIVITY (Category for apps which are primarily productivity apps, such as cloud storage or workplace apps.)
        // 8 = CATEGORY_ACCESSIBILITY (Category for apps which are primarily accessibility apps, such as screen-readers.)

    }

    int appDischarge; // The power discharge of an app used (with the system discharged)
    int SystemDischarge; // The power discharge of the system

    public void appDischargeTimer () { //Needs to run on the interval
        BatteryManager BM = (BatteryManager) context.getSystemService(BATTERY_SERVICE); //Getting access to the Battery Manager (again lol)
        ActivityManager.RunningAppProcessInfo appProcessInfo = new ActivityManager.RunningAppProcessInfo(); //Getting access to the Activity Manager and info of all the running apps
        ActivityManager.getMyMemoryState(appProcessInfo); // it does something cool and it works, but i dont know how :P

        // WorkManager runs on a background thread without a Looper, so avoid CountDownTimer here.
        int firstReading = BM.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
        int secondReading = firstReading;
        try {
            Thread.sleep(1000);
            secondReading = BM.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        int averageDischarge = (firstReading + secondReading) / 2;

        if (appProcessInfo.importance == IMPORTANCE_FOREGROUND || appProcessInfo.importance == IMPORTANCE_VISIBLE) { // Checks for if you are on an app
            setAppDischarge(averageDischarge);

            dischargeSaver();
        }
        else { // Aka. you don't have an app open (i hope it works lol)
            setSystemDischarge(averageDischarge);
        }
    }

    public void setAppDischarge (int appDischarge) {
        this.appDischarge = appDischarge;
    }

    public int getAppDischarge () {
        return appDischarge;
    }

    public void setSystemDischarge (int SystemDischarge) {
        this.SystemDischarge = SystemDischarge;
    }

    public int getSystemDischarge () {
        return SystemDischarge;
    }

    public void dischargeSaver () {
        ActivityManager.RunningAppProcessInfo appProcessInfo = new ActivityManager.RunningAppProcessInfo(); //Getting access to the Activity Manager and info of all the running apps
        ActivityManager.getMyMemoryState(appProcessInfo); // it does something cool and it works, but i dont know how :P

        List<ApplicationInfo> applicationInfoList = context.getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA); // Get a list of packages with applications
        appArray = new App[applicationInfoList.size()]; // Making an array for all the app objects ToBeMade

        int i = 0; // just a Counter again, dont worry :)

        for (ApplicationInfo applicationInfo : applicationInfoList) {
            String appName = applicationInfo.packageName; // Finding the app name
            int appCat = -1; // CATEGORY_UNDEFINED
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                appCat = applicationInfo.category; // Finding the app category
            }
            int appDischarge = getAppDischarge(); // Is getting the discharge data just collected

            if (appName != null && appName.equals(appProcessInfo.processName)) { // Should be checking if the app package name is equals to the process name (THIS NEEDS TO BE CHECKED JUST TO BE SURE IT IS WORKING)
                appArray[i] = new App(appName, appCat, appDischarge); // Making all the app objects


                var datapack = new DataPack<App>();

                for (App app : appArray) {
                    if (app != null) {
                        datapack.AddData(app); // Adding the app data object to the data pack
                    }
                }
                DataManager.SetAppDataAsync(context,datapack) ; //Sending the app data
            }
            i++;
        }
    }
}
