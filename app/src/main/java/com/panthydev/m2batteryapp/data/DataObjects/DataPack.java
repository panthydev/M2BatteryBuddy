package com.panthydev.m2batteryapp.data.DataObjects;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * <pre>Container used to transport a list of dataObjects
 * Contains "dataList" which is the thing you probably want
 * </pre>
 * @param <T> T should be a subclass of DataObject
 */
public class DataPack<T extends DataObject>{
    public ArrayList<T> dataList;
    public DataPack(){
        dataList = new ArrayList<T>();
    }

    /**
     * <pre>Adds data to the dataList. T Must be a subclass of DataObject
     * @param data DataObject to add to the dataList
     */
    public void AddData(T data){
        dataList.add(data);
    }

    public void PrepareForTransport(){
        dataList.sort(new Comparator<T>(){
            @Override
            public int compare(T o1, T o2) {
                return o1.Timestamp.compareTo(o2.Timestamp);
            }
        });
    }



}
