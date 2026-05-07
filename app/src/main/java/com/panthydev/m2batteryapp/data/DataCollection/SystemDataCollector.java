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

import com.panthydev.m2batteryapp.data.DataObjects.App;

import java.time.Duration;
import java.util.List;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void CollectAndSendUsageDataToDB () {
        List<ApplicationInfo> applicationInfoList = getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA); // Get a list of packages with applications

        App[] appArray = new App[applicationInfoList.size()]; // Making an array for all the app objects ToBeMade
        int i = 0; // just a Counter, dont worry :)

        for (ApplicationInfo applicationInfo : applicationInfoList) {
            String appName = applicationInfoList.get(i).packageName; // Finding the app name
            int appCat = applicationInfoList.get(i).category; // Finding the app category (see below for meaning of each number)

            if (appCat != -1) { //Only makes objects of importance, based on the category number (aka all but -1)
                appArray[i] = new App(appName, appCat); // Making all the app objects
                System.out.println("here is the app: " + appArray[i].getAppName() + " The Category: " + appArray[i].getAppCategory());
            }
            i++;
        }

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

    public int appDischargeCalculator () {
        BatteryManager BM = (BatteryManager) getSystemService(BATTERY_SERVICE); //Getting access to the Battery Manager (again lol)
        ActivityManager.RunningAppProcessInfo appProcessInfo = new ActivityManager.RunningAppProcessInfo(); //Getting access to the Activity Manager and info of all the running apps
        ActivityManager.getMyMemoryState(appProcessInfo); // it does something cool and it works, but i dont know how :P

        int appUsedDischarge; // The power discharge of an app used (inkl. the system discharge)
        int noAppUsedDischarge; // The power discharge when no app is used (system discharge)
        int appDischarge; // The power discharge of an app used (without the system discharged)

        if (appProcessInfo.importance == IMPORTANCE_FOREGROUND || appProcessInfo.importance == IMPORTANCE_VISIBLE) { // Checks for if you are on an app
            new CountDownTimer(2000, 1000) { // This timer gathers the discharge for 1 sec (i hope), which we can upscale as we want
                int duringAppUsedDischarge;
                int finnishedAppUsedDischarge;

                public void onTick(long millisUntilFinished) { // first discharge check
                    duringAppUsedDischarge = BM.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
                }

                public void onFinish() { // second discharge check
                    finnishedAppUsedDischarge = BM.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
                    appUsedDischarge = (duringAppUsedDischarge + finnishedAppUsedDischarge) / 2;
                }
            }.start();
        }
        else { // Aka. you don't have an app open (i hope it works lol)
            new CountDownTimer(2000, 1000) {
                int duringNoAppUsedDischarge;
                int finnishedNoAppUsedDischarge;

                public void onTick(long millisUntilFinished) { // first discharge check
                    duringNoAppUsedDischarge = BM.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
                }

                public void onFinish() { // second discharge check
                    finnishedNoAppUsedDischarge = BM.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
                    noAppUsedDischarge = (duringNoAppUsedDischarge + finnishedNoAppUsedDischarge) / 2;
                }
            }.start();
        }

        appDischarge = appUsedDischarge - noAppUsedDischarge;
        return appDischarge;
    }
}
