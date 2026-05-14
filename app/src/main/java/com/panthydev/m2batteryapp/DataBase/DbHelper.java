package com.panthydev.m2batteryapp.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.panthydev.m2batteryapp.Interfaces.Mapper;
import com.panthydev.m2batteryapp.data.DataObjects.App;
import com.panthydev.m2batteryapp.data.DataObjects.BatteryData;
import com.panthydev.m2batteryapp.data.DataObjects.DataObject;
import com.panthydev.m2batteryapp.data.DataObjects.DataPack;
import com.panthydev.m2batteryapp.data.DataObjects.Mappers.AppMapper;
import com.panthydev.m2batteryapp.data.DataObjects.Mappers.BatteryDataMapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DbHelper extends SQLiteOpenHelper implements AutoCloseable {

    //TODO Implement AppUsage methods. both for getting and setting data. Waiting for data collection to be implemented.
    private static final String DB_NAME = "DB";
    private static final int DB_VERSION = 1;
    BatteryDataMapper batteryMapper;
    AppMapper appMapper;

    public DbHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
        batteryMapper = new BatteryDataMapper();
        appMapper = new AppMapper();
    }

    //------------------------------------ Public methods ------------------------------------//

    /**
     * Adds a DataPack of BatteryData objects to the database.
     * @param dataPack A DataPack of BatteryData objects.
     */
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
            Log.d("TEST", String.valueOf(e.getMessage()));
        }
    }

    public void AddAppData(DataPack<App> dataPack)
    {
        try (var writableDB = this.getWritableDatabase()) {
            if (dataPack == null || dataPack.dataList == null) {
                return;
            }

            for (App app : dataPack.dataList) {
                if (app == null) {
                    continue;
                }

                ContentValues values = appMapper.ToContentValues(app);

                if (app.getAppDischarge() != 0) {
                    long bootstrapRowId = findBootstrapAppRowId(writableDB, app.getAppName());
                    if (bootstrapRowId != -1) {
                        int rowsUpdated = writableDB.update(
                                AppTable.TABLE_NAME,
                                values,
                                AppTable.ID_COL + " = ?",
                                new String[]{String.valueOf(bootstrapRowId)}
                        );

                        if (rowsUpdated > 0) {
                            Log.d("DbHelper", "Replaced bootstrap app row id=" + bootstrapRowId + " name=" + app.getAppName());
                            continue;
                        }
                    }
                }
                long id = writableDB.insert(AppTable.TABLE_NAME, null, values);
                Log.d("DbHelper", "Inserted app row id=" + id + " name=" + app.getAppName() + " discharge=" + app.getAppDischarge());
            }
        } catch (SQLException e) {
            Log.d("TEST", String.valueOf(e.getMessage()));
        }
    }

    public DataPack<BatteryData> GetAllBatteryData() {
        return GetBatteryDataSince(null);
    }

    public DataPack<App> GetAllAppData() {
        return GetAppData(null, -1);
    }

    public DataPack<App> GetAppData(String appName, int hours)
    {
        String query;

        if (hours < 0) {
            query = QueryBuilder.SelectAppDataSince(appName, null);
        } else {
            String startDate = GetRangeStartDate(hours);
            String endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date());
            query = QueryBuilder.SelectAppDataFromTimeRange(appName, startDate, endDate);
        }

        Cursor cursor = null;
        try {
            var db = this.getReadableDatabase();
            cursor = db.rawQuery(query, null);
            return GetAndPackData(cursor, appMapper);
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    //TODO make a method that returns a list of key value pairs of app (name) and  AVERAGE discharge, calc it by getting sum of all discharge / number of apps
    //TODO return list of all apps with app name, then activity can calculate discharge
    public DataPack<App> GetAppDataSince(String appName, String startTimestamp) {
        String query = QueryBuilder.SelectAppDataSince(appName, startTimestamp);

        Cursor cursor = null;
        try {
            var db = this.getReadableDatabase();
            cursor = db.rawQuery(query, null);
            return GetAndPackData(cursor, appMapper);
        } finally {
            if (cursor != null) cursor.close();
        }
    }



    //------------------------------------ Private methods ------------------------------------//

    /**
     * @param hours How many hours back to get data from, must be positive.
     * @return A DataPack that has a DataList of BatteryData objects.
     */
    public DataPack<BatteryData> GetBatteryData(int hours)
    {
        if (hours < 0) {
            return GetBatteryDataSince(null);
        }

        String StartDate = GetRangeStartDate(hours);
        String EndDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date());
        String query = QueryBuilder.SelectTableDataFromTimeRange(BatteryTable.TABLE_NAME, StartDate, EndDate);

        Cursor cursor = null;

        try {
            var db = this.getReadableDatabase();
            cursor = db.rawQuery(query, null);
            return GetAndPackData(cursor, batteryMapper);

        } catch (Exception e) {
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public DataPack<BatteryData> GetBatteryDataSince(String startTimestamp) {
        String query = QueryBuilder.SelectTableDataSince(BatteryTable.TABLE_NAME, startTimestamp);

        Cursor cursor = null;

        try {
            var db = this.getReadableDatabase();
            cursor = db.rawQuery(query, null);
            return GetAndPackData(cursor, batteryMapper);

        } catch (Exception e) {
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    /**
     * @param cursor Cursor object pointing at result table from db
     * @param mapper What mapper to use
     * @return A DataPack that has a DataList of DataObject objects.
     * @param <T> Type of DataObject which will always be subclassed of that type.
     */
    private <T extends DataObject> DataPack<T> GetAndPackData(Cursor cursor, Mapper<T> mapper)
    {
        DataPack<T> dataPack = new DataPack<>();

        if (cursor == null || cursor.getCount() == 0) {
            return dataPack;
        }

        if (cursor.moveToFirst()) {
            do {
                T data = mapper.fromCursor(cursor);
                dataPack.AddData(data);
            } while (cursor.moveToNext());
        }
        return dataPack;
    }

    private long findBootstrapAppRowId(SQLiteDatabase db, String appName) {
        if (appName == null || appName.isEmpty()) {
            return -1;
        }

        try (Cursor cursor = db.query(
                AppTable.TABLE_NAME,
                new String[]{AppTable.ID_COL},
                AppTable.APP_NAME_COL + " = ? AND " + AppTable.APP_DISCHARGE_COL + " = ?",
                new String[]{appName, "0"},
                null,
                null,
                AppTable.ID_COL + " ASC",
                "1"
        )) {
            if (cursor.moveToFirst()) {
                return cursor.getLong(cursor.getColumnIndexOrThrow(AppTable.ID_COL));
            }
        }

        return -1;
    }


    //------------------------------------ Default stuff ------------------------------------//

    /**
     * Converts an amount of hours to a date string
     * @param hours how many hours back to go?
     * @return A date string formatted as YYYY-MM-DD HH:MM:SS
     */
    private String GetRangeStartDate(int hours)
    {
        long nanoSeconds = System.currentTimeMillis() - ((long) hours * 60 * 60 * 1000);
        Date date = new Date(nanoSeconds);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(date);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String query = QueryBuilder.CreateBatteryTable();
        db.execSQL(query);
        query = QueryBuilder.CreateAppTable();
        db.execSQL(query);
    }

    @Override
    public void close() {
        super.close();
    }

}
