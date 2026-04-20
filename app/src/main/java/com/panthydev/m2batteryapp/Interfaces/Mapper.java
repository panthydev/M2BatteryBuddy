package com.panthydev.m2batteryapp.Interfaces;


import android.database.Cursor;

public interface Mapper<T> {
    T fromCursor(Cursor c); // from DataBase to Object
    void ToDataBaseValues(T obj);
}
