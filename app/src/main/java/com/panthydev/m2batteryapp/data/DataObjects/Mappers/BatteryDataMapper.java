package com.panthydev.m2batteryapp.data.DataObjects.Mappers;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.panthydev.m2batteryapp.DataBase.BatteryTable;
import com.panthydev.m2batteryapp.Interfaces.Mapper;
import com.panthydev.m2batteryapp.data.DataObjects.BatteryData;

import java.time.Duration;
import java.util.ArrayList;

public class BatteryDataMapper implements Mapper<BatteryData> {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public BatteryData fromCursor(Cursor c)
    {
        c.moveToFirst();
        int index = c.getColumnIndexOrThrow(BatteryTable.PERCENT_REMAINING_COL);
        int percentLeft = c.getInt(index);
        index = c.getColumnIndexOrThrow(BatteryTable.CURRENT_MAH_COL);
        int currentMAh = c.getInt(index);
        index = c.getColumnIndexOrThrow(BatteryTable.ESTIMATED_TIME_LEFT_COL);
        long nanoSeconds = c.getLong(index);
        index = c.getColumnIndexOrThrow(BatteryTable.MAX_CAPACITY_MAH_COL);
        int maxCapacityMAh = c.getInt(index);
        index = c.getColumnIndexOrThrow(BatteryTable.POWER_SAVING_ON_COL);
        boolean powerSavingOn = c.getInt(index) == 1;
        // again getting api level errors
        Duration estimatedBatTimeLeft = Duration.ofNanos(nanoSeconds);

        BatteryData data = new BatteryData(percentLeft,
                currentMAh,
                estimatedBatTimeLeft,
                maxCapacityMAh,
                powerSavingOn);
        c.close();
        return data;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public ContentValues ToContentValues(BatteryData obj) // was getting api level errors on this?
    {
        long nanoSeconds = obj.estimatedBatTimeLeft.toNanos();

        ContentValues values = new ContentValues();
        values.put("percentLeft", obj.percentLeft);
        values.put("currentMAh", obj.currentMAh);
        values.put("estimatedBatTimeLeft", nanoSeconds);
        values.put("maxCapacityMAh", obj.maxCapacityMAh);
        return values;
    }
}
