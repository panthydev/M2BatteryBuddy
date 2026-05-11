package com.panthydev.m2batteryapp.data.DataCollection;

import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.PowerManager;

import androidx.annotation.RequiresApi;

import com.panthydev.m2batteryapp.Managers.DataManager;
import com.panthydev.m2batteryapp.data.DataObjects.App;
import com.panthydev.m2batteryapp.data.DataObjects.BatteryData;
import com.panthydev.m2batteryapp.data.DataObjects.DataPack;

import java.time.Duration;
import java.util.Date;
import java.util.List;

public class SystemDataCollector extends Activity {

    App[] appArray;


    @RequiresApi(api = Build.VERSION_CODES.S)
    public void CollectAndSendBatteryDataToDB() { //Needs to run on the interval
        BatteryManager BM = (BatteryManager) getSystemService(BATTERY_SERVICE); //Getting access to the Battery Manager
        PowerManager PM = (PowerManager) getSystemService(POWER_SERVICE); //Getting access to the Power Manager

        int batLevelPercent = BM.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY); //Finds Battery percentage at this moment
        int batCapMAh = BM.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER); //Entire Battery capacity in microampere-hours, as an integer.
        int batCurrentMAh = BM.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW); //Instantaneous battery current in microamperes, as an integer (positive means it is charging, negative is draining)
        boolean powerSaveOn = PM.isPowerSaveMode(); //Checks if powersaving mode is on

        //Checks if the phone which runs this, has a high enough API level (is new enough)

            Duration remainingBatLife = PM.getBatteryDischargePrediction(); //Estimates the time in minutes there is left on the phone
            System.out.println("Estimated remaining battery left is: " + (remainingBatLife.toMinutes()) + " minutes");


        BatteryData batData = new BatteryData(batLevelPercent, batCapMAh, remainingBatLife, batCapMAh, powerSaveOn); //Making a battery data object with the collected data
        var datapack = new DataPack<BatteryData>();
        datapack.AddData(batData); //Adding the battery data object to the data pack
        DataManager.SetBatteryDataAsync(this,datapack) ; //Sending the battery data object to the database
    }

    /**
     * should only run once when the app is first opened
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void CollectAndSendUsageDataToDB () { // Needs to run once when the app is first opened
        List<ApplicationInfo> applicationInfoList = getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA); // Get a list of packages with applications

        appArray = new App[applicationInfoList.size()]; // Making an array for all the app objects ToBeMade
        int i = 0; // just a Counter, dont worry :)

        for (ApplicationInfo applicationInfo : applicationInfoList) {
            String appName = applicationInfoList.get(i).packageName; // Finding the app name
            int appCat = applicationInfoList.get(i).category; // Finding the app category (see below for meaning of each number)
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
        DataManager.SetAppDataAsync(this,datapack) ; //Sending the app data

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void appDischargeTimer () { //Needs to run on the interval
        BatteryManager BM = (BatteryManager) getSystemService(BATTERY_SERVICE); //Getting access to the Battery Manager (again lol)
        ActivityManager.RunningAppProcessInfo appProcessInfo = new ActivityManager.RunningAppProcessInfo(); //Getting access to the Activity Manager and info of all the running apps
        ActivityManager.getMyMemoryState(appProcessInfo); // it does something cool and it works, but i dont know how :P

        if (appProcessInfo.importance == IMPORTANCE_FOREGROUND || appProcessInfo.importance == IMPORTANCE_VISIBLE) { // Checks for if you are on an app
            new CountDownTimer(2000, 1000) { // This timer gathers the discharge for 1 sec (i hope), which we can upscale as we want
                int duringAppUsedDischarge;
                int finnishedAppUsedDischarge;
                int AppUsedDischarge;

                @Override public void onTick(long millisUntilFinished) { // first discharge check
                    duringAppUsedDischarge = BM.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
                }

                @Override public void onFinish() { // second discharge check
                    finnishedAppUsedDischarge = BM.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
                    AppUsedDischarge = duringAppUsedDischarge + finnishedAppUsedDischarge;
                    setAppDischarge(AppUsedDischarge / 2);
                }
            }.start();

            dischargeSaver();
        }
        else { // Aka. you don't have an app open (i hope it works lol)
            new CountDownTimer(2000, 1000) {
                int duringSystemDischarge;
                int finnishedSystemDischarge;
                int SystemDischarge;

                public void onTick(long millisUntilFinished) { // first discharge check
                    duringSystemDischarge = BM.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
                }

                public void onFinish() { // second discharge check
                    finnishedSystemDischarge = BM.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
                    SystemDischarge = duringSystemDischarge + finnishedSystemDischarge;
                    setSystemDischarge(SystemDischarge / 2);
                }
            }.start();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void dischargeSaver () {
        ActivityManager.RunningAppProcessInfo appProcessInfo = new ActivityManager.RunningAppProcessInfo(); //Getting access to the Activity Manager and info of all the running apps
        ActivityManager.getMyMemoryState(appProcessInfo); // it does something cool and it works, but i dont know how :P

        List<ApplicationInfo> applicationInfoList = getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA); // Get a list of packages with applications
        appArray = new App[applicationInfoList.size()]; // Making an array for all the app objects ToBeMade

        int i = 0; // just a Counter again, dont worry :)

        for (ApplicationInfo applicationInfo : applicationInfoList) {
            String appName = applicationInfoList.get(i).packageName; // Finding the app name
            int appCat = applicationInfoList.get(i).category; // Finding the app category (see below for meaning of each number)
            int appDischarge = getAppDischarge(); // Is getting the discharge data just collected

            if (appName == appProcessInfo.processName) { // Should be checking if the app package name is equals to the process name (THIS NEEDS TO BE CHECKED JUST TO BE SURE IT IS WORKING)
                appArray[i] = new App(appName, appCat, appDischarge); // Making all the app objects


                var datapack = new DataPack<App>();

                for (App app : appArray) {
                    if (app != null) {
                        datapack.AddData(app); // Adding the app data object to the data pack
                    }
                }
                DataManager.SetAppDataAsync(this,datapack) ; //Sending the app data
            }
            i++;
        }
    }
}
