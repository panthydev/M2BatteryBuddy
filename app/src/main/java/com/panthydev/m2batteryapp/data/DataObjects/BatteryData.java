package com.panthydev.m2batteryapp.data.DataObjects;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.panthydev.m2batteryapp.Interfaces.Mapper;

import java.time.Duration;
import java.util.Date;

public class BatteryData extends DataObject {
    public int percentLeft; // in percent
    public int currentMAh; // in MAh
    public Duration estimatedBatTimeLeft; // in Duration
    public int maxCapacityMAh; // in MAh
    public boolean powerSavingOn; // true/false


    public BatteryData(int percentLeft,
                       int currentMAh,
                       Duration estimatedBatTimeLeft,
                       int maxCapacityMAh,
                       boolean powerSavingOn,
                       Date timestamp){

        this.percentLeft = percentLeft;
        this.currentMAh = currentMAh;
        this.estimatedBatTimeLeft = estimatedBatTimeLeft;
        this.maxCapacityMAh = maxCapacityMAh;
        this.powerSavingOn = powerSavingOn;
        super.Timestamp = timestamp;
    }


    public BatteryData(int percentLeft,
                       int currentMAh,
                       Duration estimatedBatTimeLeft,
                       int maxCapacityMAh,
                       boolean powerSavingOn){

        this.percentLeft = percentLeft;
        this.currentMAh = currentMAh;
        this.estimatedBatTimeLeft = estimatedBatTimeLeft;
        this.maxCapacityMAh = maxCapacityMAh;
        this.powerSavingOn = powerSavingOn;
        super.Timestamp = new Date();
    }



}



