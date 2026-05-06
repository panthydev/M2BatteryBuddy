package com.panthydev.m2batteryapp.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import com.panthydev.m2batteryapp.data.DataObjects.BatteryData;
import com.panthydev.m2batteryapp.data.DataObjects.Mappers.BatteryDataMapper;

import java.time.Duration;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void AddBatteryData(BatteryData data){

         SQLiteDatabase db = this.getWritableDatabase();
        try{

            BatteryDataMapper mapper = new BatteryDataMapper();

            ContentValues values = mapper.ToContentValues(data);
            db.insert(BatteryTable.TABLE_NAME, null, values);
            db.close();

        } catch (SQLException e){
            Log.d("TEST", e.getMessage());
        }
    }
    
    @RequiresApi(api = Build.VERSION_CODES.O)
    public BatteryData GetBatteryData(){

        //for testing, we add batterydata here first, so theres some data to even test with
        AddBatteryData(new BatteryData(40, 500, Duration.ofMinutes(10), 1000, false));


        SQLiteDatabase db = getReadableDatabase();

        BatteryDataMapper mapper = new BatteryDataMapper();

        String query = QueryBuilder.SelectTable(BatteryTable.TABLE_NAME);
        Cursor cursor = db.rawQuery(query, null);
        BatteryData data = mapper.fromCursor(cursor);
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
