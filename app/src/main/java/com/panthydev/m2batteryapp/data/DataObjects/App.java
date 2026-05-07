package com.panthydev.m2batteryapp.data.DataObjects;

import android.app.ApplicationErrorReport;
import android.app.usage.UsageStats;
import android.graphics.drawable.Drawable;
import android.os.Parcel;

import java.util.List;

public class App {

    String appName;
    int appCategory;
    //Drawable appIcon;

    public App (String appName, int appCategory) {
        this.appName = appName;
        this.appCategory = appCategory;
        //this.appIcon = appIcon;
    }

    public String getAppName () {
        return appName;
    }

    public int getAppCategory () {
        return appCategory;
    }

//    public Drawable getAppIcon () {
//        return appIcon;
//    }
}
