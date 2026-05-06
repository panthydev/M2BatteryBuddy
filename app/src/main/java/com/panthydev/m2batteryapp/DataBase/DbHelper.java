package com.panthydev.m2batteryapp.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.android.material.bottomappbar.BottomAppBarTopEdgeTreatment;
import com.panthydev.m2batteryapp.data.DataObjects.BatteryTestData;

public class DbHelper extends SQLiteOpenHelper
{


    private static final String DB_NAME = "DB";
    private static final int DB_VERSION = 1;



    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = QueryBuilder.CreateBatteryTable();
        db.execSQL(query);
    }

    // SEPERATE DATA GETTING AND SETTING INTO TWO DIFFERENT OBJECTS

    public void AddBatteryData(float capacity,
                               float maxCharge,
                               float minCharge,
                               float powerRemaining,
                               float temperature,
                               String timestamp){

         SQLiteDatabase db = this.getWritableDatabase();

        try{

            ContentValues values = new ContentValues();
            values.put(BatteryTable.CAPACITY_COL, capacity);
            values.put(BatteryTable.MAX_CHARGE_COL, maxCharge);
            values.put(BatteryTable.MIN_CHARGE_COL, minCharge);
            values.put(BatteryTable.POWER_REMAINING_COL, powerRemaining);
            values.put(BatteryTable.TEMPERATURE_COL, temperature);
            values.put(BatteryTable.TIMESTAMP_COL, timestamp);

            db.insert(BatteryTable.TABLE_NAME, null, values);
            db.close();

        } catch (SQLException e){
            Log.d("TEST", e.getMessage());
        }
    }
    
    public BatteryTestData GetBatteryData(){

        AddBatteryData(70, 100, 0, 59, 20, "2026-04-22 10:55");


        SQLiteDatabase db = getReadableDatabase();

        BatteryTestData data = new BatteryTestData();

        String query = QueryBuilder.SelectTable(BatteryTable.TABLE_NAME);
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        int index = cursor.getColumnIndexOrThrow(BatteryTable.POWER_REMAINING_COL);
        data.BatteryPowerLeft = cursor.getFloat(index);
        cursor.close();
        return data;
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
