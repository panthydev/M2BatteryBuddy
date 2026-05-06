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
import com.panthydev.m2batteryapp.data.DataObjects.DataPack;
import com.panthydev.m2batteryapp.data.DataObjects.Mappers.BatteryDataMapper;

import java.time.Duration;
import java.util.Date;

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
    public DataPack<BatteryData> GetBatteryData(){

        //for testing, we add batterydata here first, so theres some data to even test with
        AddBatteryData(new BatteryData(63, 500, Duration.ofMinutes(10), 1000, false, new Date() ));
        AddBatteryData(new BatteryData(32, 500, Duration.ofMinutes(10), 1000, false, new Date() ));
        AddBatteryData(new BatteryData(78, 500, Duration.ofMinutes(10), 1000, false, new Date() ));
        AddBatteryData(new BatteryData(14, 500, Duration.ofMinutes(10), 1000, false, new Date() ));
        AddBatteryData(new BatteryData(33, 500, Duration.ofMinutes(10), 1000, false, new Date() ));
        AddBatteryData(new BatteryData(22, 500, Duration.ofMinutes(10), 1000, false, new Date() ));



        SQLiteDatabase db = getReadableDatabase();

        BatteryDataMapper mapper = new BatteryDataMapper();

        String query = QueryBuilder.SelectTableDataFromTimeRange(BatteryTable.TABLE_NAME, "2022-01-01 00:00:00", "2029-01-01 23:59:59");
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        DataPack<BatteryData> dataPack = new DataPack<BatteryData>();

        for(int i = 0; i < cursor.getCount(); i++){
        cursor.moveToNext();
        BatteryData data = mapper.fromCursor(cursor);
        dataPack.AddData(data);

        }
        return dataPack;
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
