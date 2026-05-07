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

import java.text.SimpleDateFormat;
import java.util.Date;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "DB";
    private static final int DB_VERSION = 1;
    BatteryDataMapper batteryMapper;

    public DbHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
        batteryMapper = new BatteryDataMapper();
    }

    //------------------------------------ Public methods ------------------------------------//
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void AddBatteryData(DataPack<BatteryData> dataPack)
    {
        var writableDB = this.getWritableDatabase();
        try {
            for (BatteryData data : dataPack.dataList) {
                ContentValues values = batteryMapper.ToContentValues(data);
                writableDB.insert(BatteryTable.TABLE_NAME, null, values);
            }

            writableDB.close();

        } catch (SQLException e) {
            Log.d("TEST", e.getMessage());
        }
    }


    //------------------------------------ Private methods ------------------------------------//

    /**
     *
     * @param hours How many hours back to get data from, must be positive.
     * @return A DataPack that has a DataList of BatteryData objects.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public DataPack<BatteryData> GetBatteryData(int hours)
    {
        String StartDate = GetRangeStartDate(hours);
        String EndDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String query = QueryBuilder.SelectTableDataFromTimeRange(BatteryTable.TABLE_NAME, StartDate, EndDate);

        Cursor cursor = null;

        try {
            var db = this.getReadableDatabase();
            cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            DataPack<BatteryData> dataPack = GetAndPackData(cursor, batteryMapper);
            return dataPack;

        } catch (Exception e) {
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
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


    //------------------------------------ Default stuff ------------------------------------//

    private String GetRangeStartDate(int hours)
    {
        long nanoSeconds = System.currentTimeMillis() - ((long) hours * 60 * 60 * 1000);
        Date date = new Date(nanoSeconds);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        String query = QueryBuilder.CreateBatteryTable();
        db.execSQL(query);
    }

}
