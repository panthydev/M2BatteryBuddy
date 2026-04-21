package com.panthydev.m2batteryapp.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.panthydev.m2batteryapp.data.DataObjects.BatteryTestData;

public class DbHelper extends SQLiteOpenHelper
{
//    public static BatteryTestData GetBatteryData() // static for testing
//    {
//        // temporary for testing, replace with actual SQLite db
//        var batteryData = new BatteryTestData();
//        batteryData.BatteryPowerLeft = 20;
//        return batteryData;
//    }

    private static final String DB_NAME = "DB";
    private static final int DB_VERSION = 1;



    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = QueryBuilder.CreateBatteryTable();
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //Constructor

    public DbHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }


}
