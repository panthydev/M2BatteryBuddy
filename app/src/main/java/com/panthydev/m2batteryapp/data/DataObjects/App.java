package com.panthydev.m2batteryapp.data.DataObjects;

import java.util.Date;

public class App extends DataObject {

    String appName;
    int appCategory;
    int appDischarge; //With systemDischarge
    int backgroundProcessCount;

    public App (String appName, int appCategory, int appDischarge, int backgroundProcessCount) {
        this.appName = appName;
        this.appCategory = appCategory;
        this.appDischarge = appDischarge;
        this.backgroundProcessCount = backgroundProcessCount;
        super.Timestamp = new Date();
    }

    public App (String appName, int appCategory, int appDischarge, int backgroundProcessCount, Date timestamp) {
        this.appName = appName;
        this.appCategory = appCategory;
        this.appDischarge = appDischarge;
        this.backgroundProcessCount = backgroundProcessCount;
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

    public int getBackgroundProcessCount() {
        return backgroundProcessCount;
    }

}
