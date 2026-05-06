package com.panthydev.m2batteryapp.Interfaces;


import android.content.ContentValues;
import android.database.Cursor;

public interface Mapper<T> {
    T fromCursor(Cursor c); // from DataBase to Object
    ContentValues ToDataBaseValues(T obj);
}
