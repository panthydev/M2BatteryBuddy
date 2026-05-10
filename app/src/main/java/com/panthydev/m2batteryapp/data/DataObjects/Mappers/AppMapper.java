package com.panthydev.m2batteryapp.data.DataObjects.Mappers;

import android.content.ContentValues;
import android.database.Cursor;

import com.panthydev.m2batteryapp.DataBase.AppTable;
import com.panthydev.m2batteryapp.Interfaces.Mapper;
import com.panthydev.m2batteryapp.data.DataObjects.App;

public class AppMapper implements Mapper<App>{

    @Override
    public App fromCursor(Cursor c) {
        int index = c.getColumnIndexOrThrow(AppTable.APP_NAME_COL);
        String appName = c.getString(index);

        index = c.getColumnIndexOrThrow(AppTable.APP_CATEGORY_COL);
        int appCategory = c.getInt(index);

        index = c.getColumnIndexOrThrow(AppTable.APP_DISCHARGE_COL);
        int appDischarge = c.getInt(index);

        return new App(appName, appCategory, appDischarge);
    }

    @Override
    public ContentValues ToContentValues(App obj) {
        ContentValues values = new ContentValues();
        values.put(AppTable.APP_NAME_COL, obj.getAppName());
        values.put(AppTable.APP_CATEGORY_COL, obj.getAppCategory());
        values.put(AppTable.APP_DISCHARGE_COL, obj.getAppDischarge());
        return values;
    }
}
