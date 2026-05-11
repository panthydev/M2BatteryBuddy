package com.panthydev.m2batteryapp.data.DataObjects;

import android.app.ApplicationErrorReport;
import android.app.usage.UsageStats;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;
import android.os.Parcel;

import java.util.Date;
import java.util.List;

public class App extends DataObject {

    String appName;
    int appCategory;
    int appDischarge; //With systemDischarge

    public App (String appName, int appCategory, int appDischarge) {
        this.appName = appName;
        this.appCategory = appCategory;
        this.appDischarge = appDischarge;
        super.Timestamp = new Date();
    }

    public App (String appName, int appCategory, int appDischarge, Date timestamp) {
        this.appName = appName;
        this.appCategory = appCategory;
        this.appDischarge = appDischarge;
        super.Timestamp = timestamp;
    }

    public String getAppName () {
        return appName;
    }

    public int getAppCategory () {
        return appCategory;
    }

    public int getAppDischarge () {
        return appDischarge;
    }

}
