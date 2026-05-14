package com.panthydev.m2batteryapp.data.DataObjects.Mappers;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.panthydev.m2batteryapp.DataBase.AppTable;
import com.panthydev.m2batteryapp.Interfaces.Mapper;
import com.panthydev.m2batteryapp.data.DataObjects.App;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AppMapper implements Mapper<App>{

    @Override
    public App fromCursor(Cursor c) {
        int index = c.getColumnIndexOrThrow(AppTable.APP_NAME_COL);
        String appName = c.getString(index);

        index = c.getColumnIndexOrThrow(AppTable.APP_CATEGORY_COL);
        int appCategory = c.getInt(index);

        index = c.getColumnIndexOrThrow(AppTable.APP_DISCHARGE_COL);
        int appDischarge = c.getInt(index);

        index = c.getColumnIndexOrThrow(AppTable.BACKGROUND_PROCESS_COUNT_COL);
        int backgroundProcessCount = c.getInt(index);

        index = c.getColumnIndexOrThrow(AppTable.TIMESTAMP_COL);
        String stringTimestamp = c.getString(index);

        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).parse(stringTimestamp);
        } catch (Exception e) {
            Log.e("AppMapper", "Failed to parse timestamp: " + stringTimestamp, e);
        }
        App app = new App(appName, appCategory, appDischarge, backgroundProcessCount);
        app.Timestamp = date;

        return app;
    }

    @Override
    public ContentValues ToContentValues(App obj) {
        ContentValues values = new ContentValues();
        values.put(AppTable.APP_NAME_COL, obj.getAppName());
        values.put(AppTable.APP_CATEGORY_COL, obj.getAppCategory());
        values.put(AppTable.APP_DISCHARGE_COL, obj.getAppDischarge());
        values.put(AppTable.BACKGROUND_PROCESS_COUNT_COL, obj.getBackgroundProcessCount());

        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(obj.Timestamp);
        values.put(AppTable.TIMESTAMP_COL, date);

        return values;
    }
}
