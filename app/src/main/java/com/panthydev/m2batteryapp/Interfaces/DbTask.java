package com.panthydev.m2batteryapp.Interfaces;

import android.database.sqlite.SQLiteDatabase;

public interface DbTask<T>
{
    T run(SQLiteDatabase db);
}
