package com.panthydev.m2batteryapp.data.DataObjects.Mappers;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.panthydev.m2batteryapp.DataBase.BatteryTable;
import com.panthydev.m2batteryapp.Interfaces.Mapper;
import com.panthydev.m2batteryapp.data.DataObjects.BatteryData;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;

public class BatteryDataMapper implements Mapper<BatteryData> {

    @Override
    public BatteryData fromCursor(Cursor c)
    {
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
        index = c.getColumnIndexOrThrow(BatteryTable.TIMESTAMP_COL);
        String stringTimestamp = c.getString(index);

        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(stringTimestamp); //TODO add date format to Resources file, standardize is
        } catch (Exception e) {
            e.printStackTrace();
        }

        BatteryData data = new BatteryData(percentLeft,
                currentMAh,
                estimatedBatTimeLeft,
                maxCapacityMAh,
                powerSavingOn,
                date);

        return data;
    }

    @Override
    public ContentValues ToContentValues(BatteryData obj) // was getting api level errors on this?
    {
        long nanoSeconds =0;
        try{
            nanoSeconds = obj.estimatedBatTimeLeft.toNanos();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("TEST", "Could not convert duration to nanoseconds, probably null");
        }



        ContentValues values = new ContentValues();
        values.put("percentLeft", obj.percentLeft);
        values.put("currentMAh", obj.currentMAh);
        values.put("estimatedBatTimeLeft", nanoSeconds);
        values.put("maxCapacityMAh", obj.maxCapacityMAh);
        values.put("powerSavingOn", obj.powerSavingOn ? 1 : 0);

        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(obj.Timestamp);
        values.put("timestamp", date);
        return values;
    }
}
