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

import com.panthydev.m2batteryapp.Interfaces.Mapper;
import com.panthydev.m2batteryapp.data.DataObjects.BatteryData;
import com.panthydev.m2batteryapp.data.DataObjects.DataObject;
import com.panthydev.m2batteryapp.data.DataObjects.DataPack;
import com.panthydev.m2batteryapp.data.DataObjects.Mappers.BatteryDataMapper;

public class DbHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "DB";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase db;
    BatteryDataMapper mapper;


    public DbHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
         db = getReadableDatabase();
         mapper = new BatteryDataMapper();

    }

    // SEPERATE DATA GETTING AND SETTING INTO TWO DIFFERENT OBJECTS

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        String query = QueryBuilder.CreateBatteryTable();
        db.execSQL(query);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void AddBatteryData(BatteryData data)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        try {

            BatteryDataMapper mapper = new BatteryDataMapper();

            ContentValues values = mapper.ToContentValues(data);
            db.insert(BatteryTable.TABLE_NAME, null, values);
            db.close();

        } catch (SQLException e) {
            Log.d("TEST", e.getMessage());
        }
    }

    private <T extends DataObject> DataPack GetAndPackData(Cursor cursor, Mapper<T> mapper)
    {

        DataPack<T> dataPack = new DataPack<T>();

        for (int i = 0; i < cursor.getCount(); i++) {
            if (cursor.moveToNext()) {
                T data = mapper.fromCursor(cursor);
                dataPack.AddData(data);
            }
        }
        return dataPack;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public DataPack<BatteryData> GetBatteryData()
    {

        String query = QueryBuilder.SelectTableDataFromTimeRange(BatteryTable.TABLE_NAME, "2026-05-07 10:00:00", "2029-01-01 10:40:59");

        Cursor cursor = null;

        try {
            cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            DataPack<BatteryData> dataPack = GetAndPackData(cursor, mapper);
            return dataPack;

        } catch (Exception e) {
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }




    //Constructor

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }


}
