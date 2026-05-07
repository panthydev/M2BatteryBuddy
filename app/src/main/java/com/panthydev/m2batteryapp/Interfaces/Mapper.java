package com.panthydev.m2batteryapp.Interfaces;


import android.content.ContentValues;
import android.database.Cursor;

import com.panthydev.m2batteryapp.data.DataObjects.DataObject;

/**
 *
 * @param <T> Type of DataObject which will always be subclassed of that type.
 * Mapper *maps* to and from DB
 */
public interface Mapper<T extends DataObject> {

    /**
     *
     * @param c Cursor object pointing at result table from db
     * @return Object of type T
     */
    T fromCursor(Cursor c); // from DataBase to Object

    /**
     *
     * @param obj Object (of type T) we want to convert
     * @return Object represented as a ContentValues object
     */
    ContentValues ToContentValues(T obj);
}
