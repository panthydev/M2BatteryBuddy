package com.panthydev.m2batteryapp.Interfaces;

public interface Callback<T>
{
    /**
     *good luck.
     * @param Result The result of the DB search, T is of type DataPack<T> where T (inner) is a subclass of DataObject.
     */
    void OnResult(T Result);
}
