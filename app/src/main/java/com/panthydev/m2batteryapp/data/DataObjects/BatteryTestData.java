package com.panthydev.m2batteryapp.data.DataObjects;

import android.content.ContentValues;
import android.database.Cursor;

import com.panthydev.m2batteryapp.Interfaces.Mapper;

public class BatteryTestData implements Mapper<BatteryTestData>
{
    public float BatteryPowerLeft;


@Override
public BatteryTestData fromCursor(Cursor c)
{
    return null;
}

@Override
public ContentValues ToDataBaseValues(BatteryTestData obj)
{
    return null;
}
}
